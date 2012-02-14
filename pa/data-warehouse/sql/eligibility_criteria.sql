DROP TABLE IF EXISTS DW_STUDY_ELIGIBILITY_CRITERIA;

CREATE TABLE DW_STUDY_ELIGIBILITY_CRITERIA (
	CDE_PUBLIC_IDENTIFIER integer,
	DISPLAY_ORDER integer,
    CDE_VERSION character varying(25),
    ELIGIBLE_GENDER_CODE character varying(25),
    INCLUSION_INDICATOR boolean,
    STRUCTURED_INDICATOR boolean,
    INTERNAL_SYSTEM_ID integer,
    CRITERION_NAME character varying(500),
    NCI_ID character varying(255),
    OPERATOR character varying(50),
    UNIT character varying(50),
    VALUE character varying(50)    
);

CREATE INDEX DW_STUDY_CRITERIA_CDE_ID_IDX on dw_study_eligibility_criteria(cde_public_identifier);
CREATE INDEX DW_STUDY_CRITERIA_ORDER_IDX on dw_study_eligibility_criteria(display_order);
CREATE INDEX DW_STUDY_CRITERIA_CDE_VERSION_IDX on dw_study_eligibility_criteria(cde_version);
CREATE INDEX DW_STUDY_CRITERIA_GENDER_CODE_IDX on dw_study_eligibility_criteria(eligible_gender_code);
CREATE INDEX DW_STUDY_CRITERIA_INCLUSION_IDX on dw_study_eligibility_criteria(inclusion_indicator);
CREATE INDEX DW_STUDY_CRITERIA_STRUCTURED_IDX on dw_study_eligibility_criteria(structured_indicator);
CREATE INDEX DW_STUDY_CRITERIA_ID_IDX on dw_study_eligibility_criteria(internal_system_id);
CREATE INDEX DW_STUDY_CRITERIA_NAME_IDX on dw_study_eligibility_criteria(criterion_name);
CREATE INDEX DW_STUDY_CRITERIA_NCI_ID_IDX on dw_study_eligibility_criteria(nci_id);
CREATE INDEX DW_STUDY_CRITERIA_OPERATOR_IDX on dw_study_eligibility_criteria(operator);
CREATE INDEX DW_STUDY_CRITERIA_UNIT_IDX on dw_study_eligibility_criteria(unit);
CREATE INDEX DW_STUDY_CRITERIA_VALUE_IDX on dw_study_eligibility_criteria(value);

