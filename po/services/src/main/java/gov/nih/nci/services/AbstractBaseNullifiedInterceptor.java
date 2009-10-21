package gov.nih.nci.services;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.CdConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IdConverterRegistry;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.IiDsetConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceBean;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceBean;

import java.util.Map.Entry;

import javax.interceptor.InvocationContext;

import org.apache.commons.collections.keyvalue.UnmodifiableMapEntry;

/**
 * Contains helper method for Correlation, Person and Organization Null checking.
 * 
 * @author ludetc
 *
 */
public abstract class AbstractBaseNullifiedInterceptor {

    /**
     * Check for OrganizationDTO nullified state.
     * 
     * @param invContext the invocation context
     * @param dto the dto to check
     * @return the potential nullified entry
     */
    @SuppressWarnings("unchecked")
    protected Entry<Ii, Ii> handle(InvocationContext invContext, OrganizationDTO dto) {
        EntityStatus status = StatusCodeConverter.convertToStatusEnum(dto.getStatusCode());
        if (EntityStatus.NULLIFIED.equals(status)) {
            OrganizationEntityServiceBean bean = (OrganizationEntityServiceBean) invContext.getTarget();
            Organization org = bean.getOrganizationServiceBean()
                    .getById(IiConverter.convertToLong(dto.getIdentifier()));

            Ii duplicateOfIi = null;
            if (org.getDuplicateOf() != null) {
                IdConverter idConverter = IdConverterRegistry.find(Organization.class);
                duplicateOfIi = idConverter.convertToIi(org.getDuplicateOf().getId());
            }
            return new UnmodifiableMapEntry(dto.getIdentifier(), duplicateOfIi);
        }
        return null;
    } 
    
    /**
     * Check for PersonDTO nullified state.
     * 
     * @param invContext the invocation context
     * @param dto the dto to check
     * @return the potential nullified entry
     */
    @SuppressWarnings("unchecked")
    protected Entry<Ii, Ii> handle(InvocationContext invContext, PersonDTO dto) {
        EntityStatus status = StatusCodeConverter.convertToStatusEnum(dto.getStatusCode());
        if (EntityStatus.NULLIFIED.equals(status)) {
            PersonEntityServiceBean bean = (PersonEntityServiceBean) invContext.getTarget();
            Person person = bean.getPersonServiceBean().getById(IiConverter.convertToLong(dto.getIdentifier()));

            Ii duplicateOfIi = null;
            if (person.getDuplicateOf() != null) {
                IdConverter idConverter = IdConverterRegistry.find(Person.class);
                duplicateOfIi = idConverter.convertToIi(person.getDuplicateOf().getId());
            }
            return new UnmodifiableMapEntry(dto.getIdentifier(), duplicateOfIi);
        }
        return null;
    }
 
    /**
     * Check for CorrelationDto nullified state.
     * 
     * @param dto the dto to check
     * @return the potential nullified entry
     */
    @SuppressWarnings("unchecked")
    protected Entry<Ii, Ii> handle(CorrelationDto dto) {
        RoleStatus status = CdConverter.convertToRoleStatus(dto.getStatus());
        if (RoleStatus.NULLIFIED.equals(status)) {
            return new UnmodifiableMapEntry(IiDsetConverter.convertToIi(dto.getIdentifier()), dto.getDuplicateOf());
        }
        return null;
    }
}
