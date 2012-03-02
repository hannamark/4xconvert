DROP TABLE IF EXISTS DW_STUDY_MILESTONE;

CREATE TABLE DW_STUDY_MILESTONE (
    COMMENTS character varying(200),
    DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    INTERNAL_SYSTEM_ID INTEGER not null,
	NAME character varying (50),
    NCI_ID character varying(255),
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500)
);

CREATE INDEX DW_STUDY_MILESTONE_COMMENTS_IDX on dw_study_milestone(comments);
CREATE INDEX DW_STUDY_MILESTONE_DATE_IDX on dw_study_milestone(date);
CREATE INDEX DW_STUDY_MILESTONE_DATE_CREATED_IDX on dw_study_milestone(date_created);
CREATE INDEX DW_STUDY_MILESTONE_DATE_UPDATED_IDX on dw_study_milestone(date_last_updated);
CREATE INDEX DW_STUDY_MILESTONE_ID_IDX on dw_study_milestone(internal_system_id);
CREATE INDEX DW_STUDY_MILESTONE_NAME_IDX on dw_study_milestone(name);
CREATE INDEX DW_STUDY_MILESTONE_NCI_ID_IDX on dw_study_milestone(nci_id);
CREATE INDEX DW_STUDY_MILESTONE_USER_CREATED_IDX on dw_study_milestone(user_name_created);
CREATE INDEX DW_STUDY_MILESTONE_USER_UPDATED_IDX on dw_study_milestone(user_name_last_updated);
