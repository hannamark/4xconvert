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

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.po.data.bo.PersonRace;
import gov.nih.nci.services.PoIsoConstraintException;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.collections.bidimap.UnmodifiableBidiMap;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


/**
 * Utility class for converting between BO and ISO types.
 *
 * @author mshestopalov
 */
public final class RaceCodeConverter {

    /**
     * Bidirectional map status codes.
     * <table border="1">
     * <tr><th>Key(String)</th><th>Value(PersonRace)</th/></tr>
     * <tr><td>"white"</td><td>{@link PersonRace#WHITE}</td></tr>
     * <tr><td>"black_or_african_american"</td><td>{@link PersonRace#BLACK_OR_AFRICAN_AMERICAN}</td></tr>
     * <tr><td>"asian"</td><td>{@link PersonRace#ASIAN}</td></tr>
     * <tr><td>"american_indian_or_alaska_native"</td>
     * <td>{@link PersonRace#AMERICAN_INDIAN_OR_ALASKA_NATIVE}</td></tr>
     * <tr><td>"native_hawaiian_or_other_islander"</td>
     * <td>{@link PersonRace#NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER}</td></tr>
     <tr><td>"not_reported"</td><td>{@link PersonRace#NOT_REPORTED}</td></tr>
     * <tr><td>"unknown"</td><td>{@link PersonRace#UNKNOWN}</td></tr>
     * </table>
     */
    public static final BidiMap STATUS_MAP;
    static {
        DualHashBidiMap map = new DualHashBidiMap();
        for (PersonRace es : PersonRace.values()) {
            map.put(es.name().toLowerCase(), es);
        }
        STATUS_MAP = UnmodifiableBidiMap.decorate(map);
    }

    /**
     * convert {@link PersonRace} to other types.
     */
    @SuppressWarnings("unchecked")
    public static class EnumConverter extends AbstractXSnapshotConverter<Set<PersonRace>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public <TO> TO convert(Class<TO> returnClass, Set<PersonRace> value) {
            if (returnClass != DSet.class) {
                throw new UnsupportedOperationException(returnClass.getName());
            }
            
            DSet<Cd> cds = new DSet<Cd>();
            cds.setItem(new HashSet<Cd>());

            if (CollectionUtils.isEmpty(value)) {
                return (TO) cds;
            }
            for (PersonRace race : value) {
                Cd cd = convertToCd(race);
                cds.getItem().add(cd);
            }

            return (TO) cds;

        }
    }
    /**
     * @param iso a status code
     * @return best guess of <code>iso</code>'s ISO equivalent.
     */
    public static PersonRace convertToRaceEnum(Cd iso) {
        if (iso == null) {
            return null;
        }

        if (iso.getNullFlavor() != null) {
            return null;
        }
        String code = iso.getCode();
        if (StringUtils.isBlank(code)) {
            throw new PoIsoConstraintException("code must be set");
        }
        PersonRace cs = (PersonRace) STATUS_MAP.get(code.toLowerCase(Locale.getDefault()));
        if (cs == null) {
            throw new PoIsoConstraintException("unsupported code " + cs);
        }
        return cs;
    }

    /**
     * @param cs PO entity status.
     * @return best guess of <code>cs</code>'s ISO equivalent.
     */
    public static Cd convertToCd(PersonRace cs) {
        return CdConverter.convertToCd(cs, STATUS_MAP);
    }
    
    /**
     * Converter for DSet.
     */
    public static class DSetConverter extends AbstractXSnapshotConverter<DSet<Cd>> {

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <TO> TO convert(Class<TO> returnClass, DSet<Cd> value) {
            if (returnClass != Set.class) {
                throw new UnsupportedOperationException(returnClass.getName());
            }
        
            if (value == null || value.getItem() == null) {
                return null;
            }
            Set<PersonRace> race = new HashSet<PersonRace>();
            for (Cd cd : value.getItem()) {
                race.add(convertToRaceEnum(cd));
            }
            return (TO) race;
        }

    }
}
