package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseAlternameDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.LEXEVSLookupException;
import gov.nih.nci.pa.util.NCItTermsLookup;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for Managing Intervention/Disease terms
 *
 * @author Gopal Unnikrishnan
 * @since 12/1/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class ManageTermsAction extends ActionSupport implements Preparable {

    private static final String ALTNAME_TYPECODE_SYNONYM = "Synonym";

    private static final String PARENT_DISEASE_CODE_ISA = "ISA";

    private static final long serialVersionUID = 9154119501161489767L;

    private static final String INTERVENTION = "intervention"; // NOPMD
    private static final String SEARCH_INTERVENTION = "searchIntervention";
    private static final String SYNC_INTERVENTION = "syncIntervention";

    private static final String DISEASE = "disease"; // NOPMD
    private static final String SEARCH_DISEASE = "searchDisease";
    private static final String SYNC_DISEASE = "syncDisease";

    private static final String  AJAX_RESPONSE = "ajaxResponse";

    private InterventionServiceLocal interventionService;
    private InterventionAlternateNameServiceLocal interventionAltNameService;
    private InterventionWebDTO intervention = new InterventionWebDTO();
    private InterventionWebDTO currentIntervention = new InterventionWebDTO();

    private PDQDiseaseParentServiceRemote diseaseParentService;
    private PDQDiseaseServiceLocal diseaseService;
    private PDQDiseaseAlternameServiceLocal diseaseAltNameService;
    private DiseaseWebDTO disease = new DiseaseWebDTO();
    private DiseaseWebDTO currentDisease = new DiseaseWebDTO();

    private boolean importTerm = false;

    private InputStream ajaxResponseStream;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        // Initialize intervention services
        interventionService = PaRegistry.getInterventionService();
        interventionAltNameService = PaRegistry.getInterventionAlternateNameService();

        // Initialize disease services
        diseaseService = PaRegistry.getDiseaseService();
        diseaseParentService = PaRegistry.getDiseaseParentService();
        diseaseAltNameService = PaRegistry.getDiseaseAlternameService();
    }

    /**
     * Deafult action
     *
     * @return res
     * @throws PAException
     *             exception
     */
    @Override
    public String execute() throws PAException {
        return SUCCESS;
    }

    /**
     * Create intervention
     *
     * @return view
     */
    public String createIntervention() {
        importTerm = false;
        return INTERVENTION;
    }

    /**
     * Import intervention
     *
     * @return view
     */
    public String importIntervention() {
        importTerm = true;
        return INTERVENTION;
    }

    /**
     * Create Disease
     *
     * @return view
     */
    public String createDisease() {
        importTerm = false;
        return DISEASE;
    }

    /**
     * Import Disease
     *
     * @return view
     */
    public String importDisease() {
        importTerm = true;
        return DISEASE;
    }

    /**
     * Save a new intervention
     *
     * @return view
     */
    public String saveIntervention() {
        try {
            if (!validateIntervention()) {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "Please correct the errors listed below and resubmit");
                return INTERVENTION;
            }

            if (getExistingIntervention(intervention.getNtTermIdentifier()) == null) { // Check
                                                                                       // if
                // intervention
                // with same
                // NCTid exists
                InterventionDTO dto = new InterventionDTO();
                dto.setPdqTermIdentifier(StConverter.convertToSt(intervention.getIdentifier()));
                dto.setNtTermIdentifier(StConverter.convertToSt(intervention.getNtTermIdentifier()));
                dto.setName(StConverter.convertToSt(intervention.getName()));
                dto.setCtGovTypeCode(CdConverter.convertStringToCd(intervention.getCtGovType()));
                dto.setTypeCode(CdConverter.convertStringToCd(intervention.getType()));
                dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                dto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                dto = interventionService.create(dto);

                if (intervention.getAlterNames() != null) {
                    for (String altName : intervention.getAlterNames()) {
                        InterventionAlternateNameDTO altDto = new InterventionAlternateNameDTO();
                        altDto.setName(StConverter.convertToSt(altName));
                        altDto.setNameTypeCode(StConverter.convertToSt(ALTNAME_TYPECODE_SYNONYM));
                        altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                        altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil
                                .today())));
                        altDto.setInterventionIdentifier(dto.getIdentifier());
                        interventionAltNameService.create(altDto);
                    }
                }

                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                        "New intervention " + intervention.getNtTermIdentifier() + " added successfully");
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "Intervention with NCIt code " + intervention.getNtTermIdentifier() + " already exists!");
                return INTERVENTION;
            }
        } catch (PAException e) {
            LOG.error("Error saving intervention", e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return INTERVENTION;
        }
        return SUCCESS;
    }

    /**
     * Search for intervention by NCIt code in NCI Thesaurus
     *
     * @return view
     */
    public String searchIntervention() {
        String ncitCode = intervention.getNtTermIdentifier();
        importTerm = true;
        if (StringUtils.isEmpty(ncitCode)) {
            if (ServletActionContext.getRequest().getParameter("searchStart") == null) {
                ServletActionContext.getRequest()
                        .setAttribute(Constants.FAILURE_MESSAGE, "Enter valid NCIt Identifier");
            }
            return SEARCH_INTERVENTION;
        }
        try {
            ncitCode = ncitCode.toUpperCase(Locale.getDefault());
            InterventionWebDTO temp = new NCItTermsLookup().lookupIntervention(ncitCode);
            if (temp != null) {
                intervention = temp;
                // Check if the term already exists
                InterventionDTO existingIntrvDto = getExistingIntervention(intervention.getNtTermIdentifier());
                if (existingIntrvDto != null) {
                    currentIntervention = new InterventionWebDTO();
                    currentIntervention.setIdentifier(existingIntrvDto.getPdqTermIdentifier().getValue());
                    currentIntervention.setNtTermIdentifier(existingIntrvDto.getNtTermIdentifier().getValue());
                    currentIntervention.setName(existingIntrvDto.getName().getValue());
                    currentIntervention.setCtGovType(CdConverter.convertCdToString(existingIntrvDto
                                                                                    .getCtGovTypeCode()));
                    currentIntervention.setType(CdConverter.convertCdToString(existingIntrvDto
                                                                                         .getTypeCode()));

                    List<InterventionAlternateNameDTO> altNames = interventionAltNameService
                            .getByIntervention(existingIntrvDto.getIdentifier());
                    if (altNames != null && !altNames.isEmpty()) {
                        for (Iterator<InterventionAlternateNameDTO> iterator = altNames.iterator();
                                iterator.hasNext();) {
                            InterventionAlternateNameDTO interventionAlternateNameDTO = iterator.next();
                            if (ALTNAME_TYPECODE_SYNONYM.equals(interventionAlternateNameDTO.getNameTypeCode()
                                    .getValue())) {
                                currentIntervention.getAlterNames().add(
                                        interventionAlternateNameDTO.getName().getValue());
                            }

                        }
                    }
                    ServletActionContext.getRequest().getSession().setAttribute("intervention", intervention);
                    ServletActionContext.getRequest().setAttribute(
                            Constants.FAILURE_MESSAGE,
                            "Intervention with NCIt code '" + ncitCode
                                    + "' already present in CTRP, compare the values below and click "
                                    + "'Sync Term' to update the CTRP term with values from NCIt");
                    return SYNC_INTERVENTION;
                }
            } else {
                ServletActionContext.getRequest().setAttribute(
                        Constants.FAILURE_MESSAGE,
                        "No intervention with NCIt code '" + ncitCode
                                + "' found in NCI Thesaurus, try a different code");

                return SEARCH_INTERVENTION;

            }
        } catch (Exception e) {
            LOG.error("Error looking up intervention", e);
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Error looking up intervention, make sure the NCIt identifier '" + ncitCode
                            + "' corresponds to a intervention. Ther error was: " + e.getLocalizedMessage());
            return SEARCH_INTERVENTION;
        }

        return INTERVENTION;
    }

    /**
     * Synchronize an existing CTRP intervention with intervention retrieved
     * from NCIt
     *
     * @return view
     * @throws PAException
     *             PAException
     */
    public String syncIntervention() throws PAException {
        InterventionWebDTO newIntervention = (InterventionWebDTO) ServletActionContext.getRequest().getSession()
                .getAttribute("intervention");
        if (newIntervention != null) {
            ServletActionContext.getRequest().getSession().removeAttribute("intervention");
            intervention = newIntervention;
        }

        InterventionDTO currentIntrv = getExistingIntervention(intervention.getNtTermIdentifier());
        if (currentIntrv != null) {
            currentIntrv.setName(StConverter.convertToSt(intervention.getName()));

            // Remove existing synonyms
            List<InterventionAlternateNameDTO> altNames = interventionAltNameService.getByIntervention(currentIntrv
                    .getIdentifier());
            if (altNames != null && !altNames.isEmpty()) {
                for (Iterator<InterventionAlternateNameDTO> iterator = altNames.iterator(); iterator.hasNext();) {
                    InterventionAlternateNameDTO interventionAlternateNameDTO = (InterventionAlternateNameDTO) iterator
                            .next();
                    if (ALTNAME_TYPECODE_SYNONYM.equals(interventionAlternateNameDTO.getNameTypeCode().getValue())) {
                        interventionAltNameService.delete(interventionAlternateNameDTO.getIdentifier());
                    }

                }
            }

            // Save new synonyms
            if (intervention.getAlterNames() != null) {
                for (String altName : intervention.getAlterNames()) {
                    InterventionAlternateNameDTO altDto = new InterventionAlternateNameDTO();
                    altDto.setName(StConverter.convertToSt(altName));
                    altDto.setNameTypeCode(StConverter.convertToSt(ALTNAME_TYPECODE_SYNONYM));
                    altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    altDto.setInterventionIdentifier(currentIntrv.getIdentifier());
                    interventionAltNameService.create(altDto);
                }
            }

            interventionService.update(currentIntrv);
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE,
                    "Intervention " + currentIntrv.getNtTermIdentifier().getValue()
                            + " synchronized with NCI thesaurus");
        } else {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    "No intervention with NCIt code '" + intervention.getNtTermIdentifier() + "' found in CTRP");
        }
        return SUCCESS;
    }

    /**
     * Search for existing intervention by NCIt Id
     *
     * @param ncitId
     * @return matched intervention DTO, <code>null</code> if no match was found
     * @throws PAException
     */
    private InterventionDTO getExistingIntervention(String ncitId) throws PAException {
        InterventionDTO dto = new InterventionDTO();
        dto.setNtTermIdentifier(StConverter.convertToSt(ncitId));
        List<InterventionDTO> searcchResults = interventionService.search(dto);
        if (!searcchResults.isEmpty()) {
            return searcchResults.get(0);
        }
        return null;
    }

    /**
     * Validate data entered in the intervention form
     */
    private boolean validateIntervention() {
        boolean valid = true;
        if (StringUtils.isEmpty(intervention.getNtTermIdentifier())) {
            addFieldError("intervention.ntTermIdentifier", getText("manageTerms.fieldError.ntTermIdentifier"));
            valid = false;
        }

        if (StringUtils.isEmpty(intervention.getName())) {
            addFieldError("intervention.name", getText("manageTerms.fieldError.name"));
            valid = false;
        }

        return valid;
    }

    /**
     * @return the intervention
     */
    public InterventionWebDTO getIntervention() {
        return intervention;
    }

    /**
     * @param intervention
     *            the intervention to set
     */
    public void setIntervention(InterventionWebDTO intervention) {
        this.intervention = intervention;
    }

    /**
     * @return the currentintervention
     */
    public InterventionWebDTO getCurrentIntervention() {
        return currentIntervention;
    }

    /**
     * @param currentIntervention
     *            the currentIntervention to set
     */
    public void setCurrentIntervention(InterventionWebDTO currentIntervention) {
        this.currentIntervention = currentIntervention;
    }

    /**
     * @return the importTerm
     */
    public boolean isImportTerm() {
        return importTerm;
    }

    /**
     * @param importTerm
     *            the importTerm to set
     */
    public void setImportTerm(boolean importTerm) {
        this.importTerm = importTerm;
    }

    // Disease actions and methods

    /**
     * Save a new disease/condition
     *
     * @return view
     */
    public String saveDisease() {
        try {
            if (!validateDisease()) {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "Please correct the errors listed below and resubmit");
                return DISEASE;
            }
            if (getExistingDisease(disease.getNtTermIdentifier()) == null) {

                List<String> missingTerms = new ArrayList<String>();
                List<PDQDiseaseParentDTO> parentDtos = new ArrayList<PDQDiseaseParentDTO>();
                List<PDQDiseaseParentDTO> childDtos = new ArrayList<PDQDiseaseParentDTO>();

                // Check if all parent and children terms exists in CTRP
                for (Iterator<String> iterator = disease.getParentTermList().iterator(); iterator.hasNext();) {
                    String parentTerm = iterator.next();
                    String parentCode = parentTerm.split(":")[0];
                    PDQDiseaseDTO parent = getExistingDisease(parentCode);
                    if (parent == null) {
                        missingTerms.add(parentCode);
                        parent = retrieveAndSaveMissingTerm(parentCode);
                    }
                    PDQDiseaseParentDTO p = new PDQDiseaseParentDTO();
                    p.setParentDiseaseCode(StConverter.convertToSt(PARENT_DISEASE_CODE_ISA));
                    p.setParentDiseaseIdentifier(parent.getIdentifier());
                    p.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    p.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    parentDtos.add(p);
                }

                for (Iterator<String> iterator = disease.getChildTermList().iterator(); iterator.hasNext();) {
                    String childTerm = iterator.next();
                    String childCode = childTerm.split(":")[0];
                    PDQDiseaseDTO child = getExistingDisease(childCode);
                    if (child == null) {
                        missingTerms.add(childCode);
                        child = retrieveAndSaveMissingTerm(childCode);
                    }
                    PDQDiseaseParentDTO c = new PDQDiseaseParentDTO();
                    c.setDiseaseIdentifier(child.getIdentifier());
                    c.setParentDiseaseCode(StConverter.convertToSt(PARENT_DISEASE_CODE_ISA));
                    c.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    c.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    childDtos.add(c);
                }

                PDQDiseaseDTO diseaseDto = new PDQDiseaseDTO();
                diseaseDto.setDiseaseCode(StConverter.convertToSt(disease.getCode()));
                diseaseDto.setNtTermIdentifier(StConverter.convertToSt(disease.getNtTermIdentifier()));
                diseaseDto.setPreferredName(StConverter.convertToSt(disease.getPreferredName()));
                diseaseDto.setDisplayName(StConverter.convertToSt(disease.getMenuDisplayName()));
                diseaseDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                diseaseDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                diseaseDto = diseaseService.create(diseaseDto);

                // Save alter names
                if (disease.getAlterNameList() != null) {
                    for (String altName : disease.getAlterNameList()) {
                        PDQDiseaseAlternameDTO altDto = new PDQDiseaseAlternameDTO();
                        altDto.setAlternateName(StConverter.convertToSt(altName));
                        altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                        altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil
                                .today())));
                        altDto.setDiseaseIdentifier(diseaseDto.getIdentifier());
                        diseaseAltNameService.create(altDto);
                    }
                }

                // Save parents
                for (Iterator<PDQDiseaseParentDTO> iterator = parentDtos.iterator(); iterator.hasNext();) {
                    PDQDiseaseParentDTO parentDto = iterator.next();
                    parentDto.setDiseaseIdentifier(diseaseDto.getIdentifier());
                    diseaseParentService.create(parentDto);
                }

                // Save Children
                for (Iterator<PDQDiseaseParentDTO> iterator = childDtos.iterator(); iterator.hasNext();) {
                    PDQDiseaseParentDTO childDto = iterator.next();
                    childDto.setParentDiseaseIdentifier(diseaseDto.getIdentifier());
                    diseaseParentService.create(childDto);
                }

                if (!missingTerms.isEmpty()) {
                    String errorMsg = createTermsMissingErrorMessage(missingTerms);
                    ServletActionContext.getRequest().setAttribute(
                            Constants.SUCCESS_MESSAGE,
                            "New Disease " + disease.getNtTermIdentifier()
                                    + " added successfully. Some parent/child terms were also created in CTRP"
                                    + " as they were not present in CTRP already. "
                                    + " Here are the list of term that were created additionally: " + errorMsg);
                } else {
                    ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                            "New Disease " + disease.getNtTermIdentifier() + " added successfully");
                }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "Disease with NCIt code " + disease.getNtTermIdentifier() + " already exists!");
                return DISEASE;
            }
        } catch (PAException e) {
            LOG.error("Error saving disease", e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return DISEASE;
        } catch (LEXEVSLookupException lexe) {
            LOG.error("Error retrieving missing parent/child disease", lexe);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, lexe.getLocalizedMessage());
            return DISEASE;
        }
        return SUCCESS;
    }

    /**
     * Search for Disease by NCIt code in NCI Thesaurus
     *
     * @return view
     */
    public String searchDisease() {
        String ncitCode = disease.getNtTermIdentifier();
        importTerm = true;
        if (StringUtils.isEmpty(ncitCode)) {
            if (ServletActionContext.getRequest().getParameter("searchStart") == null) {
                ServletActionContext.getRequest()
                        .setAttribute(Constants.FAILURE_MESSAGE, "Enter valid NCIt Identifier");
            }
            return SEARCH_DISEASE;
        }
        try {
            ncitCode = ncitCode.toUpperCase(Locale.getDefault());
            DiseaseWebDTO temp = new NCItTermsLookup().lookupDisease(ncitCode);
            if (temp != null) {
                disease = temp;
                // Check if the term already exists
                PDQDiseaseDTO existingDiseaseDto = getExistingDisease(disease.getNtTermIdentifier());
                if (existingDiseaseDto != null) {
                    currentDisease = new DiseaseWebDTO();
                    currentDisease.setCode(existingDiseaseDto.getDiseaseCode().getValue());
                    currentDisease.setNtTermIdentifier(existingDiseaseDto.getNtTermIdentifier().getValue());
                    currentDisease.setPreferredName(existingDiseaseDto.getPreferredName().getValue());
                    currentDisease.setMenuDisplayName(existingDiseaseDto.getDisplayName().getValue());
                    List<PDQDiseaseAlternameDTO> altNames = diseaseAltNameService.getByDisease(existingDiseaseDto
                            .getIdentifier());
                    if (altNames != null && !altNames.isEmpty()) {
                        for (Iterator<PDQDiseaseAlternameDTO> iterator = altNames.iterator(); iterator.hasNext();) {
                            PDQDiseaseAlternameDTO diseaseAlternateNameDTO = (PDQDiseaseAlternameDTO) iterator.next();
                            currentDisease.getAlterNameList()
                                    .add(diseaseAlternateNameDTO.getAlternateName().getValue());

                        }
                    }

                    getExistingParentsAndChildrenForDisease(existingDiseaseDto, currentDisease);

                    ServletActionContext.getRequest().getSession().setAttribute("disease", disease);
                    ServletActionContext.getRequest().getSession().setAttribute("currentDisease", currentDisease);
                    ServletActionContext.getRequest().setAttribute(
                            Constants.FAILURE_MESSAGE,
                            "Disease with NCIt code '" + ncitCode
                                    + "' already present in CTRP, compare the values below and click"
                                    + " 'Sync Term' to update the CTRP term with values from NCIt");
                    return SYNC_DISEASE;
                }
            } else {
                ServletActionContext.getRequest().setAttribute(
                        Constants.FAILURE_MESSAGE,
                        "No intervention with NCIt code '" + ncitCode
                                + "' found in NCI Thesaurus, try a different code");

                return SEARCH_DISEASE;

            }
        } catch (Exception e) {
            LOG.error("Error looking up disease", e);
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Error looking up disease/condition, make sure the NCIt identifier '" + ncitCode
                            + "' corresponds to a disease/condition. Ther error was: " + e.getLocalizedMessage());
            return SEARCH_DISEASE;
        }

        return DISEASE;
    }

    /**
     * Synchronize an existing CTRP intervention with intervention retrieved
     * from NCIt
     * @return view
     */
    public String syncDisease() {
        DiseaseWebDTO newDisease = (DiseaseWebDTO) ServletActionContext.getRequest().getSession()
                .getAttribute("disease");
        if (newDisease != null) {
            disease = newDisease;
            currentDisease = (DiseaseWebDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute("currentDisease");
            ServletActionContext.getRequest().getSession().removeAttribute("disease");
            ServletActionContext.getRequest().getSession().removeAttribute("currentDisease");
        }
        try {
        PDQDiseaseDTO currDisease = getExistingDisease(disease.getNtTermIdentifier());
        if (currDisease != null) {

            //Get existing parent and children terms

            List<String> missingTerms = new ArrayList<String>();
            List<PDQDiseaseParentDTO> parentDtos = new ArrayList<PDQDiseaseParentDTO>();
            List<PDQDiseaseParentDTO> childDtos = new ArrayList<PDQDiseaseParentDTO>();

            List<String> existingParentCodes = new ArrayList<String>();
            for (Iterator<String> iterator = currentDisease.getParentTermList().iterator(); iterator.hasNext();) {
                existingParentCodes.add(iterator.next().split(":")[0]);
            }



            // Check if all the new parent and children terms exists in CTRP
            for (Iterator<String> iterator = disease.getParentTermList().iterator(); iterator.hasNext();) {
                String parentTerm = iterator.next();
                String parentCode = parentTerm.split(":")[0];
                if (!existingParentCodes.contains(parentCode)) {
                    PDQDiseaseDTO parent = getExistingDisease(parentCode);

                    // If term does not exists retrieve it
                    if (parent == null) {
                        missingTerms.add(parentCode);
                        parent = retrieveAndSaveMissingTerm(parentCode);
                    }

                    PDQDiseaseParentDTO p = new PDQDiseaseParentDTO();
                    p.setParentDiseaseCode(StConverter.convertToSt(PARENT_DISEASE_CODE_ISA));
                    p.setParentDiseaseIdentifier(parent.getIdentifier());
                    p.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    p.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    parentDtos.add(p);
                }
            }


            List<String> existingChildCodes = new ArrayList<String>();
            for (Iterator<String> iterator = currentDisease.getChildTermList().iterator(); iterator.hasNext();) {
                existingChildCodes.add(iterator.next().split(":")[0]);
            }
            for (Iterator<String> iterator = disease.getChildTermList().iterator(); iterator.hasNext();) {
                String childTerm = iterator.next();
                String childCode = childTerm.split(":")[0];
                if (!existingChildCodes.contains(childCode)) {

                    PDQDiseaseDTO child = getExistingDisease(childCode);

                    // If term does not exists retrieve it
                    if (child == null) {
                        missingTerms.add(childCode);
                        child = retrieveAndSaveMissingTerm(childCode);
                    }

                    PDQDiseaseParentDTO c = new PDQDiseaseParentDTO();
                    c.setDiseaseIdentifier(child.getIdentifier());
                    c.setParentDiseaseCode(StConverter.convertToSt(PARENT_DISEASE_CODE_ISA));
                    c.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    c.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    childDtos.add(c);
                }
            }

            currDisease.setPreferredName(StConverter.convertToSt(disease.getPreferredName()));

            // Remove existing synonyms
            List<PDQDiseaseAlternameDTO> altNames = diseaseAltNameService.getByDisease(currDisease.getIdentifier());
            if (altNames != null && !altNames.isEmpty()) {
                for (Iterator<PDQDiseaseAlternameDTO> iterator = altNames.iterator(); iterator.hasNext();) {
                    PDQDiseaseAlternameDTO diseaseAlternateNameDTO = iterator.next();
                    diseaseAltNameService.delete(diseaseAlternateNameDTO.getIdentifier());
                }
            }

            // Save new synonyms
            if (disease.getAlterNameList() != null) {
                for (String altName : disease.getAlterNameList()) {
                    PDQDiseaseAlternameDTO altDto = new PDQDiseaseAlternameDTO();
                    altDto.setAlternateName(StConverter.convertToSt(altName));
                    altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    altDto.setDiseaseIdentifier(currDisease.getIdentifier());
                    diseaseAltNameService.create(altDto);
                }
            }

            // Save parents
            for (Iterator<PDQDiseaseParentDTO> iterator = parentDtos.iterator(); iterator.hasNext();) {
                PDQDiseaseParentDTO parentDto = iterator.next();
                parentDto.setDiseaseIdentifier(currDisease.getIdentifier());
                diseaseParentService.create(parentDto);
            }

            // Save Children
            for (Iterator<PDQDiseaseParentDTO> iterator = childDtos.iterator(); iterator.hasNext();) {
                PDQDiseaseParentDTO childDto = iterator.next();
                childDto.setParentDiseaseIdentifier(currDisease.getIdentifier());
                diseaseParentService.create(childDto);
            }

            diseaseService.update(currDisease);
            if (!missingTerms.isEmpty()) {
                String errorMsg = createTermsMissingErrorMessage(missingTerms);
                ServletActionContext
                        .getRequest()
                        .setAttribute(
                                Constants.SUCCESS_MESSAGE,
                                "Disease "
                                        + disease.getNtTermIdentifier()
                                        + " synchronized with NCIt successfully. Some parent/child terms were "
                                        + "also created in CTRP as they were not present in CTRP already. "
                                        + " Here are the list of term that were created additionally: "
                                        + errorMsg);
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                        "Disease " + currDisease.getNtTermIdentifier().getValue() + " synchronized with NCI thesaurus");
            }
        } else {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    "No Disease/Condition with NCIt code '" + disease.getNtTermIdentifier() + "' found in CTRP");
        }
        } catch (PAException  e) {
            LOG.error("Error saving disease", e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return DISEASE;
        } catch (LEXEVSLookupException lexe) {
            LOG.error("Error retrieving missing parent/child disease", lexe);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, lexe.getLocalizedMessage());
            return DISEASE;
        }
        return SUCCESS;
    }

    /**
     * Get the list of existing parents and children
     *
     * @param existingDiseaseDto
     * @throws PAException
     */
    private void getExistingParentsAndChildrenForDisease(PDQDiseaseDTO existingDiseaseDto, DiseaseWebDTO disc)
            throws PAException {
        // Get parent & children terms
        List<PDQDiseaseParentDTO> parents = diseaseParentService.getByChildDisease(existingDiseaseDto.getIdentifier());
        if (parents != null && !parents.isEmpty()) {
            disc.getParentTermList().clear();
            for (Iterator<PDQDiseaseParentDTO> iterator = parents.iterator(); iterator.hasNext();) {
                PDQDiseaseDTO parent = diseaseService.get(iterator.next().getParentDiseaseIdentifier());
                disc.getParentTermList().add(
                        parent.getNtTermIdentifier().getValue() + ": " + parent.getPreferredName().getValue());
            }

        }

        List<PDQDiseaseParentDTO> children = diseaseParentService
                .getByParentDisease(existingDiseaseDto.getIdentifier());
        if (parents != null && !children.isEmpty()) {
            disc.getChildTermList().clear();
            for (Iterator<PDQDiseaseParentDTO> iterator = children.iterator(); iterator.hasNext();) {
                PDQDiseaseDTO child = diseaseService.get(iterator.next().getDiseaseIdentifier());
                disc.getChildTermList().add(
                        child.getNtTermIdentifier().getValue() + ": " + child.getPreferredName().getValue());
            }

        }

    }

    /**
     * Validate data entered in the disease form
     */
    private boolean validateDisease() {
        boolean valid = true;
        if (StringUtils.isEmpty(disease.getNtTermIdentifier())) {
            addFieldError("disease.ntTermIdentifier", getText("manageTerms.fieldError.ntTermIdentifier"));
            valid = false;
        }

        if (StringUtils.isEmpty(disease.getPreferredName())) {
            addFieldError("disease.preferredName", getText("manageTerms.fieldError.name"));
            valid = false;
        }

        if (StringUtils.isEmpty(disease.getMenuDisplayName())) {
            addFieldError("disease.menuDisplayName", getText("manageTerms.fieldError.menuDisplayName"));
            valid = false;
        }
        return valid;
    }

    /**
     * Create a well formatted missing terms error message for display in the UI
     *
     * @param missingTerms
     *            missing terms list
     * @return error message
     */
    private String createTermsMissingErrorMessage(List<String> missingTerms) {
        StringBuilder errorMsg = new StringBuilder();
        for (Iterator<String> iterator = missingTerms.iterator(); iterator.hasNext();) {
            String term = iterator.next();
            errorMsg.append(term);
            if (iterator.hasNext()) {
                errorMsg.append(", ");
            }
        }
        return errorMsg.toString();
    }

    /**
     * Search for existing disease by NCIt Id
     *
     * @param ncitId
     * @return matched intervention DTO, <code>null</code> if no match was found
     * @throws PAException
     */
    private PDQDiseaseDTO getExistingDisease(String ncitId) throws PAException {
        PDQDiseaseDTO searchCriteria = new PDQDiseaseDTO();
        searchCriteria.setNtTermIdentifier(StConverter.convertToSt(ncitId));
        List<PDQDiseaseDTO> searchResults = diseaseService.search(searchCriteria);
        if (!searchResults.isEmpty()) {
            return searchResults.get(0);
        }
        return null;
    }

    /**
     * Retrieve and save a term into CTRP. This terms relationships wont be
     * saved.
     *
     * @param ncitCode
     * @return created disease term reference
     * @throws PAException
     * @throws LEXEVSLookupException
     */
    private PDQDiseaseDTO retrieveAndSaveMissingTerm(String ncitCode) throws LEXEVSLookupException, PAException {
        DiseaseWebDTO disc = new NCItTermsLookup().lookupDisease(ncitCode);
        PDQDiseaseDTO diseaseDto = new PDQDiseaseDTO();
        diseaseDto.setDiseaseCode(StConverter.convertToSt(""));
        diseaseDto.setNtTermIdentifier(StConverter.convertToSt(disc.getNtTermIdentifier()));
        diseaseDto.setPreferredName(StConverter.convertToSt(disc.getPreferredName()));
        diseaseDto.setDisplayName(StConverter.convertToSt(""));
        diseaseDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        diseaseDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
        diseaseDto = diseaseService.create(diseaseDto);
        // Save alter names
        if (disc.getAlterNameList() != null) {
            for (String altName : disc.getAlterNameList()) {
                PDQDiseaseAlternameDTO altDto = new PDQDiseaseAlternameDTO();
                altDto.setAlternateName(StConverter.convertToSt(altName));
                altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                altDto.setDiseaseIdentifier(diseaseDto.getIdentifier());
                diseaseAltNameService.create(altDto);
            }
        }
        return diseaseDto;
    }

    /**
     * @return the disease
     */
    public DiseaseWebDTO getDisease() {
        return disease;
    }

    /**
     * @param disease
     *            the disease to set
     */
    public void setDisease(DiseaseWebDTO disease) {
        this.disease = disease;
    }

    /**
     * @return the currentDisease
     */
    public DiseaseWebDTO getCurrentDisease() {
        return currentDisease;
    }

    /**
     * @param currentDisease
     *            the currentDisease to set
     */
    public void setCurrentDisease(DiseaseWebDTO currentDisease) {
        this.currentDisease = currentDisease;
    }


    //Ajax actions

    /**
     * Get disease details ajax action
     * @return view
     * @throws PAException PAException
     */
    public String ajaxGetDiseases() throws PAException {
        String ids = ServletActionContext.getRequest().getParameter("diseaseIds");
        StringBuilder result = new StringBuilder();
        if (ids != null && ids.length() != 0) {
            String[] diseaseIds = ids.split(",");
            for (int i = 0; i < diseaseIds.length; i++) {
                PDQDiseaseDTO dis = diseaseService.get(IiConverter.convertToIi(Long.parseLong(diseaseIds[i])));
                if (dis != null) {
                    result.append(dis.getNtTermIdentifier().getValue() + ": " + dis.getPreferredName().getValue());
                    if (i != diseaseIds.length) {
                       result.append('\n');
                    }
                }
            }
        }
        ajaxResponseStream = new ByteArrayInputStream(result.toString().getBytes());
        return AJAX_RESPONSE;
    }

    /**
     * @return the ajaxResponseStream
     */
    public InputStream getAjaxResponseStream() {
        return ajaxResponseStream;
    }

    /**
     * @param ajaxResponseStream the ajaxResponseStream to set
     */
    public void setAjaxResponseStream(InputStream ajaxResponseStream) {
        this.ajaxResponseStream = ajaxResponseStream;
    }



}
