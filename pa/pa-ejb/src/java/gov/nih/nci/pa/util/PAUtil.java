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

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.iso.util.TsConverter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Protocol DAO for accessing DAO.
 *
 * @author Naveen Amiruddin
 * @since 05/30/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings("PMD")
public class PAUtil {
    /**
     * utility method to determine if the object is not null. 
     * it returns true, if the object is not null , and does not contain any white space
     * @deprecated
     * @param obj obj1
     * @return boolean 
     */
    public static boolean isNotNullOrNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof String) {
            String s = obj.toString();
            if (s != null && s.trim().length() > 0) {
                return true;
            }
            return false;
        } else if (obj != null) {
            return true;
        }
        return false;
    }

    /**
     * checks if Ii is null.
     * @param ii ii
     * @return boolean
     */
    public static boolean isIiNull(Ii ii) {
        boolean isNull = false;
        if (ii == null || ii.getExtension() == null) {
            return true;
        } else {
            if (ii.getExtension().trim().length() == 0) {
                isNull = true;
            }
        }
        try {
            new Long(ii.getExtension());
        } catch (NumberFormatException nfe) {
            // if cannot be converted, consider as null
            isNull = true;
        }
        return isNull;
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
        } else {
            if (cd.getCode().trim().length() == 0) {
                isNull = true;
            }
        }
        return isNull;
    }

    /**
     * checks if Cd is null.
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
     * Private class used to decode and normalize date strings.
     */
    private static class ValidDateFormat {
        String pattern;
        int endIndex;
        boolean lenient;

        public ValidDateFormat(String pattern) {
            this.pattern = pattern;
            this.endIndex = pattern.length();
            this.lenient = false;
        }
        public ValidDateFormat(String pattern, Integer length, Boolean lenient) {
            this.pattern = pattern;
            this.endIndex = length;
            this.lenient = lenient;
        }
    }

    /**
     * Static ordered list of valid date format patterns.
     */
    private static ValidDateFormat[] dateFormats;
    static {
        dateFormats = new ValidDateFormat[] { 
                new ValidDateFormat("MM/dd/yyyy"),
                new ValidDateFormat("yyyy-MM-dd"),
                new ValidDateFormat("yyyy/MM/dd")
        };
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
        for (ValidDateFormat fm : dateFormats) {
            if (outDate != null) {
                break; 
            }
            sdf.applyPattern(fm.pattern);
            sdf.setLenient(fm.lenient);
            try {
                int endIndex = (inDate.trim().length() < fm.endIndex) ? inDate.trim().length() : fm.endIndex;
                outDate = sdf.parse(inDate.trim().substring(0, endIndex));
            } catch (ParseException e) {
                outDate = null;
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
        sdf.applyPattern(dateFormats[0].pattern);
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
    * Returns whether the argument is null or has only whitespace characters 
    * within it. This method is more efficient than performing a trim() operation 
    * because no intermediate strings are created and we often don't need to 
    * iterate over the whole string. 
    * 
    * @param aString String 
    * @return boolean 
    */ 
    public static boolean isEmpty(String aString) { 
     
        if (aString == null) {
            return true; 
        }
         
        int length = aString.length(); 
         
        for (int i = 0; i < length; i++) { 
            if (Character.isWhitespace(aString.charAt(i))) {
                continue; 
            }
            return false; 
        } 
     
    return true; 
    } 
    
    /**
     * 
     * @param aString aString
     * @return boolean
     */
    public static boolean isNotEmpty(String aString) { 
        return !isEmpty(aString); 
    }

    /**
     * Method designed for string setters to concatenate rather then cause exceptions.
     * @param value a string
     * @param maxLength the maximum length allowed for the string
     * @return null if empty, otherwise a string no longer than maxlegth
     */
    public static String stringSetter(String value, int maxLength) {
        if (isEmpty(value)) {
            return null;
        }
        return (value.length() > maxLength) ? value.substring(0, maxLength) : value;
    }

    /**
     * Method designed for string setters to concatenate rather then cause exceptions.
     * @param value a string
     * @return null if empty, otherwise a string no longer than the default
     */
    public static String stringSetter(String value) {
        return stringSetter(value, AbstractEntity.LONG_TEXT_LENGTH);
    }

    /**
     * Util method to validate email addresses.
     * 
     * @param email to check the string
     * @return boolean whether email is valid or not
     */
    public static boolean isValidEmail(String email) {
      if (email != null) {
        email = email.trim();
       }
       Pattern p = Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$");
       Matcher m = p.matcher(email);
       return  m.matches();
    }

    /**
     * util method to trim a length.
     * @param data String data
     * @param len length to trim
     * @return trimmed data
     */
    public static String trim(String data , int len) {
        if (data == null) {
            return null;
        }
        if (data.length() > len) {
            return (data.substring(0, len - 1) + "...");
        } else {
            return data;
        }
    }

    /**
     * @param data the input value which will be converted parameter used in search query
     * @return not null sting with * converted to %
     */
    public static String wildcardCriteria(String data) {
        if (data == null) {
            data = "";
        }
        return data.replace('*', '%');
    }
}
