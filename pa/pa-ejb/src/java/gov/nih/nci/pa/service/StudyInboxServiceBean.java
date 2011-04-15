/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.iso.convert.StudyInboxConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PAFieldException;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author Anupama Sharma
 * @since 09/08/2009
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyInboxServiceBean extends AbstractStudyIsoService<StudyInboxDTO, StudyInbox, StudyInboxConverter>
    implements StudyInboxServiceLocal {

    /** id for inboxDateRange.open. */
    public static final int FN_DATE_OPEN = 1;
    /** id for inboxDateRange.close. */
    public static final int FN_DATE_CLOSE = 2;
    @EJB
    private DocumentWorkflowStatusServiceLocal docWrkFlowStatusService;
    @EJB
    private AbstractionCompletionServiceRemote abstractionCompletionService;

    /**
     * @param dto dto
     * @return dto
     * @throws PAException exception
     */
    @Override
    public StudyInboxDTO create(StudyInboxDTO dto) throws PAException {
        setTimeIfToday(dto);
        dateRules(dto);
        return super.create(dto);
    }

    /**
     * This method creates a record in the inbox (Only When some conditions are more). This method should be called
     * during update workflow.
     * @param documentDTOs list of document Dtos
     * @param studyProtocolIi studyProtocol Identifier
     * @throws PAException on any error
     */
    public void create(List<DocumentDTO> documentDTOs, Ii studyProtocolIi) throws PAException {
        StringBuffer comments = new StringBuffer();
        if (PAUtil.isIiNull(studyProtocolIi)) {
            throw new PAException(" Study Protocol Identifier cannot be null");
        }
        DocumentWorkflowStatusDTO dws = docWrkFlowStatusService.getCurrentByStudyProtocol(studyProtocolIi);
        if (dws == null) {
            throw new PAException(" Document workflow status is null for StudyProtocol identifier "
                + studyProtocolIi.getExtension());
        }
        comments.append(createComments(documentDTOs));
        comments.append(createComments(dws, studyProtocolIi));
        if (comments.length() > 0) {
            StudyInboxDTO studyInboxDTO = new StudyInboxDTO();
            studyInboxDTO.setStudyProtocolIdentifier(studyProtocolIi);
            studyInboxDTO.setInboxDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()),
                                                                                  null));
            studyInboxDTO.setComments(StConverter.convertToSt(comments.toString()));
            create(studyInboxDTO);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyInboxDTO update(StudyInboxDTO dto) throws PAException {
        StudyInboxDTO wrkDto = super.get(dto.getIdentifier());
        if (wrkDto != null) {
            wrkDto.getInboxDateRange().setHigh(dto.getInboxDateRange().getHigh());
            setTimeIfToday(wrkDto);
            dateRules(wrkDto);
        }
        return super.update(wrkDto);
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyInboxDTO> getOpenInboxEntries(Ii studyProtocolIi) throws PAException {
        Criteria crit =
            PaHibernateUtil.getCurrentSession().createCriteria(StudyInbox.class).add(Restrictions.isNull("closeDate"))
            .addOrder(Order.desc("openDate"))
            .createCriteria("studyProtocol").add(Restrictions.eq("id", IiConverter.convertToLong(studyProtocolIi)));
        try {
            @SuppressWarnings("unchecked")
            List<StudyInbox> entries = crit.list();
            return convertFromDomainToDTOs(entries);
        } catch (Exception e) {
            throw new PAException("Error retrieving open inbox entries.", e);
        }
    }

    private void setTimeIfToday(StudyInboxDTO dto) {
        Timestamp now = new Timestamp(new Date().getTime());
        Timestamp tsLow = IvlConverter.convertTs().convertLow(dto.getInboxDateRange());
        Timestamp tsHigh = IvlConverter.convertTs().convertHigh(dto.getInboxDateRange());
        if (tsLow != null && tsLow.equals(PAUtil.dateStringToTimestamp(PAUtil.today()))) {
            tsLow = now;
        }
        if (tsHigh != null && tsHigh.equals(PAUtil.dateStringToTimestamp(PAUtil.today()))) {
            tsHigh = now;
        }
        dto.setInboxDateRange(IvlConverter.convertTs().convertToIvl(tsLow, tsHigh));
    }

    private void dateRules(StudyInboxDTO dto) throws PAException {
        Timestamp low = IvlConverter.convertTs().convertLow(dto.getInboxDateRange());
        if (low == null) {
            throw new PAFieldException(FN_DATE_OPEN, "Open date is required.");
        }
        Timestamp now = new Timestamp(new Date().getTime());
        if (now.before(low)) {
            throw new PAFieldException(FN_DATE_OPEN, "Open dates must be only past or current dates.");
        }
        Timestamp high = IvlConverter.convertTs().convertHigh(dto.getInboxDateRange());
        if (high != null) {
            if (now.before(high)) {
                throw new PAFieldException(FN_DATE_CLOSE, "Close dates must be only past or current dates.");
            }
            if (high.before(low)) {
                throw new PAFieldException(FN_DATE_CLOSE,
                    "Close date must be bigger than open date for the same open record.");
            }
        }
    }

    private StringBuffer createComments(List<DocumentDTO> documentDTOs) {
        StringBuffer comments = new StringBuffer();
        if (CollectionUtils.isNotEmpty(documentDTOs)) {
            for (DocumentDTO doc : documentDTOs) {
                comments.append(CdConverter.convertCdToString(doc.getTypeCode())).append(" Document was uploaded <br>");
            }
        }
        return comments;
    }

    private StringBuffer createComments(DocumentWorkflowStatusDTO dws, Ii studyProtocolIi) throws PAException {
        StringBuffer comments = new StringBuffer();
        if (PAUtil.isAbstractedAndAbove(dws.getStatusCode())) {
            List<AbstractionCompletionDTO> errorList = abstractionCompletionService
                .validateAbstractionCompletion(studyProtocolIi);
            if (!errorList.isEmpty()) {
                comments.append("<b>Type :</b>  <b>Description :</b> <b>Comments :</b><br>");
                for (AbstractionCompletionDTO abDTO : errorList) {
                    comments.append(abDTO.getErrorType()).append(" : ").append(abDTO.getErrorDescription())
                            .append(" : ").append(abDTO.getComment()).append("<br>");
                }
            }
        }
        return comments;
    }
}
