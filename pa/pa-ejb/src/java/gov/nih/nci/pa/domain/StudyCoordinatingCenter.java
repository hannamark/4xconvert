package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The organization that has responsibility for protocol management, 
 * registration management, statistical management and data management on a study. 
 * @author Naveen Amiruddin
 * @since 06/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_COORDINATING_CENTER")
public class StudyCoordinatingCenter  extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;
    private StudyProtocol studyProtocol;
    private Organization organization;
    
    private List<StudyCoordinatingCenterRole> studyCoordinatingCenterRoles = 
        new ArrayList<StudyCoordinatingCenterRole>();
    
    /**
     * 
     * @return StudyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID", nullable = false)
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }
    
    /**
     * 
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
    /**
     * 
     * @return Organization
     */
    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID", nullable = false)
    public Organization getOrganization() {
        return organization;
    }
    /**
     *  
     * @param organization organization
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    /**
     * 
     * @return studyCoordinatingCenterRoles
     */
    
    @OneToMany(mappedBy = "studyCoordinatingCenter")
    public List<StudyCoordinatingCenterRole> getStudyCoordinatingCenterRoles() {
        return studyCoordinatingCenterRoles;
    }
    
    /**
     * 
     * @param studyCoordinatingCenterRoles studyCoordinatingCenterRole
     */
    
    public void setStudyCoordinatingCenterRoles(List<StudyCoordinatingCenterRole> studyCoordinatingCenterRoles) {
        this.studyCoordinatingCenterRoles = studyCoordinatingCenterRoles;
    }
    
}
