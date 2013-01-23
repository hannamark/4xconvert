CREATE TABLE misc_documents
(
   name varchar NOT NULL,
   application varchar NOT NULL,
   version varchar NOT NULL,
   expiration_date date NULL,
   content text NOT NULL,
   CONSTRAINT misc_documents_pkey PRIMARY KEY (name,application,version)
);

INSERT INTO misc_documents (name,application,version,expiration_date,content) VALUES ('Disclaimer','PO','01',null,'This is a U.S. Government computer system, which may be accessed and used only for authorized Government business by authorized personnel. Unauthorized access or use of this computer system may subject violators to criminal, civil, and/or administrative action.\n<br>\n<br>\nAll information on this computer system may be intercepted, recorded, read, copied, and disclosed by and to authorized personnel for official purposes, including criminal investigations. Such information includes sensitive data encrypted to comply with confidentiality and privacy requirements. Access or use of this computer system by any person, whether authorized or unauthorized, constitutes consent to these terms. There is no right of privacy in this system. ');