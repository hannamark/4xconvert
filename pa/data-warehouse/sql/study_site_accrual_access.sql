DROP TABLE IF EXISTS stg_dw_study_site_accrual_access;

CREATE TABLE stg_dw_study_site_accrual_access ( 
	access_status character varying(200),
	accrual_admin character varying(500),
        date_last_created timestamp without time zone,
        date_last_updated timestamp without time zone,
        nci_id character varying(255),
        org_name character varying(200),
        org_recruitment_status character varying(200),
        request_details character varying(10000),
        org_org_family character varying(400),
        user_name_last_created character varying(500),
        user_name_last_updated character varying(500),
        internal_system_id bigint
	)
	;