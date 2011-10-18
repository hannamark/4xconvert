/*
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
 * and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.report.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

/**
 *
 * @author Hugh Reinhart
 * @since 05/05/2009
 */
public class TestSchema {

    public static List<User> user;
    public static List<Organization> organization;
    public static List<HealthCareFacility> healthCareFacility;
    public static List<ResearchOrganization> researchOrganization;
    public static List<StudyProtocol> studyProtocol;
    public static List<DocumentWorkflowStatus> documentWorkflowStatus;
    public static List<StudyMilestone> studyMilestone;
    public static List<StudySite> studySite;


    /**
     * @param <T> t
     * @param obj o
     */
    public static <T> void addUpdObject(T obj) {
        Session session = PaHibernateUtil.getCurrentSession();
        session.saveOrUpdate(obj);
    }

    /**
     *
     * @param <T> t
     * @param oList o
     */
    public static <T> void addUpdObjects(ArrayList<T> oList) {
        for (T obj : oList) {
            addUpdObject(obj);
        }
    }

    public static void primeData() {
        user = new ArrayList<User>();
        organization = new ArrayList<Organization>();
        healthCareFacility = new ArrayList<HealthCareFacility>();
        researchOrganization = new ArrayList<ResearchOrganization>();
        studyProtocol = new ArrayList<StudyProtocol>();
        documentWorkflowStatus = new ArrayList<DocumentWorkflowStatus>();
        studyMilestone = new ArrayList<StudyMilestone>();
        studySite = new ArrayList<StudySite>();

        User usr = new User();
        usr.setLoginName("testUser");
        usr.setFirstName("John");
        usr.setLastName("Doe");
        usr.setOrganization("testOrganization");
        usr.setUpdateDate(new Date());
        user.add(usr);
        addUpdObject(usr);

        Organization org = new Organization();
        org.setCity("city");
        org.setCountryName("countryName");
        org.setDateLastCreated(new Date());
        org.setIdentifier("ORG ID");
        org.setName("Duke");
        org.setPostalCode("12345");
        org.setState("NC");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        org.setUserLastCreated(usr);
        organization.add(org);
        addUpdObject(org);

        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setDateLastCreated(new Date());
        hcf.setIdentifier(org.getIdentifier());
        hcf.setOrganization(org);
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        hcf.setUserLastCreated(usr);
        healthCareFacility.add(hcf);
        addUpdObject(hcf);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setDateLastCreated(new Date());
        ro.setIdentifier(org.getIdentifier());
        ro.setOrganization(org);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ro.setUserLastCreated(usr);
        researchOrganization.add(ro);
        addUpdObject(ro);

        StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("cancer for THOLA");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);

        Set<Ii> studyOtherIdentifiers = new HashSet<Ii>();
        Ii assignedIdentifier = IiConverter.convertToAssignedIdentifierIi("NCI-2009-00001");
        studyOtherIdentifiers.add(assignedIdentifier);

        sp.setOtherIdentifiers(studyOtherIdentifiers);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setUserLastCreated(usr);
        sp.setDateLastCreated(PAUtil.dateStringToTimestamp("1/1/2009"));
        sp.setProprietaryTrialIndicator(false);
        studyProtocol.add(sp);
        addUpdObject(sp);

        DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        dws.setCommentText("Submitted by cancer center.");
        dws.setUserLastCreated(usr);
        addUpdObject(dws);

        dws = new DocumentWorkflowStatus();
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        dws.setCommentText("Accepted by CTRO staff");
        dws.setUserLastCreated(usr);
        documentWorkflowStatus.add(dws);
        addUpdObject(dws);

        StudyMilestone sm = new StudyMilestone();
        sm.setCommentText("test data");
        sm.setMilestoneCode(MilestoneCode.SUBMISSION_RECEIVED);
        sm.setMilestoneDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        sm.setStudyProtocol(sp);
        studyMilestone.add(sm);
        addUpdObject(sm);

        StudySite spart = new StudySite();
        spart.setDateLastCreated(new Date());
        spart.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        spart.setResearchOrganization(ro);
        spart.setLocalStudyProtocolIdentifier("local sp id");
        spart.setReviewBoardApprovalStatusCode(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED);
        spart.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        spart.setStudyProtocol(sp);
        spart.setUserLastCreated(usr);
        studySite.add(spart);
        addUpdObject(spart);

        PaHibernateUtil.getCurrentSession().flush();
    }

}
