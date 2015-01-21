/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;

/**
 * Class that encapsulates validation rules for participating sites.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class ParticipatingSiteValidator implements Validateable {

    private final SubmittedOrganizationDTO siteDTO;

    private final ValidationAware errorReporter;

    private final TextProvider textProvider;

    private final PAServiceUtils paServiceUtil;

    /**
     * @param siteDTO
     *            siteDTO
     * @param errorReporter
     *            errorReporter
     * @param textProvider
     *            textProvider
     * @param paServiceUtil
     *            paServiceUtil
     */
    public ParticipatingSiteValidator(SubmittedOrganizationDTO siteDTO,
            ValidationAware errorReporter, TextProvider textProvider,
            PAServiceUtils paServiceUtil) {
        this.siteDTO = siteDTO;
        this.errorReporter = errorReporter;
        this.textProvider = textProvider;
        this.paServiceUtil = paServiceUtil;
    }

    @Override
    public void validate() {
        enforceBusinessRulesForProprietary();
    }

    // The following validation methods have been copied as-is from
    // gov.nih.nci.pa.action.ParticipatingOrganizationsAction and adjusted for
    // use here.
    // The original validation methods in ParticipatingOrganizationsAction are
    // hard-wired
    // into the action class and are not easily reusable; hence the copy &
    // paste.
    // This needs to be re-visited at a later point to see how we could extract
    // the validation
    // into common functionality and stay DRY. TO DO. Sorry.
    private void enforceBusinessRulesForProprietary() {
        checkInvestigatorStatus();
        enforcePartialRulesForProp1();
    }

    private void checkInvestigatorStatus() {
        if (siteDTO.getInvestigatorId() != null) {
            Ii investigatorIi = IiConverter.convertToPoPersonIi(siteDTO
                    .getInvestigatorId().toString());
            if (paServiceUtil.getPoPersonEntity(investigatorIi) == null) {
                errorReporter.addFieldError("investigator",
                        textProvider.getText("error.nullifiedInvestigator"));
            }
        }

    }

    private void enforcePartialRulesForProp1() {
        checkFieldError(
                StringUtils.isEmpty(siteDTO.getSiteLocalTrialIdentifier()),
                "localIdentifier", "error.siteLocalTrialIdentifier.required");
        checkFieldError(siteDTO.getInvestigatorId() == null, "investigator",
                "error.selectedPersId.required");
        checkFieldError(StringUtils.isEmpty(siteDTO.getRecruitmentStatus()),
                "statusCode",
                "error.participatingOrganizations.recruitmentStatus");
        if (!PAUtil.isValidDate(siteDTO.getRecruitmentStatusDate())) {
            errorReporter
                    .addFieldError("statusDate", "A valid Recruitment Status Date is required");
        } else if (PAUtil.isDateCurrentOrPast(siteDTO
                .getRecruitmentStatusDate())) {
            errorReporter.addFieldError("statusDate",
                    textProvider.getText("error.submit.invalidStatusDate"));
        }
    }
   

    // NOPMD
    private void checkFieldError(boolean condition, String fieldName,
            String textKey) {
        if (condition) {
            errorReporter.addFieldError(fieldName,
                    textProvider.getText(textKey));
        }
    }
}
