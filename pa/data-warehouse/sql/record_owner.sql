DROP TABLE IF EXISTS DW_STUDY_RECORD_OWNER;

CREATE TABLE DW_STUDY_RECORD_OWNER (
    ADDRESS_CITY character varying(200),
    ADDRESS_LINE character varying(2000),
    ADDRESS_STATE character varying(100),
    ADDRESS_ZIP_CODE character varying(100),
	EMAIL character varying(200),
	NAME character varying(500),
    NCI_ID character varying(255),
    PHONE_NUMBER character varying(500)
);

CREATE INDEX DW_STUDY_RECORD_OWNER_CITY_IDX on dw_study_record_owner(address_city);
CREATE INDEX DW_STUDY_RECORD_OWNER_LINE1_IDX on dw_study_record_owner(address_line);
CREATE INDEX DW_STUDY_RECORD_OWNER_STATE_IDX on dw_study_record_owner(address_state);
CREATE INDEX DW_STUDY_RECORD_OWNER_EMAIL_IDX on dw_study_record_owner(email);
CREATE INDEX DW_STUDY_RECORD_OWNER_NAME_IDX on dw_study_record_owner(name);
CREATE INDEX DW_STUDY_RECORD_OWNER_NCI_ID_IDX on dw_study_record_owner(nci_id);
CREATE INDEX DW_STUDY_RECORD_OWNER_PHONE_ID_IDX on dw_study_record_owner(phone_number);

