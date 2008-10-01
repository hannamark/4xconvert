/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
public class StudyParticipationContactServiceBean 
        extends AbstractBasePaService<StudyParticipationContactDTO> 
        implements StudyParticipationContactServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyParticipationContactServiceBean.class);

    @Override
    Logger getLogger() {
        return LOG;
    }


}
