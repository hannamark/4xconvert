/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po-app
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po-app Software License (the License) is between NCI and You. You (or
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
 * its rights in the po-app Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po-app Software; (ii) distribute and
 * have distributed to and by third parties the po-app Software and any
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
package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;
import gov.nih.nci.po.util.NotEmpty;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ValidStateCountry;
import gov.nih.nci.po.util.ValidZip;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Primary address class.
 */
@Entity
@ValidStateCountry
@ValidZip
public class Address implements Auditable, PersistentObject {
    private static final long serialVersionUID = 1L;

    private static final int LINE_LENGTH = 254;
    private static final int CITY_LENGTH = 50;
    private static final int STATE_LENGTH = 50;
    private static final int POSTAL_LENGTH = 20;

    private Long id;
    private String streetAddressLine;
    private String deliveryAddressLine;
    private String cityOrMunicipality;
    private String stateOrProvince;
    private String postalCode;
    private Country country;

    /**
     * @param streetAddressLine line 1
     * @param cityOrMunicipality cityOrMunicipality
     * @param stateOrProvince stateOrProvince
     * @param postalCode postal code
     * @param country country
     */
    public Address(String streetAddressLine, String cityOrMunicipality, String stateOrProvince, String postalCode,
                   Country country) {
        this.streetAddressLine = streetAddressLine;
        this.cityOrMunicipality = cityOrMunicipality;
        this.stateOrProvince = stateOrProvince;
        this.postalCode = postalCode;
        this.country = country;
    }

    /**
     * default constructor.
     */
    public Address() {
      // Empty constructor.
    }

    /**
     * Copy the data from a different address.
     * @param src the src address.
     */
    public void copy(Address src) {
        this.setCityOrMunicipality(src.getCityOrMunicipality());
        this.setCountry(src.getCountry());
        this.setDeliveryAddressLine(src.getDeliveryAddressLine());
        this.setPostalCode(src.getPostalCode());
        this.setStateOrProvince(src.getStateOrProvince());
        this.setStreetAddressLine(src.getStreetAddressLine());
    }

    /**
     * @return database identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * The setter for the id.
     * @param id the id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return line 1
     */
    @NotEmpty
    @Length(max = LINE_LENGTH)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "street1")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getStreetAddressLine() {
        return streetAddressLine;
    }

    /**
     * @param streetAddressLine line 1
     */
    public void setStreetAddressLine(String streetAddressLine) {
        this.streetAddressLine = streetAddressLine;
    }

    /**
     * @return line 2
     */
    @Length(max = LINE_LENGTH)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "street2")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getDeliveryAddressLine() {
        return deliveryAddressLine;
    }

    /**
     * @param deliveryAddressLine line 2
     */
    public void setDeliveryAddressLine(String deliveryAddressLine) {
        this.deliveryAddressLine = deliveryAddressLine;
    }

    /**
     * @return cityOrMunicipality
     */
    @NotEmpty
    @Length(max = CITY_LENGTH)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "city")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getCityOrMunicipality() {
        return cityOrMunicipality;
    }

    /**
     * @param cityOrMunicipality cityOrMunicipality
     */
    public void setCityOrMunicipality(String cityOrMunicipality) {
        this.cityOrMunicipality = cityOrMunicipality;
    }

    /**
     * @return postal code
     */
    @Length(max = POSTAL_LENGTH)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "postalCode")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return stateOrProvince
     */
    @Length(max = STATE_LENGTH)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "state")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getStateOrProvince() {
        return stateOrProvince;
    }

    /**
     * @param stateOrProvince stateOrProvince
     */
    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @ForeignKey(name = "ADDRESS_COUNTRY_FK")
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "country")
    @Searchable(nested = true)
    public Country getCountry() {
        return country;
    }

    /**
     * @param country country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Checks if the contents of this Address are equal to the contents of another address, ignoring the ID.
     * @param other other Address to compare against
     * @return true if the contents are equal, false otherwise
     */
    public boolean contentEquals(Address other) {
        return StringUtils.equals(this.getStreetAddressLine(), other.getStreetAddressLine())
                && StringUtils.equals(this.getDeliveryAddressLine(), other.getDeliveryAddressLine())
                && StringUtils.equals(this.getCityOrMunicipality(), other.getCityOrMunicipality())
                && StringUtils.equals(this.getStateOrProvince(), other.getStateOrProvince())
                && StringUtils.equals(this.getPostalCode(), other.getPostalCode())
                && ((this.getCountry() == null && other.getCountry() == null)
                        || (this.getCountry() != null && other.getCountry() != null
                                && ObjectUtils.equals(this.getCountry().getId(), other.getCountry().getId())));
    }

}
