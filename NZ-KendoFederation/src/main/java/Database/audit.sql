BEGIN;

CREATE SCHEMA IF NOT EXISTS audit;


DROP TRIGGER IF EXISTS club_role_audit ON public.club_role;
DROP TRIGGER IF EXISTS event_line_audit ON public.event_line;
DROP TRIGGER IF EXISTS member_grading_audit ON public.member_grading;
DROP TRIGGER IF EXISTS event_audit ON public.event;
DROP TRIGGER IF EXISTS club_audit ON public.club;
DROP TRIGGER IF EXISTS grading_audit ON public.grading;
DROP TRIGGER IF EXISTS martial_arts_audit ON public.martial_arts;
DROP TRIGGER IF EXISTS member_audit ON public.member;
DROP TRIGGER IF EXISTS app_role_audit ON public.app_role;

DROP TABLE IF EXISTS audit.club_role_audit;
DROP TABLE IF EXISTS audit.event_line_audit;
DROP TABLE IF EXISTS audit.member_grading_audit;
DROP TABLE IF EXISTS audit.event_audit;
DROP TABLE IF EXISTS audit.club_audit;
DROP TABLE IF EXISTS audit.grading_audit;
DROP TABLE IF EXISTS audit.martial_arts_audit;
DROP TABLE IF EXISTS audit.member_audit;
DROP TABLE IF EXISTS audit.app_role_audit;


CREATE TABLE IF NOT EXISTS audit.member_audit
(
	operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
    member_id SERIAL NOT NULL,
    nzkf_membership_id character varying NOT NULL,
    app_role_id integer NOT NULL,
    password character varying NOT NULL,
    email character varying,
    date_of_birth timestamp without time zone,
    join_date timestamp without time zone,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    middle_name character varying,
    sex character varying,
    ethnicity character varying,
	phone_num character varying,
    PRIMARY KEY (member_id, stamp, operation)
);

CREATE OR REPLACE FUNCTION process_member_audit() RETURNS TRIGGER AS $member_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO audit.member_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.member_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.member_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$member_audit$ LANGUAGE plpgsql;

CREATE TRIGGER member_audit
AFTER INSERT OR UPDATE OR DELETE ON public.member
    FOR EACH ROW EXECUTE PROCEDURE process_member_audit();


CREATE TABLE IF NOT EXISTS audit.club_audit
(
	operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
    club_id SERIAL NOT NULL,
	mem_num integer,
    name character varying NOT NULL,
    location character varying,
	email character varying,
	phone character varying,
    PRIMARY KEY (club_id, stamp)
);

CREATE OR REPLACE FUNCTION process_club_audit() RETURNS TRIGGER AS $club_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO club_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO club_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO club_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$club_audit$ LANGUAGE plpgsql;

CREATE TRIGGER club_audit
AFTER INSERT OR UPDATE OR DELETE ON club
    FOR EACH ROW EXECUTE PROCEDURE process_club_audit();

CREATE TABLE IF NOT EXISTS audit.club_role_audit
(
	operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
    member_id integer NOT NULL,
    club_id integer NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (member_id, club_id, stamp)
);

CREATE OR REPLACE FUNCTION process_club_role_audit() RETURNS TRIGGER AS $club_role_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO club_role_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO club_role_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO club_role_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$club_role_audit$ LANGUAGE plpgsql;

CREATE TRIGGER club_role_audit
AFTER INSERT OR UPDATE OR DELETE ON club_role
    FOR EACH ROW EXECUTE PROCEDURE process_club_role_audit();

CREATE TABLE IF NOT EXISTS audit.event_audit
(
    operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
	event_id SERIAL NOT NULL UNIQUE,
    name character varying NOT NULL,
	start_date timestamp without time zone,
    club_id integer NOT NULL,
    venue character varying,
	status character NOT NULL,
    grading_id integer NOT NULL, --highest grade available.
    PRIMARY KEY (event_id,stamp)
);

CREATE OR REPLACE FUNCTION process_event_audit() RETURNS TRIGGER AS $event_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO event_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO event_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO event_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$event_audit$ LANGUAGE plpgsql;

CREATE TRIGGER event_audit
AFTER INSERT OR UPDATE OR DELETE ON event
    FOR EACH ROW EXECUTE PROCEDURE process_event_audit();

CREATE TABLE IF NOT EXISTS audit.event_line_audit
(
    operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
	event_id integer NOT NULL,
    member_id integer NOT NULL,
    PRIMARY KEY (event_id, member_id,stamp)
);

CREATE OR REPLACE FUNCTION process_event_line_audit() RETURNS TRIGGER AS $event_line_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO event_line_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO event_line_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO event_line_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$event_line_audit$ LANGUAGE plpgsql;

CREATE TRIGGER event_line_audit
AFTER INSERT OR UPDATE OR DELETE ON event_line
    FOR EACH ROW EXECUTE PROCEDURE process_event_line_audit();

CREATE TABLE IF NOT EXISTS audit.martial_arts_audit
(
    operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
	martial_art_id SERIAL NOT NULL,
    name character varying NOT NULL,
    "desc" character varying,
    PRIMARY KEY (martial_art_id,stamp)
);

CREATE OR REPLACE FUNCTION process_martial_arts_audit() RETURNS TRIGGER AS $martial_arts_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO martial_arts_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO martial_arts_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO martial_arts_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$martial_arts_audit$ LANGUAGE plpgsql;

CREATE TRIGGER martial_arts_audit
AFTER INSERT OR UPDATE OR DELETE ON martial_arts
    FOR EACH ROW EXECUTE PROCEDURE process_martial_arts_audit();

CREATE TABLE IF NOT EXISTS audit.member_grading
(
	operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
	club_id integer NOT NULL,
    member_id integer NOT NULL,
    grading_id integer NOT NULL,
	date_received timestamp without time zone NOT NULL,
    PRIMARY KEY (member_id, club_id, grading_id,stamp)
);

CREATE OR REPLACE FUNCTION process_member_grading_audit() RETURNS TRIGGER AS $member_grading_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO member_grading_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO member_grading_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO member_grading_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$member_grading_audit$ LANGUAGE plpgsql;

CREATE TRIGGER member_grading_audit
AFTER INSERT OR UPDATE OR DELETE ON member_grading
    FOR EACH ROW EXECUTE PROCEDURE process_member_grading_audit();

CREATE TABLE IF NOT EXISTS audit.app_role_audit
(
	operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
    app_role_id SERIAL NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (app_role_id,stamp)
);

CREATE OR REPLACE FUNCTION process_app_role_audit() RETURNS TRIGGER AS $app_role_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO app_role_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO app_role_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO app_role_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$app_role_audit$ LANGUAGE plpgsql;

CREATE TRIGGER app_role_audit
AFTER INSERT OR UPDATE OR DELETE ON app_role
    FOR EACH ROW EXECUTE PROCEDURE process_app_role_audit();

CREATE TABLE IF NOT EXISTS audit.grading_audit
	(
	operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
	grading_id SERIAL NOT NULL,
	martial_art_id integer NOT NULL,
	time_in_grade INTERVAL,
	name character varying NOT NULL,
	primary key (grading_id,stamp)
);

CREATE OR REPLACE FUNCTION process_grading_audit() RETURNS TRIGGER AS $grading_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO grading_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO grading_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO grading_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$grading_audit$ LANGUAGE plpgsql;

CREATE TRIGGER grading_audit
AFTER INSERT OR UPDATE OR DELETE ON grading
    FOR EACH ROW EXECUTE PROCEDURE process_grading_audit();


GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO javaapp;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA audit TO javaapp;

COMMIT;