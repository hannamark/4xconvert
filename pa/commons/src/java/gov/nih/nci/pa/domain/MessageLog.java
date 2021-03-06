/**
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
*
*
*/
package gov.nih.nci.pa.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

/**
 * @author  Harsha
 * @since  12/09/2008
 */
@Entity
@Table(name = "MESSAGES_LOG")
public class MessageLog implements Serializable {

    private static final long serialVersionUID = 8652324159502559218L;
    /**
     * @uml.property  name="id"
     */
    private Long id;
    //private StudyProtocol studyProtocol;
    /**
     * @uml.property  name="studyProtocol"
     */
    private Long studyProtocol;
    /**
     * @uml.property  name="entityName"
     */
    private String entityName;
    /**
     * @uml.property  name="assignedIdentifier"
     */
    private String assignedIdentifier;
    /**
     * @uml.property  name="dateCreated"
     */
    private Date dateCreated;
    /**
     * @uml.property  name="messageAction"
     */
    private String messageAction;
    /**
     * @uml.property  name="result"
     */
    private Boolean result;
    /**
     * @uml.property  name="exceptionMessage"
     */
    private String exceptionMessage;
    
    private List<MessageLogAudit> messageLogAudits = new ArrayList<MessageLogAudit>();

    /**
     * set id.
     * @param id  id
     * @uml.property  name="id"
     */
     public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * @return  the id
     * @uml.property  name="id"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    public Long getId() {
        return this.id;
    }

//    /**
//     *
//     * @return StudyProtocol
//     */
//    @ManyToOne
//    @JoinColumn(name = "STUDY_PROTOCOL_IDENTIFIER", nullable = false)
//    public Long getStudyProtocol() {
//        return studyProtocol;
//    }
//
//    /**
//     *
//     * @param studyProtocol studyProtocol
//     */
//    public void setStudyProtocol(StudyProtocol studyProtocol) {
//        this.studyProtocol = studyProtocol;
//    }

    /**
     * @return  statusDateRangeLow
     * @uml.property  name="dateCreated"
     */
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreated() {
        return this.dateCreated;
    }

    /**
     * @param createdDate  last updated date
     * @uml.property  name="dateCreated"
     */
    public void setDateCreated(Date createdDate) {
        this.dateCreated = createdDate;
    }

    /**
     * @return  the entityName
     * @uml.property  name="entityName"
     */
    @Column(name = "ENTITY_NAME")
    public String getEntityName() {
        return entityName;
    }

    /**
     * @param entityName  the entityName to set
     * @uml.property  name="entityName"
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return  the assignedIdentifier
     * @uml.property  name="assignedIdentifier"
     */
     @Column(name = "ASSIGNED_IDENTIFIER")
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }

    /**
     * @param assignedIdentifier  the assignedIdentifier to set
     * @uml.property  name="assignedIdentifier"
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }

    /**
     * @return  the messageAction
     * @uml.property  name="messageAction"
     */
    @Column(name = "MESSAGE_ACTION")
    public String getMessageAction() {
        return messageAction;
    }

    /**
     * @param messageAction  the messageAction to set
     * @uml.property  name="messageAction"
     */
    public void setMessageAction(String messageAction) {
        this.messageAction = messageAction;
    }

    /**
     * @return  the result
     * @uml.property  name="result"
     */
    @Column(name = "RESULT")
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result  the result to set
     * @uml.property  name="result"
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    /**
     * @return  the exceptionMessage
     * @uml.property  name="exceptionMessage"
     */
    @Column(name = "EXCEPTION_MESSAGE")
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * @param exceptionMessage  the exceptionMessage to set
     * @uml.property  name="exceptionMessage"
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * @return  the studyProtocol
     * @uml.property  name="studyProtocol"
     */
    @Column(name = "STUDY_PROTOCOL_IDENTIFIER")
    @Index(name = "messages_log_study_protocol_idx")
    public Long getStudyProtocol() {
        return studyProtocol;
    }

    /**
     * @param studyProtocol  the studyProtocol to set
     * @uml.property  name="studyProtocol"
     */
    public void setStudyProtocol(Long studyProtocol) {
        this.studyProtocol = studyProtocol;
    }

    /**
     * @return the messageLogAudits
     */
    @OneToMany
    @JoinColumn(name = "messageIdentifier")
    @NotNull    
    public List<MessageLogAudit> getMessageLogAudits() {
        return messageLogAudits;
    }

    /**
     * @param messageLogAudits the messageLogAudits to set
     */
    public void setMessageLogAudits(List<MessageLogAudit> messageLogAudits) {
        this.messageLogAudits = messageLogAudits;
    }
    
    

   }
