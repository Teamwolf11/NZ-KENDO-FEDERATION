BEGIN;

CREATE SCHEMA IF NOT EXISTS audit;
CREATE SCHEMA IF NOT EXISTS email;

REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM javaapp;
REVOKE ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public FROM javaapp;
REVOKE ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public FROM javaapp;
REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA audit FROM javaapp;
REVOKE ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA audit FROM javaapp;
REVOKE ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA audit FROM javaapp;
REVOKE ALL PRIVILEGES ON SCHEMA audit FROM javaapp;
REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA email FROM javaapp;
REVOKE ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA email FROM javaapp;
REVOKE ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA email FROM javaapp;
REVOKE ALL PRIVILEGES ON SCHEMA email FROM javaapp;
DROP USER javaapp;

DROP TRIGGER IF EXISTS current_grade ON public.member_grading;
DROP TRIGGER IF EXISTS club_role_audit ON public.club_role;
DROP TRIGGER IF EXISTS event_line_audit ON public.event_line;
DROP TRIGGER IF EXISTS member_grading_audit ON public.member_grading;
DROP TRIGGER IF EXISTS event_audit ON public.event;
DROP TRIGGER IF EXISTS club_audit ON public.club;
DROP TRIGGER IF EXISTS grading_audit ON public.grading;
DROP TRIGGER IF EXISTS martial_arts_audit ON public.martial_arts;
DROP TRIGGER IF EXISTS member_audit ON public.member;
DROP TRIGGER IF EXISTS app_role_audit ON public.app_role;
DROP TRIGGER IF EXISTS mem_grade_next_date_insert ON public.member_grading;
DROP TRIGGER IF EXISTS mem_grade_next_date ON public.member_grading;
DROP TRIGGER IF EXISTS mem_grade_current_grade ON public.member_grading;
 
DROP VIEW IF EXISTS vw_member_grading;
DROP TABLE IF EXISTS audit.club_role_audit;
DROP TABLE IF EXISTS audit.grading_panel_audit;
DROP TABLE IF EXISTS audit.event_line_audit;
DROP TABLE IF EXISTS audit.member_grading_audit;
DROP TABLE IF EXISTS audit.event_audit;
DROP TABLE IF EXISTS audit.club_audit;
DROP TABLE IF EXISTS audit.grading_audit;
DROP TABLE IF EXISTS audit.martial_arts_audit;
DROP TABLE IF EXISTS audit.member_audit;
DROP TABLE IF EXISTS audit.app_role_audit;
DROP Table IF EXISTS email.grading_vault;
DROP TABLE IF EXISTS public.expiry_email_store;
DROP TABLE IF EXISTS public.grading_panel;
DROP TABLE IF EXISTS public.club_role;
DROP TABLE IF EXISTS public.event_line;
DROP TABLE IF EXISTS public.member_grading;
DROP TABLE IF EXISTS public.event;
DROP TABLE IF EXISTS public.club;
DROP TABLE IF EXISTS public.grading;
DROP TABLE IF EXISTS public.member_martial_arts;
DROP TABLE IF EXISTS public.martial_arts;
DROP TABLE IF EXISTS public.member;
DROP TABLE IF EXISTS public.user;
DROP TABLE IF EXISTS public.role_path;
DROP TABLE IF EXISTS public.app_role;

CREATE TABLE IF NOT EXISTS public.expiry_email_store
(
	expire_row SERIAL NOT NULL,
	member_id integer NOT NULL,
	nzkf_membership_renew_date character varying NOT NULL,
	status character varying NOT NULL,
	primary key(member_id, nzkf_membership_renew_date)
);

CREATE TABLE IF NOT EXISTS public.grading_panel
(
	gp_row_num SERIAL NOT NULL,
	event_id integer NOT NULL,
	grading_member_name character varying NOT NULL,
	grading_role character varying NOT NULL,
	PRIMARY KEY (event_id, grading_member_name, grading_role)
);

CREATE TABLE IF NOT EXISTS public.role_path
(
	row_id SERIAL NOT NULL,
	app_role_id integer NOT NULL,
	path character varying NOT NULL,
	method character varying NOT NULL,
	PRIMARY KEY (app_role_id,path)
);


CREATE TABLE IF NOT EXISTS public.club
(
    club_id SERIAL NOT NULL,
    mem_num integer,
    name character varying NOT NULL UNIQUE,
    location character varying,
    email character varying,
    phone character varying,
    description character varying,                     -- added description 
    PRIMARY KEY (club_id)
);
 
CREATE TABLE IF NOT EXISTS public.club_role
(
    member_id integer NOT NULL,
    club_id integer NOT NULL,
    role_name character varying NOT NULL,               -- added club_role   general, leader, etc...                             
    PRIMARY KEY (member_id, club_id)
);
 
CREATE TABLE IF NOT EXISTS public.event
(
    event_id SERIAL NOT NULL UNIQUE,
    name character varying NOT NULL,
    club_id integer NOT NULL,
    venue character varying,
    status character varying NOT NULL DEFAULT 'on going',
    description character varying,
    start_date_time character varying NOT NULL,
    end_date_time character varying,
    grading_id integer NOT NULL, --highest grade available.
    PRIMARY KEY (event_id)
);
 
CREATE TABLE IF NOT EXISTS public.event_line
(
    event_id integer NOT NULL,
    member_id integer NOT NULL,
    PRIMARY KEY (event_id, member_id)
);
 
CREATE TABLE IF NOT EXISTS public.martial_arts
(
    martial_art_id SERIAL NOT NULL,
    name character varying NOT NULL,
    "desc" character varying,
    PRIMARY KEY (martial_art_id)
);
 
CREATE TABLE IF NOT EXISTS public.member_grading
(
    club_id integer NOT NULL,
    member_id integer NOT NULL,
    grading_id integer NOT NULL,
    date_received character varying NOT NULL,  --update date
    date_next_grade_available character varying,
    event_id character varying,
    PRIMARY KEY (member_id, club_id, grading_id)
);
 
CREATE TABLE IF NOT EXISTS public.app_role
(
    app_role_id SERIAL NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (app_role_id)
);
 
CREATE TABLE IF NOT EXISTS public.member
(
    member_id SERIAL NOT NULL,
    nzkf_membership_id SERIAL NOT NULL,
    nzkf_membership_renew_date character varying DEFAULT TO_CHAR(NOW() + interval '1 year','DD-MM-YYYY'),
    app_role_id integer DEFAULT 3,
    password character varying NOT NULL,
    email character varying UNIQUE,
    date_of_birth character varying,
    join_date character varying,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    middle_name character varying,
    sex character varying,
    ethnicity character varying,
    phone_num character varying,
    PRIMARY KEY (member_id)
);
 
CREATE TABLE IF NOT EXISTS public.grading
	(
	grading_id SERIAL NOT NULL,
	martial_art_id integer NOT NULL,
	time_in_grade INTERVAL,
	name character varying NOT NULL,
	grade_level int NOT NULL,	
	primary key (grading_id)
);
 
ALTER TABLE public.club_role
    ADD FOREIGN KEY (member_id)
    REFERENCES public.member (member_id)
    NOT VALID;
	
ALTER TABLE public.club_role
    ADD FOREIGN KEY (club_id)
    REFERENCES public.club (club_id)
    NOT VALID;
 
ALTER TABLE public.event
    ADD FOREIGN KEY (club_id)
    REFERENCES public.club (club_id)
    NOT VALID;
 
ALTER TABLE public.event_line
    ADD FOREIGN KEY (member_id)
    REFERENCES public.member (member_id)
    NOT VALID;
	
ALTER TABLE public.expiry_email_store
    ADD FOREIGN KEY (member_id)
    REFERENCES public.member (member_id)
    NOT VALID;
 
ALTER TABLE public.event_line
    ADD FOREIGN KEY (event_id)
    REFERENCES public.event (event_id)
    NOT VALID;
 
ALTER TABLE public.member_grading
    ADD FOREIGN KEY (member_id)
    REFERENCES public.member (member_id)
    NOT VALID;
 
ALTER TABLE public.member_grading
    ADD FOREIGN KEY (grading_id)
    REFERENCES public.grading (grading_id)
    NOT VALID;
 
ALTER TABLE public.member
    ADD FOREIGN KEY (app_role_id)
    REFERENCES public.app_role (app_role_id)
    NOT VALID;
	
ALTER TABLE public.grading
   	ADD FOREIGN KEY (martial_art_id)
    REFERENCES public.martial_arts (martial_art_id)
    NOT VALID;
	
ALTER TABLE public.role_path
   	ADD FOREIGN KEY (app_role_id)
    REFERENCES public.app_role (app_role_id)
    NOT VALID;
	
ALTER TABLE public.grading_panel
	ADD FOREIGN KEY (event_id)
	REFERENCES public.event (event_id)
	NOT VALID;
	
ALTER TABLE public.member ALTER COLUMN join_date SET DEFAULT TO_CHAR(now(), 'DD/MM/YYYY');
		
CREATE OR REPLACE FUNCTION process_member_grading_next_date() RETURNS TRIGGER AS $mem_grade_next_date$
    BEGIN 
			NEW.date_next_grade_available := TO_CHAR(to_date(NEW.date_received, 'DD/MM/YYYY') + g.time_in_grade,'DD/MM/YYYY') 
			FROM public.grading g
			WHERE NEW.grading_id = g.grading_id;
            RETURN NEW;
    END;
$mem_grade_next_date$ LANGUAGE plpgsql;
 
CREATE TRIGGER mem_grade_next_date
BEFORE INSERT OR UPDATE ON public.member_grading
    FOR EACH ROW EXECUTE PROCEDURE process_member_grading_next_date();
	
CREATE TABLE IF NOT EXISTS audit.grading_panel_audit
(
	operation char(1) NOT NULL,
    stamp timestamp NOT NULL,
	gp_row_num SERIAL NOT NULL,
	event_id integer NOT NULL,
	grading_member_name character varying NOT NULL,
	grading_role character varying NOT NULL,
	PRIMARY KEY (event_id, grading_member_name, grading_role, operation, stamp)
);

CREATE OR REPLACE FUNCTION process_grading_panel_audit() RETURNS TRIGGER AS $grading_panel_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO audit.grading_panel_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.grading_panel_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.grading_panel_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$grading_panel_audit$ LANGUAGE plpgsql;

CREATE TRIGGER grading_panel_audit
AFTER INSERT OR UPDATE OR DELETE ON public.grading_panel
    FOR EACH ROW EXECUTE PROCEDURE process_grading_panel_audit();


CREATE TABLE IF NOT EXISTS audit.member_audit
(
    operation char(1) NOT NULL,
    stamp timestamp NOT NULL,
 	member_id SERIAL NOT NULL,
    nzkf_membership_id SERIAL NOT NULL,
    nzkf_membership_renew_date character varying DEFAULT TO_CHAR(NOW() + interval '1 year','DD-MM-YYYY'),
    app_role_id integer DEFAULT 3,
    password character varying NOT NULL,
    email character varying,
    date_of_birth character varying,
    join_date character varying,
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
    description character varying,
    PRIMARY KEY (club_id, stamp)
);

CREATE OR REPLACE FUNCTION process_club_audit() RETURNS TRIGGER AS $club_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO audit.club_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.club_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.club_audit SELECT 'I', now(), NEW.*;
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
    role_name character varying NOT NULL, 
    PRIMARY KEY (member_id, club_id, stamp)
);

CREATE OR REPLACE FUNCTION process_club_role_audit() RETURNS TRIGGER AS $club_role_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO audit.club_role_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.club_role_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.club_role_audit SELECT 'I', now(), NEW.*;
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
	event_id SERIAL NOT NULL,
    name character varying NOT NULL,
    club_id integer NOT NULL,
    venue character varying,
    status character varying NOT NULL DEFAULT 'on going',
    description character varying,
    start_date_time character varying NOT NULL,
    end_date_time character varying,
    grading_id integer NOT NULL, --highest grade available.
    PRIMARY KEY (event_id,stamp)
);

CREATE OR REPLACE FUNCTION process_event_audit() RETURNS TRIGGER AS $event_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO audit.event_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.event_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.event_audit SELECT 'I', now(), NEW.*;
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
            INSERT INTO audit.event_line_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.event_line_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.event_line_audit SELECT 'I', now(), NEW.*;
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
            INSERT INTO audit.martial_arts_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.martial_arts_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.martial_arts_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$martial_arts_audit$ LANGUAGE plpgsql;

CREATE TRIGGER martial_arts_audit
AFTER INSERT OR UPDATE OR DELETE ON martial_arts
    FOR EACH ROW EXECUTE PROCEDURE process_martial_arts_audit();

CREATE TABLE IF NOT EXISTS audit.member_grading_audit
(
	operation char(1) NOT NULL,
	stamp timestamp NOT NULL,
	club_id integer NOT NULL,
    member_id integer NOT NULL,
    grading_id integer NOT NULL,
    date_received character varying NOT NULL,  --update date
    date_next_grade_available character varying,
    event_id character varying,
    PRIMARY KEY (member_id, club_id, grading_id,stamp)
);

CREATE OR REPLACE FUNCTION process_member_grading_audit() RETURNS TRIGGER AS $member_grading_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO audit.member_grading_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.member_grading_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.member_grading_audit SELECT 'I', now(), NEW.*;
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
            INSERT INTO audit.app_role_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.app_role_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.app_role_audit SELECT 'I', now(), NEW.*;
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
	grade_level int NOT NULL,
	primary key (grading_id,stamp)
);

CREATE OR REPLACE FUNCTION process_grading_audit() RETURNS TRIGGER AS $grading_audit$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO audit.grading_audit SELECT 'D', now(), OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO audit.grading_audit SELECT 'U', now(), NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO audit.grading_audit SELECT 'I', now(), NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
$grading_audit$ LANGUAGE plpgsql;

CREATE TRIGGER grading_audit
AFTER INSERT OR UPDATE OR DELETE ON grading
    FOR EACH ROW EXECUTE PROCEDURE process_grading_audit();
	
CREATE TABLE IF NOT EXISTS email.grading_vault
(
    club_id integer NOT NULL,
    member_id integer NOT NULL,
    grading_id integer NOT NULL,
    date_received character varying NOT NULL,  --update date
    date_next_grade_available character varying,
    event_id character varying,
	current_grade character,
    PRIMARY KEY (member_id, club_id, grading_id)
);

CREATE OR REPLACE FUNCTION process_current_grade() RETURNS TRIGGER AS $current_grade$
    BEGIN 
		 	IF (TG_OP = 'DELETE') THEN
				DELETE FROM email.grading_vault WHERE grading_vault.club_id = OLD.club_id AND grading_vault.member_id = OLD.member_id AND grading_vault.grading_id = OLD.grading_id;
				
			UPDATE email.grading_vault
			SET current_grade = 'Y' 
			FROM (SELECT mg.club_id, mg.grading_id, mg.member_id FROM member_grading mg JOIN grading g ON mg.Grading_id = g.grading_id 
	  			WHERE g.Martial_art_id = (SELECT martial_art_id FROM grading WHERE grading_id = OLD.grading_id) AND mg.member_id = OLD.member_id order by g.grade_level desc limit 1) AS tbl
			WHERE tbl.club_id = grading_vault.club_id AND tbl.member_id = grading_vault.member_id AND tbl.grading_id = grading_vault.grading_id;
			
			UPDATE email.grading_vault
			SET current_grade = 'N' 
			FROM (SELECT mg.club_id, mg.grading_id, mg.member_id FROM member_grading mg JOIN grading g ON mg.Grading_id = g.grading_id 
	  			WHERE g.Martial_art_id = (SELECT martial_art_id FROM grading WHERE grading_id = OLD.grading_id) AND mg.member_id = OLD.member_id order by g.grade_level desc OFFSET 1 ROWS) AS tbl
            WHERE tbl.club_id = grading_vault.club_id AND tbl.member_id = grading_vault.member_id AND tbl.grading_id = grading_vault.grading_id;
			RETURN OLD;
				
			ELSIF (TG_OP = 'UPDATE') THEN
				DELETE FROM email.grading_vault WHERE grading_vault.club_id = OLD.club_id AND grading_vault.member_id = OLD.member_id AND grading_vault.grading_id = OLD.grading_id;
				INSERT INTO email.grading_vault SELECT NEW.*;
				--RETURN NEW;
			END if;
			IF (TG_OP = 'INSERT') THEN
				INSERT INTO email.grading_vault SELECT NEW.*;
				--RETURN NEW;
			END if;
			
			UPDATE email.grading_vault
			SET current_grade = 'Y' 
			FROM (SELECT mg.club_id, mg.grading_id, mg.member_id FROM member_grading mg JOIN grading g ON mg.Grading_id = g.grading_id 
	  			WHERE g.Martial_art_id = (SELECT martial_art_id FROM grading WHERE grading_id = NEW.grading_id) AND mg.member_id = NEW.member_id order by g.grade_level desc limit 1) AS tbl
			WHERE tbl.club_id = grading_vault.club_id AND tbl.member_id = grading_vault.member_id AND tbl.grading_id = grading_vault.grading_id;
			
			UPDATE email.grading_vault
			SET current_grade = 'N' 
			FROM (SELECT mg.club_id, mg.grading_id, mg.member_id FROM member_grading mg JOIN grading g ON mg.Grading_id = g.grading_id 
	  			WHERE g.Martial_art_id = (SELECT martial_art_id FROM grading WHERE grading_id = NEW.grading_id) AND mg.member_id = NEW.member_id order by g.grade_level desc OFFSET 1 ROWS) AS tbl
            WHERE tbl.club_id = grading_vault.club_id AND tbl.member_id = grading_vault.member_id AND tbl.grading_id = grading_vault.grading_id;
			RETURN NEW;
			
    END;
$current_grade$ LANGUAGE plpgsql;

CREATE TRIGGER current_grade
AFTER INSERT OR UPDATE OR DELETE ON public.member_grading
    FOR EACH ROW EXECUTE PROCEDURE process_current_grade();
 
INSERT INTO app_role (name) VALUES ('Admin');
INSERT INTO app_role (name) VALUES ('Club Leader');
INSERT INTO app_role (name) VALUES ('General Member');
 
INSERT INTO martial_arts (name,martial_art_id) VALUES ('Kendo',1);
INSERT INTO martial_arts (name,martial_art_id) VALUES ('Iaido',2);
INSERT INTO martial_arts (name,martial_art_id) VALUES ('Jodo',3);
INSERT INTO martial_arts (name,martial_art_id) VALUES ('Naginata',4);
 
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (1,1,NULL, '7 Kyu',1);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (2,1,NULL, '6 Kyu',2);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (3,1,NULL, '5 Kyu',3);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (4,1,NULL, '4 Kyu',4);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (5,1,NULL, '3 Kyu',5);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (6,1,NULL, '2 Kyu',6);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (7,1,INTERVAL '3 months', '1 Kyu',7);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (8,1,INTERVAL '1 year', '1 Dan',8);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (9,1,INTERVAL '2 year', '2 Dan',9);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (10,1,INTERVAL '3 year', '3 Dan',10);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (11,1,INTERVAL '4 year', '4 Dan',11);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (12,1,INTERVAL '5 year', '5 Dan',12);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (13,1,INTERVAL '6 year', '6 Dan',13);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (14,1,INTERVAL '10 year', '7 Dan',14);		
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (15,1,NULL, '8 Dan',15);
 
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (16,2,NULL, '7 Kyu',1);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (17,2,NULL, '6 Kyu',2);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (18,2,NULL, '5 Kyu',3);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (19,2,NULL, '4 Kyu',4);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (20,2,NULL, '3 Kyu',5);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (21,2,NULL, '2 Kyu',6);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (22,2,INTERVAL '3 months', '1 Kyu',7);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (23,2,INTERVAL '1 year', '1 Dan',8);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (24,2,INTERVAL '2 year', '2 Dan',9);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (25,2,INTERVAL '3 year', '3 Dan',10);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (26,2,INTERVAL '4 year', '4 Dan',11);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (27,2,INTERVAL '5 year', '5 Dan',12);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (28,2,INTERVAL '6 year', '6 Dan',13);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (29,2,INTERVAL '10 year', '7 Dan',14);		
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (30,2,NULL, '8 Dan',15);
 
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (31,3,NULL, '7 Kyu',1);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (32,3,NULL, '6 Kyu',2);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (33,3,NULL, '5 Kyu',3);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (34,3,NULL, '4 Kyu',4);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (35,3,NULL, '3 Kyu',5);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (36,3,NULL, '2 Kyu',6);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (37,3,INTERVAL '3 months', '1 Kyu',7);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (38,3,INTERVAL '1 year', '1 Dan',8);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (39,3,INTERVAL '2 year', '2 Dan',9);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (40,3,INTERVAL '3 year', '3 Dan',10);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (41,3,INTERVAL '4 year', '4 Dan',11);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (42,3,INTERVAL '5 year', '5 Dan',12);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (43,3,INTERVAL '6 year', '6 Dan',13);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (44,3,INTERVAL '10 year', '7 Dan',14);		
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (45,3,NULL, '8 Dan',15);
 
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (46,4,NULL, '7 Kyu',1);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (47,4,NULL, '6 Kyu',2);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (48,4,NULL, '5 Kyu',3);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (49,4,NULL, '4 Kyu',4);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (50,4,NULL, '3 Kyu',5);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (51,4,NULL, '2 Kyu',6);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (52,4,INTERVAL '3 months', '1 Kyu',7);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (53,4,INTERVAL '1 year', '1 Dan',8);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (54,4,INTERVAL '2 year', '2 Dan',9);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (55,4,INTERVAL '3 year', '3 Dan',10);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (56,4,INTERVAL '4 year', '4 Dan',11);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (57,4,INTERVAL '5 year', '5 Dan',12);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (58,4,INTERVAL '6 year', '6 Dan',13);
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (59,4,INTERVAL '10 year', '7 Dan',14);		
INSERT INTO grading (grading_id, martial_art_id, time_in_grade, name,grade_level) VALUES (60,4,NULL, '8 Dan',15);

ALTER SEQUENCE grading_grading_id_seq RESTART WITH 61;
ALTER SEQUENCE martial_arts_martial_art_id_seq RESTART WITH 5;
ALTER SEQUENCE member_nzkf_membership_id_seq RESTART WITH 10000;

/* example data */
INSERT INTO public.member (email, password, date_of_birth, nzkf_membership_renew_date, first_name, last_name, middle_name, sex, ethnicity, phone_num) VALUES ('login.test','login','08/09/2002','12-12-9999','FirstName1','MiddleName1','LastName1','M','NZ European','123456789');
INSERT INTO public.member (email, password, date_of_birth, nzkf_membership_renew_date, first_name, last_name, middle_name, sex, ethnicity, phone_num) VALUES ('example.email2','q2weadfe4','21/01/1997','12-12-9999','FirstName2','MiddleName2','LastName2','F','Maori','1312346243');
INSERT INTO public.member (email, password, date_of_birth, nzkf_membership_renew_date, first_name, last_name, middle_name, sex, ethnicity, phone_num) VALUES ('example.email3','Password123','07/12/1962','12-12-9999','FirstName3','MiddleName3','LastName3','M','NZ European','2374523412');
INSERT INTO public.member (email, password, date_of_birth, nzkf_membership_renew_date, first_name, last_name, middle_name, sex, ethnicity, phone_num) VALUES ('example.email4','1111111','08/09/1999','12-12-9999','FirstName4','MiddleName4','LastName4','F','Other','3456234575');
INSERT INTO public.member (email, password, date_of_birth, nzkf_membership_renew_date, first_name, last_name, middle_name, sex, ethnicity, phone_num) VALUES ('example.email5','dasIsGood','11/03/1995','12-12-9999','FirstName5','MiddleName5','LastName5','M','NZ European','21346467454');

INSERT INTO public.club (name, location, mem_num, email, phone, description) VALUES ('example1','Dunedin', 5,'example.club.email1','123456789','example club 1');     
INSERT INTO public.club (name, location, mem_num, email, phone, description) VALUES ('example2','Auckland', 2,'example.club.email2','123456789','example club 2'); 

INSERT INTO public.club_role (club_id, member_id, role_name) VALUES (1,1,'general member');
INSERT INTO public.club_role (club_id, member_id, role_name) VALUES (1,2,'club leader');
INSERT INTO public.club_role (club_id, member_id, role_name) VALUES (1,3,'deputy club leader');
INSERT INTO public.club_role (club_id, member_id, role_name) VALUES (1,4,'general member');
INSERT INTO public.club_role (club_id, member_id, role_name) VALUES (1,5,'general member');
INSERT INTO public.club_role (club_id, member_id, role_name) VALUES (2,1,'general member');
INSERT INTO public.club_role (club_id, member_id, role_name) VALUES (2,2,'general member');

INSERT INTO public.event (name, club_id, venue, description, start_date_time, end_date_time, grading_id, status) VALUES ('example event 1',1,'Dunedin Edgar Centre','example desc','13/10/2021 12:00:00','13/10/2021 15:00:00',11,'finished');
INSERT INTO public.event (name, club_id, venue, description, start_date_time, end_date_time, grading_id, status) VALUES ('example event 1',2,'Currently undetermined','example desc','13/11/2021 12:00:00','13/11/2021 15:00:00',4,'up coming');

INSERT INTO public.grading_panel (event_id, grading_member_name,grading_role) VALUES (1,'Head Grader Steve','Head');
INSERT INTO public.grading_panel (event_id, grading_member_name,grading_role) VALUES (1,'Barbara','second');
INSERT INTO public.grading_panel (event_id, grading_member_name,grading_role) VALUES (1,'Michael','second');
INSERT INTO public.grading_panel (event_id, grading_member_name,grading_role) VALUES (1,'John','Head');

INSERT INTO public.member_grading (grading_id, date_received, member_id,club_id, event_id) VALUES (3,'13/10/2021',1,1,1);
INSERT INTO public.member_grading (grading_id, date_received, member_id,club_id, event_id) VALUES (4,'13/10/2021',1,1,1);
INSERT INTO public.member_grading (grading_id, date_received, member_id,club_id, event_id) VALUES (5,'13/10/2021',1,1,1);
INSERT INTO public.member_grading (grading_id, date_received, member_id,club_id, event_id) VALUES (1,'13/10/2021',2,1,1);
INSERT INTO public.member_grading (grading_id, date_received, member_id,club_id, event_id) VALUES (3,'13/10/2021',3,1,1);
INSERT INTO public.member_grading (grading_id, date_received, member_id,club_id, event_id) VALUES (9,'13/10/2021',4,1,1);
INSERT INTO public.member_grading (grading_id, date_received, member_id,club_id, event_id) VALUES (11,'13/10/2021',5,1,1);

INSERT INTO public.event_line (event_id, member_id) VALUES (1,1);
INSERT INTO public.event_line (event_id, member_id) VALUES (1,2);
INSERT INTO public.event_line (event_id, member_id) VALUES (1,3);
  
INSERT INTO public.member (email, password, date_of_birth, nzkf_membership_renew_date, first_name, last_name, middle_name, app_role_id) VALUES ('admin','admin','12/12/9999','12-12-9999','Admin','Admin','Admin',2);


CREATE USER javaapp WITH PASSWORD 'D4h/XW57%sw31';
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO javaapp;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO javaapp;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO javaapp;
GRANT TRIGGER ON ALL TABLES IN SCHEMA audit TO javaapp;
GRANT INSERT ON ALL TABLES IN SCHEMA audit TO javaapp;
GRANT USAGE ON SCHEMA audit TO javaapp;
GRANT TRIGGER ON ALL TABLES IN SCHEMA email TO javaapp;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA email TO javaapp;
GRANT USAGE ON SCHEMA email TO javaapp;	

 
COMMIT;
