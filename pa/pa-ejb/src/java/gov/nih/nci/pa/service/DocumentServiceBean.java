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

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.convert.DocumentConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
/**
 * @author Kalpana Guthikonda
 * @since 10/1/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity" })
public class DocumentServiceBean implements DocumentServiceRemote {

    private static final Logger LOG  = Logger.getLogger(DocumentServiceBean.class);

    private SessionContext ejbContext;
    
    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }
    /**
     * @param studyProtocolIi Ii
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocol Identifer should not be null ");
            throw new PAException(" studyProtocol Identifer should not be null ");
        }
        LOG.info("Entering getDocumentsByStudyProtocol");
        Session session = null;
        List<Document> queryList = new ArrayList<Document>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select doc "
                       + " from Document doc "
                       + " join doc.studyProtocol sp "
                       + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
                       + " and doc.activeIndicator =  '" + Boolean.TRUE + "'";

           LOG.info(" query getDocumentsByStudyProtocol = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();


        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getDocumentsByStudyProtocol" , hbe);
            throw new PAException(" Hibernate exception while retrieving getDocumentsByStudyProtocol "  , hbe);
        }

        ArrayList<DocumentDTO> resultList = new ArrayList<DocumentDTO>();
        for (Document bo : queryList) {
            resultList.add(DocumentConverter.convertFromDomainToDTO(bo));
        }
        session.flush();
        LOG.info("Leaving getDocumentsByStudyProtocol");
        return resultList;
    }

    /**
     * @param docDTO DocumentDTO
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentDTO create(DocumentDTO docDTO)
    throws PAException {
        if (docDTO == null) {
            throw new PAException(" docDTO should not be null ");
        }
        LOG.debug("Entering createTrialDocument ");
        checkTypeCodes(docDTO);
        Session session = null;
        Document doc = DocumentConverter.convertFromDTOToDomain(docDTO);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        doc.setDateLastCreated(now);
        doc.setDateLastUpdated(now);
        if (ejbContext != null) {
            doc.setUserLastCreated(ejbContext.getCallerPrincipal().getName());
        }
        // create Protocol Obj
        /*StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(docDTO.getStudyProtocolIi()));

        doc.setStudyProtocol(studyProtocol);*/
        doc.setActiveIndicator(true);
        try {
            session = HibernateUtil.getCurrentSession();

            session.save(doc);
            session.flush();
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while createTrialDocument " , hbe);
            throw new PAException(" Hibernate exception while createTrialDocument " , hbe);
        }
        docDTO.setIdentifier(IiConverter.converToDocumentIi(doc.getId()));
        saveFile(docDTO);
        LOG.debug("Leaving createStudyResourcing ");
        return docDTO;
    }

    /**
     * @param id Ii
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentDTO get(Ii id) throws PAException {

        LOG.info("Entering getTrialDocumentById");
        Session session = null;
        DocumentDTO docDTO = null;
        Document doc = null;
        List<Document> queryList = new ArrayList<Document>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select doc "
                       + " from Document doc "
                       + " where doc.id = " + IiConverter.convertToLong(id);

           LOG.info(" query getTrialDocumentById = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();


        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getTrialDocumentById" , hbe);
            throw new PAException(" Hibernate exception while retrieving getTrialDocumentById "  , hbe);
        }

        if (!queryList.isEmpty()) {
            doc = queryList.get(0);
            docDTO = DocumentConverter.convertFromDomainToDTO(doc);

        }
        session.flush();
        LOG.info("Leaving getTrialDocumentById");
        return docDTO;
    }

    /**
     *
     * @param docDTO DocumentDTO
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentDTO update(DocumentDTO docDTO) throws PAException {

        if (docDTO == null) {
            LOG.error(" docDTO should not be null ");
            throw new PAException(" docDTO should not be null ");
        }
        LOG.debug("Entering updateTrialDocument ");
        Session session = null;
        DocumentDTO docRetDTO = null;
        try {
            docDTO.setInactiveCommentText(StConverter.convertToSt("A new record will be created"));
            delete(docDTO);
            docDTO.setInactiveCommentText(null);
            docRetDTO = create(docDTO);

        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving updateTrialDocument" , hbe);
            throw new PAException(" Hibernate exception while retrieving updateTrialDocument "  , hbe);
        }
        LOG.debug("Leaving updateTrialDocument ");
        return docRetDTO;
    }

    /**
     *
     * @param docDTO DocumentDTO
     * @return Boolean
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Boolean delete(DocumentDTO docDTO) throws PAException {

        LOG.debug("Entering deleteTrialDocumentByID ");
        Boolean result = false;
        Session session = null;
        Document doc = null;
        List<Document> queryList = new ArrayList<Document>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select d"
                       + " from Document d"
                       + " where d.id = " + IiConverter.convertToLong(docDTO.getIdentifier());
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            doc = queryList.get(0);
            // set the values from paramter
            doc.setActiveIndicator(false);
            doc.setInactiveCommentText(StConverter.convertToString(
                    docDTO.getInactiveCommentText()));
            doc.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
            if (ejbContext != null) {
                doc.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
            }
            session.update(doc);
            session.flush();
            result = true;
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving deleteTrialDocumentByID" , hbe);
            throw new PAException(" Hibernate exception while retrieving deleteTrialDocumentByID "  , hbe);
        }
        LOG.debug("Leaving deleteTrialDocumentByID ");
        return result;
    }
    /**
     * @param docDTO DocumentDTO
     * @return Boolean
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Boolean checkTypeCodes(DocumentDTO docDTO) throws PAException {
        Boolean result = false;
        List<DocumentDTO> resultList = new ArrayList<DocumentDTO>();
        resultList = getDocumentsByStudyProtocol(docDTO.getStudyProtocolIi());
        if (!(resultList.isEmpty())) {
        for (DocumentDTO check : resultList) {
            if (check.getTypeCode().getCode().equals(DocumentTypeCode.Protocol_Document.getCode())
                    || check.getTypeCode().getCode().equals(DocumentTypeCode.IRB_Approval_Document.getCode())) {
                if (check.getTypeCode().getCode().equals(docDTO.getTypeCode().getCode())) {
                    result = false;
                    throw new PAException("Document with selected type already exists on the trial. ");
                    //break;
                } else {
                result = true;
                }
            }
         }
        } else {
            result = true;
        }
        return result;
    }


    private void saveFile(DocumentDTO docDTO) throws PAException {
        if (docDTO.getText() == null || docDTO.getText().getData() == null) {
            throw new PAException("Document data cannot be null ");
        }
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StudyProtocolServiceBean spBean = new StudyProtocolServiceBean();
        StudyProtocolDTO sp = spBean.getStudyProtocol(docDTO.getStudyProtocolIi());
        StringBuffer sb  = new StringBuffer(folderPath);
        sb.append(File.separator).append(sp.getAssignedIdentifier().getExtension());
        File f = new File(sb.toString());
        if (!f.exists()) {
            // create a new directory
            try {
                boolean md = f.mkdir();
                if (!md) {
                    throw new PAException("unable to create a folder  " + sb.toString());
                }
            } catch (SecurityException e) {
                throw new PAException("Security Exception while creating a folder "
                        + sb.toString() + e.getMessage(), e);
            }
        }

        // create the file
        sb.append(File.separator).append(docDTO.getIdentifier().getExtension()).
            append('-').append(docDTO.getFileName().getValue());
        try {
            File outFile = new File(sb.toString());
            FileOutputStream fos = new FileOutputStream(outFile);
            fos.write(docDTO.getText().getData());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new PAException("Error while creating directory  " + sb.toString() , e);
        }
    }
}
