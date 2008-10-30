/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.iso.convert.InterventionConverter;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class InterventionServiceBean
        extends AbstractBasePaService<InterventionDTO>
        implements InterventionServiceRemote {

    private static final Logger LOG  = Logger.getLogger(InterventionServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    /**
     * @param ii index
     * @return the intervention
     * @throws PAException Exception.
     */
    @Override
    public InterventionDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        InterventionDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            Intervention bo = (Intervention) session.get(Intervention.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".  ");
            }
            resultDto = InterventionConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }

}
