package gov.nih.nci.pa.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Investigator bean for managing PI.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_INVESTIGATOR")
public class StudyInvestigator extends StudyContact {
    
    private static final long serialVersionUID = 1234567890L;

    
}
