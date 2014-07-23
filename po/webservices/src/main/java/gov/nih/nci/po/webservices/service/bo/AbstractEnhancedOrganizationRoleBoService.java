package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.webservices.service.bo.filter.AliasFilter;

import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;

/**
 * @param <TYPE>    see {@link AbstractRoleBoService}
 * @param <CR_TYPE> see {@link AbstractRoleBoService}
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 * @author Rohit Gupta
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public abstract class AbstractEnhancedOrganizationRoleBoService
        <TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>>
        extends AbstractRoleBoService<TYPE, CR_TYPE> {

    /**
     * Default constructor that initializes some filters.
     */
    public AbstractEnhancedOrganizationRoleBoService() {
        super();
        getUpdateFilters().add(new AliasFilter<TYPE, CR_TYPE>());
        getCrCreationFilters().add(new AliasFilter<TYPE, CR_TYPE>());
    }

    /**
     * Curate HCF or RO
     * @param updatedInstance updatedInstance
     * @param ctepId CTEP ID
     * @throws JMSException JMSException
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.AvoidThrowingRawExceptionTypes" })
    public void curate(TYPE updatedInstance, String ctepId) throws JMSException {

        TYPE currentInstance = getCorrelationService().getById(
                updatedInstance.getId());

        applyUpdateFilters(currentInstance, updatedInstance);

        CR_TYPE cr = createCr(currentInstance, updatedInstance);
        applyCrCreateFilters(updatedInstance, cr);

        if (currentInstance != null && !hasChanges(cr)
                && !isCtepIdChanged(currentInstance, ctepId)) {
            return;
        }

        if (updateDirectly(currentInstance)) {
            if (updatedInstance instanceof gov.nih.nci.po.data.bo.ResearchOrganization) {
                ((ResearchOrganizationServiceLocal) getCorrelationService())
                        .curate((ResearchOrganization) updatedInstance, ctepId);
            } else if (updatedInstance instanceof gov.nih.nci.po.data.bo.HealthCareFacility) {
                ((HealthCareFacilityServiceLocal) getCorrelationService())
                        .curate((HealthCareFacility) updatedInstance, ctepId);
            }
        } else {
            try {
                if (updatedInstance instanceof gov.nih.nci.po.data.bo.ResearchOrganization) {
                    PoRegistry.getInstance().getServiceLocator().getResearchOrganizationCRService()
                            .create((ResearchOrganizationCR) updatedInstance, ctepId);
                } else if (updatedInstance instanceof gov.nih.nci.po.data.bo.HealthCareFacility) {                    
                    PoRegistry.getInstance().getServiceLocator().getHealthCareFacilityCRService()
                            .create((HealthCareFacilityCR) updatedInstance, ctepId);
                }

            } catch (EntityValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isCtepIdChanged(TYPE currentInstance, String ctepId) {

        String currentCtepId = PoServiceUtil
                .getOrgRoleBoCtepId((AbstractEnhancedOrganizationRole) currentInstance);

        return !StringUtils.equalsIgnoreCase(currentCtepId, ctepId);
    }

}
