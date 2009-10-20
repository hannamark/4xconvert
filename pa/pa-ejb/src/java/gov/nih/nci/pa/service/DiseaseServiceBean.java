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

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.iso.convert.DiseaseConverter;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.NPathComplexity" })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DiseaseServiceBean
        extends AbstractBaseIsoService<DiseaseDTO, Disease, DiseaseConverter>
        implements DiseaseServiceRemote {
    
    private static final Logger LOG = Logger.getLogger(DiseaseServiceBean.class);
    private static final String TRUE = "true";
    /**
     * @param searchCriteria search string
     * @return all diseases with preferred names or alternate names matching search string
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DiseaseDTO> search(DiseaseDTO searchCriteria) throws PAException {
        if (searchCriteria == null) {
            throw new PAException("Must pass in search criteria when calling search().");
        }
        if (searchCriteria.getPreferredName() == null) {
            throw new PAException("Must pass in a name when calling search().");
        }
        getLogger().info("Entering search().  ");
        List<Disease> queryList = new ArrayList<Disease>();
        Session session = HibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer();
        Query query = null;

        // step 1: form the hql
        if (!StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)) {
            hql.append("select distinct dis "
                    + "from Disease dis ");

        } else {
            hql.append("select distinct dis "
                    + "from Disease dis " 
                    + "left join dis.diseaseAlternames alt ");
        }
        hql.append(generateWhereClause(searchCriteria));   
        hql.append("order by dis.preferredName asc");
        getLogger().info("query Disease = " + hql.toString());

        // step 2: construct query object
        query = session.createQuery(hql.toString());

        // step 3: query the result
        queryList = query.list();
        ArrayList<DiseaseDTO> resultList = new ArrayList<DiseaseDTO>();
        for (Disease bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving search(), returning " + resultList.size() + " object(s).");
        return resultList;
    }
    
    /**
     * Generate where clause.
     * 
     * @param searchCriteria the search criteria
     * 
     * @return the string
     * 
     * @throws PAException the PA exception
     */
    @SuppressWarnings({"PMD" })
    private String generateWhereClause(DiseaseDTO searchCriteria)throws PAException {
        LOG.debug("Entering generateWhereClause ");
        StringBuffer where = new StringBuffer();
        try {
            where.append("where 1 = 1 ");
           // 
           if (PAUtil.isNotEmpty(StConverter.convertToString(searchCriteria.getPreferredName()))) {
               
               where.append(" and (dis.statusCode = 'ACTIVE')");  
                             
               //Case1:include synonym is checked and exact match is unchecked
               if (StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)
                     && !StConverter.convertToString(searchCriteria.getExactMatch()).equals(TRUE)) {
                  
                  String term = PAUtil.wildcardCriteria(
                          StConverter.convertToString(searchCriteria.getPreferredName()))
                          .toUpperCase().trim().replaceAll("'", "''");
                    where.append(" and (upper(dis.preferredName) like upper('%" + term + "%') " 
                    + "or ((upper(alt.alternateName) like upper('%" + term + "%')) and (alt.statusCode = 'ACTIVE'))) ");
                   
               }
               //Case2:include synonym is unchecked and exact match is checked
               if (!StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)
                     && StConverter.convertToString(searchCriteria.getExactMatch()).equals(TRUE)) {
                   
                   String exactString = stringToSearch(StConverter.convertToString(
                           searchCriteria.getPreferredName()).toUpperCase().trim().replaceAll("'", "''"));
                   where.append(" and upper(dis.preferredName) like '" + exactString + "'");
                    
               }
               //Case3:include synonym and exact match are both checked
               if (StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)
                     && StConverter.convertToString(searchCriteria.getExactMatch()).equals(TRUE)) {
                  

                   String exactString = stringToSearch(StConverter.convertToString(
                           searchCriteria.getPreferredName()).toUpperCase().trim().replaceAll("'", "''"));
                   
                   where.append(" and (upper(dis.preferredName) like upper('" + exactString + "') "
                        + "or ((upper(alt.alternateName) like upper('" + exactString + "')) " 
                        + "and (alt.statusCode = 'ACTIVE'))) ");
                        
               } else if (!StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)
                       && !StConverter.convertToString(searchCriteria.getExactMatch()).equals(TRUE)) {
                   //Case4: both include Synonym and exact match are unchecked  
                       where.append(" and upper(dis.preferredName)  like '%" 
                               + PAUtil.wildcardCriteria(StConverter.
                                  convertToString(searchCriteria.getPreferredName())
                                   .toUpperCase().trim().replaceAll("'", "''"))
                               + "%'");
                       
                }
                 
            }
      
        } catch (Exception e) {
            LOG.error("General error in while create where cluase", e);
            throw new PAException("General error in while create where cluase", e);
        } finally {
            LOG.debug("Leaving generateWhereClause ");
        }
        return where.toString();
    }  
    
    /**
     * String to search.
     * 
     * @param searchTerm the search term
     * 
     * @return the string
     */
    private String stringToSearch(String searchTerm) {
        String term = "";
        //checks if wildcard is present within the string not at extremities
        Pattern pat = Pattern.compile("^[^*].*\\**.*[^*]$");
        Matcher mat = pat.matcher(searchTerm);
        
        if (!searchTerm.contains("*")) {
            term = searchTerm;
        } 
        if (mat.find()) {
             term = PAUtil.wildcardCriteria(searchTerm);
          } else {
            term = searchTerm;
        } 
        
        return term;
    }
}
