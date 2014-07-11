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

package gov.nih.nci.po.data.convert;

import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.State;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.services.PoIsoConstraintException;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Converts for Ad (both simple and set based).
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class AdConverter {

    /**
     * Converter for simple addresses (ie, not sets or bags).
     */
    public static class SimpleConverter extends AbstractXSnapshotConverter<Ad> {
        private static final String GB = "GB ";
        private static final String UK = "UK";

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public <TO> TO convert(Class<TO> returnClass, Ad value) {
            if (returnClass == Address.class) {
                return (TO) convertToAddress(value);
            }
            throw new UnsupportedOperationException(returnClass.getName());
        }

        /**
         * @param iso the address to convert into a BO Address.
         * @return a BO address.
         */
        public static Address convertToAddress(Ad iso) {
            if (iso == null || iso.getNullFlavor() != null) {
                return null;
            }

            return processParts(iso);

        }

        private static Address processParts(Ad iso) {
            Address a = new Address();

            StringBuffer street = new StringBuffer();
            StringBuffer delivery = new StringBuffer();

            processParts(iso, a, street, delivery);

            a.setStreetAddressLine(StringUtils.trimToNull(street.toString()));
            a.setDeliveryAddressLine(StringUtils.trimToNull(delivery.toString()));
            return a;
        }

        @SuppressWarnings({ "PMD.UseStringBufferForStringAppends", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
        private static void processParts(Ad iso, Address a, StringBuffer street, StringBuffer delivery) {
            String sdelimitor = "";
            String ddelimitor = "";
            Adxp stateProvince = null;

            for (Adxp part : iso.getPart()) {
                AddressPartType type = part.getType();
                if (type == null) {
                    verify(part);
                    street.append(sdelimitor).append(part.getValue());
                } else {
                    if (type != AddressPartType.CNT && type != AddressPartType.DEL) {
                        verify(part);
                    }

                    switch (type) {
                        case CAR:
                            ddelimitor += "c/o ";
                        case DAL:
                        case ADL:
                            delivery.append(ddelimitor).append(part.getValue());
                            break;
                        case DEL:
                            String del = part.getValue() == null ? "\n" : part.getValue();
                            street.append(del);
                            sdelimitor = "";
                            continue;
                        case CNT:
                            verifyAndAdjustCnt(part);
                            String code = StringUtils.trimToNull(part.getCode());
                            if (code == null) {
                                a.setCountry(PoRegistry.getCountryService().getCountryByName(part.getValue()));
                            } else if (code.length() == 2) {
                                a.setCountry(PoRegistry.getCountryService().getCountryByAlpha2(code));
                            } else {
                                a.setCountry(PoRegistry.getCountryService().getCountryByAlpha3(code));
                            }
                            sdelimitor = "";
                            continue;
                        case STA:
                            stateProvince = part;
                                if (StringUtils.isNotEmpty(stateProvince.getCode())) {
                                    stateProvince.setCode(stateProvince.getCode().trim());
                                }
                            a.setStateOrProvince(stateProvince.getValue());
                            sdelimitor = "";
                            continue;
                        case CTY:
                            a.setCityOrMunicipality(part.getValue());
                            sdelimitor = "";
                            continue;
                        case ZIP:
                            a.setPostalCode(part.getValue());
                            sdelimitor = "";
                            continue;
                        case POB:
                            sdelimitor += "P.O.Box ";
                        default:
                            street.append(sdelimitor).append(part.getValue());
                    }
                    sdelimitor = street.length() == 0 ? "" : " ";
                    ddelimitor = delivery.length() == 0 ? "" : " ";
                }
            }
            validate(a, stateProvince);
        }

        private static void verify(Adxp part) {
            if (StringUtils.isBlank(part.getValue())) {
                throw new PoIsoConstraintException("Adxp.value is required");
            }
        }

        private static void verifyAndAdjustCnt(Adxp part) {
            String code = StringUtils.trimToNull(part.getCode());
            String codeSystem = StringUtils.trimToNull(part.getCodeSystem());
            if (code != null && codeSystem == null) {
                throw new PoIsoConstraintException("Adxp.codeSystem is required");
            }
            // PO-4180: CTEP is coding United Kingdom as UK, which is invalid: the country's ISO code is GB. 
            // PO can't find the country record and ends up with a curation error. 
            if (UK.equals(code)) {
                part.setCode(GB);
            }
        }

        private static void validate(Address a, Adxp stateProvince) {
            if (stateProvince == null) {
                return;
            }
            if (StringUtils.isNotBlank(stateProvince.getCode()) && a.getCountry() != null
                    && a.getCountry().getId() != null && !a.getCountry().getStates().isEmpty()) {
                a.setStateOrProvince(stateProvince.getCode());
                State stateByCode = PoRegistry.getCountryService().getStateByCode(a.getCountry(),
                        a.getStateOrProvince());
                if (stateByCode == null) {
                    throw new PoIsoConstraintException("unsupported ISO 3166 state or province code '"
                            + a.getStateOrProvince() + "' for Country code '" + a.getCountry().getAlpha3() + "'");
                }
            }
        }
    }

    /**
     * Converter for DSet.
     */
    public static class DSetConverter extends AbstractXSnapshotConverter<DSet<Ad>> {

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <TO> TO convert(Class<TO> returnClass, DSet<Ad> value) {
            if (value == null || value.getItem() == null) {
                return null;
            }
            Set<Address> addresses = new HashSet<Address>();
            for (Ad ad : value.getItem()) {
                addresses.add(SimpleConverter.convertToAddress(ad));
            }
            return (TO) addresses;
        }

    }
}
