/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.iso.dto.BaseDTO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Abstract base class for converter tests.
 * @author Steve Lustbader
 */
@SuppressWarnings("unchecked")
public abstract class AbstractConverterTest<C extends AbstractConverter<DTO, BO>, DTO extends BaseDTO, BO extends AbstractEntity> {

    protected static final Long ID = 123L;
    protected static final Long STUDY_PROTOCOL_ID = 1L;
    protected static final Long STUDY_SITE_ID = 2L;
    private static final int NUM_OBJ_TO_CONVERT = 10;

    private C converter;
    private final Class<C> converterClass;
    {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments[0] instanceof Class) {
            converterClass = (Class<C>) actualTypeArguments[0];
        } else {
            converterClass = (Class<C>) ((ParameterizedType) actualTypeArguments[0]).getRawType();
        }
    }

    protected StudyProtocol getStudyProtocol() {
        StudyProtocol sp = new StudyProtocol();
        sp.setId(STUDY_PROTOCOL_ID);
        return sp;
    }

    protected StudySite getStudySite() {
        StudySite ss = new StudySite();
        ss.setId(STUDY_SITE_ID);
        return ss;
    }

    @Before
    public void initConverter() throws Exception {
        converter = converterClass.newInstance();
    }

    @Test
    public void testAll() throws Exception {
        testBoConvert();
        testDtoConvert();
    }

    public void testBoConvert() throws Exception {
        // test converting an empty list
        List<BO> bos = new ArrayList<BO>();
        List<DTO> dtos = converter.convertFromDomainToDtos(bos);
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
        assertTrue(bos.isEmpty());

        // test converting a populated list
        for (int i = 0 ; i < NUM_OBJ_TO_CONVERT ; i++) {
            bos.add(makeBo());
        }
        dtos = converter.convertFromDomainToDtos(bos);
        for (DTO dto : dtos) {
            verifyDto(dto);
        }
        verifyDto(converter.convertFromDomainToDto(makeBo()));
    }

    public void testDtoConvert() throws Exception {
        // test converting an empty list
        List<DTO> dtos = new ArrayList<DTO>();
        List<BO> bos = converter.convertFromDtoToDomains(dtos);
        assertNotNull(bos);
        assertTrue(bos.isEmpty());
        assertTrue(dtos.isEmpty());

        // test converting a populated list
        for (int i = 0 ; i < NUM_OBJ_TO_CONVERT ; i++) {
            dtos.add(makeDto());
        }
        bos = converter.convertFromDtoToDomains(dtos);
        for (BO bo : bos) {
            verifyBo(bo);
        }
        verifyBo(converter.convertFromDtoToDomain(makeDto()));
    }

    public abstract BO makeBo();
    public abstract DTO makeDto();

    public abstract void verifyBo(BO bo);
    public abstract void verifyDto(DTO dto);

}
