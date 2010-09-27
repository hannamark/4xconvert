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
package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ed;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter.JavaPq;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil.ValidDateFormat;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * This is a selection of utilities, useful for PA. This set of utilities is safe to use in the grid services. Do
 * not, I repeat, do not add methods that reference domain objects. If you need to manipulate domain objects do so
 * in PADomainUtils.
 *
 * @author Naveen Amiruddin
 * @since 05/30/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class PAUtil {
    private static final int MAXF = 1024;
    private static final String EXTN = "extn";
    private static final int EXTN_COUNT = 4;

    /**
     * checks if Ii is null.
     * @param ii ii
     * @return boolean
     */
    public static boolean isIiNull(Ii ii) {
        boolean isNull = false;
        if (ii == null || ii.getExtension() == null) {
            return true;
        }
        if (ii.getExtension().trim().length() == 0) {
            isNull = true;
        }
        return isNull;
    }

    /**
     *
     * @param ii ii to validate
     * @return boolean
     */
    public static boolean isIiNotNull(Ii ii) {
        return !isIiNull(ii);

    }
    /**
     *
     * @param toValidate ii to validate
     * @param source source of the ii to validate
     * @return boolean
     * @throws PAException on invalid ii
     */
    public static boolean isValidIi(Ii toValidate , Ii source) throws PAException {
        boolean isValid = true;
        StringBuffer sb = new StringBuffer();
        if (isIiNull(toValidate)) {
            throw new PAException("to Validate Identifier is null");
        }
        if (!source.getIdentifierName().equals(toValidate.getIdentifierName())) {
            sb.append(" Identifier Name does not match for " + source.getIdentifierName()
                    +  ", Exptected is " + toValidate.getIdentifierName());
        }
        if (!source.getRoot().equals(toValidate.getRoot())) {
            sb.append(" Root does not match for " + source.getRoot() + ", Exptected is " + toValidate.getRoot());
        }
        if (sb.length() > 0) {
            throw new PAException(sb.toString());
        }
        return isValid;
    }
    /**
     * checks if Cd is null.
     * @param cd cd
     * @return boolean
     */
    public static boolean isCdNull(Cd cd) {
        boolean isNull = false;
        if (cd == null || cd.getCode() == null) {
            return true;
        }
        if (cd.getCode().trim().length() == 0) {
            isNull = true;
        }
        return isNull;
    }

    /**
     * checks if St is null.
     * @param st st
     * @return boolean
     */
    public static boolean isStNull(St st) {
        boolean isNull = false;
        if (st == null || st.getValue() == null) {
            return true;
        }
        if (st.getValue().trim().length() == 0) {
            isNull = true;
        }
        return isNull;
    }

    /**
     * checks if Ts is null.
     * @param ts Ts
     * @return boolean
     */
    public static boolean isTsNull(Ts ts) {
        boolean isNull = false;
        if ((ts == null) || (ts.getValue() == null)) {
            isNull = true;
        }
        return isNull;
    }

    /**
     * checks if Bl is null.
     * @param bl Bl
     * @return boolean
     */
    public static boolean isBlNull(Bl bl) {
        boolean isNull = false;
        if (bl == null || bl.getValue() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * checks if Int is null.
     * @param in Int
     * @return boolean
     */
    public static boolean isIntNull(Int in) {
        boolean isNull = false;
        if (in == null || in.getValue() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * checks if pq value is null.
     * @param pq Pq
     * @return boolean
     */
    public static boolean isPqValueNull(Pq pq) {
        boolean isNull = false;
        if (pq == null || pq.getValue() == null) {
            return true;
        }
        return isNull;
    }
    /**
     * checks if pq Unit is null.
     * @param pq Pq
     * @return boolean
     */
    public static boolean isPqUnitNull(Pq pq) {
        boolean isNull = false;
        if (pq == null || pq.getUnit() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * checks if ivl high is null.
     * @param ivl Ivl
     * @return boolean
     */
    public static boolean isIvlHighNull(Ivl<Pq> ivl) {
        boolean isNull = false;
        if (ivl == null || ivl.getHigh() == null || ivl.getHigh().getValue() == null) {
            return true;
        }
        return isNull;
    }
    /**
     * checks if ivl low is null.
     * @param ivl Ivl
     * @return boolean
     */
    public static boolean isIvlLowNull(Ivl<Pq> ivl) {
        boolean isNull = false;
        if (ivl == null || ivl.getLow() == null || ivl.getLow().getValue() == null) {
            return true;
        }
        return isNull;
    }
    /**
     * checks if ivl unit is null.
     * @param ivl Ivl
     * @return boolean
     */
    public static boolean isIvlUnitNull(Ivl<Pq> ivl) {
        boolean isNull = false;
        if (ivl == null ||  ivl.getHigh() == null
            || ivl.getLow() == null || ivl.getHigh().getUnit() == null || ivl.getLow().getUnit() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * Convert an input string to a Date.
     *
     * @param inDate string to be normalized
     * @return Date
     */
    private static Date dateStringToDate(String inDate) {
        if (inDate == null) {
            return null;
        }

        Date outDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (ValidDateFormat fm : ValidDateFormat.getDateFormats()) {
            if (outDate != null) {
                break;
            }
            sdf.applyPattern(fm.getPattern());
            sdf.setLenient(fm.isLenient());
            try {
                int endIndex = (inDate.trim().length() < fm.getEndIndex()) ? inDate.trim().length() : fm.getEndIndex();
                outDate = sdf.parse(inDate.trim().substring(0, endIndex));
            } catch (ParseException e) {
                // BUGBUG: outDate can only be null here - this method does nothing!
                outDate = null;
            }
        }
        return outDate;
    }
    /**
     * Convert an input string to a Date.
     *
     * @param inDate string to be normalized
     * @return Date
     */
    private static Date dateStringToDateTime(String inDate) {
        if (inDate == null) {
            return null;
        }
        Date outDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (ValidDateFormat fm : ValidDateFormat.getDateFormats()) {
            sdf.applyPattern(fm.getPattern());
            sdf.setLenient(false);
            try {
                int endIndex = (inDate.trim().length() < fm.getEndIndex()) ? inDate.trim().length() : fm.getEndIndex();
                String dateToParse = inDate.trim().substring(0, endIndex);
                outDate = sdf.parse(dateToParse);
                break;
            } catch (ParseException e) {
               continue; //best effort to try the other date format(s).
            }
        }
        return outDate;
    }

    /**
     *
     * @param isoTs timestamp
     * @param format data format
     * @return String
     */
    public static String convertTsToFormarttedDate(Ts isoTs , String format) {
        Timestamp ts = TsConverter.convertToTimestamp(isoTs);
        if (ts == null) {
            return  null;
        }
        DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return  formatter.format(ts);
    }

    /**
    * Convert a Ts to a formatted date string. Output format is
    * determined by the first element in the static dateFormats array.
    *
    * @param isoTs timestamp
    * @return String
    */
   public static String convertTsToFormattedDate(Ts isoTs) {
       return convertTsToFormarttedDate(isoTs, ValidDateFormat.getDateFormats().get(0).getPattern());
   }

    /**
     * Convert an input string to a normalized date string.
     * The output format is determined by the first element in
     * the static dateFormats array.
     *
     * @param inDate string to be normalized
     * @return normalized string
     */
    public static String normalizeDateString(String inDate) {
        Date outDate = dateStringToDate(inDate);
        if (outDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(ValidDateFormat.getDateFormats().get(0).getPattern());
        return sdf.format(outDate);
    }
    /**
     * Convert an input string to a normalized date string.
     * The output format is determined by the first element in
     * the static dateFormats array.
     *
     * @param inDate string to be normalized
     * @return normalized string
     */
    public static String normalizeDateStringWithTime(String inDate) {
        Date outDate = dateStringToDateTime(inDate);
        if (outDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(ValidDateFormat.getDateFormats().get(1).getPattern());
        return sdf.format(outDate);
    }

    /**
     * Convert an input string to a Timestamp.
     *
     * @param inDate string to be normalized
     * @return Timestamp
     */
    public static Timestamp dateStringToTimestamp(String inDate) {
        Date dt = dateStringToDate(inDate);
        return (dt == null) ? null : new Timestamp(dt.getTime());
    }

    /**
     * @return today's date as a string
     */
    public static String today() {
        return normalizeDateString(new Timestamp((new Date()).getTime()).toString());
    }

    /**
     * Util method to validate email addresses.
     *
     * @param email to check the string
     * @return boolean whether email is valid or not
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        String match = email.trim();
        Pattern p = Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$");
        Matcher m = p.matcher(match);
        return  m.matches();
    }

    /**
     * Checks if is valid phone.
     *
     * @param phone the phone
     *
     * @return true, if is valid phone
     */
    public static boolean isValidPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        String match = phone.trim();
        Pattern p = Pattern.compile("^([\\w\\s\\-\\.\\+\\(\\)])*$");
        Matcher m = p.matcher(match);
        return  m.matches();
    }

     /**
     * Util method to validate Selection Yes/No.
     *
     * @param selection to check the string
     * @return boolean whether selection is valid or not
     */
    public static boolean isYesNo(String selection) {
       return "yes".equalsIgnoreCase(selection) || "no".equalsIgnoreCase(selection);
    }

    /**
     * @param data the input value which will be converted parameter used in search query
     * @return not null sting with * converted to %
     */
    public static String wildcardCriteria(String data) {
        String criteria = data;
        if (criteria == null) {
            criteria = "";
        }
        return criteria.replace('*', '%');
    }
    /**
     * util method to find if the lenght is more than len parameter.
     * @param st String data
     * @param len length to trim
     * @return trimmed data
     */
    public static boolean isGreaterThan(St st, int len) {
        boolean ret = false;
        String str = null;
        if (st == null) {
            ret = false;
        }
        str = StConverter.convertToString(st);
        if (str == null) {
            ret = false;
        } else if (str.length() > len) {
            ret = true;
        }
        return ret;
    }

    /**
     * util method to find if the length is between min and max.
     * @param st String data
     * @param min minimum numbers of characters
     * @param max maximim numbers of characters
     * @return trimmed data
     */
    public static boolean isWithinRange(St st , int min , int max) {
        boolean ret = false;
        String str = null;
        if (st == null) {
            ret = true;
        }
        str = StConverter.convertToString(st);
        if (str == null) {
            ret = true;
        } else if (str.length() >= min && str.length() <= max) {
            ret = true;
        }
        return ret;
    }
    /**
     *
     * @param identifier Ii
     * @return str
     */
    public static String getIiExtension(Ii identifier) {
        String ext = "";
        if (!isIiNull(identifier)) {
            ext = identifier.getExtension();
        }
        return ext;
    }
    /**
     * @param errMap error map
     * @return str
     */
    public static String getErrorMsg(Map<String, String[]> errMap) {
        TreeSet<String> orderedKeys = new TreeSet<String>(errMap.keySet());
        StringBuffer errMsg = new StringBuffer();
        for (Iterator<String> iterator = orderedKeys.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            String dash = " - ";
            errMsg.append(key).append(dash);
            errMsg.append(Arrays.deepToString(errMap.get(key)));
            if (iterator.hasNext()) {
                errMsg.append('\n');
            }
        }
        String strMsg = "";
        if (errMsg.length() > 1) {
            strMsg =  errMsg.toString();
            strMsg = strMsg.replace('[', ' ');
            strMsg = strMsg.replace(']', ' ');
        }
        return strMsg;
    }

    /**
     * loops through the map and returns the identifier of the ii.
     * @param map map of iis
     * @param key key
     * @return matching ii
     */
    public static Ii containsIi(Map<Ii, Ii> map , Ii key) {
        Ii value = null;
        if (map == null || key == null) {
            return value;
        }
        for (Ii tmp : map.keySet()) {
            if (tmp.getExtension().equals(key.getExtension())) {
                value = map.get(tmp);
                break;
            }
        }
        return value;
    }

    /**
     *
     * @param <TYPE> any base object extending BaseDTO
     * @param list list of objects
     * @return <TYPE> any base object extending BaseDTO
     */
    @SuppressWarnings("unchecked")
    public static <TYPE extends BaseDTO> TYPE getFirstObj(List<? extends BaseDTO> list) {
        TYPE type = null;
        if (list != null && !list.isEmpty()) {
             type =  (TYPE) list.get(0);
        }
        return type;

    }

    /**
     * Read an input stream in its entirety into a byte array.
     * @param inputStream is
     * @return byte[]
     * @throws IOException on error
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {

        int bufSize = MAXF * MAXF;
        byte[] content;

        List<byte[]> parts = new LinkedList<byte[]>();
        InputStream in = new BufferedInputStream(inputStream);

        byte[] readBuffer = new byte[bufSize];
        byte[] part = null;
        int bytesRead = 0;

        // read everyting into a list of byte arrays
        while ((bytesRead = in.read(readBuffer, 0, bufSize)) != -1) {
            part = new byte[bytesRead];
            System.arraycopy(readBuffer, 0, part, 0, bytesRead);
            parts.add(part);
        }

        // calculate the total size
        int totalSize = 0;
        for (byte[] partBuffer : parts) {
            totalSize += partBuffer.length;
        }

        // allocate the array
        content = new byte[totalSize];
        int offset = 0;
        for (byte[] partBuffer : parts) {
            System.arraycopy(partBuffer, 0, content, offset, partBuffer.length);
            offset += partBuffer.length;
        }

        return content;
    }

    /**
     *
     * @param documentIdentifier document identifier
     * @param fileName name of the file
     * @param nciIdentifier nci identifier
     * @return the file path
     * @throws PAException on error
     */
    public static String getDocumentFilePath(Long documentIdentifier , String fileName , String nciIdentifier)
    throws PAException {
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StringBuffer sb  = new StringBuffer(folderPath);
        sb.append(File.separator).append(nciIdentifier).
                append(File.separator).append(documentIdentifier).append('-').append(fileName);
        return sb.toString();

    }
    /**
     * @param date date
     * @return boolean
     */
    public static boolean isDateCurrentOrPast(String date) {
        Timestamp siteStatusDate = PAUtil.dateStringToTimestamp(date);
        return isDateCurrentOrPast(siteStatusDate);
    }

    /**
     * @param date date
     * @return boolean
     */
    public static boolean isDateCurrentOrPast(Timestamp date) {
        boolean retValue = false;
        Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
        if (currentTimeStamp.before(date)) {
            retValue = true;
        }
        return retValue;
    }

    /**
     * check if the date is of valid format.
     * @param dateString dateString
     * @return boolean
     */
    public static boolean isValidDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return false;
        }
        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        if (dateString.trim().length() != dateFormat.toPattern().length())  {
            return false;
        }
        dateFormat.setLenient(false);
        try {
            //parse the date
            dateFormat.parse(dateString.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
   }

    /**
     * Checks if the documentWorkflowStatus code is abstracted and above.
     *
     * @param documentWorkFlowStatusCode the document work flow status code
     *
     * @return true, if is abstracted and above
     */
    public static boolean isAbstractedAndAbove(Cd documentWorkFlowStatusCode) {
      boolean retValue = false;
      String dwfs = documentWorkFlowStatusCode.getCode();
      if (dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTED.getCode())
              || dwfs.equals(DocumentWorkflowStatusCode.VERIFICATION_PENDING.getCode())
            || dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode())
            || dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode())) {
        retValue = true;
      }
      return retValue;
    }

    /**
     * @param value value
     * @return bd
     */
    public static BigDecimal convertStringToDecimal(String value) {
        BigDecimal bd = null;
        if (NumberUtils.isNumber(value)) {
            bd = BigDecimal.valueOf(Double.parseDouble(value));
        }
        return bd;
    }

    /**
     * @param javapq javapq
     * @return bd
     */
    public static String convertPqToUnit(JavaPq javapq) {
        String unit = null;
        if (javapq != null && javapq.getUnit() != null) {
           unit = javapq.getUnit();
        }
        return unit;
    }

    /**
     * @param javapq javapq
     * @return bd
     */
    public static BigDecimal convertPqToDecimal(JavaPq javapq) {
        BigDecimal bd = null;
        if (javapq != null && javapq.getValue() != null) {
            bd = javapq.getValue();
        }
        return bd;
    }

    /**
     * @param javapq javapq
     * @return bd
     */
    public static Integer convertPqToPrecision(JavaPq javapq) {
        Integer precision = null;
        if (javapq != null && javapq.getPrecision() != null) {
          precision = javapq.getPrecision();
        }
        return precision;
    }

    /**
     * Checks if is type intervention.
     *
     * @param cd the cd
     *
     * @return true, if is type intervention
     */
    public static boolean isTypeIntervention(Cd cd) {
      boolean isTypeIntervention = false;
      if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                 .convertCdToString(cd)))
            || ActivityCategoryCode.PLANNED_PROCEDURE.equals(ActivityCategoryCode.getByCode(CdConverter
                     .convertCdToString(cd)))
            || ActivityCategoryCode.SUBSTANCE_ADMINISTRATION.equals(ActivityCategoryCode.getByCode(CdConverter
                     .convertCdToString(cd)))) {
        isTypeIntervention = true;
      }
       return isTypeIntervention;
    }

    /**
     *
     * @param phoneNumber no
     * @return s
     */
    public static boolean isPhoneValidForUSA(String phoneNumber) {
        boolean isValidPhoneNumber = false;
        if (phoneNumber != null) {
            Pattern numberPattern = Pattern.compile("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}([e][x][t][n][0-9]{1,10})?$");
            Matcher fit = numberPattern.matcher(phoneNumber);
            if (fit.matches()) {
                isValidPhoneNumber = true;
            }
        }
        return isValidPhoneNumber;
    }
    /**
     *
     * @param telecomAddresses tel
     * @return boolean
     */
    public static boolean isDSetTelNull(DSet<Tel> telecomAddresses) {
        boolean isNull = true;
        if (telecomAddresses == null || telecomAddresses.getItem() == null) {
            isNull = true;
        } else {
            for (Tel t : telecomAddresses.getItem()) {
                if (t.getNullFlavor() != null) {
                    continue;
                }
                String phone = "";
                if (t instanceof TelPhone) {
                    try {
                        phone = URLDecoder.decode(t.getValue().getSchemeSpecificPart(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        continue;
                    }
                } else {
                    String url = t.getValue().toString();
                    if (url != null && url.startsWith("tel")) {
                        try {
                            phone = URLDecoder.decode(t.getValue().getSchemeSpecificPart(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            continue;
                        }
                    }
                }
                if (StringUtils.isNotEmpty(phone)) {
                    isNull = false;
                }
            }

        }
        return isNull;
    }

    /**
     * @param dset telecom address
     * @return email the email
     */
    public static String getEmail(DSet<Tel> dset) {
        return getEmailOrPhone(DSetConverter.convertDSetToList(dset, "EMAIL"));
    }

    /**
     * @param dset telecom address
     * @return phone the phone without extension
     */
    public static String getPhone(DSet<Tel> dset) {
        String phone = getEmailOrPhone(DSetConverter.convertDSetToList(dset, "PHONE"));
        return (phone != null ? getPhone(phone) : null);
    }

    /**
     * @param dset telecom address
     * @return extn the phone extension
     */
    public static String getPhoneExtension(DSet<Tel> dset) {
        String phoneWithExtn = getEmailOrPhone(DSetConverter.convertDSetToList(dset, "PHONE"));
        return (phoneWithExtn != null ? getPhoneExtn(phoneWithExtn) : null);
    }

    private static String getEmailOrPhone(List<String> emailsOrPhones) {
        String retVal = null;
        if (emailsOrPhones != null && !emailsOrPhones.isEmpty()) {
            retVal = emailsOrPhones.get(0);
        }
        return retVal;
    }

    /**
     *
     * @param phone phone with ex
     * @return extn
     */
    public static String getPhoneExtn(String phone) {
        String strExtn = "";
        if (phone.contains(EXTN)) {
            strExtn = phone.substring(phone.indexOf(EXTN) + EXTN_COUNT);
        }
        return strExtn;
    }
    /**
     *
     * @param phone phone
     * @return phone
     */
    public static String getPhone(String phone) {
        String strPhone = "";
        if (phone.contains(EXTN)) {
            strPhone = phone.substring(0, phone.indexOf(EXTN));
        } else {
            strPhone = phone;
        }
        return strPhone;
    }
    /**
     *
     * @param minUnit min
     * @param maxUnit max
     * @return true
     */
    public static boolean isUnitLessOrSame(String minUnit, String maxUnit) {
        boolean isSameorLess = false;
        if (minUnit.equalsIgnoreCase(maxUnit)) {
            isSameorLess = true;
        }
        if (maxUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode())) {
            isSameorLess = true;
        } else if (maxUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                && !minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode())) {
            isSameorLess = true;
        } else if (maxUnit.equalsIgnoreCase(UnitsCode.WEEKS.getCode())
                && !(minUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode()))) {
            isSameorLess = true;
        } else if (maxUnit.equalsIgnoreCase(UnitsCode.DAYS.getCode())
                && !(minUnit.equalsIgnoreCase(UnitsCode.WEEKS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode()))) {
            isSameorLess = true;
        } else if (maxUnit.equalsIgnoreCase(UnitsCode.HOURS.getCode())
                && !(minUnit.equalsIgnoreCase(UnitsCode.DAYS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.WEEKS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode()))) {
            isSameorLess = true;
        } else if (maxUnit.equalsIgnoreCase(UnitsCode.MINUTES.getCode())
                && !(minUnit.equalsIgnoreCase(UnitsCode.HOURS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.DAYS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.WEEKS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode()))) {
            isSameorLess = true;
        }
        return isSameorLess;
    }
    /**
     * @param age age
     * @return s
     */
    public static String getAge(BigDecimal age) {
        String retAge = age.toString();
        if (retAge.endsWith(".0")) {
            retAge = retAge.replace(".0", "");
        }
        return retAge;
    }

    /**
     * Gets the assigned identifier extension.
     *
     * @param spDTO the sp dto
     *
     * @return the assigned identifier
     */
    public static String getAssignedIdentifierExtension(StudyProtocolDTO spDTO) {
        String assignedIdentifier = "";
        if (spDTO.getSecondaryIdentifiers() != null && spDTO.getSecondaryIdentifiers().getItem() != null) {
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    return ii.getExtension();
                }
            }
        }
        return assignedIdentifier;
    }

    /**
     * Gets the assigned identifier.
     *
     * @param spDTO the sp dto
     *
     * @return the assigned identifier
     */
    public static Ii getAssignedIdentifier(StudyProtocolDTO spDTO) {
        Ii assignedIdentifier = new Ii();
        assignedIdentifier.setNullFlavor(NullFlavor.NI);
        if (spDTO.getSecondaryIdentifiers() != null && spDTO.getSecondaryIdentifiers().getItem() != null) {
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    return ii;
                }
            }
        }
        return assignedIdentifier;
    }

    /**
     * Returns a listing of a study protocols other identifier extensions.
     * @param spDTO the study protocol dto
     * @return the other identifiers
     */
    public static List<Ii> getOtherIdentifiers(StudyProtocolDTO spDTO) {
        List<Ii> results = new ArrayList<Ii>();
        if (spDTO.getSecondaryIdentifiers() != null
                && CollectionUtils.isNotEmpty(spDTO.getSecondaryIdentifiers().getItem())) {
            for (Ii id : spDTO.getSecondaryIdentifiers().getItem()) {
                if (StringUtils.equals(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT, id.getRoot())) {
                    results.add(id);
                }
            }
        }
        return results;
    }

    /**
     * Returns a listing of a study protocols identifiers that are not other identifiers.
     * @param spDTO the study protocol dto
     * @return the identifiers
     */
    public static Ii getNonOtherIdentifiers(StudyProtocolDTO spDTO) {
        Ii results = new Ii();
        if (spDTO.getSecondaryIdentifiers() != null
                && CollectionUtils.isNotEmpty(spDTO.getSecondaryIdentifiers().getItem())) {
            for (Ii id : spDTO.getSecondaryIdentifiers().getItem()) {
                if (!StringUtils.equals(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT, id.getRoot())) {
                    return id;
                }
            }
        }
        return results;
    }


    /**
     * Check assigned identifier exists dto.
     *
     * @param spDTO the sp dto
     *
     * @return true, if successful
     */
    public static boolean checkAssignedIdentifierExists(StudyProtocolDTO spDTO) {
        boolean assignedIdentifierExists = false;
        if (spDTO.getSecondaryIdentifiers() != null && spDTO.getSecondaryIdentifiers().getItem() != null) {
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    return true;
                }
            }
        }
        return assignedIdentifierExists;
    }

    /**
     * Given a username string, determines if its in the grid format, returns the CN.
     * @param gridIdentity the grid identity string.
     * @return the username or the grid identity.
     */
    public static String getGridIdentityUsername(String gridIdentity) {
        String splitString = "CN=";
        if (StringUtils.contains(gridIdentity, splitString)) {
            return gridIdentity.split(splitString)[1];
        }
        return gridIdentity;
    }
    /**
     *checks if Ed is null.
     * @param ed ed
     * @return boolean
     */
    public static boolean isEdNull(Ed ed) {
      return (ed == null || ed.getData() == null);
    }
    
    /**
     * Given a NullifiedEntityException pull out a useful error message.
     * @param e NEE.
     * @return message
     */
    public static String handleNullifiedEntityException(NullifiedEntityException e) {
        StringBuffer message = new StringBuffer("The entity is no longer available.");
        if (e.getNullifiedEntities().size() > 0) {
            message = new StringBuffer("");
            for (Ii key : e.getNullifiedEntities().keySet()) {
                if (IiConverter.ORG_ROOT.equals(key.getRoot())) {
                    message.append("The Organization with id ");
                } else if (IiConverter.PERSON_ROOT.equals(key.getRoot())) {
                    message.append("The Person with id ");
                } else {
                    continue;
                }
                 
                message.append(key.getExtension());
                message.append(" is no longer available.");
                Ii value = e.getNullifiedEntities().get(key);
                if (PAUtil.isIiNotNull(value)) {
                    message.append("Switch to id ");
                    message.append(value.getExtension());
                }
                                                                
            }
        }
        return message.toString();
    }
    
    /**
     * Current time.
     * @return current time.
     * @throws ParseException if parse error.
     */
    public static Timestamp getCurrentTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String simpleDate = sdf.format(new Date());
        return new Timestamp(sdf.parse(simpleDate).getTime());
    }
}
