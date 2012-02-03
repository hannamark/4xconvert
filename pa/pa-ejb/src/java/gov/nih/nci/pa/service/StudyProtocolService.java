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
*
*
*/
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;

import java.util.List;

/**
 * @author Bala Nair
 * @since 03/23/2009
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public interface StudyProtocolService {


    /**
     * Gets a study protocol by either the internal PA DB ID, the NCI assigned identifier, the DCP identifier,
     * CTEP identifier or the NCT identifier.
     * If the <code>ii.extension</code> starts with "NCI," the identifier is assumed to be the
     * NCI assigned identifier; The ii.root is used to differentiate between DCP, NCT, CTEP identifiers; otherwise,
     * it is assumed to be the internal ID.
     * The NCI, DCP, NCT and CTEP identifiers must be the full identifier, as an exact match is performed,
     * not a starts-with search.
     * @param ii Primary DB ID or NCI assigned identifier, DCP Identifier, CTEP identifier or NCT identifier
     * of the study protocol to get. The ID must uniquely identify a study protocol.
     * @return StudyProtocolDTO
     * @throws PAException on error, including if the given II matches more than one study protocol
     */
    StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException;


    /**
     * Gets a study protocol by either the internal PA DB ID, the NCI assigned identifier, the DCP identifier,
     * CTEP identifier or the NCT identifier.
     * If the <code>ii.extension</code> starts with "NCI," the identifier is assumed to be the
     * NCI assigned identifier; The ii.root is used to differentiate between DCP, NCT, CTEP identifiers; otherwise,
     * it is assumed to be the internal ID.
     * The NCI, DCP, NCT and CTEP identifiers must be the full identifier, as an exact match is performed,
     * not a starts-with search.
     * @param ii Primary DB ID or NCI assigned identifier, DCP Identifier, CTEP identifier or NCT identifier
     * of the study protocol to get. The ID must uniquely identify a study protocol.
     * @return the study protocol with the given ii. Will return null if there are no matches or multiple matches with
     * the given Ii
     */
    StudyProtocolDTO loadStudyProtocol(Ii ii);

    /**
     *
     * @param studyProtocolDTO studyProtocolDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyProtocolDTO updateStudyProtocol(StudyProtocolDTO studyProtocolDTO) throws PAException;

    /**
     *
     * @param ii ii
     * @return InterventionalStudyProtocolDTO
     * @throws PAException PAException
     */
    InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii) throws PAException;

    /**
     *
     * @param ispDTO studyProtocolDTO
     * @return InterventionalStudyProtocolDTO
     * @throws PAException PAException
     */
    InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO) throws PAException;

    /**
     * for creating a new ISP.
     * @param ispDTO  for isp
     * @return ii ii
     * @throws PAException exception
     */
    Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDTO) throws PAException;
    /**
     *
     * @param ii ii
     * @return ObservationalStudyProtocolDTO
     * @throws PAException PAException
     */
    ObservationalStudyProtocolDTO getObservationalStudyProtocol(Ii ii) throws PAException;

    /**
     *
     * @param ospDTO ObservationalStudyProtocolDTO
     * @return ObservationalStudyProtocolDTO
     * @throws PAException PAException
     */
    ObservationalStudyProtocolDTO updateObservationalStudyProtocol(
            ObservationalStudyProtocolDTO ospDTO) throws PAException;

    /**
     * for creating a new OSP.
     * @param ospDTO  for osp
     * @return ii ii
     * @throws PAException exception
     */
    Ii createObservationalStudyProtocol(ObservationalStudyProtocolDTO ospDTO) throws PAException;

    /**
     * deletes protocol and all of its related classes.
     * @param ii ii of study Protocol
     * @throws PAException on any error
     */
    void deleteStudyProtocol(Ii ii) throws PAException;

    /**
     * This method is an extension of the existing search method. The key difference being the support for paginated
     * results (similar to SQL LIMIT OFFSET queries).
     * @see #search(StudyProtocolDTO) for general search behavior
     * @see LimitOffset#LimitOffset(int, int) for special notes related to behavior
     * @param dto the dto
     * @param pagingParams the settings for control pagination of results
     * @return the study protocol
     * @throws PAException the PA exception
     * @throws TooManyResultsException when the system's limit is exceeded
     */
    List<StudyProtocolDTO> search(StudyProtocolDTO dto, LimitOffset pagingParams)
        throws PAException, TooManyResultsException;

    /**
     * This method returns all collaborative trials in the system (ie, those sponsored by the DCP and CTEP orgs) that
     * have the document workflow status of ABSTRACTION_VERIFIED_RESPONSE orABSTRACTION_VERIFIED_NORESPONSE.
     * @return the list of all abstracted collaborative trials
     * @throws PAException on error
     */
    List<StudyProtocolDTO> getAbstractedCollaborativeTrials() throws PAException;

    /**
     * validates all the attributes of study protocol.
     * @param studyProtocolDTO study protocol Dto
     * @throws PAException error on any validation
     */
    void validate(StudyProtocolDTO studyProtocolDTO) throws PAException;
    /**
     * This method is to use to change the ownership of the studyProtocol record.
     * @param id protocol identifier
     * @param recordOwners a set of record owners identified by email addresses. Null is treated as "no change"; 
     * an empty set will remove all existing ownerships.
     * @throws PAException on error
     * @see StudyProtocolDTO#getRecordOwners()
     * @see https://tracker.nci.nih.gov/browse/PO-3441
     */
    void changeOwnership(Ii id, DSet<Tel> recordOwners) throws PAException;
    
    /**
     * This method is to use to change the ownership of the studyProtocol.
     * @param studyProtocolDTO studyProtocolDTO
     * @throws PAException on error
     */
    void changeOwnership(StudyProtocolDTO studyProtocolDTO) throws PAException;

}
