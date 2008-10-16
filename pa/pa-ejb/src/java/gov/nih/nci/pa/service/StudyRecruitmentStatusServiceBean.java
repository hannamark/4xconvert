/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;

import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 * @since 10/14/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyRecruitmentStatusServiceBean 
        extends AbstractStudyPaService<StudyRecruitmentStatusDTO>  
        implements StudyRecruitmentStatusServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyRecruitmentStatusServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { 
        return LOG; 
    }
    
    /**
     * Protected static create method for auto-generating a recruitment status domain object to be 
     * used in other services.
     * @param bo the StudyOverallStatus domain object.
     * @return the recruitment status domain object.
     */
    protected static StudyRecruitmentStatus create(StudyOverallStatus bo) {
        // automatically update StudyRecruitmentStatus for applicable overall status code's
        if ((bo != null) && (StudyRecruitmentStatusCode.getByStudyStatusCode(bo.getStatusCode()) != null)) {
            StudyRecruitmentStatus srsBo = new StudyRecruitmentStatus();
            srsBo.setStatusCode(StudyRecruitmentStatusCode.getByStudyStatusCode(bo.getStatusCode()));
            srsBo.setStatusDate(bo.getStatusDate());
            srsBo.setStudyProtocol(bo.getStudyProtocol());
            return srsBo;
        }
        return null;
    }
}