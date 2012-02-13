DROP TABLE IF EXISTS DW_STUDY_ON_HOLD_STATUS;

CREATE TABLE DW_STUDY_ON_HOLD_STATUS (
    REASON_DESCRIPTION character varying(2000),
	REASON character varying(200),
    ON_HOLD_DATE date,
    OFF_HOLD_DATE date,
    DATE_CREATED date,
    DATE_LAST_UPDATED date,
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_ID character varying(255),
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500)
);

CREATE INDEX DW_STUDY_ON_HOLD_REASON_DESC_IDX on dw_study_on_hold_status(reason_description);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_IDX on dw_study_on_hold_status(reason);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_ON_HOLD_DATE_IDX on dw_study_on_hold_status(on_hold_date);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_OFF_HOLD_DATE_IDX on dw_study_on_hold_status(off_hold_date);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_DATE_CREATED_IDX on dw_study_on_hold_status(date_created);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_DATE_UPDATED_IDX on dw_study_on_hold_status(date_last_updated);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_ID_IDX on dw_study_on_hold_status(internal_system_id);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_NCI_ID_IDX on dw_study_on_hold_status(nci_id);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_NAME_CREATED_IDX on dw_study_on_hold_status(user_name_created);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_NAME_UPDATED_IDX on dw_study_on_hold_status(user_name_last_updated);
