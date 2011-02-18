/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.accrual.service.util;

import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Utility methods for converting batch uploads into data objects.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class BatchUploadUtils {
    private static final Logger LOG = Logger.getLogger(BatchUploadUtils.class);
    
    private static final Map<String, PatientGenderCode> GENDER_MAP = new HashMap<String, PatientGenderCode>();
    static {
        GENDER_MAP.put("1", PatientGenderCode.MALE);
        GENDER_MAP.put("2", PatientGenderCode.FEMALE);
        GENDER_MAP.put("9", PatientGenderCode.UNKNOWN);
    }
    
    private static final Map<String, PatientEthnicityCode> ETHNICITY_MAP = new HashMap<String, PatientEthnicityCode>();
    static {
        ETHNICITY_MAP.put("1", PatientEthnicityCode.HISPANIC);
        ETHNICITY_MAP.put("2", PatientEthnicityCode.NOT_HISPANIC);
        ETHNICITY_MAP.put("8", PatientEthnicityCode.NOT_REPORTED);
        ETHNICITY_MAP.put("9", PatientEthnicityCode.UNKNOWN);
    }
    
    private static final Map<String, PaymentMethodCode> PAYMENT_METHOD_MAP = new HashMap<String, PaymentMethodCode>();
    static {
        PAYMENT_METHOD_MAP.put("1", PaymentMethodCode.PRIVATE);
        PAYMENT_METHOD_MAP.put("2", PaymentMethodCode.MEDICARE);
        PAYMENT_METHOD_MAP.put("3", PaymentMethodCode.MEDICARE_AND_PRIVATE);
        PAYMENT_METHOD_MAP.put("4", PaymentMethodCode.MEDICAID);
        PAYMENT_METHOD_MAP.put("5", PaymentMethodCode.MEDICAID_AND_MEDICARE);
        PAYMENT_METHOD_MAP.put("6", PaymentMethodCode.MILITARY_OR_VETERANS);
        PAYMENT_METHOD_MAP.put("6A", PaymentMethodCode.MILITARY);
        PAYMENT_METHOD_MAP.put("6a", PaymentMethodCode.MILITARY);
        PAYMENT_METHOD_MAP.put("6B", PaymentMethodCode.VETERANS);
        PAYMENT_METHOD_MAP.put("6b", PaymentMethodCode.VETERANS);
        PAYMENT_METHOD_MAP.put("7", PaymentMethodCode.SELF);
        PAYMENT_METHOD_MAP.put("8", PaymentMethodCode.NO_MEANS_OF_PAYMENT);
        PAYMENT_METHOD_MAP.put("98", PaymentMethodCode.OTHER);
        PAYMENT_METHOD_MAP.put("99", PaymentMethodCode.UNKNOWN);
    }
    
    private static final Map<String, PatientRaceCode> RACE_MAP = new HashMap<String, PatientRaceCode>();
    static {
        RACE_MAP.put("01", PatientRaceCode.WHITE);
        RACE_MAP.put("03", PatientRaceCode.BLACK);
        RACE_MAP.put("04", PatientRaceCode.HAWAIIAN);
        RACE_MAP.put("05", PatientRaceCode.ASIAN);
        RACE_MAP.put("06", PatientRaceCode.AMERICAN_INDIAN);
        RACE_MAP.put("98", PatientRaceCode.NOT_REPORTED);
        RACE_MAP.put("99", PatientRaceCode.UNKNOWN);
    }
    
    private static final String DOB_DATE_FORMAT = "yyyyMM";
    
    /**
     * Returns the gender code enum corresponding to the give batch gender or null if no such code exists.
     * @param gender the batch gender code
     * @return the gender code or null if a corresponding one doesn't exist
     */
    public static PatientGenderCode getGender(String gender) {
        return GENDER_MAP.get(gender);
    }
    
    /**
     * Returns the ethnicity code enum for the given batch ethnicity code or null if no such code exists.
     * @param ethnicity the batch ethnicity code
     * @return the ethnicity code enum or null if a corresponding one doesn't exist
     */
    public static PatientEthnicityCode getEthnicity(String ethnicity) {
        return ETHNICITY_MAP.get(ethnicity);
    }
    
    /**
     * Returns the payment method code enum for the given batch payment method code or null if no such code exists.
     * @param paymentMethod the batch payment method code
     * @return the payment mehtod code enum or null if a corresponding one doesn't exist
     */
    public static PaymentMethodCode getPaymentMethod(String paymentMethod) {
        return PAYMENT_METHOD_MAP.get(paymentMethod);
    }
    
    /**
     * Returns the race code enum for the give batch race code or null if no such code exists.
     * @param race the batch race code
     * @return the race code enum or null of a corresponding one doesn't exist
     */
    public static PatientRaceCode getRace(String race) {
        return RACE_MAP.get(race);
    }
    
    /**
     * Returns the patient date of birth from the given dob string.
     * @param dob the dob string in year/month format
     * @return the parsed date or null if the date is unparseable
     */
    public static Date getPatientDOB(String dob) {
        SimpleDateFormat formatter = new SimpleDateFormat(DOB_DATE_FORMAT, Locale.getDefault());
        Date date = null;
        try {
            date = formatter.parse(dob);
        } catch (ParseException e) {
            LOG.error("Error parsing the following dob: " + dob);
        }
        return date;
    }
}
