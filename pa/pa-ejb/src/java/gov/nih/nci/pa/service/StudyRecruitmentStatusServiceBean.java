/**
 * 
 */
package gov.nih.nci.pa.service;

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
}