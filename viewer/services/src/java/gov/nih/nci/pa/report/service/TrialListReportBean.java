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
package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.TrialListCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.util.ReportConstants;
import gov.nih.nci.pa.report.util.ViewerHibernateSessionInterceptor;
import gov.nih.nci.pa.report.util.ViewerHibernateUtil;
import gov.nih.nci.pa.service.PAException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;

/**
* @author Hugh Reinhart
* @since 4/10/2009
*/
@Stateless
@Interceptors(ViewerHibernateSessionInterceptor.class)
public class TrialListReportBean extends AbstractBaseReportBean<TrialListCriteriaDto, TrialListResultDto>
        implements TrialListLocal {

    private static final int ORGANIZATION_IDX = 0;
    private static final int DATE_LAST_CREATED_IDX = 1;
    private static final int ASSIGNED_IDENTIFIER_IDX = 2;
    private static final int OFFICIAL_TITLE_IDX = 3;
    private static final int STATUS_CODE_IDX = 4;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TrialListResultDto> get(TrialListCriteriaDto criteria) throws PAException {
        super.get(criteria);
        ArrayList<TrialListResultDto> rList = new ArrayList<TrialListResultDto>();
        try {
            session = ViewerHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            String sql
                = "SELECT cm.organization, sp.date_last_created, sp.assigned_identifier "
                + "       , sp.official_title, dws.status_code "
                + "FROM study_protocol AS sp "
                + "INNER JOIN document_workflow_status AS dws ON (sp.identifier = dws.study_protocol_identifier) "
                + "LEFT OUTER JOIN csm_user AS cm ON (sp.user_last_created = cm.login_name) "
                + "WHERE dws.identifier in "
                + "      ( select max(identifier) "
                + "        from document_workflow_status "
                + "        group by study_protocol_identifier ) "
                + "  AND (sp.user_last_created NOT IN (:EXCLUDE_LIST) OR sp.user_last_created IS NULL)"
                + "ORDER BY cm.organization, sp.date_last_created";
            logger.info("query = " + sql);
            query = session.createSQLQuery(sql);
            if (BlConverter.covertToBool(criteria.getCtrpOnly())) {
                query.setParameterList("EXCLUDE_LIST", ReportConstants.NON_CTRP_SUBMITTERS);
            } else {
                query.setParameter("EXCLUDE_LIST", "");
            }
            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
            for (Object[] sr : queryList) {
                TrialListResultDto rdto = new TrialListResultDto();
                rdto.setOrganization(StConverter.convertToSt((String) sr[ORGANIZATION_IDX]));
                rdto.setDateLastCreated(TsConverter.convertToTs((Timestamp) sr[DATE_LAST_CREATED_IDX]));
                rdto.setAssignedIdentifier(StConverter.convertToSt((String) sr[ASSIGNED_IDENTIFIER_IDX]));
                rdto.setOfficialTitle(StConverter.convertToSt((String) sr[OFFICIAL_TITLE_IDX]));
                rdto.setStatusCode(CdConverter.convertStringToCd((String) sr[STATUS_CODE_IDX]));
                rList.add(rdto);
            }
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        logger.info("Leaving get(TrialListCriteriaDto), returning " + rList.size() + " object(s).");
        return rList;
    }
}
