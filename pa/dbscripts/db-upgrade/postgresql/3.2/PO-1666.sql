ALTER TABLE REGISTRY_USER ADD COLUMN EMAIL_ADDRESS VARCHAR(255);
UPDATE REGISTRY_USER SET EMAIL_ADDRESS = (SELECT  LOGIN_NAME FROM CSM_USER WHERE CSM_USER_ID = USER_ID);
