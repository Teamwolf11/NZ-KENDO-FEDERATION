BEGIN;
CREATE SCHEMA IF NOT EXISTS email;

DROP TRIGGER IF EXISTS current_grade ON public.member_grading;
DROP Table IF EXISTS email.grading_vault;

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
	
GRANT TRIGGER ON ALL TABLES IN SCHEMA email TO javaapp;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA email TO javaapp;
GRANT USAGE ON SCHEMA email TO javaapp;	
	
COMMIT;