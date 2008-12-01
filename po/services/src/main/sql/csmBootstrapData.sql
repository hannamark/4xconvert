INSERT INTO CSM_APPLICATION(APPLICATION_NAME, APPLICATION_DESCRIPTION) VALUES ('csmupt', 'CSM UPT Super Admin Application');
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ( 'csmadmin', 'CSM', 'Admin','zJPWCwDeSgG8j2uyHEABIQ==');

INSERT INTO CSM_PROTECTION_ELEMENT(PROTECTION_ELEMENT_NAME, PROTECTION_ELEMENT_DESCRIPTION, OBJECT_ID, APPLICATION_ID)
    VALUES ('csmupt', 'CSM UPT Super Admin Application Protection Element', 'csmupt', 1);

INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID) VALUES (1, 1);

INSERT INTO CSM_APPLICATION(APPLICATION_NAME, APPLICATION_DESCRIPTION) VALUES ('po', 'po');

INSERT INTO CSM_PROTECTION_ELEMENT(PROTECTION_ELEMENT_NAME, PROTECTION_ELEMENT_DESCRIPTION, OBJECT_ID, APPLICATION_ID)
    VALUES ('po', 'PO Admin Application Protection Element', 'po', 1);

INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION)
    VALUES('CREATE','This privilege grants permission to a user to create an entity. This entity can be an object, a database entry, or a resource such as a network connection');

INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION)
    VALUES('ACCESS','This privilege allows a user to access a particular resource.  Examples of resources include a network or database connection, socket, module of the application, or even the application itself');

INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION)
    VALUES('READ','This privilege permits the user to read data from a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to read data about a particular entry');

INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION)
    VALUES('WRITE','This privilege allows a user to write data to a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to write data about a particular entity');

INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION)
    VALUES('UPDATE','This privilege grants permission at an entity level and signifies that the user is allowed to update data for a particular entity. Entities may include an object, object attribute, database row etc');

INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION)
    VALUES('DELETE','This privilege permits a user to delete a logical entity. This entity can be an object, a database entry, a resource such as a network connection, etc');

INSERT INTO CSM_PRIVILEGE(PRIVILEGE_NAME, PRIVILEGE_DESCRIPTION)
    VALUES('EXECUTE','This privilege allows a user to execute a particular resource. The resource can be a method, function, behavior of the application, URL, button etc');