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
package gov.nih.nci.accrual.util;

import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import au.com.bytecode.opencsv.CSVParser;

/**
 *
 * @author Hugh Reinhart
 * @since 07/27/2009
 */
public class AccrualUtil {
    private static final int YR_MO_FORMAT_IDX = 5;
    private static final CSVParser PARSER = new CSVParser();


    /**
     * Private class used to decode and normalize date strings.
     */
    private static class ValidYearMonthFormat {
        private final String pattern;
        private final int endIndex;
        public ValidYearMonthFormat(String pattern) {
            this.pattern = pattern;
            endIndex = pattern.length();
        }
    }

    /**
     * Static ordered list of valid date format patterns.
     */
    private static ValidYearMonthFormat[] yearMonthFormats;
    static {
        yearMonthFormats = new ValidYearMonthFormat[] {
                new ValidYearMonthFormat("MM/dd/yyyy"),
                new ValidYearMonthFormat("yyyy-MM-dd HH:mm:ss"),
                new ValidYearMonthFormat("yyyy-MM-dd"),
                new ValidYearMonthFormat("yyyy/MM/dd"),
                new ValidYearMonthFormat("MM-dd-yyyy HH:mm:ss"),
                new ValidYearMonthFormat("MM/yyyy"),
                new ValidYearMonthFormat("MM-yyyy")
        };
    }

    /**
     * Convert an input string to a Date.
     *
     * @param inDate string to be normalized
     * @return Date
     */
    private static Date yearMonthStringToDate(String inDate) {
        if (inDate == null) {
            return null;
        }
        Date outDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (ValidYearMonthFormat fm : yearMonthFormats) {
            sdf.applyPattern(fm.pattern);
            sdf.setLenient(false);
            try {
                int endIndex = inDate.trim().length() < fm.endIndex ? inDate.trim().length() : fm.endIndex;
                inDate.trim().substring(0, endIndex);
                outDate = sdf.parse(inDate);  //dateToParse);
                break;
            } catch (ParseException e) {
               continue; //best effort to try the other date format(s).
            }
        }
        return outDate;
    }
    /**
     * Convert an input string to a normalized year month string.
     * The output format is determined by the first element in
     * the static yearMonthFormats array.
     *
     * @param inDate string to be normalized
     * @return normalized string
     */
    public static String normalizeYearMonthString(String inDate) {
        Date outDate = yearMonthStringToDate(inDate);
        if (outDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(yearMonthFormats[YR_MO_FORMAT_IDX].pattern);
        return sdf.format(outDate);
    }

    /**
     * @param ts iso timestamp
     * @return timestamp represented as a string in MM/YYYY format
     */
    public static String tsToYearMonthString(Ts ts) {
        return normalizeYearMonthString(TsConverter.convertToString(ts));
    }

    /**
     * @param yrMonthString string of year and month
     * @return Ts representation of string
     */
    public static Ts yearMonthStringToTs(String yrMonthString) {
        Date dt = yearMonthStringToDate(normalizeYearMonthString(yrMonthString));
        if (dt == null) {
            return null;
        }
        Timestamp ts = new Timestamp(dt.getTime());
        return TsConverter.convertToTs(ts);
    }

    /**
     * @param yrMonthTs iso Ts year and month value
     * @return Timestamp
     */
    public static Timestamp yearMonthTsToTimestamp(Ts yrMonthTs) {
        Date dt = yearMonthStringToDate(normalizeYearMonthString(TsConverter.convertToString(yrMonthTs)));
        if (dt == null) {
            return null;
        }
        return new Timestamp(dt.getTime());
    }
    /**
     * @param dateString year month date string
     * @return timestamp
     */
    public static Timestamp yearMonthStringToTimestamp(String dateString) {
        return new Timestamp(yearMonthStringToDate(dateString).getTime());
    }
    
    /**
     * Checks that user has accrual access to site with id provided.
     * @param studySiteIi site ii
     * @return boolean
     * @throws PAException on error.
     */
    public static boolean isUserAllowedAccrualAccess(Ii studySiteIi)
        throws PAException {
        User user = AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser());

        RegistryUser regUser = PaServiceLocator.getInstance()
            .getRegistryUserService().getUser(user.getLoginName());
       
        return isUserAllowedAccrualAccess(studySiteIi, regUser);
    }
    
    /**
     * Checks that user has accrual access to site with id provided.
     * @param studySiteIi site ii
     * @param regUser user
     * @return boolean
     * @throws PAException on error.
     */
    public static boolean isUserAllowedAccrualAccess(Ii studySiteIi, RegistryUser regUser)
        throws PAException {
        Integer result = 
            (Integer) PaHibernateUtil.getCurrentSession().createCriteria(StudySiteAccrualAccess.class)
            .add(Restrictions.eq("studySite.id", Long.parseLong(studySiteIi.getExtension())))
            .add(Restrictions.eq("registryUser", regUser))
            .add(Restrictions.eq("statusCode", ActiveInactiveCode.ACTIVE))
            .setProjection(Projections.rowCount()).uniqueResult();            
                
        if (result == null || result.intValue() == 0) {           
            return false;
        }
        return true;
    }
    
    /**
     * Checks that a given study site id is valid and associated with a treating site.
     * @param studySiteIi the study site identifier
     * @return boolean
     * @throws PAException on error
     */
    public static boolean isValidTreatingSite(Ii studySiteIi) throws PAException {
        if (ISOUtil.isIiNull(studySiteIi)) {
            return false;
        }
        StudySiteDTO dto = PaServiceLocator.getInstance().getStudySiteService().get(studySiteIi);
        if (dto == null) {
            return false;
        }
        StudySiteFunctionalCode code = CdConverter.convertCdToEnum(StudySiteFunctionalCode.class,
                dto.getFunctionalCode());
        return code == StudySiteFunctionalCode.TREATING_SITE;
    }

    /**
     * Retrieve a trimmed String from a List<String>. Return null if index is invalid. 
     * @param list the list
     * @param index index to return
     * @return trimmed String
     */
    public static String safeGet(List<String> list, int index) {
        if (CollectionUtils.isEmpty(list) || list.size() <= index) {
            return null;
        }
        return StringUtils.trim(list.get(index));
    }

    /**
     * Parse a line of csv. Trim all the elements.
     * @param line the line
     * @return string array
     */
    public static String[] csvParseAndTrim(String line) {
        String[] arr;
        try {
            arr = PARSER.parseLine(StringUtils.trim(line));
        } catch (IOException e) {
            arr = null;
        }
        if (arr == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        for (String elem : arr) {
            result.add(elem.trim());
        }
        String[] strResult = new String[result.size()];
        result.toArray(strResult);  
        return strResult;
    }
}
