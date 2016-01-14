/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author gundalar
 * 
 */
public class FamilyProgramCodeBeanLocalServiceTest extends AbstractEjbTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    FamilyProgramCodeBeanLocal bean;

    @Before
    public void init() throws Exception {
        bean = (FamilyProgramCodeBeanLocal) getEjbBean(FamilyProgramCodeBeanLocal.class);
        TestSchema.primeData();        
        PaHibernateUtil.getCurrentSession().flush();

        UsernameHolder
                .setUserCaseSensitive("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor");
    }
    
    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#createProgramCode(gov.nih.nci.pa.dto.FamilyDTO,
     * gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
     * 
     * @throws PAException
     */
   @Test
   public final void testCreateNewProgramCode() throws PAException {
       FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
       assertEquals(6,familyDTO.getProgramCodes().size());
       ProgramCodeDTO dto = new ProgramCodeDTO();
       dto.setProgramCode("PG1");
       dto.setProgramName("Program Name1");
       bean.createProgramCode(familyDTO,dto);
       
       //verify that program code is successfully created and added to family
       familyDTO = bean.getFamilyDTOByPoId(-1L);
       assertEquals(7,familyDTO.getProgramCodes().size());
       
   }
   
   
   /**
    * Test method for
    * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#createProgramCode(gov.nih.nci.pa.dto.FamilyDTO,
    * gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
    * 
    * @throws PAException
    */   
   @Test
   public final void testCreateDuplicateProgramCodeException() throws PAException {
       
       thrown.expect(PAValidationException.class);
       thrown.expectMessage(FamilyProgramCodeBeanLocal.DUPE_PROGRAM_CODE);

       FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
       assertEquals(6,familyDTO.getProgramCodes().size());
       ProgramCodeDTO dto = new ProgramCodeDTO();
       dto.setProgramCode("1");
       dto.setProgramName("Program Name1");
       bean.createProgramCode(familyDTO,dto);
   }

}
