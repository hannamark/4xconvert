-- This script assumes that 'public' schema is used for the particular database in postgres.
-- Structure =
-- > database->schema->tables
-- > database->schema->trigger
-- > database->schema->functions
-- > database->schema->indexes
-- Refer the postgres documentation for further details.

-- CREATE SCHEMA <<schema_name>>;

  CREATE SEQUENCE CSM_APPLICATI_APPLICATION__SEQ;
  CREATE TABLE CSM_APPLICATION
  (
    APPLICATION_ID INTEGER default nextval('CSM_APPLICATI_APPLICATION__SEQ'), UNIQUE(APPLICATION_ID),
    APPLICATION_NAME character varying(255) NOT NULL,
    APPLICATION_DESCRIPTION character varying(255) NOT NULL,
    UPDATE_DATE date NOT NULL,
    DATABASE_URL character varying(100),
    DATABASE_USER_NAME character varying(100),
    DATABASE_PASSWORD character varying(100),
    DATABASE_DIALECT character varying(100),
    DATABASE_DRIVER character varying(100),
    DECLARATIVE_FLAG character varying(1) DEFAULT 0,
    ACTIVE_FLAG character varying(1) DEFAULT 0,
    PRIMARY KEY (APPLICATION_ID)
  )
  ;
  CREATE INDEX CSM_APPLICATION_APPLICATION_ID_IDX ON CSM_APPLICATION (APPLICATION_ID);


  CREATE SEQUENCE CSM_GROUP_GROUP_ID_SEQ;
  CREATE TABLE CSM_GROUP
  (
    GROUP_ID INTEGER default nextval('CSM_GROUP_GROUP_ID_SEQ'), UNIQUE(GROUP_ID),
    GROUP_NAME character varying(255) NOT NULL,
    GROUP_DESC character varying(255) NOT NULL,
    UPDATE_DATE date NOT NULL,
    APPLICATION_ID integer NOT NULL,
    CONSTRAINT PK_GROUP_ID PRIMARY KEY (GROUP_ID)
  )
  ;
  CREATE INDEX CSM_GROUP_GROUP_ID_IDX ON CSM_GROUP (GROUP_ID);


  CREATE SEQUENCE CSM_PRIVILEGE_PRIVILEGE_ID_SEQ ;
  CREATE TABLE CSM_PRIVILEGE (
    PRIVILEGE_ID INTEGER default nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ'), UNIQUE(PRIVILEGE_ID),
    PRIVILEGE_NAME character varying(100) NOT NULL,
    PRIVILEGE_DESCRIPTION character varying(200),
    UPDATE_DATE DATE NOT NULL,
    PRIMARY KEY(PRIVILEGE_ID)
  )
  ;
  CREATE INDEX CSM_PRIVILEGE_GROUP_ID_IDX ON CSM_PRIVILEGE (PRIVILEGE_ID);

  CREATE SEQUENCE CSM_PROTECTIO_PROTECTION_E_SEQ;
  CREATE TABLE CSM_PROTECTION_ELEMENT (
    PROTECTION_ELEMENT_ID INTEGER default nextval('CSM_PROTECTIO_PROTECTION_E_SEQ'), UNIQUE(PROTECTION_ELEMENT_ID),
    PROTECTION_ELEMENT_NAME character varying(100) NOT NULL,
    PROTECTION_ELEMENT_DESCRIPTION character varying(200),
    OBJECT_ID character varying(100) NOT NULL,
    ATTRIBUTE character varying(100) ,
    PROTECTION_ELEMENT_TYPE character varying(100),
    APPLICATION_ID integer NOT NULL,
    UPDATE_DATE DATE NOT NULL,
	ATTRIBUTE_VALUE character varying(100),
    PRIMARY KEY(PROTECTION_ELEMENT_ID)
  )
  ;
  CREATE INDEX CSM_PROTECTION_ELEMENT_PROTECTION_ELEMENT_ID_IDX ON CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID);

  CREATE SEQUENCE CSM_PROTECTIO_PROTECTION_G_SEQ;
  CREATE TABLE CSM_PROTECTION_GROUP (
    PROTECTION_GROUP_ID INTEGER default nextval('CSM_PROTECTIO_PROTECTION_G_SEQ'), UNIQUE(PROTECTION_GROUP_ID),
    PROTECTION_GROUP_NAME character varying(100) NOT NULL,
    PROTECTION_GROUP_DESCRIPTION character varying(200),
    APPLICATION_ID integer NOT NULL,
    LARGE_ELEMENT_COUNT_FLAG smallint NOT NULL,
    UPDATE_DATE DATE NOT NULL,
    PARENT_PROTECTION_GROUP_ID integer,
    PRIMARY KEY(PROTECTION_GROUP_ID)
  )
  ;
  CREATE INDEX CSM_PROTECTION_GROUP_PROTECTION_GROUP_ID_IDX ON CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID);

  CREATE SEQUENCE CSM_PG_PE_PG_PE_ID_SEQ;
  CREATE TABLE CSM_PG_PE (
    PG_PE_ID INTEGER default nextval('CSM_PG_PE_PG_PE_ID_SEQ'), UNIQUE(PG_PE_ID),
    PROTECTION_GROUP_ID integer NOT NULL,
    PROTECTION_ELEMENT_ID integer NOT NULL,
    UPDATE_DATE DATE NOT NULL,
    PRIMARY KEY(PG_PE_ID)
  )
  ;
  CREATE INDEX CSM_PG_PE_PG_PE_ID_IDX ON CSM_PG_PE (PG_PE_ID);

  CREATE FUNCTION SET_CSM_PG_PE_PG_PE_ID() RETURNS trigger AS $SET_CSM_PG_PE_PG_PE_ID$
      BEGIN
    NEW.PG_PE_ID := nextval('CSM_PG_PE_PG_PE_ID_SEQ');
    RETURN NEW;
      END;
  $SET_CSM_PG_PE_PG_PE_ID$ LANGUAGE plpgsql;

  CREATE TRIGGER SET_CSM_PG_PE_PG_PE_ID BEFORE INSERT OR UPDATE ON CSM_PG_PE
      FOR EACH ROW EXECUTE PROCEDURE SET_CSM_PG_PE_PG_PE_ID();

  CREATE FUNCTION SET_CSM_PG_PE_UPDATE_DATE() RETURNS trigger AS $SET_CSM_PG_PE_UPDATE_DATE$
      BEGIN
    NEW.update_date := current_date;
    RETURN NEW;
      END;
  $SET_CSM_PG_PE_UPDATE_DATE$ LANGUAGE plpgsql;

  CREATE TRIGGER SET_CSM_PG_PE_UPDATE_DATE BEFORE INSERT OR UPDATE ON CSM_PG_PE
      FOR EACH ROW EXECUTE PROCEDURE SET_CSM_PG_PE_UPDATE_DATE();





  CREATE SEQUENCE CSM_ROLE_ROLE_ID_SEQ;
  CREATE TABLE CSM_ROLE (
    ROLE_ID INTEGER default nextval('CSM_ROLE_ROLE_ID_SEQ'), UNIQUE(ROLE_ID),
    ROLE_NAME character varying(100) NOT NULL,
    ROLE_DESCRIPTION character varying(200),
    APPLICATION_ID integer NOT NULL,
    ACTIVE_FLAG smallint NOT NULL,
    UPDATE_DATE DATE NOT NULL,
    PRIMARY KEY(ROLE_ID)
  )
  ;
  CREATE INDEX CSM_ROLE_ROLE_ID_IDX ON CSM_ROLE (ROLE_ID);

  CREATE SEQUENCE CSM_ROLE_PRIV_ROLE_PRIVILE_SEQ;
  CREATE TABLE CSM_ROLE_PRIVILEGE (
    ROLE_PRIVILEGE_ID INTEGER default nextval('CSM_ROLE_PRIV_ROLE_PRIVILE_SEQ'), UNIQUE(ROLE_PRIVILEGE_ID),
    ROLE_ID integer NOT NULL,
    PRIVILEGE_ID integer NOT NULL,
    UPDATE_DATE DATE NOT NULL,
    PRIMARY KEY(ROLE_PRIVILEGE_ID)
  )
  ;
  CREATE INDEX CSM_ROLE_PRIVILEGE_ROLE_PRIVILEGE_ID_IDX ON CSM_ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID);


  CREATE FUNCTION SET_CSM_ROLE_PRIV_ROLE_PRIVILE() RETURNS trigger AS $SET_CSM_ROLE_PRIV_ROLE_PRIVILE$
      BEGIN
    NEW.role_privilege_id := nextval('CSM_ROLE_PRIV_ROLE_PRIVILE_SEQ');
    RETURN NEW;
      END;
  $SET_CSM_ROLE_PRIV_ROLE_PRIVILE$ LANGUAGE plpgsql;

  CREATE TRIGGER SET_CSM_ROLE_PRIV_ROLE_PRIVILE BEFORE INSERT ON CSM_ROLE_PRIVILEGE
      FOR EACH ROW EXECUTE PROCEDURE SET_CSM_ROLE_PRIV_ROLE_PRIVILE();


  CREATE FUNCTION SET_CSM_ROLE_PRIV_UPDATE_DATE() RETURNS trigger AS $SET_CSM_ROLE_PRIV_UPDATE_DATE$
      BEGIN
    NEW.update_date := current_date;
    RETURN NEW;
      END;
  $SET_CSM_ROLE_PRIV_UPDATE_DATE$ LANGUAGE plpgsql;

  CREATE TRIGGER SET_CSM_ROLE_PRIV_UPDATE_DATE BEFORE INSERT ON CSM_ROLE_PRIVILEGE
      FOR EACH ROW EXECUTE PROCEDURE SET_CSM_ROLE_PRIV_UPDATE_DATE();


  CREATE SEQUENCE CSM_USER_USER_ID_SEQ;
  CREATE TABLE CSM_USER (
    USER_ID INTEGER default nextval('CSM_USER_USER_ID_SEQ'), UNIQUE(USER_ID),
    LOGIN_NAME character varying(100) NOT NULL,
    FIRST_NAME character varying(100) NOT NULL,
    LAST_NAME character varying(100) NOT NULL,
    ORGANIZATION character varying(100),
    DEPARTMENT character varying(100),
    TITLE character varying(100),
    PHONE_NUMBER character varying(15),
    PASSWORD character varying(100),
    EMAIL_ID character varying(100),
    START_DATE DATE,
    END_DATE DATE,
    UPDATE_DATE DATE NOT NULL,
    PRIMARY KEY(USER_ID)
  )
  ;
  CREATE INDEX CSM_USER_USER_ID_IDX ON CSM_USER (USER_ID);

  CREATE SEQUENCE CSM_USER_GROU_USER_GROUP_I_SEQ;
  CREATE TABLE CSM_USER_GROUP (
    USER_GROUP_ID INTEGER default nextval('CSM_USER_GROU_USER_GROUP_I_SEQ'), UNIQUE(USER_GROUP_ID),
    USER_ID integer NOT NULL,
    GROUP_ID integer NOT NULL,
    PRIMARY KEY(USER_GROUP_ID)
  )
  ;
  CREATE INDEX CSM_USER_GROUP_USER_GROUP_ID_IDX ON CSM_USER_GROUP (USER_GROUP_ID);

  CREATE FUNCTION SET_CSM_USER_GROU_USER_GROUP_I() RETURNS trigger AS $SET_CSM_USER_GROU_USER_GROUP_I$
      BEGIN
    NEW.USER_GROUP_ID := nextval('CSM_USER_GROU_USER_GROUP_I_SEQ');
    RETURN NEW;
      END;
  $SET_CSM_USER_GROU_USER_GROUP_I$ LANGUAGE plpgsql;

  CREATE TRIGGER SET_CSM_USER_GROU_USER_GROUP_I BEFORE INSERT ON CSM_USER_GROUP
      FOR EACH ROW EXECUTE PROCEDURE SET_CSM_USER_GROU_USER_GROUP_I();


  CREATE SEQUENCE CSM_USER_GROU_USER_GROUP_R_SEQ;
  CREATE TABLE CSM_USER_GROUP_ROLE_PG (
    USER_GROUP_ROLE_PG_ID INTEGER default nextval('CSM_USER_GROU_USER_GROUP_R_SEQ'), UNIQUE(USER_GROUP_ROLE_PG_ID),
    USER_ID integer,
    GROUP_ID integer,
    ROLE_ID integer NOT NULL,
    PROTECTION_GROUP_ID integer NOT NULL,
    UPDATE_DATE DATE NOT NULL,
    PRIMARY KEY(USER_GROUP_ROLE_PG_ID)
  )
  ;

  CREATE SEQUENCE CSM_USER_PE_USER_PROTECTIO_SEQ;
  CREATE TABLE CSM_USER_PE (
    USER_PROTECTION_ELEMENT_ID INTEGER default nextval('CSM_USER_PE_USER_PROTECTIO_SEQ'), UNIQUE(USER_PROTECTION_ELEMENT_ID),
    PROTECTION_ELEMENT_ID integer NOT NULL,
    USER_ID integer NOT NULL,
    UPDATE_DATE DATE NOT NULL,
    PRIMARY KEY(USER_PROTECTION_ELEMENT_ID)
  )
  ;
  CREATE INDEX CSM_USER_PE_USER_PROTECTION_ELEMENT_ID_IDX ON CSM_USER_PE (USER_PROTECTION_ELEMENT_ID);

  CREATE FUNCTION SET_CSM_USER_PE_USER_PROTECTIO() RETURNS trigger AS $SET_CSM_USER_PE_USER_PROTECTIO$
      BEGIN
    NEW.USER_PROTECTION_ELEMENT_ID := nextval('CSM_USER_PE_USER_PROTECTIO_SEQ');
    RETURN NEW;
      END;
  $SET_CSM_USER_PE_USER_PROTECTIO$ LANGUAGE plpgsql;

  CREATE TRIGGER SET_CSM_USER_PE_USER_PROTECTIO BEFORE INSERT ON CSM_USER_PE
      FOR EACH ROW EXECUTE PROCEDURE SET_CSM_USER_PE_USER_PROTECTIO();


  CREATE FUNCTION SET_CSM_USER_PE_UPDATE_DATE() RETURNS trigger AS $SET_CSM_USER_PE_UPDATE_DATE$
      BEGIN
    NEW.UPDATE_DATE := current_date;
    RETURN NEW;
      END;
  $SET_CSM_USER_PE_UPDATE_DATE$ LANGUAGE plpgsql;

  CREATE TRIGGER SET_CSM_USER_PE_UPDATE_DATE BEFORE INSERT ON CSM_USER_PE
      FOR EACH ROW EXECUTE PROCEDURE SET_CSM_USER_PE_UPDATE_DATE();

  ALTER TABLE CSM_APPLICATION
  ADD CONSTRAINT UQ_APPLICATION_NAME UNIQUE (APPLICATION_NAME)
  ;

  CREATE INDEX idx_APPLICATION_ID ON CSM_GROUP USING btree (APPLICATION_ID)
  ;
  ALTER TABLE CSM_GROUP
  ADD CONSTRAINT UQ_GROUP_GROUP_NAME UNIQUE (APPLICATION_ID, GROUP_NAME)
  ;
  ALTER TABLE CSM_PRIVILEGE
  ADD CONSTRAINT UQ_PRIVILEGE_NAME UNIQUE (PRIVILEGE_NAME)
  ;
  CREATE INDEX idx_APPLICATION_ID_PE ON CSM_PROTECTION_ELEMENT USING btree (APPLICATION_ID)
  ;
  ALTER TABLE CSM_PROTECTION_ELEMENT
  ADD CONSTRAINT UQ_PE_PE_NAME_ATTRIBUTE_APP_ID UNIQUE (OBJECT_ID, ATTRIBUTE, APPLICATION_ID)
  ;
  CREATE INDEX idx_APPLICATION_ID_PG ON CSM_PROTECTION_GROUP USING btree (APPLICATION_ID)
  ;
  ALTER TABLE CSM_PROTECTION_GROUP
  ADD CONSTRAINT UQ_PROTECTION_GROUP_PROTECTION_GROUP_NAME UNIQUE (APPLICATION_ID, PROTECTION_GROUP_NAME)
  ;
  CREATE INDEX idx_PARENT_PROTECTION_GROUP_ID ON CSM_PROTECTION_GROUP USING btree (PARENT_PROTECTION_GROUP_ID)
  ;
  CREATE INDEX idx_PROTECTION_ELEMENT_ID ON CSM_PG_PE USING btree (PROTECTION_ELEMENT_ID)
  ;
  CREATE INDEX idx_PROTECTION_GROUP_ID_PGPE ON CSM_PG_PE USING btree (PROTECTION_GROUP_ID)
  ;
  ALTER TABLE CSM_PG_PE
  ADD CONSTRAINT UQ_PROTECTION_GROUP_PROTECTION_ELEMENT_PROTECTION_GROUP_ID UNIQUE (PROTECTION_ELEMENT_ID, PROTECTION_GROUP_ID)
  ;
  CREATE INDEX idx_APPLICATION_ID_R ON CSM_ROLE USING btree (APPLICATION_ID)
  ;
  ALTER TABLE CSM_ROLE
  ADD CONSTRAINT UQ_ROLE_ROLE_NAME UNIQUE (APPLICATION_ID, ROLE_NAME)
  ;
  CREATE INDEX idx_PRIVILEGE_ID ON CSM_ROLE_PRIVILEGE USING btree (PRIVILEGE_ID)
  ;
  ALTER TABLE CSM_ROLE_PRIVILEGE
  ADD CONSTRAINT UQ_ROLE_PRIVILEGE_ROLE_ID UNIQUE (PRIVILEGE_ID, ROLE_ID)
  ;
  CREATE INDEX idx_ROLE_ID ON CSM_ROLE_PRIVILEGE USING btree (ROLE_ID)
  ;
  ALTER TABLE CSM_USER
  ADD CONSTRAINT UQ_LOGIN_NAME UNIQUE (LOGIN_NAME)
  ;
  CREATE INDEX idx_USER_ID ON CSM_USER_GROUP USING btree (USER_ID)
  ;
  CREATE INDEX idx_GROUP_ID ON CSM_USER_GROUP USING btree (GROUP_ID)
  ;
  CREATE INDEX idx_GROUP_ID_UGRPG ON CSM_USER_GROUP_ROLE_PG USING btree (GROUP_ID)
  ;
  CREATE INDEX idx_ROLE_ID_UGRPG ON CSM_USER_GROUP_ROLE_PG USING btree (ROLE_ID)
  ;
  CREATE INDEX idx_PROTECTION_GROUP_ID_UGRPG ON CSM_USER_GROUP_ROLE_PG USING btree (PROTECTION_GROUP_ID)
  ;
  CREATE INDEX idx_USER_ID_UGRPG ON CSM_USER_GROUP_ROLE_PG USING btree (USER_ID)
  ;
  CREATE INDEX idx_USER_ID_UPE ON CSM_USER_PE USING btree (USER_ID)
  ;
  CREATE INDEX idx_PROTECTION_ELEMENT_ID_UPE ON CSM_USER_PE USING btree (PROTECTION_ELEMENT_ID)
  ;
  ALTER TABLE CSM_USER_PE
  ADD CONSTRAINT UQ_USER_PROTECTION_ELEMENT_PROTECTION_ELEMENT_ID UNIQUE (USER_ID, PROTECTION_ELEMENT_ID)
  ;


  ALTER TABLE CSM_GROUP ADD CONSTRAINT FK_APPLICATION_GROUP
  FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_PROTECTION_ELEMENT ADD CONSTRAINT FK_PE_APPLICATION
  FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_PROTECTION_GROUP ADD CONSTRAINT FK_PG_APPLICATION
  FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_PROTECTION_GROUP ADD CONSTRAINT FK_PROTECTION_GROUP
  FOREIGN KEY (PARENT_PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID)
  ;

  ALTER TABLE CSM_PG_PE ADD CONSTRAINT FK_PROTECTION_ELEMENT_PROTECTION_GROUP
  FOREIGN KEY (PROTECTION_ELEMENT_ID) REFERENCES CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_PG_PE ADD CONSTRAINT FK_PROTECTION_GROUP_PROTECTION_ELEMENT
  FOREIGN KEY (PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_ROLE ADD CONSTRAINT FK_APPLICATION_ROLE
  FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_ROLE_PRIVILEGE ADD CONSTRAINT FK_PRIVILEGE_ROLE
  FOREIGN KEY (PRIVILEGE_ID) REFERENCES CSM_PRIVILEGE (PRIVILEGE_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_ROLE_PRIVILEGE ADD CONSTRAINT FK_ROLE
  FOREIGN KEY (ROLE_ID) REFERENCES CSM_ROLE (ROLE_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_USER_GROUP ADD CONSTRAINT FK_USER_GROUP
  FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_USER_GROUP ADD CONSTRAINT FK_UG_GROUP
  FOREIGN KEY (GROUP_ID) REFERENCES CSM_GROUP (GROUP_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PROTECTION_GROUP_GROUPS
  FOREIGN KEY (GROUP_ID) REFERENCES CSM_GROUP (GROUP_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PROTECTION_GROUP_ROLE
  FOREIGN KEY (ROLE_ID) REFERENCES CSM_ROLE (ROLE_ID)
  ON DELETE CASCADE
  ;


  ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PROTECTION_GROUP_PROTECTION_GROUP
  FOREIGN KEY (PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PROTECTION_GROUP_USER
  FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_USER_PE ADD CONSTRAINT FK_PE_USER
  FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID)
  ON DELETE CASCADE
  ;

  ALTER TABLE CSM_USER_PE ADD CONSTRAINT FK_PROTECTION_ELEMENT_USER
  FOREIGN KEY (PROTECTION_ELEMENT_ID) REFERENCES CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID)
  ON DELETE CASCADE
  ;
  
