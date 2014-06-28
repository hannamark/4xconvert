/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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
package gov.nih.nci.po.service;

import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.AdxpAdl;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.services.PoIsoConstraintException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.lang.StringUtils;

/**
 * @author mshestopalov
 *
 */
public class TestConvertHelper {
    /**
    *
    * @param value a boolean to parse.
    * @return an iso BL
    */
   public static Bl convertToBl(Boolean value) {
       Bl iso = new Bl();
       if (value == null) {
           iso.setNullFlavor(NullFlavor.NI);
       } else {
           iso.setValue(value);
       }
       return iso;
   }

   /**
    * @param value string to parse.
    * @return a 1 part EnOn.
    */
   public static EnOn convertToEnOn(String value) {
       EnOn iso = new EnOn();
       if (value == null) {
           iso.setNullFlavor(NullFlavor.NI);
       } else {
           Enxp e = new Enxp(null);
           e.setValue(value);
           iso.getPart().add(e);
       }
       return iso;
   }

   /**
    *
    * @param value a string to parse.
    * @return an iso ST
    */
   public static St convertToSt(String value) {
       St iso = new St();
       if (value == null || value.length() == 0) {
           iso.setNullFlavor(NullFlavor.NI);
       } else {
           iso.setValue(value);
       }
       return iso;
   }

   private static void setAdxpValue(List<Adxp> l, String s, Adxp x) {
       if (StringUtils.isNotBlank(s)) {
           x.setValue(s);
           l.add(x);
       }
   }

   /**
    * @param streetAddressLine street address
    * @param deliveryAddressLine delivery address
    * @param cityOrMunicipality city name
    * @param stateOrProvince state or province
    * @param postalCode postal code
    * @param countryAlpha3 ISO-3316 3-letter country code
    * @return simply ISO address
    */
   public static Ad createAd(String streetAddressLine, String deliveryAddressLine, String cityOrMunicipality,
           String stateOrProvince, String postalCode, String countryAlpha3) {
       Ad iso = new Ad();
       List<Adxp> l = new ArrayList<Adxp>();
       iso.setPart(l);
       setAdxpValue(l, streetAddressLine, new AdxpAl());
       if (StringUtils.isNotBlank(deliveryAddressLine)) {
           setAdxpValue(l, deliveryAddressLine, new AdxpAdl());
       }
       setAdxpValue(l, cityOrMunicipality, new AdxpCty());
       setAdxpValue(l, stateOrProvince, new AdxpSta());
       setAdxpValue(l, postalCode, new AdxpZip());

       if (StringUtils.isNotBlank(countryAlpha3)) {
           Adxp x = new AdxpCnt();
           x.setValue("adxp.value is required");
           x.setCode(countryAlpha3);
           x.setCodeSystem("ISO 3166-1 alpha-3 code");
           l.add(x);
       }
       return iso;
   }

   /**
    * @param value an II used to identify PO entities.
    * @return a long suitable for a hibernate entity Id
    */
   public static Long convertToLong(Ii value) {
       if (value == null || value.getNullFlavor() != null) {
           return null;
       }

       enforcePoIiIsoConstraints(value);

       String root = value.getRoot();
       if (root == null) {
           throw new IllegalArgumentException("root is required");
       }

       return Long.valueOf(value.getExtension());
   }

   private static void enforcePoIiIsoConstraints(Ii value) {
       if (StringUtils.isEmpty(value.getExtension())) {
           throw new PoIsoConstraintException("ii.extension is required if a null flavor is not provided.");
       }
   }

   public static Ii convertToOrgIi(Long value) {
       Ii iso = new Ii();
       if (value == null) {
           iso.setNullFlavor(NullFlavor.NI);
       } else {
           iso.setExtension(value.toString());
           iso.setDisplayable(true);
           iso.setScope(IdentifierScope.OBJ);
           iso.setIdentifierName("NCI organization entity identifier");
           iso.setRoot("2.16.840.1.113883.3.26.6.2");
           // change me if hibernates IDs are no loger autogenerated
           iso.setReliability(IdentifierReliability.ISS);

       }

       return iso;
   }

   private static void addEnxp(EnPn enpn, String value, EntityNamePartType type) {
       if (StringUtils.isNotEmpty(value)) {
           Enxp part = new Enxp(type);
           part.setValue(value);
           enpn.getPart().add(part);
       }
   }

   /**
    * @param firstName given name
    * @param middleName middle name
    * @param lastName family name
    * @param prefix prefix
    * @param suffix suffix
    * @return ISO EN Person Name
    */
   public static final EnPn convertToEnPn(String firstName, String middleName, String lastName, String prefix,
           String suffix) {
       EnPn enpn = new EnPn();
       addEnxp(enpn, lastName, EntityNamePartType.FAM);
       addEnxp(enpn, firstName, EntityNamePartType.GIV);
       addEnxp(enpn, middleName, EntityNamePartType.GIV);
       addEnxp(enpn, prefix, EntityNamePartType.PFX);
       addEnxp(enpn, suffix, EntityNamePartType.SFX);
       return enpn;
   }

   /**
    * @param email email
    * @param fax fax
    * @param phone phone
    * @param url url
    * @param text tty
    * @return a list containg all the params' content converted to a Tel type.
    */
   public static DSet<Tel> convertToDSetTel(List<String> email, List<String> fax,
           List<String> phone, List<String> url, List<String> text) {
       DSet<Tel> dset = new DSet<Tel>();
       @SuppressWarnings("unchecked")
       Set<Tel> set = new ListOrderedSet();
       dset.setItem(set);
       if (email != null) {
           for (String c : email) {
               TelEmail t = new TelEmail();
               t.setValue(createURI(TelEmail.SCHEME_MAILTO, c));
               set.add(t);
           }
       }
       if (fax != null) {
           for (String c : fax) {
               TelPhone t = new TelPhone();
               t.setValue(createURI(TelPhone.SCHEME_X_TEXT_FAX, c));
               set.add(t);
           }
       }
       if (phone != null) {
           for (String c : phone) {
               TelPhone t = new TelPhone();
               t.setValue(createURI(TelPhone.SCHEME_TEL, c));
               set.add(t);
           }
       }
       if (url != null) {
           for (String c : url) {
               TelUrl t = new TelUrl();
               t.setValue(URI.create(c));
               set.add(t);
           }
       }
       if (text != null) {
           for (String c : text) {
               TelPhone t = new TelPhone();
               t.setValue(createURI(TelPhone.SCHEME_X_TEXT_TEL, c));
               set.add(t);
           }
       }
       return dset;
   }

   private static URI createURI(String scheme, String schemeSpecificPart) {
       try {
           return new URI(scheme, schemeSpecificPart, null);
       } catch (URISyntaxException ex) {
           throw new IllegalArgumentException(ex);
       }
   }
}
