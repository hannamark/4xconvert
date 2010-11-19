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
package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.po.data.bo.PersonEthnicGroup;
import gov.nih.nci.po.data.bo.PersonRace;
import gov.nih.nci.po.data.bo.PersonSex;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.CdConverter;
import gov.nih.nci.po.data.convert.EthnicGroupCodeConverter;
import gov.nih.nci.po.data.convert.RaceCodeConverter;
import gov.nih.nci.po.data.convert.SexCodeConverter;
import gov.nih.nci.po.data.convert.TsConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.TestConvertHelper;
import gov.nih.nci.services.correlation.PatientDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;

import javax.ejb.EJBException;

import org.junit.Test;

/**
 * @author Scott Miller
 *
 */
public class PersonEntityServiceTest extends AbstractPersonEntityService {
    private static final String ENXP_GIV = "--";
    private static final String ENXP_FAM = "_ -!@#$%^&*()+=~`'\":{}[]|<>?/,.\\\t\n\b\f\r";
    private static final String DEFAULT_URL = "http://default.example.com";
    private static final String DEFAULT_EMAIL = "default@example.com";
    private Ii personId;
    private Ii patientId;

    public Ii getPersonId() {
        return personId;
    }

    public Ii getPatientId() {
        return patientId;
    }

    @Test
    public void createMinimal() throws Exception {
        if (personId != null) {
            return; // test already run from getById.
        }
        try {
            PersonDTO dto = new PersonDTO();
            dto.setName(new EnPn());
            Enxp part = new Enxp(EntityNamePartType.GIV);
            part.setValue(ENXP_GIV);
            dto.getName().getPart().add(part);
            part = new Enxp(EntityNamePartType.FAM);
            part.setValue(ENXP_FAM);
            dto.getName().getPart().add(part);
            dto.setPostalAddress(TestConvertHelper.createAd("street", "delivery", "city", "WY", "zip", "USA"));
            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());
            dto.setTelecomAddress(telco);

            TelEmail email = new TelEmail();
            email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
            dto.getTelecomAddress().getItem().add(email);

            TelUrl url = new TelUrl();
            url.setValue(new URI(DEFAULT_URL));
            dto.getTelecomAddress().getItem().add(url);
            personId = getPersonService().createPerson(dto);
            assertNotNull(personId);
            assertNotNull(personId.getExtension());
        } catch (EntityValidationException e) {
            fail(e.getErrorMessages());
        }
    }

    @Test
    public void createMinimalPatient() throws Exception {
        if (patientId != null) {
            return; // test already run from getPatientById.
        }
        try {
            Ii orgId = null;
            OrganizationDTO dto = new OrganizationDTO();
            dto.setName(TestConvertHelper.convertToEnOn("_"));
            dto.setPostalAddress(TestConvertHelper.createAd("123 abc ave.", null, "mycity", "WY", "12345", "USA"));
            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());
            dto.setTelecomAddress(telco);

            TelEmail email = new TelEmail();
            email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
            dto.getTelecomAddress().getItem().add(email);

            TelUrl url = new TelUrl();
            url.setValue(new URI(DEFAULT_URL));
            dto.getTelecomAddress().getItem().add(url);

            orgId = getOrgService().createOrganization(dto);
            assertNotNull(orgId);

            updateOrgStatusToActive(Long.valueOf(orgId.getExtension()));

            PatientDTO patDto = new PatientDTO();
            patDto.setScoperIdentifier(orgId);

            patDto.setTelecomAddress(new DSet<Tel>());
            patDto.getTelecomAddress().setItem(new HashSet<Tel>());

            TelPhone ph1 = new TelPhone();
            ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-123-6543"));
            patDto.getTelecomAddress().getItem().add(ph1);

            patientId = getPatientService().createCorrelation(patDto);

            assertNotNull(patientId);
            assertNotNull(patientId.getExtension());
            patientId.setExtension("PT" + patientId.getExtension());

            PersonDTO perDTO = getPersonService().getPerson(patientId);

            Ts ts = new Ts();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            ts.setValue(sdf.parse("09/28/1980"));
            perDTO.setBirthDate(ts);

            Cd sexCode = new Cd();
            sexCode.setCode("MALE");
            perDTO.setSexCode(sexCode);

            DSet<Cd> ethnic = new DSet<Cd>();
            ethnic.setItem(new HashSet<Cd>());
            Cd ethnicCode = new Cd();
            ethnicCode.setCode("NOT_HISPANIC_OR_LATINO");
            ethnic.getItem().add(ethnicCode);
            perDTO.setEthnicGroupCode(ethnic);

            DSet<Cd> race = new DSet<Cd>();
            race.setItem(new HashSet<Cd>());
            Cd raceCode = new Cd();
            raceCode.setCode("WHITE");
            race.getItem().add(raceCode);
            perDTO.setRaceCode(race);

            getPersonService().updatePerson(perDTO);

        } catch (EntityValidationException e) {
            fail(e.getErrorMessages());
        }
    }

    @Test
    public void createWithBioCodes() throws Exception {

            PersonDTO dto = new PersonDTO();
            dto.setName(new EnPn());
            Enxp part = new Enxp(EntityNamePartType.GIV);
            part.setValue(ENXP_GIV);
            dto.getName().getPart().add(part);
            part = new Enxp(EntityNamePartType.FAM);
            part.setValue(ENXP_FAM);
            dto.getName().getPart().add(part);
            dto.setPostalAddress(TestConvertHelper.createAd("street", "delivery", "city", "WY", "zip", "USA"));
            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());
            dto.setTelecomAddress(telco);

            TelEmail email = new TelEmail();
            email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
            dto.getTelecomAddress().getItem().add(email);

            TelUrl url = new TelUrl();
            url.setValue(new URI(DEFAULT_URL));
            dto.getTelecomAddress().getItem().add(url);

            Cd sexCode = new Cd();
            sexCode.setCode("MALE");
            dto.setSexCode(sexCode);

            Cd raceCode = new Cd();
            raceCode.setCode("white");
            DSet<Cd> raceCodes = new DSet<Cd>();
            raceCodes.setItem(new HashSet<Cd>());
            raceCodes.getItem().add(raceCode);
            Cd raceCode2 = new Cd();
            raceCode2.setCode("black_or_african_american");
            raceCodes.getItem().add(raceCode2);
            dto.setRaceCode(raceCodes);

            Cd ethnicCode = new Cd();
            ethnicCode.setCode("hispanic_or_latino");
            DSet<Cd> ethnicCodes = new DSet<Cd>();
            ethnicCodes.setItem(new HashSet<Cd>());
            ethnicCodes.getItem().add(ethnicCode);
            Cd ethnicCode2 = new Cd();
            ethnicCode2.setCode("not_hispanic_or_latino");
            ethnicCodes.getItem().add(ethnicCode2);
            dto.setEthnicGroupCode(ethnicCodes);

            Ts birthDate = new Ts();
            SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
            birthDate.setValue(sdf.parse("09/28/1980"));
            dto.setBirthDate(birthDate);

            Ii p = getPersonService().createPerson(dto);
            assertNotNull(p);
            assertNotNull(p.getExtension());
            PersonDTO perDto = getPersonService().getPerson(p);
            assertEquals("male", perDto.getSexCode().getCode());
            assertEquals("09/28/1980", sdf.format(perDto.getBirthDate().getValue()));
            assertEquals(2, perDto.getEthnicGroupCode().getItem().size());
            assertEquals(2, perDto.getRaceCode().getItem().size());
            assertEquals(2, perDto.getEthnicGroupCode().getItem().size());

    }

    @Test
    public void getById() throws Exception {
        if (personId == null) {
            createMinimal();
        }
        PersonDTO dto = getPersonService().getPerson(personId);
        assertNotNull(dto);
        assertNotSame(personId, dto.getIdentifier());
        assertEquals(personId.getExtension(), dto.getIdentifier().getExtension());
        assertEquals(2, dto.getName().getPart().size());
        Iterator<Enxp> enxpItr = dto.getName().getPart().iterator();
        Enxp next = enxpItr.next();
        assertEquals(ENXP_FAM, next.getValue());
        next = enxpItr.next();
        assertEquals(ENXP_GIV, next.getValue());
        assertEquals("pending", dto.getStatusCode().getCode());
    }

    @Test
    public void getPatientById() throws Exception {
        if (patientId == null) {
            createMinimalPatient();
        }
        PersonDTO dto = getPersonService().getPerson(patientId);
        assertNotNull(dto);
        assertNotSame(patientId, dto.getIdentifier());
        assertEquals(patientId.getExtension(), dto.getIdentifier().getExtension());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        // everything other than the status and 4 bio fields should be masked nullflavor
        assertEquals(gov.nih.nci.iso21090.NullFlavor.MSK, dto.getName().getNullFlavor());
        assertEquals(gov.nih.nci.iso21090.NullFlavor.MSK, dto.getPostalAddress().getNullFlavor());
        assertEquals(gov.nih.nci.iso21090.NullFlavor.MSK, (dto.getTelecomAddress().getItem()
                .iterator().next()).getNullFlavor());
        assertEquals(RoleStatus.PENDING, CdConverter.convertToRoleStatus(dto.getStatusCode()));

        // 4 prop fields
        assertEquals(getPatientId().getExtension(), dto.getIdentifier().getExtension());
        assertEquals(sdf.parse("09/28/1980"), TsConverter.convertToDate(dto.getBirthDate()));
        assertEquals(PersonSex.MALE, SexCodeConverter.convertToStatusEnum(dto.getSexCode()));
        assertEquals(PersonEthnicGroup.NOT_HISPANIC_OR_LATINO,
                EthnicGroupCodeConverter.convertToEthnicGroupEnum((Cd)dto.getEthnicGroupCode().getItem()
                        .iterator().next()));
        assertEquals(PersonRace.WHITE,
                RaceCodeConverter.convertToRaceEnum((Cd)dto.getRaceCode().getItem()
                        .iterator().next()));
    }


    @Test
    public void update() throws Exception {
        if (personId == null) {
            createMinimal();
        }

        Connection c = DataGeneratorUtil.getJDBCConnection();
        ResultSet rs = c.createStatement().executeQuery("select count(*) from personcr where target = "+personId.getExtension());
        assertTrue(rs.next());
        int count0 = rs.getInt(1);
        rs.close();

        PersonDTO dto = getPersonService().getPerson(personId);
        Enxp n = new Enxp(EntityNamePartType.SFX);
        n.setValue("IV");
        dto.getName().getPart().add(n);
        TelPhone e = new TelPhone();
        e.setValue(new URI("tel:123-123-6543"));
        dto.getTelecomAddress().getItem().add(e);
        getPersonService().updatePerson(dto);

        rs = c.createStatement().executeQuery("select count(*) from personcr where target = "+personId.getExtension());
        assertTrue(rs.next());
        int count1 = rs.getInt(1);
        rs.close();
        assertEquals(count0 + 1, count1);
    }

    private void updateOrgStatusToActive(long id) throws SQLException {
        Connection c = DataGeneratorUtil.getJDBCConnection();
        PreparedStatement ps = c.prepareStatement("update organization set status = 'ACTIVE' where id = ?");
        ps.setLong(1, id);
        ps.execute();
        ps.close();
    }

    @Test
    public void updatePatient() throws Exception {
        if (patientId == null) {
            createMinimalPatient();
        }

        Connection c = DataGeneratorUtil.getJDBCConnection();
        ResultSet rs = c.createStatement()
            .executeQuery("select count(*) from patient_ethnicgroup where patient_id = " + patientId.getExtension().substring(2));
        assertTrue(rs.next());
        int count0 = rs.getInt(1);
        rs.close();

        PersonDTO dto = getPersonService().getPerson(patientId);
        Cd eth = new Cd();
        eth.setCode("UNKNOWN");
        dto.getEthnicGroupCode().getItem().add(eth);
        getPersonService().updatePerson(dto);

        rs = c.createStatement()
            .executeQuery("select count(*) from patient_ethnicgroup where patient_id = " + patientId.getExtension().substring(2));
        assertTrue(rs.next());
        int count1 = rs.getInt(1);
        rs.close();
        assertEquals(count0 + 1, count1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void updateWithChangedStatus () throws Throwable {
        if (personId == null) {
            createMinimal();
        }
        PersonDTO dto = getPersonService().getPerson(personId);
        Cd cd = new Cd();
        cd.setCode("nullified");
        dto.setStatusCode(cd);
        try {
            getPersonService().updatePerson(dto);
        } catch (EJBException e) {
            throw e.getCause();
        }
    }

    @Test
    public void updateStatus() throws Exception {
        if (personId == null) {
            createMinimal();
        }

        Connection c = DataGeneratorUtil.getJDBCConnection();
        ResultSet rs = c.createStatement().executeQuery("select count(*) from personcr where target = "+personId.getExtension()+" and status = 'INACTIVE'");
        assertTrue(rs.next());
        int count0 = rs.getInt(1);
        rs.close();

        Cd cd = new Cd();
        cd.setCode("inactive"); // maps to DEPRECATED
        getPersonService().updatePersonStatus(personId, cd);

        rs = c.createStatement().executeQuery("select count(*) from personcr where target = "+personId.getExtension()+" and status = 'INACTIVE'");
        assertTrue(rs.next());
        int count1 = rs.getInt(1);
        rs.close();
        assertEquals(count0 + 1, count1);
    }
}
