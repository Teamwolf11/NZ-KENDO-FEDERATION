BEGIN;

DROP TABLE IF EXISTS public.club_role;
DROP TABLE IF EXISTS public.event_line;
DROP TABLE IF EXISTS public.member_grading;
DROP TABLE IF EXISTS public.event;
DROP TABLE IF EXISTS public.club;
DROP TABLE IF EXISTS public.grading;
DROP TABLE IF EXISTS public.martial_arts;
DROP TABLE IF EXISTS public.member;
DROP TABLE IF EXISTS public.app_role;

CREATE TABLE IF NOT EXISTS public.club
(
    club_id SERIAL NOT NULL,
	mem_num integer,
    name character varying NOT NULL,
    location character varying,
	email character varying,
	phone character varying,
    PRIMARY KEY (club_id)
);

CREATE TABLE IF NOT EXISTS public.club_role
(
    member_id integer NOT NULL,
    club_id integer NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (member_id, club_id)
);

CREATE TABLE IF NOT EXISTS public.event
(
    event_id SERIAL NOT NULL UNIQUE,
    name character varying NOT NULL,
	start_date timestamp without time zone,
    club_id integer NOT NULL,
    venue character varying,
	status character NOT NULL,
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
	date_received timestamp without time zone NOT NULL,
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
    PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS public.grading
	(
	grading_id SERIAL NOT NULL,
	martial_art_id integer NOT NULL,
	time_in_grade timestamp without time zone,
	name character varying NOT NULL,
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

INSERT INTO app_role (name) VALUES ('Admin');
INSERT INTO app_role (name) VALUES ('Club Leader');
INSERT INTO app_role (name) VALUES ('General Member');

--CREATE USER javaapp WITH PASSWORD 'D4h/XW57%sw31';
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO javaapp;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO javaapp;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO javaapp;

COMMIT;