/**
 * 
 */
package gov.nih.nci.pa.service.util; // NOPMD

import static org.apache.commons.lang.StringUtils.defaultString;
import static org.apache.commons.lang.StringUtils.indexOf;
import static org.apache.commons.lang.StringUtils.isAsciiPrintable;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.commons.lang.StringUtils.left;
import static org.apache.commons.lang.StringUtils.trim;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO.ResponsiblePartyType;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.OutcomeMeasureTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudySubtypeCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.IvlConverter.JavaPq;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.ctgov.ArmGroupStruct;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.ctgov.ContactStruct;
import gov.nih.nci.pa.service.ctgov.DateStruct;
import gov.nih.nci.pa.service.ctgov.EligibilityStruct;
import gov.nih.nci.pa.service.ctgov.EnrollmentStruct;
import gov.nih.nci.pa.service.ctgov.IdInfoStruct;
import gov.nih.nci.pa.service.ctgov.InvestigatorStruct;
import gov.nih.nci.pa.service.ctgov.ProtocolOutcomeStruct;
import gov.nih.nci.pa.service.ctgov.ResponsiblePartyStruct;
import gov.nih.nci.pa.service.ctgov.SponsorStruct;
import gov.nih.nci.pa.service.ctgov.TextblockStruct;
import gov.nih.nci.pa.service.util.AbstractPDQTrialServiceHelper.PersonWithFullNameDTO;
import gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal.ProtocolSnapshot;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Denis G. Krylov
 * 
 */
@Stateless
@SuppressWarnings({ "unchecked", "PMD.TooManyMethods",
        "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CTGovSyncServiceBean implements CTGovSyncServiceLocal {

    /**
     * Identifies CTGOV Import status as failure
     */
    public static final String FAILURE = "Failure: ";

    /**
     * Identifies CTGOV Import status as success
     */
    public static final String SUCCESS = "Success";

    private static final int INDEX_3 = 3;

    private static final String MASKING = "Masking";

    private static final String NEW_TRIAL_ACTION = "New Trial";

    private static final String UPDATE_ACTION = "Update";

    private static final String EMPTY = "";

    private static final String CTGOVIMPORT_USERNAME = "ctgovimport";

    private static final String EXCLUSION_CRITERIA_MARKER = 
            "(?:(?:\\sExclusion (?:C|c)riteria\\s*:?)|(?:\\s{3,}Exclusion:\\s))";

    private static final String INCLUSION_CRITERIA_MARKER = "\\sInclusion (?:C|c)riteria\\s*:?";

    private static final int L_254 = 254;

    private static final String NOT_PROVIDED = "Not provided";

    private static final String US_CANADA_PHONE_FORMAT = "^\\d{3}-\\d{3}-\\d{4}(x\\d+)?$";

    private static final String UNKNOWN = "Unknown";

    private static final int L_10 = 10;

    private static final int C_5 = 5;

    private static final int L_50 = 50;

    private static final int L_160 = 160;

    private static final String UNSPECIFIED = "Unspecified";

    private static final int L_600 = 600;

    private static final int L_2000 = 2000;

    private static final String AGE_PATTERN = "^(\\d+(\\.\\d+)?)\\s*(\\p{Alpha}+)$";

    private static final int L_1000 = 1000;

    private static final int L_4000 = 4000;

    private static final int L_32000 = 32000;

    private static final int L_5000 = 5000;
    
    private static final int L_10000 = 10000;
    
    private static final int L_800 = 800;

    private static final int L_200 = 200;

    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    @EJB
    private RegulatoryInformationServiceRemote regulatoryAuthorityService;
    @EJB
    private TrialRegistrationServiceLocal trialRegistrationService;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private DocumentWorkflowStatusServiceLocal documentWorkflowStatusService;
    @EJB
    private ProtocolComparisonServiceLocal protocolComparisonService;

    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    private static final Logger LOG = Logger
            .getLogger(CTGovSyncServiceBean.class);

    private static final int CONNECT_TIMEOUT = 30 * 1000;

    private static final int L_300 = 300;

    /**
     * Mapping of values between ClinicalTrials.gov and CTRP. Key is ClinicalTrials.gov, Value is CTRP
     */
    public static final Map<String, String> CTGOV_TO_CTRP_MAP = new HashMap<String, String>();

    private static final int L_20 = 20;

    private static final String GENERIC_ORG_NAME = "Generic Organization";

    static {

        CTGOV_TO_CTRP_MAP.put("Randomized",
                AllocationCode.RANDOMIZED_CONTROLLED_TRIAL.getCode());
        CTGOV_TO_CTRP_MAP.put("Non-Randomized",
                AllocationCode.NON_RANDOMIZED_TRIAL.getCode());

        CTGOV_TO_CTRP_MAP.put("Open Label", BlindingSchemaCode.OPEN.getCode());
        CTGOV_TO_CTRP_MAP.put("Single-Blind",
                BlindingSchemaCode.SINGLE_BLIND.getCode());
        CTGOV_TO_CTRP_MAP.put("Double-Blind",
                BlindingSchemaCode.DOUBLE_BLIND.getCode());

        CTGOV_TO_CTRP_MAP.put("Single Group Assignment",
                DesignConfigurationCode.SINGLE_GROUP.getCode());
        CTGOV_TO_CTRP_MAP.put("Parallel Assignment",
                DesignConfigurationCode.PARALLEL.getCode());
        CTGOV_TO_CTRP_MAP.put("Crossover Assignment",
                DesignConfigurationCode.CROSSOVER.getCode());
        CTGOV_TO_CTRP_MAP.put("Factorial Assignment",
                DesignConfigurationCode.FACTORIAL.getCode());

        CTGOV_TO_CTRP_MAP.put("Safety Study",
                StudyClassificationCode.SAFETY.getCode());
        CTGOV_TO_CTRP_MAP.put("Efficacy Study",
                StudyClassificationCode.EFFICACY.getCode());
        CTGOV_TO_CTRP_MAP.put("Safety/Efficacy Study",
                StudyClassificationCode.SAFETY_OR_EFFICACY.getCode());
        CTGOV_TO_CTRP_MAP.put("Bio-equivalence Study",
                StudyClassificationCode.BIO_EQUIVALENCE.getCode());
        CTGOV_TO_CTRP_MAP.put("Bio-availability Study",
                StudyClassificationCode.BIO_AVAILABILITY.getCode());
        CTGOV_TO_CTRP_MAP.put("Pharmacokinetics Study",
                StudyClassificationCode.PHARMACOKINETICS.getCode());
        CTGOV_TO_CTRP_MAP.put("Pharmacodynamics Study",
                StudyClassificationCode.PHARMACODYNAMICS.getCode());
        CTGOV_TO_CTRP_MAP.put("Pharmacokinetics/Dynamics Study",
                StudyClassificationCode.PHARMACOKINETICS_OR_DYNAMICS.getCode());
        CTGOV_TO_CTRP_MAP.put("Principal Investigator",
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode());
        CTGOV_TO_CTRP_MAP.put(XmlGenHelper.NA, AllocationCode.NA.getCode());

        CTGOV_TO_CTRP_MAP.put("Not yet recruiting",
                StudyStatusCode.IN_REVIEW.getCode());
        CTGOV_TO_CTRP_MAP.put("Approved for marketing",
                StudyStatusCode.APPROVED.getCode());
        CTGOV_TO_CTRP_MAP.put("Withdrawn", StudyStatusCode.WITHDRAWN.getCode());
        CTGOV_TO_CTRP_MAP.put("Withheld", StudyStatusCode.WITHDRAWN.getCode());
        CTGOV_TO_CTRP_MAP.put("Available", StudyStatusCode.ACTIVE.getCode());
        CTGOV_TO_CTRP_MAP.put("Recruiting", StudyStatusCode.ACTIVE.getCode());
        CTGOV_TO_CTRP_MAP.put("Enrolling by invitation",
                StudyStatusCode.ENROLLING_BY_INVITATION.getCode());
        CTGOV_TO_CTRP_MAP.put("Active, not recruiting",
                StudyStatusCode.CLOSED_TO_ACCRUAL.getCode());
        CTGOV_TO_CTRP_MAP.put("Active, not recruiting",
                StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode());
        CTGOV_TO_CTRP_MAP.put("No longer available",
                StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode());
        CTGOV_TO_CTRP_MAP.put("Suspended",
                StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode());
        CTGOV_TO_CTRP_MAP.put("Suspended",
                StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION
                        .getCode());
        CTGOV_TO_CTRP_MAP.put("Completed", StudyStatusCode.COMPLETE.getCode());
        CTGOV_TO_CTRP_MAP.put("Terminated",
                StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode());

        CTGOV_TO_CTRP_MAP.put("Phase 1", PhaseCode.I.getCode());
        CTGOV_TO_CTRP_MAP.put("Phase 1/Phase 2", PhaseCode.I_II.getCode());
        CTGOV_TO_CTRP_MAP.put("Phase 2", PhaseCode.II.getCode());
        CTGOV_TO_CTRP_MAP.put("Phase 2/Phase 3", PhaseCode.II_III.getCode());
        CTGOV_TO_CTRP_MAP.put("Phase 3", PhaseCode.III.getCode());
        CTGOV_TO_CTRP_MAP.put("Phase 4", PhaseCode.IV.getCode());
        CTGOV_TO_CTRP_MAP.put("N/A", PhaseCode.NA.getCode());
        CTGOV_TO_CTRP_MAP.put("Phase 0", PhaseCode.O.getCode());

        CTGOV_TO_CTRP_MAP.put("Year", UnitsCode.YEARS.getCode());
        CTGOV_TO_CTRP_MAP.put("Month", UnitsCode.MONTHS.getCode());

        CTGOV_TO_CTRP_MAP.put("Case-Only", StudyModelCode.CASE_ONLY.getCode());
        CTGOV_TO_CTRP_MAP.put("Case Control",
                StudyModelCode.CASE_CONTROL.getCode());
        CTGOV_TO_CTRP_MAP.put("Family-Based",
                StudyModelCode.FAMILY_BASED.getCode());
        CTGOV_TO_CTRP_MAP.put("Ecologic or Community",
                StudyModelCode.ECOLOGIC_OR_COMMUNITY_STUDIES.getCode());

        CTGOV_TO_CTRP_MAP.put("Cross-Sectional",
                TimePerspectiveCode.CROSS_SECTION.getCode());

        CTGOV_TO_CTRP_MAP.put("No Intervention",
                ArmTypeCode.NO_INTERVENTION.getCode());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.CTGovSyncServiceLocal#getCtGovStudyByNctId
     * (java.lang.String)
     */
    @Override
    public ClinicalStudy getCtGovStudyByNctId(String nctID) throws PAException {
        if (isBlank(nctID) || !isAsciiPrintable(nctID)) {
            return null;
        }
        try {
            String xml = getCtGovXmlByNctId(nctID);
            if (isNotBlank(xml)) {
                return unmarshallClinicalStudy(xml);
            }
        } catch (Exception e) {
            LOG.error(e, e);
            throw new PAException(e.getMessage()); // NOPMD
        }
        return null;
    }

    /**
     * @param xml
     * @return
     * 
     */
    private ClinicalStudy unmarshallClinicalStudy(String xml)
            throws PAException {
        try {
            String packageName = ClinicalStudy.class.getPackage().getName();
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            ClinicalStudy study = (ClinicalStudy) u.unmarshal(new StringReader(
                    xml));
            return study;
        } catch (Exception e) {
            LOG.error(e, e);
            throw new PAException(e.getMessage()); // NOPMD
        }
    }

    private String getCtGovXmlByNctId(String nctID) throws PAException,
            IOException {

        InputStream is = null;
        try {
            String urlTemplate = lookUpTableService
                    .getPropertyValue("ctgov.api.getByNct");
            URL url = new URL(urlTemplate.replace("${nctid}", nctID));
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            conn.setUseCaches(false);
            is = conn.getInputStream();
            return IOUtils.toString(is, "UTF-8");
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

    /**
     * @param lookUpTableService
     *            the lookUpTableService to set
     */
    public void setLookUpTableService(
            LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    @Override
    public CTGovStudyAdapter getAdaptedCtGovStudyByNctId(String nctID)
            throws PAException {
        ClinicalStudy study = getCtGovStudyByNctId(nctID);
        return study != null ? new CTGovStudyAdapter(study) : null;
    }

    @Override
    public String importTrial(String nctID) throws PAException {
        if (isBlank(nctID) || !isAsciiPrintable(nctID)) {
            throw new PAException("Invalid NCT ID");
        }
        final String currentUser = getCurrentUser();
        String xml;
        try {
            xml = getCtGovXmlByNctId(nctID);
        } catch (Exception e) {
            LOG.error(e, e);
            createImportLogEntry(EMPTY, nctID, EMPTY, EMPTY,
                    "Failure: unable to retrieve from ClinicalTrials.gov", currentUser, false);
            throw new PAException(e.getMessage()); // NOPMD
        }
        if (xml == null) {
            throw new PAException("Study " + nctID + " not found");
        }
        return importOrUpdateStudy(xml, nctID);
    }

    private String importOrUpdateStudy(String xml, String nctID)
            throws PAException {
        List<String> nciIDs = new ArrayList<String>();
        List<StudyProtocolDTO> list = studyProtocolService
                .getStudyProtocolsByNctId(nctID);
        if (list.isEmpty()) {
            nciIDs.add(importNewStudy(xml, nctID));
        } else {
            for (StudyProtocolDTO dto : list) {
                DocumentWorkflowStatusDTO statusDTO = documentWorkflowStatusService
                        .getCurrentByStudyProtocol(dto.getIdentifier());
                if (statusDTO != null
                        && !DocumentWorkflowStatusCode.REJECTED.name()
                                .equalsIgnoreCase(
                                        statusDTO.getStatusCode().getCode())) {
                    nciIDs.add(updateStudy(xml, nctID, dto));
                }
            }
        }
        return StringUtils.join(nciIDs, ", ");
    }

    private String importNewStudy(String xml, String nctIdStr) // NOPMD
            throws PAException { // NOPMD
        String title = EMPTY;
        final String currentUser = getCurrentUser();
        try {
            ClinicalStudy study = unmarshallClinicalStudy(xml);
            nctIdStr = study.getIdInfo().getNctId();
            title = study.getOfficialTitle();

            StudyProtocolDTO studyProtocolDTO = instantiateStudyProtocolDTO(study);

            final String protocolID = verifyPopulateAndPersist(
                    studyProtocolDTO, study, nctIdStr, xml, false);

            String trialNciId = paServiceUtils.getTrialNciId(Long
                    .valueOf(protocolID));
            createImportLogEntry(trialNciId, nctIdStr, title, NEW_TRIAL_ACTION,
                    SUCCESS, currentUser, false);
            return trialNciId;
        } catch (Exception e) {
            createImportLogEntry(EMPTY, nctIdStr, title, NEW_TRIAL_ACTION,
                    FAILURE + e.getMessage(), currentUser, false);
            throw new PAException(e);
        }

    }

    private String updateStudy(String xml, String nctIdStr, // NOPMD
            final StudyProtocolDTO existentStudy) // NOPMD
            throws PAException { // NOPMD

        final Long id = Long
                        .valueOf(existentStudy.getIdentifier().getExtension());
        String trialNciId = paServiceUtils.getTrialNciId(id);
        String title = EMPTY;
        final String currentUser = getCurrentUser();
        try {            
            ClinicalStudy study = unmarshallClinicalStudy(xml);
            nctIdStr = study.getIdInfo().getNctId();
            title = study.getOfficialTitle();

            StudyProtocolDTO studyProtocolDTO = existentStudy;
            if (!BlConverter.convertToBool(studyProtocolDTO.getProprietaryTrialIndicator())) {
                throw new PAException("Complete trials cannot be updated from ClinicalTrials.gov");
            }
            
            ProtocolSnapshot before = protocolComparisonService.captureSnapshot(id);
            verifyPopulateAndPersist(studyProtocolDTO, study, nctIdStr, xml,
                    true);
            ProtocolSnapshot after = protocolComparisonService.captureSnapshot(id);
            createImportLogEntry(trialNciId, nctIdStr, title, UPDATE_ACTION,
                    SUCCESS, currentUser, needsReview(before, after));
            return trialNciId;
        } catch (Exception e) {
            LOG.error(e, e);
            createImportLogEntry(trialNciId, nctIdStr, title, UPDATE_ACTION,
                    FAILURE + e.getMessage(), currentUser, false);
            throw new PAException(e.getMessage()); // NOPMD
        }

    }

    private boolean needsReview(ProtocolSnapshot before, ProtocolSnapshot after)
            throws PAException {
        List<String> ognls = Arrays.asList(lookUpTableService.getPropertyValue(
                "ctgov.sync.fields_of_interest").split(";"));
        return !protocolComparisonService.compare(before, after, ognls)
                .isEmpty();
    }

    private String verifyPopulateAndPersist(StudyProtocolDTO studyProtocolDTO, // NOPMD
            ClinicalStudy study, String nctIdStr, String xml, boolean isUpdate)
            throws PAException {
        boolean importPO = !Boolean.parseBoolean(lookUpTableService
                .getPropertyValue("ctgov.sync.skip_po_data"));
        verifyTrialCategory(study);

        // convert into CTRP DTOs, piece by piece
        extractStudyProtocolDTOFields(study, studyProtocolDTO);
        List<ArmDTO> arms = extractArms(study);
        List<PlannedEligibilityCriterionDTO> eligibility = extractEligibility(study
                .getEligibility());
        StudySiteDTO leadOrgID = extractLeadOrgId(study.getIdInfo());

        StudySiteDTO nctID = new StudySiteDTO();
        nctID.setLocalStudyProtocolIdentifier(StConverter.convertToSt(nctIdStr));

        studyProtocolDTO.setSecondaryIdentifiers(mergeIdentifiers(
                extractOtherIdentifiers(study.getIdInfo()),
                studyProtocolDTO.getSecondaryIdentifiers()));
        PersonDTO centralContactDTO = importPO ? extractCentralContact(study
                .getOverallContact()) : null;

        PersonDTO investigatorDTO = importPO ? extractInvestigator(study) : null;
        OrganizationDTO leadOrgDTO = importPO ? extractSponsor(study)
                : getGenericOrganization();
        StudyOverallStatusDTO overallStatusDTO = extractOverallStatusDTO(study);
        StudyRegulatoryAuthorityDTO regAuthDTO = extractRegulatoryAuthorityDTO(study);

        List<StudyOutcomeMeasureDTO> outcomes = new ArrayList<StudyOutcomeMeasureDTO>();
        outcomes.addAll(extractOutcomes(study.getPrimaryOutcome(), true,
                OutcomeMeasureTypeCode.PRIMARY));
        outcomes.addAll(extractOutcomes(study.getSecondaryOutcome(), false,
                OutcomeMeasureTypeCode.SECONDARY));
        outcomes.addAll(extractOutcomes(study.getOtherOutcome(), false,
                OutcomeMeasureTypeCode.OTHER_PRE_SPECIFIED));

        OrganizationDTO sponsorDTO = importPO ? extractSponsor(study) : null;
        ResponsiblePartyDTO partyDTO = importPO ? extractResponsibleParty(study) : null;
        List<OrganizationDTO> collaborators = importPO ? extractCollaborators(study)
                : new ArrayList<OrganizationDTO>();

        DocumentDTO document = new DocumentDTO();
        document.setActiveIndicator(BlConverter.convertToBl(true));
        document.setFileName(StConverter.convertToSt(nctIdStr + ".xml"));
        document.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        document.setText(EdConverter.convertToEd(xml.getBytes()));

        if (isUpdate) {
            return trialRegistrationService.updateAbbreviatedStudyProtocol(
                    studyProtocolDTO, nctID, sponsorDTO,
                    investigatorDTO, partyDTO, centralContactDTO, overallStatusDTO,
                    regAuthDTO, arms, eligibility, outcomes, collaborators,
                    Arrays.asList(document)).getExtension();
        } else {
            return trialRegistrationService.createAbbreviatedStudyProtocol(
                    studyProtocolDTO, nctID, leadOrgDTO, leadOrgID, sponsorDTO,
                    investigatorDTO, partyDTO, centralContactDTO, overallStatusDTO,
                    regAuthDTO, arms, eligibility, outcomes, collaborators,
                    Arrays.asList(document)).getExtension();
        }
    }

    private OrganizationDTO getGenericOrganization() {
        OrganizationDTO dto = getNewOrganizationDTO();
        dto.setName(EnOnConverter.convertToEnOn(GENERIC_ORG_NAME));
        return dto;
    }

    private ResponsiblePartyDTO extractResponsibleParty(ClinicalStudy study)
            throws PAException {
        ResponsiblePartyDTO partyDTO = null;
        final ResponsiblePartyStruct party = study.getResponsibleParty();
        if (party != null && party.getResponsiblePartyType() != null) {
            final String type = party.getResponsiblePartyType();
            partyDTO = new ResponsiblePartyDTO();
            if ("Sponsor".equalsIgnoreCase(type)) {
                partyDTO.setType(ResponsiblePartyType.SPONSOR);
            } else if ("Principal Investigator".equalsIgnoreCase(type)) {
                partyDTO.setType(ResponsiblePartyType.PRINCIPAL_INVESTIGATOR);
                extractRespPartyInvestigatorInfo(partyDTO, party);
            } else if ("Sponsor-Investigator".equalsIgnoreCase(type)) {
                partyDTO.setType(ResponsiblePartyType.SPONSOR_INVESTIGATOR);
                extractRespPartyInvestigatorInfo(partyDTO, party);
            } else {
                throw new PAException("Unsupported responsible party type: "
                        + type);
            }
        }
        return partyDTO;
    }

    /**
     * @param partyDTO
     * @param party
     * @throws PAException
     */
    private void extractRespPartyInvestigatorInfo(ResponsiblePartyDTO partyDTO,
            final ResponsiblePartyStruct party) throws PAException {
        final String agency = left(party.getInvestigatorAffiliation(), L_160);
        if (isBlank(agency)) {
            throw new PAException(
                    "Responsbile party's investigator affiliation is missing");
        }
        OrganizationDTO org = getNewOrganizationDTO();
        org.setName(EnOnConverter.convertToEnOn(agency));

        if (isBlank(party.getInvestigatorFullName())) {
            throw new PAException(
                    "Responsbile party's investigator name information is missing");
        }
        PersonDTO person = getNewPersonDTO();
        person.setName(breakDownCtGovPersonName(EnPnConverter.convertToEnPn(
                null, null, left(party.getInvestigatorFullName(), L_50), null,
                null)));
        setFullNameCtGovStyle(person, null,
                null, party.getInvestigatorFullName(),
                null);

        partyDTO.setTitle(left(party.getInvestigatorTitle(), L_200));
        partyDTO.setAffiliation(org);
        partyDTO.setInvestigator(person);
    }

    /**
     * @return
     * @throws PAException
     */
    private String getCurrentUser() throws PAException {
        User csmUser = CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser());
        RegistryUser ru = registryUserService.getUser(csmUser.getLoginName());
        if (ru != null) {
            return ru.getFullName();
        } else {
            return CsmUserUtil.getDisplayUsername(csmUser);
        }
    }

    private void createImportLogEntry(String trialNciId, String nctIdStr, // NOPMD
            String title, String action, String status, String user, boolean needsReview)
            throws PAException {
        CTGovImportLog log = new CTGovImportLog();
        log.setNciID(trialNciId);
        log.setNctID(left(nctIdStr, L_20));
        log.setTitle(left(title, L_4000));
        log.setAction(action);
        log.setImportStatus(left(status, L_200));
        log.setUserCreated(user);
        log.setDateCreated(new Date());
        log.setReviewRequired(needsReview);
        createCtGovImportLogEntry(log);
    }

    private void createCtGovImportLogEntry(CTGovImportLog log) {
        org.hibernate.classic.Session currentSession = null;
        try {
            SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) PaHibernateUtil
                    .getHibernateHelper().getSessionFactory();
            currentSession = sessionFactoryImplementor.openSession(null, true,
                    false, ConnectionReleaseMode.AFTER_STATEMENT);
            currentSession.save(log);
            currentSession.flush();
        } finally {
            if (currentSession != null) {
                currentSession.close();
            }
        }

    }

    private List<OrganizationDTO> extractCollaborators(ClinicalStudy study)
            throws PAException {
        List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
        if (study.getSponsors() != null) {
            for (SponsorStruct struct : study.getSponsors().getCollaborator()) {
                OrganizationDTO dto = getNewOrganizationDTO();
                dto.setName(EnOnConverter.convertToEnOn(left(
                        struct.getAgency(), L_160)));
                if (isBlank(struct.getAgency())) {
                    throw new PAException(
                            "Collaborator organization is missing a name");
                }
                list.add(dto);
            }
        }
        return list;
    }

    private OrganizationDTO extractSponsor(ClinicalStudy study)
            throws PAException {
        if (study.getSponsors() == null
                || study.getSponsors().getLeadSponsor() == null) {
            return null;
        }
        OrganizationDTO dto = getNewOrganizationDTO();
        final String agency = left(study.getSponsors().getLeadSponsor()
                .getAgency(), L_160);
        if (isBlank(agency)) {
            throw new PAException("Lead sponsor organization is missing a name");
        }
        dto.setName(EnOnConverter.convertToEnOn(agency));
        return dto;
    }

    private List<StudyOutcomeMeasureDTO> extractOutcomes(
            List<ProtocolOutcomeStruct> list, boolean primaryIndicator,
            OutcomeMeasureTypeCode typeCode) {
        List<StudyOutcomeMeasureDTO> outcomes = new ArrayList<StudyOutcomeMeasureDTO>();
        for (ProtocolOutcomeStruct struct : list) {
            StudyOutcomeMeasureDTO outcome = new StudyOutcomeMeasureDTO();
            outcome.setName(StConverter.convertToSt(left(struct.getMeasure(),
                    L_2000)));
            outcome.setSafetyIndicator(BlConverter.convertToBl(BooleanUtils
                    .toBoolean(struct.getSafetyIssue())));
            outcome.setPrimaryIndicator(BlConverter
                    .convertToBl(primaryIndicator));
            outcome.setTimeFrame(StConverter.convertToSt(left(
                    struct.getTimeFrame(), L_254)));
            outcome.setDescription(StConverter.convertToSt(left(
                    struct.getDescription(), L_600)));
            outcome.setTypeCode(CdConverter.convertToCd(typeCode));
            outcomes.add(outcome);
        }
        return outcomes;
    }

    private StudyRegulatoryAuthorityDTO extractRegulatoryAuthorityDTO(
            ClinicalStudy study) throws PAException {
        if (study.getOversightInfo() == null
                || study.getOversightInfo().getAuthority().isEmpty()) {
            return null;
        }
        String regulatoryAuthority = study.getOversightInfo().getAuthority()
                .get(0);
        String authorityName = EMPTY;
        String countryName = EMPTY;
        if (isNotEmpty(regulatoryAuthority)
                && !UNSPECIFIED.equalsIgnoreCase(regulatoryAuthority)) {
            int index = indexOf(regulatoryAuthority, ':');
            if (index != -1) {
                authorityName = regulatoryAuthority.substring(index + 1).trim();
                countryName = regulatoryAuthority.substring(0, index).trim();
            } else {
                throw new PAException(
                        "Unrecognizable regulatory authority information: "
                                + regulatoryAuthority);
            }
        }

        final Long regulatoryAuthorityId = findRegulatoryAuthorityId(
                countryName, authorityName);
        if (regulatoryAuthorityId != null) {
            StudyRegulatoryAuthorityDTO dto = new StudyRegulatoryAuthorityDTO();
            dto.setRegulatoryAuthorityIdentifier(IiConverter
                    .convertToRegulatoryAuthorityIi(regulatoryAuthorityId));
            return dto;
        } else {
            return null;
        }
    }

    private Long findRegulatoryAuthorityId(String countryName,
            String authorityName) throws PAException {
        Long id = regulatoryAuthorityService.getRegulatoryAuthorityId(
                authorityName, countryName);
        if (id == null) {
            LOG.warn("Unable to find a regulatory authority: " + authorityName
                    + " in " + countryName);

        }
        return id;
    }

    private StudyOverallStatusDTO extractOverallStatusDTO(ClinicalStudy study)
            throws PAException {

        StudyOverallStatusDTO status = new StudyOverallStatusDTO();
        final String studyStatus = convertCtGovValue(study.getOverallStatus());
        status.setStatusCode(CdConverter.convertStringToCd(checkCodeExistence(
                studyStatus, StudyStatusCode.class)));
        status.setStatusDate(TsConverter.convertToTs(new Date()));

        String whyStopped = left(study.getWhyStopped(), L_2000);
        if (StudyStatusCode.getByCode(studyStatus) != null
                && StudyStatusCode.getByCode(studyStatus).requiresReasonText()
                && isBlank(whyStopped)) {
            whyStopped = NOT_PROVIDED;
        }
        status.setReasonText(StConverter.convertToSt(whyStopped));
        return status;
    }

    private OrganizationDTO getNewOrganizationDTO() {
        // ClinicalTrials.gov API will not provide nothing but a name for an organization.
        // PO won't accept organization
        // curation requests without an address. Hence, we will do what PDQ
        // Import does: a fake address.
        return AbstractPDQTrialServiceHelper.getUnknownOrganizationDTO();
    }

    private PersonDTO extractInvestigator(ClinicalStudy study)
            throws PAException {
        InvestigatorStruct investigator = findInvestigatorElement(study);
        if (investigator != null) {
            PersonDTO person = getNewPersonDTO();
            if (isBlank(investigator.getLastName())) {
                throw new PAException("Investigator is missing a last name");
            }
            person.setName(breakDownCtGovPersonName(EnPnConverter
                    .convertToEnPn(left(investigator.getFirstName(), L_50),
                            left(investigator.getMiddleName(), L_50),
                            left(investigator.getLastName(), L_50), null,
                            left(investigator.getDegrees(), L_10))));
            setFullNameCtGovStyle(person, investigator.getFirstName(),
                    investigator.getMiddleName(), investigator.getLastName(),
                    investigator.getDegrees());
            return person;
        } else {
            return null;
        }

    }

    private InvestigatorStruct findInvestigatorElement(ClinicalStudy study) {
        if (study.getOverallOfficial().isEmpty()) {
            return null;
        }
        for (InvestigatorStruct element : study.getOverallOfficial()) {
            if ("Principal Investigator".equalsIgnoreCase(element.getRole())) {
                return element;
            }
        }
        return study.getOverallOfficial().get(0);

    }

    private PersonDTO extractCentralContact(ContactStruct contact)
            throws PAException {
        if (contact == null) {
            return null;
        }
        if (isBlank(contact.getLastName())) {
            throw new PAException("Overall contact is missing a last name");
        }
        PersonDTO contactDTO = getNewPersonDTO();
        final EnPn ctgovPersonName = EnPnConverter
                .convertToEnPn(left(contact.getFirstName(), L_50),
                        left(contact.getMiddleName(), L_50),
                        left(contact.getLastName(), L_50), null,
                        left(contact.getDegrees(), L_10));
        contactDTO.setName(breakDownCtGovPersonName(ctgovPersonName));
        setFullNameCtGovStyle(contactDTO, contact.getFirstName(),
                contact.getMiddleName(), contact.getLastName(),
                contact.getDegrees());

        final String phone = convertCtGovPhone(isNotBlank(contact.getPhone()) ? (contact
                .getPhone() + (isNotBlank(contact.getPhoneExt()) ? ("x" + contact
                .getPhoneExt()) : EMPTY))
                : null);
        contactDTO.setTelecomAddress(PAUtil.getDset(
                defaultString(contact.getEmail(),
                        "replacewithrealemail@nih.gov"), phone));
        return contactDTO;
    }

    private void setFullNameCtGovStyle(PersonDTO p, String firstName,
            String middleName, String lastName, String degrees) {
        if (p instanceof PersonWithFullNameDTO) {
            PersonWithFullNameDTO person = (PersonWithFullNameDTO) p;
            String fullname = (StringUtils.defaultString(firstName) + " "
                    + StringUtils.defaultString(middleName) + " "
                    + StringUtils.defaultString(lastName) + (StringUtils
                    .isNotBlank(degrees) ? ", " + degrees : "")).trim()
                    .replaceAll("\\s+", " ");
            // This fullname will later be used in
            // gov.nih.nci.pa.service.util.POServiceUtils.findPersonInPoByMappingTables(PersonDTO)
            person.setFullName(fullname);
        }
    }

    /**
     * ClinicalTrials.gov XMLs contain all kinds of stuff in phone numbers that upsets PO.
     * For example, "888-662-6728 (U.S. Only)" upsets PO because we default
     * country to USA and this is not a valid USA number according to PO rules.
     * This method is trying to normalize numbers whenever possible.
     * 
     * @param phone
     * @return
     */
    String convertCtGovPhone(String phone) {
        String parsedPhone = null;
        if (phone != null) {
            parsedPhone = phone
                    .replaceAll("(?i) \\(U.S. Only\\)", EMPTY)
                    .replaceAll("^(\\d{3})\\s*(\\d{3})\\s*(\\d{4})$", "$1-$2-$3") // NOPMD                    
                    .replaceAll("^(\\d+)-(\\d+) (\\d+)$", "$1-$2-$3") // NOPMD                    
                    .replaceAll("^(\\d+)\\s+(\\d+)-(\\d+)$", "$1-$2-$3")
                    .replaceAll("^\\+1\\((\\d+)\\)\\s*(\\d+)-(\\d+)$",
                            "$1-$2-$3")
                    .replaceAll("^\\((\\d+)\\)\\s*(\\d+)-(\\d+)$", "$1-$2-$3")
                    .replaceAll("(\\d)(/|\\.)(\\d)", "$1-$3")
                    .replaceAll("^1-(\\d+)-(\\d+)-(\\d+)$", "$1-$2-$3")
                    .replaceAll(" / .+$", EMPTY)
                    .replaceAll("(?i)\\s*extn\\s*", "x")
                    .replaceAll("(?i)\\s*ext\\s*", "x")
                    .replaceAll("\\s", EMPTY).replaceAll("^1-8", "8");
            // If, after all manipulations, the phone still does not match the
            // US format mandated by PO, we blank it out
            // because otherwise we are guaranteed to receive an
            // EntityValidationException
            // from PO downflow.
            if (!parsedPhone.matches(US_CANADA_PHONE_FORMAT)) {
                parsedPhone = null;
            }

        }
        return parsedPhone;
    }

    /**
     * Interestingly enough, ClinicalTrials.gov Public API always stuffs a person's full
     * name into last_name element, leaving first_name blank. This eventually
     * upsets PO because a person must have both first and last names specified.
     * This method will do the job of breaking down a person's full name into
     * parts, whenever possible.
     * 
     * @param enpn
     * @return EnPn
     * @throws PAException
     */
    EnPn breakDownCtGovPersonName(final EnPn enpn) throws PAException { // NOPMD
        String firstName = EnPnConverter.getNamePart(enpn,
                EntityNamePartType.GIV, 0);
        String lastName = EnPnConverter.getNamePart(enpn,
                EntityNamePartType.FAM);
        String middleName = EnPnConverter.getNamePart(enpn,
                EntityNamePartType.GIV, 1);
        String suffix = EnPnConverter.getNamePart(enpn, EntityNamePartType.SFX);
        String prefix = EnPnConverter.getNamePart(enpn, EntityNamePartType.PFX);
        if (isNotBlank(firstName) && isNotBlank(lastName)) {
            // both first & last provided. nothing to do.
            return enpn;
        }
        if (isBlank(firstName) && isBlank(lastName)) {
            throw new PAException(
                    "A person information is missing both first and last name");
        }
        // At this point it is clear ClinicalTrials.gov stuffed the entire name into a
        // single element, usually <last_name>
        String fullName = isNotBlank(lastName) ? lastName : firstName;
        if (!fullName.contains(" ")) {
            // no spaces. can't break it into parts.
            lastName = fullName;
            firstName = UNKNOWN;
        } else {
            Pattern p = Pattern
                    .compile("(?i)^((?:Ms|Miss|Mrs|Mr|Dr|Atty|Prof|Hon)(?:\\.|\\s))?\\s*"
                            + "(([a-zA-Z'\\-\\.]+\\s*)+)(,\\s*(([a-zA-Z\\.\\(\\)\\-/ ,;])+))?$");
            Matcher m = p.matcher(fullName);
            if (!m.matches()) {
                // unparseable.
                lastName = fullName;
                firstName = UNKNOWN;
            } else {
                prefix = left(m.group(1), L_10);
                suffix = left(m.group(C_5), L_10);
                String mainNamePart = m.group(2).trim();
                String[] parts = mainNamePart.split("\\s+");
                switch (parts.length) { // CHECKSTYLE:OFF
                case 1:
                    lastName = mainNamePart;
                    firstName = UNKNOWN;
                    break;
                case 2:
                    firstName = parts[0];
                    lastName = parts[1];
                    break;
                case 3:
                    firstName = parts[0];
                    middleName = parts[1];
                    lastName = parts[2];
                    break;
                default:
                    firstName = parts[0];
                    middleName = parts[1];
                    lastName = StringUtils.join(
                            Arrays.copyOfRange(parts, 2, parts.length), " ");
                    break;
                }
            }
        }
        middleName = middleName != null ? middleName.replaceFirst("\\.$", "") // NOPMD
                : middleName;
        return EnPnConverter.convertToEnPn(firstName, middleName, lastName,
                prefix, suffix);
    }

    // CHECKSTYLE:ON
    private PersonDTO getNewPersonDTO() {
        // ClinicalTrials.gov API will not provide enough information about a person to be
        // created in PO.
        // PO won't accept a person
        // curation requests without an address & contact info. Hence, we will
        // do what PDQ
        // Import does: a fake address.
        return AbstractPDQTrialServiceHelper.getUnknownPersonDTO();
    }

    private DSet<Ii> extractOtherIdentifiers(IdInfoStruct idInfo) {
        DSet<Ii> otherIds = new DSet<Ii>();
        Set<Ii> iis = new HashSet<Ii>();
        for (String id : idInfo.getSecondaryId()) {
            Ii ii = new Ii();
            ii.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
            ii.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
            ii.setExtension(id);
            iis.add(ii);
        }
        otherIds.setItem(iis);
        return otherIds;
    }

    private DSet<Ii> mergeIdentifiers(DSet<Ii> mergeInto, DSet<Ii> mergeFrom) {
        if (mergeFrom != null && mergeFrom.getItem() != null) {
            l1: for (Ii id : mergeFrom.getItem()) {
                for (Ii id2 : mergeInto.getItem()) {
                    if (StringUtils.equals(id2.getExtension(),
                            id.getExtension())
                            && StringUtils.equals(id2.getRoot(), id.getRoot())) {
                        continue l1;
                    }
                }
                mergeInto.getItem().add(id);
            }
        }
        return mergeInto;
    }

    private StudySiteDTO extractLeadOrgId(IdInfoStruct idInfo)
            throws PAException {
        if (idInfo == null) {
            throw new PAException(
                    "The trial data file is missing identifiers section");
        }
        final String orgStudyId = idInfo.getOrgStudyId();
        if (isBlank(orgStudyId)) {
            throw new PAException(
                    "The trial data file is missing a lead organization identifier value");
        }
        StudySiteDTO dto = new StudySiteDTO();
        dto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(orgStudyId));
        return dto;
    }

    private List<PlannedEligibilityCriterionDTO> extractEligibility(
            EligibilityStruct elig) throws PAException {
        List<PlannedEligibilityCriterionDTO> list = new ArrayList<PlannedEligibilityCriterionDTO>();
        if (elig != null) {
            String eligibleGenderCode = elig.getGender();
            if (isNotEmpty(eligibleGenderCode)) {
                PlannedEligibilityCriterionDTO pEligibiltyCriterionDTO = new PlannedEligibilityCriterionDTO();
                pEligibiltyCriterionDTO.setCriterionName(StConverter
                        .convertToSt("GENDER"));
                pEligibiltyCriterionDTO
                        .setEligibleGenderCode(getGenderCode(elig));
                pEligibiltyCriterionDTO
                        .setCategoryCode(CdConverter
                                .convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
                pEligibiltyCriterionDTO.setInclusionIndicator(BlConverter
                        .convertToBl(Boolean.TRUE));
                list.add(pEligibiltyCriterionDTO);
            }
            String minimumValue = elig.getMinimumAge();
            String maximumValue = elig.getMaximumAge();
            PlannedEligibilityCriterionDTO pEligibiltyCriterionDTO = new PlannedEligibilityCriterionDTO();
            pEligibiltyCriterionDTO.setCriterionName(StConverter
                    .convertToSt("AGE"));
            pEligibiltyCriterionDTO.setValue(convertAgeRangeToIvlPq(
                    defaultIfNotSpecified(minimumValue, "0 Years"),
                    defaultIfNotSpecified(maximumValue, "999 Years")));
            pEligibiltyCriterionDTO.setCategoryCode(CdConverter
                    .convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
            pEligibiltyCriterionDTO.setInclusionIndicator(BlConverter
                    .convertToBl(Boolean.TRUE));
            list.add(pEligibiltyCriterionDTO);

            list.addAll(extractInlusionExclusionCriteria(elig));
        }
        return list;
    }

    private String defaultIfNotSpecified(String value, String defStr) {
        return (isBlank(value) || "N/A".equalsIgnoreCase(value)) ? defStr
                : value;
    }

    private List<PlannedEligibilityCriterionDTO> extractInlusionExclusionCriteria(
            EligibilityStruct elig) {
        List<PlannedEligibilityCriterionDTO> list = new ArrayList<PlannedEligibilityCriterionDTO>();
        String text = elig.getCriteria() != null ? elig.getCriteria()
                .getTextblock() : null;
        if (isNotBlank(text)) {
            String inclusionList = extractEligibilityBlock(text,
                    INCLUSION_CRITERIA_MARKER, EXCLUSION_CRITERIA_MARKER);
            String exclusionList = extractEligibilityBlock(text,
                    EXCLUSION_CRITERIA_MARKER, INCLUSION_CRITERIA_MARKER);
            if (isBlank(inclusionList) || isBlank(exclusionList)) {
                // treat the entire text as one large inclusion & exclusion
                // criteria
                list.add(buildEligibilityCriteriaDTO(text, Boolean.TRUE));
                list.add(buildEligibilityCriteriaDTO(text, Boolean.FALSE));
            } else {
                for (String criterion : extractCriterions(inclusionList)) {
                    list.add(buildEligibilityCriteriaDTO(criterion,
                            Boolean.TRUE));
                }
                for (String criterion : extractCriterions(exclusionList)) {
                    list.add(buildEligibilityCriteriaDTO(criterion,
                            Boolean.FALSE));
                }
            }
        }
        return list;

    }

    private List<String> extractCriterions(String block) {
        List<String> list = new ArrayList<String>();
        for (String s : block.split("(?m)$\\s+-\\s{2}")) {
            s = trim(s);
            if (!isBlank(s)) {
                list.add(s);
            }
        }
        return list;
    }

    private PlannedEligibilityCriterionDTO buildEligibilityCriteriaDTO(
            String text, Boolean inclusionIndicator) {
        PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
        dto.setCriterionName(StConverter.convertToSt(null));
        dto.setCategoryCode(CdConverter
                .convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
        dto.setInclusionIndicator(BlConverter.convertToBl(inclusionIndicator));
        dto.setTextDescription(StConverter.convertToSt(left(text, L_5000)));
        dto.setStructuredIndicator(BlConverter.convertToBl(false));
        return dto;
    }

    private String extractEligibilityBlock(String text, String startMarker,
            String endMarker) {
        Pattern p = Pattern.compile("(?s)" + startMarker + "(.*?)(" + endMarker
                + "|$)");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return m.group(1);
        }
        return EMPTY;
    }

    private Ivl<Pq> convertAgeRangeToIvlPq(String minAge, String maxAge)
            throws PAException {
        IvlConverter.JavaPq low = parseAgeValue(minAge);
        IvlConverter.JavaPq high = parseAgeValue(maxAge);
        return IvlConverter.convertPq().convertToIvl(low, high);
    }

    private JavaPq parseAgeValue(String age) throws PAException {
        if (isBlank(age) || "N/A".equalsIgnoreCase(age)) {
            return null;
        }
        Pattern p = Pattern.compile(AGE_PATTERN);
        Matcher m = p.matcher(age);
        if (m.matches()) {
            BigDecimal value = new BigDecimal(m.group(1)); // CHECKSTYLE:OFF
            final String uom = m.group(3);
            UnitsCode unit = UnitsCode.getByCode(convertCtGovValue(uom)); // CHECKSTYLE:ON
            if (unit == null) {
                throw new PAException(
                        "Age value has an unsupported unit of measure: " + age);
            }
            return new JavaPq(unit.getCode(), value, value.precision());
        } else {
            throw new PAException("Unrecognizable age value: " + age);
        }
    }

    /**
     * @param elig
     * @return
     * @throws PAException
     */
    private Cd getGenderCode(EligibilityStruct elig) throws PAException {
        final EligibleGenderCode code = EligibleGenderCode.getByCode(elig
                .getGender());
        if (code == null) {
            throw new PAException("Unrecognizable gender code: "
                    + elig.getGender());
        }
        return CdConverter.convertToCd(code);
    }

    private List<ArmDTO> extractArms(ClinicalStudy study) throws PAException {
        List<ArmDTO> arms = new ArrayList<ArmDTO>();
        for (ArmGroupStruct ctArm : study.getArmGroup()) {
            ArmDTO armDTO = new ArmDTO();
            armDTO.setName(getSt(ctArm.getArmGroupLabel(), L_200));
            armDTO.setTypeCode(CdConverter
                    .convertStringToCd(checkCodeExistence(
                            convertCtGovValue(ctArm.getArmGroupType()),
                            ArmTypeCode.class)));
            armDTO.setDescriptionText(getSt(ctArm.getDescription(), L_1000));
            arms.add(armDTO);
        }
        return arms;
    }

    @SuppressWarnings("PMD.ExcessiveMethodLength")
    private void extractStudyProtocolDTOFields(ClinicalStudy study,
            StudyProtocolDTO dto) throws PAException {

        dto.setAcronym(getSt(study.getAcronym(), L_200));
        dto.setPublicDescription(getSt(study.getBriefSummary(), L_5000));
        dto.setPublicTitle(getSt(study.getBriefTitle(), L_300));

        final Ts startDate = getTs(study.getStartDate());
        final Ts primaryCompletionDate = getTs(study.getPrimaryCompletionDate());
        final Ts completionDate = getTs(study.getCompletionDate());

        dto.setCompletionDate(completionDate);
        dto.setCompletionDateTypeCode(getDateType(study.getCompletionDate()));
        dto.setPrimaryCompletionDate(primaryCompletionDate);
        dto.setPrimaryCompletionDateTypeCode(getDateType(study
                .getPrimaryCompletionDate()));
        dto.setScientificDescription(getSt(study.getDetailedDescription(),
                L_32000));
        dto.setTargetAccrualNumber(getIvl(study.getEnrollment()));
        dto.setExpandedAccessIndicator(getYesNoAsBl(study
                .getHasExpandedAccess()));
        dto.setFdaRegulatedIndicator(getYesNoAsBl(study.getIsFdaRegulated()));
        dto.setSection801Indicator(getYesNoAsBl(study.getIsSection801()));
        dto.setKeywordText(getSt(study.getKeyword(), L_4000));
        dto.setOfficialTitle(getSt(defaultString(study.getOfficialTitle()),
                L_4000));
        dto.setPhaseCode(CdConverter.convertStringToCd(convertPhaseCode(study
                .getPhase())));
        dto.setStartDate(startDate);
        dto.setStartDateTypeCode(getDateType(study.getStartDate()));
        dto.setRecordVerificationDate(getTs(study.getVerificationDate()));
        if (study.getEligibility() != null) {
            final String healthyVolunteers = study.getEligibility()
                    .getHealthyVolunteers();
            if ("Accepts Healthy Volunteers"
                    .equalsIgnoreCase(healthyVolunteers)) {
                dto.setAcceptHealthyVolunteersIndicator(BlConverter
                        .convertToBl(true));
            } else {
                dto.setAcceptHealthyVolunteersIndicator(getYesNoAsBl(healthyVolunteers));
            }
        }
        if (study.getOversightInfo() != null) {
            dto.setDataMonitoringCommitteeAppointedIndicator(getYesNoAsBl(study
                    .getOversightInfo().getHasDmc()));
        }

        final Cd purposeCd = CdConverter.convertStringToCd(checkCodeExistence(
                extractStudyDesignElement(study, "Primary Purpose"),
                PrimaryPurposeCode.class));
        dto.setPrimaryPurposeCode(purposeCd);
        if (ISOUtil.isCdNull(purposeCd)) {
            setPrimaryPurposeCodeDefaults(dto);
        }

        if (dto instanceof InterventionalStudyProtocolDTO) {
            extractInterventionalStudyProtocolDTO(
                    (InterventionalStudyProtocolDTO) dto, study);
        } else {
            extractNonInterventionalStudyProtocolDTO(
                    (NonInterventionalStudyProtocolDTO) dto, study);
        }

        dto.setUserLastCreated(StConverter.convertToSt(CTGOVIMPORT_USERNAME));

    }

    /**
     * @param dto
     */
    private void setPrimaryPurposeCodeDefaults(StudyProtocolDTO dto) {
        dto.setPrimaryPurposeCode(CdConverter
                .convertToCd(PrimaryPurposeCode.OTHER));
        dto.setPrimaryPurposeOtherText(StConverter
                .convertToSt("Not provided by ClinicalTrials.gov"));
        dto.setPrimaryPurposeAdditionalQualifierCode(CdConverter
                .convertToCd(PrimaryPurposeAdditionalQualifierCode.OTHER));
    }

    @SuppressWarnings("rawtypes")
    private String checkCodeExistence(String code, Class enumOrLovClass)
            throws PAException {
        if (isNotBlank(code)) {
            Object valueByCode = null;
            try {
                valueByCode = enumOrLovClass.getMethod("getByCode",
                        String.class).invoke(null, code);
            } catch (Exception e) {
                LOG.error(e, e);
                throw new PAException(e.getMessage()); // NOPMD
            }
            if (valueByCode == null) {
                throw new PAException(
                        "The following ClinicalTrials.gov value does not correspond to a valid CTRP code: \""
                                + code + "\" of class "
                                + enumOrLovClass.getSimpleName());
            }
        }
        return code;
    }

    private String extractStudyDesignElement(ClinicalStudy study, String name) {
        if (isNotBlank(study.getStudyDesign())) {
            Pattern p = Pattern.compile(name + ":(([a-zA-Z\\s\\-/])+)");
            Matcher m = p.matcher(study.getStudyDesign());
            if (m.find()) {
                return convertCtGovValue(m.group(1).trim());
            }
        }
        return null;
    }

    private String convertCtGovValue(String ctgovValue) {
        return CTGOV_TO_CTRP_MAP.get(ctgovValue) != null ? CTGOV_TO_CTRP_MAP
                .get(ctgovValue) : ctgovValue;
    }

    private String convertPhaseCode(String phase) throws PAException {
        if (isBlank(phase)) {
            return null;
        }
        return checkCodeExistence(convertCtGovValue(phase), PhaseCode.class);
    }

    private St getSt(List<String> list, int len) {
        if (CollectionUtils.isEmpty(list)) {
            return StConverter.convertToSt(null);
        }
        return StConverter.convertToSt(left(join(list, ", "), len));
    }

    private Bl getYesNoAsBl(String str) throws PAException {
        if (isBlank(str)) {
            return BlConverter.convertToBl(null);
        } else if ("Yes".equalsIgnoreCase(str)) {
            return BlConverter.convertToBl(true);
        } else if ("No".equalsIgnoreCase(str)) {
            return BlConverter.convertToBl(false);
        } else {
            throw new PAException(
                    "Unrecognizable value of a Yes/No indicator: " + str);
        }

    }

    /**
     * @param enrollment
     * @return
     */
    private Ivl<Int> getIvl(EnrollmentStruct enrollment) {
        return enrollment != null ? IvlConverter.convertInt().convertToIvl(
                enrollment.getContent(), null) : IvlConverter.convertInt()
                .convertToIvl(null, null);
    }

    private St getSt(String str, int length) {
        return StConverter.convertToSt(left(str, length));
    }

    private St getSt(TextblockStruct str, int length) {
        return StConverter.convertToSt(left(str != null ? str.getTextblock()
                : null, length));
    }

    private Cd getDateType(DateStruct date) throws PAException {
        if (date == null) {
            return CdConverter.convertStringToCd(null);
        }
        if (isBlank(date.getType())) {
            return determineDateTypeFromDateValue(date);
        }
        if (ActualAnticipatedTypeCode.getByCode(date.getType()) == null) {
            throw new PAException("Unrecognizable date type code: "
                    + date.getType());
        }
        return CdConverter.convertStringToCd(date.getType());
    }

    private Cd determineDateTypeFromDateValue(DateStruct ctGovDate)
            throws PAException {
        Ts ts = getTs(ctGovDate);
        if (!ISOUtil.isTsNull(ts)) {
            Date date = TsConverter.convertToTimestamp(ts);
            return date.before(new Date()) ? CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.ACTUAL)
                    : CdConverter
                            .convertToCd(ActualAnticipatedTypeCode.ANTICIPATED);
        }
        return CdConverter.convertStringToCd(null);
    }

    private Ts getTs(DateStruct date) throws PAException {
        if (date == null || isBlank(date.getContent())) {
            return TsConverter.convertToTs(null);
        }
        DateFormat fmt = new SimpleDateFormat("MMMM yyyy", Locale.US);
        Date d;
        try {
            d = fmt.parse(date.getContent());
        } catch (ParseException e) {
            throw new PAException("Unrecognizable date format: " // NOPMD
                    + date.getContent());
        }
        return TsConverter.convertToTs(d);
    }

    private void extractNonInterventionalStudyProtocolDTO(
            NonInterventionalStudyProtocolDTO dto, ClinicalStudy study)
            throws PAException {
        dto.setBiospecimenDescription(StConverter.convertToSt(left(study
                .getBiospecDescr() != null ? study.getBiospecDescr()
                .getTextblock() : null, L_800)));
        dto.setBiospecimenRetentionCode(CdConverter.convertStringToCd(study
                .getBiospecRetention()));
        dto.setNumberOfGroups(getInt(study.getNumberOfGroups()));

        dto.setStudyModelCode(CdConverter.convertStringToCd(checkCodeExistence(
                extractStudyDesignElement(study, "Observational Model"),
                StudyModelCode.class)));
        dto.setTimePerspectiveCode(CdConverter
                .convertStringToCd(checkCodeExistence(
                        extractStudyDesignElement(study, "Time Perspective"),
                        TimePerspectiveCode.class)));
        dto.setStudySubtypeCode(CdConverter
                .convertToCd(StudySubtypeCode.OBSERVATIONAL));
        
        final EligibilityStruct elig = study.getEligibility();
        if (elig != null && StringUtils.isNotBlank(elig.getSamplingMethod())) {
            dto.setSamplingMethodCode(CdConverter
                    .convertStringToCd(checkCodeExistence(
                            elig.getSamplingMethod(), SamplingMethodCode.class)));
        }
        if (elig != null && elig.getStudyPop() != null
                && StringUtils.isNotBlank(elig.getStudyPop().getTextblock())) {
            dto.setStudyPopulationDescription(StConverter.convertToSt(left(elig
                    .getStudyPop().getTextblock(), L_800)));
        }

    }

    private void extractInterventionalStudyProtocolDTO(
            InterventionalStudyProtocolDTO dto, ClinicalStudy study)
            throws PAException {
        dto.setNumberOfInterventionGroups(getInt(study.getNumberOfArms()));
        dto.setAllocationCode(CdConverter.convertStringToCd(checkCodeExistence(
                extractStudyDesignElement(study, "Allocation"),
                AllocationCode.class)));
        dto.setStudyClassificationCode(CdConverter
                .convertStringToCd(checkCodeExistence(
                        extractStudyDesignElement(study,
                                "Endpoint Classification"),
                        StudyClassificationCode.class)));
        dto.setDesignConfigurationCode(CdConverter
                .convertStringToCd(checkCodeExistence(
                        extractStudyDesignElement(study, "Intervention Model"),
                        DesignConfigurationCode.class)));
        dto.setBlindingSchemaCode(CdConverter
                .convertStringToCd(checkCodeExistence(
                        extractStudyDesignElement(study, MASKING),
                        BlindingSchemaCode.class)));
        dto.setBlindedRoleCode(extractBlindingRoleCodes(study));
    }

    /**
     * @return
     * @throws PAException
     */
    private DSet<Cd> extractBlindingRoleCodes(ClinicalStudy study)
            throws PAException {
        DSet<Cd> set = new DSet<Cd>();
        set.setItem(new HashSet<Cd>());
        if (isNotBlank(study.getStudyDesign())) {
            Pattern p = Pattern.compile(MASKING
                    + ":(([a-zA-Z\\s\\-/])+)\\((.+?)\\)");
            Matcher m = p.matcher(study.getStudyDesign());
            if (m.find()) {
                String codesStr = m.group(INDEX_3);
                for (String code : codesStr.split(",")) {
                    set.getItem().add(
                            CdConverter.convertStringToCd(checkCodeExistence(
                                    code.trim(), BlindingRoleCode.class)));
                }
            }
        }
        return set;
    }

    private Int getInt(String str) {
        if (isBlank(str)) {
            return IntConverter.convertToInt((String) null);
        }
        return IntConverter.convertToInt(str);
    }

    private StudyProtocolDTO instantiateStudyProtocolDTO(ClinicalStudy study)
            throws PAException {
        final String studyType = study.getStudyType();
        if ("Interventional".equalsIgnoreCase(studyType)) {
            return new InterventionalStudyProtocolDTO();
        } else if ("Observational".equalsIgnoreCase(studyType)) {
            return new NonInterventionalStudyProtocolDTO();
        } else if ("Expanded Access".equalsIgnoreCase(studyType)) {
            return new InterventionalStudyProtocolDTO();
        } else {
            throw new PAException("Unsupported study type: " + studyType);
        }

    }

    private void verifyTrialCategory(ClinicalStudy study) throws PAException {
        CTGovStudyAdapter adapter = new CTGovStudyAdapter(study);
        if (!"Industry".equalsIgnoreCase(adapter.getStudyCategory())
                && !"Other".equalsIgnoreCase(adapter.getStudyCategory())) {
            throw new PAException(
                    "Unable to import study "
                            + adapter.getNctId()
                            + " because it does not belong to Industrial/Consortia category");
        }
    }

    @Override        
    @SuppressWarnings("PMD.ExcessiveParameterList")
    // CHECKSTYLE:OFF More than 7 Parameters
    public List<CTGovImportLog> getLogEntries(String nciIdentifier, String nctIdentifier, String officialTitle, 
            String action, String importStatus, String userCreated, Date onOrAfter, Date onOrBefore)
            throws PAException {
        // CHECKSTYLE:ON
        Session session = PaHibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(CTGovImportLog.class);
        criteria.setMaxResults(L_10000);
        if (StringUtils.isNotEmpty(nciIdentifier)) {
            criteria.add(Restrictions.like("nciID", "%" + nciIdentifier + "%"));
        }
        if (StringUtils.isNotEmpty(nctIdentifier)) {
            criteria.add(Restrictions.like("nctID", "%" + nctIdentifier + "%"));
        }
        if (StringUtils.isNotEmpty(officialTitle)) {
            criteria.add(Restrictions.like("title", "%" + officialTitle + "%"));
        }
        if (StringUtils.isNotEmpty(action)) {
            criteria.add(Restrictions.eq("action", action));
        }
        if (StringUtils.isNotEmpty(userCreated)) {
            criteria.add(Restrictions.eq("userCreated", userCreated));
        }
        if (StringUtils.isNotEmpty(importStatus)) {
            criteria.add(Restrictions.like("importStatus", importStatus + "%"));
        }
        //start date is specified but end date is not specified
        if (onOrAfter != null && onOrBefore == null) {
            criteria.add(Restrictions.ge("dateCreated", onOrAfter));
        } else if (onOrBefore != null && onOrAfter == null) {                
            //end date is specified but start date is not specified
            criteria.add(Restrictions.le("dateCreated", onOrBefore));
        } else if (onOrBefore != null && onOrAfter != null) {
            //both start and end dates are specified
            criteria.add(Restrictions.between("dateCreated", onOrAfter, onOrBefore));
        }
        criteria.addOrder(Order.desc("dateCreated"));
        return criteria.list();
    }
    
    /**
     * @param regulatoryAuthorityService
     *            the regulatoryAuthorityService to set
     */
    public void setRegulatoryAuthorityService(
            RegulatoryInformationServiceRemote regulatoryAuthorityService) {
        this.regulatoryAuthorityService = regulatoryAuthorityService;
    }

    /**
     * @param trialRegistrationService
     *            the trialRegistrationService to set
     */
    public void setTrialRegistrationService(
            TrialRegistrationServiceLocal trialRegistrationService) {
        this.trialRegistrationService = trialRegistrationService;
    }

    /**
     * @param paServiceUtils
     *            the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @param studyProtocolService
     *            the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param registryUserService
     *            the registryUserService to set
     */
    public void setRegistryUserService(
            RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param documentWorkflowStatusService
     *            the documentWorkflowStatusService to set
     */
    public void setDocumentWorkflowStatusService(
            DocumentWorkflowStatusServiceLocal documentWorkflowStatusService) {
        this.documentWorkflowStatusService = documentWorkflowStatusService;
    }

    /**
     * @param protocolComparisonService the protocolComparisonService to set
     */
    public void setProtocolComparisonService(
            ProtocolComparisonServiceLocal protocolComparisonService) {
        this.protocolComparisonService = protocolComparisonService;
    }

}
