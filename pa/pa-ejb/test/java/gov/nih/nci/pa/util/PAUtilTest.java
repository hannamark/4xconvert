/**
 *
 */
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.iso.util.IvlConverter.JavaPq;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PAUtilTest {

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIiNull(gov.nih.nci.iso21090.Ii)}.
	 */
	@Test
	public void testIsIiNull() {
		Ii ii = null;
		assertTrue(PAUtil.isIiNull(ii));
        ii = new Ii();
        assertTrue(PAUtil.isIiNull(ii));
        ii.setExtension(null);
        assertTrue(PAUtil.isIiNull(ii));
        ii.setExtension("");
        assertTrue(PAUtil.isIiNull(ii));

	}
	
	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIiNotNull(gov.nih.nci.iso21090.Ii)}.
	 */
	@Test
	public void testIsIiNotNull() {
		assertTrue(PAUtil.isIiNotNull(IiConverter.convertToIi("1")));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isValidIi(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Ii)}.
	 */
	@Test
	public void testIsValidIi1() throws  PAException {
		assertTrue(PAUtil.isValidIi(IiConverter.convertToStudyProtocolIi(1L) , IiConverter.convertToStudyProtocolIi(null)));
	}

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#isValidIi(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Ii)}.
     */
	@Test(expected=PAException.class)
    public void testIsValidIi2() throws  PAException {
        PAUtil.isValidIi(null , null);
    }

	
	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isCdNull(gov.nih.nci.iso21090.Cd)}.
	 */
	@Test
	public void testIsCdNull() {
		Cd cd = null;
		assertTrue(PAUtil.isCdNull(cd));
		cd = new Cd();
		assertTrue(PAUtil.isCdNull(cd));
		cd.setCode(null);
	    assertTrue(PAUtil.isCdNull(cd));
        cd.setCode("");
        assertTrue(PAUtil.isCdNull(cd));

	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isStNull(gov.nih.nci.iso21090.St)}.
	 */
	@Test
	public void testIsStNull() {
		St st = null;
		assertTrue(PAUtil.isStNull(st));
		st = new St();
		st.setValue(null);
		assertTrue(PAUtil.isStNull(st));
		st.setValue("");
		assertTrue(PAUtil.isStNull(st));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isTsNull(gov.nih.nci.iso21090.Ts)}.
	 */
	@Test
	public void testIsTsNull() {
		Ts ts = null;
		assertTrue(PAUtil.isTsNull(ts));
		ts = new Ts();
		ts.setValue(null);
		assertTrue(PAUtil.isTsNull(ts));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isBlNull(gov.nih.nci.iso21090.Bl)}.
	 */
	@Test
	public void testIsBlNull() {
		Bl bl = null;
		assertTrue(PAUtil.isBlNull(bl));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIntNull(gov.nih.nci.iso21090.Int)}.
	 */
	@Test
	public void testIsIntNull() {
		Int in = null;
		assertTrue(PAUtil.isIntNull(in));
		in = new Int();
		in.setValue(null);
		assertTrue(PAUtil.isIntNull(in));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isPqValueNull(gov.nih.nci.iso21090.Pq)}.
	 */
	@Test
	public void testIsPqValueNull() {
		Pq pq =null;
		assertTrue(PAUtil.isPqValueNull(pq));
		pq = new Pq();
		pq.setValue(null);
		assertTrue(PAUtil.isPqValueNull(pq));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isPqUnitNull(gov.nih.nci.iso21090.Pq)}.
	 */
	@Test
	public void testIsPqUnitNull() {
		Pq pq =null;
		assertTrue(PAUtil.isPqUnitNull(pq));
		pq = new Pq();
        pq.setUnit(null);		
        assertTrue(PAUtil.isPqUnitNull(pq));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIvlHighNull(gov.nih.nci.iso21090.Ivl)}.
	 */
	@Test
	public void testIsIvlHighNull() {
	    Ivl<Pq> ivl = null;
	    assertTrue(PAUtil.isIvlHighNull(ivl));
	    ivl = new Ivl<Pq>();
		Pq pqHigh = null;
		ivl.setHigh(pqHigh);
		assertTrue(PAUtil.isIvlHighNull(ivl));
		pqHigh = new Pq();
		pqHigh.setValue(null);
		ivl.setHigh(pqHigh);
		assertTrue(PAUtil.isIvlHighNull(ivl));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIvlLowNull(gov.nih.nci.iso21090.Ivl)}.
	 */
	@Test
	public void testIsIvlLowNull() {
	    Ivl<Pq> ivl = null;
	    assertTrue(PAUtil.isIvlLowNull(ivl));
		ivl = new Ivl<Pq>();
		Pq pqLow = null;
		ivl.setLow(pqLow);
		assertTrue(PAUtil.isIvlLowNull(ivl));
		pqLow = new Pq();
		pqLow.setValue(null);
        ivl.setLow(pqLow);
        assertTrue(PAUtil.isIvlLowNull(ivl));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIvlUnitNull(gov.nih.nci.iso21090.Ivl)}.
	 */
	@Test
	public void testIsIvlUnitNull() {
	    Ivl<Pq> ivl = null;
	    assertTrue(PAUtil.isIvlUnitNull(ivl));
		ivl = new Ivl<Pq>();
		Pq pqHigh = null;
        ivl.setHigh(pqHigh);
        assertTrue(PAUtil.isIvlUnitNull(ivl));
        pqHigh = new Pq();
        ivl.setHigh(pqHigh);
        assertTrue(PAUtil.isIvlUnitNull(ivl));
		pqHigh.setUnit(null);
		Pq pqLow = new Pq();
		ivl.setHigh(pqHigh);
		ivl.setLow(pqLow);
		assertTrue(PAUtil.isIvlUnitNull(ivl));
        pqHigh.setUnit("");
        ivl.setHigh(pqHigh);
        pqLow.setUnit("");
        ivl.setLow(pqLow);
        assertFalse(PAUtil.isIvlUnitNull(ivl));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertTsToFormarttedDate(gov.nih.nci.iso21090.Ts, java.lang.String)}.
	 */
	@Test
	public void testConvertTsToFormarttedDate() {
		String date =
			PAUtil.convertTsToFormarttedDate(TsConverter.convertToTs(new Timestamp(new Date("11/16/2009").getTime())), "yyyy-MM");
		assertEquals("2009-11",date);
	}

	/**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertTsToFormattedDate(gov.nih.nci.iso21090.Ts)}.
     */
    @Test
    public void testConvertTsToDefaultFormattedDate() {
        String date =
            PAUtil.convertTsToFormattedDate(TsConverter.convertToTs(new Timestamp(new Date("1/16/2009").getTime())));
        assertEquals("01/16/2009",date);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#normalizeDateStringWithTime(java.lang.String)}.
     */
    @Test
    public void normalizeDateStringWithTime() {
        assertNull(PAUtil.normalizeDateStringWithTime(null));
        assertNotNull(PAUtil.normalizeDateStringWithTime("01/16/2009"));
        assertEquals(PAUtil.normalizeDateStringWithTime("01/16/2009"),"2009-01-16 00:00:00");
    }

    /**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#normalizeDateString(java.lang.String)}.
	 */
	@Test
	public void testNormalizeDateString() {
		assertEquals("01/31/2001", PAUtil.normalizeDateString("1/31/2001abcdefg"));
        assertEquals("01/31/2001", PAUtil.normalizeDateString("2001-01-31abcdefg"));
        assertNull(PAUtil.normalizeDateString("Tuesday"));
        assertNull(PAUtil.normalizeDateString(null));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#normalizeDateStringWithTime(java.lang.String)}.
	 */
	@Test
	public void testNormalizeDateStringWithTime() {
		assertNull(PAUtil.normalizeDateStringWithTime(null));
	}
    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#dateStringToTimestamp(java.lang.String)}.
     */
    @Test
    public void testDateStringToTimestamp() {
         Timestamp now = new Timestamp(new Date().getTime());
         assertTrue(now.after(PAUtil.dateStringToTimestamp(now.toString())));
    }

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#today()}.
	 */
	@Test
	public void testToday() {
		assertNotNull(PAUtil.today());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isEmpty(java.lang.String)}.
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(PAUtil.isEmpty(null));
		assertFalse(PAUtil.isEmpty("Hello"));
		assertTrue(PAUtil.isEmpty(" "));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isNotEmpty(java.lang.String)}.
	 */
	@Test
	public void testIsNotEmpty() {
		assertTrue(PAUtil.isNotEmpty("hello"));
		assertFalse(PAUtil.isNotEmpty(null));
		assertFalse(PAUtil.isNotEmpty(""));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#stringSetter(java.lang.String, int)}.
	 */
	@Test
	public void testStringSetterStringInt() {
		assertEquals("He",PAUtil.stringSetter("Hello",2));
		assertNull(PAUtil.stringSetter(null,2));
		assertEquals("Hello",PAUtil.stringSetter("Hello",-2));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#stringSetter(java.lang.String)}.
	 */
	@Test
	public void testStringSetterString() {
		assertEquals("Hello",PAUtil.stringSetter("Hello"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isValidEmail(java.lang.String)}.
	 */
	@Test
	public void testIsValidEmail() {
		assertTrue(PAUtil.isValidEmail("a@a.com"));
		assertFalse(PAUtil.isValidEmail(null));
	}

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#isValidPhone(java.lang.String)}.
     */
    @Test
    public void testIsValidPhone() {
        assertTrue(PAUtil.isValidPhone("111111"));
        assertFalse(PAUtil.isValidPhone(null));
    }

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#trim(java.lang.String, int)}.
	 */
	@Test
	public void testTrim() {
		assertNull(PAUtil.trim(null,2));
		assertEquals(PAUtil.trim("abcde",2),"a...");
		assertEquals(PAUtil.trim("abcde",10),"abcde");
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#wildcardCriteria(java.lang.String)}.
	 */
	@Test
	public void testWildcardCriteria() {
		assertEquals("",PAUtil.wildcardCriteria(null));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isGreatenThan(gov.nih.nci.iso21090.St, int)}.
	 */
	@Test
	public void testIsGreatenThan() {
		assertTrue(PAUtil.isGreatenThan(StConverter.convertToSt("hello"), 2));
		assertFalse(PAUtil.isGreatenThan(null, 2));
		St st = new St();
		st.setValue(null);
		assertFalse(PAUtil.isGreatenThan(st, 2));
		st.setValue("hello");
		assertTrue(PAUtil.isGreatenThan(st, 2));
		assertFalse(PAUtil.isGreatenThan(st, 6));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isWithinRange(gov.nih.nci.iso21090.St, int, int)}.
	 */
	@Test
	public void testIsWithinRange() {
		assertTrue(PAUtil.isWithinRange(StConverter.convertToSt("hello"), 2 , 6));
		assertTrue(PAUtil.isWithinRange(null, 2 , 6));
        St st = new St();
        st.setValue(null);
        assertTrue(PAUtil.isWithinRange(st, 2 , 6));
        st.setValue("hello");
        assertFalse(PAUtil.isWithinRange(st, 2 , 3));
        assertTrue(PAUtil.isWithinRange(st, 2 , 10));

	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#getIiExtension(gov.nih.nci.iso21090.Ii)}.
	 */
	@Test
	public void testGetIiExtension() {
		assertEquals("1", PAUtil.getIiExtension(IiConverter.convertToIi("1")));
		assertEquals("", PAUtil.getIiExtension(null));
	}

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getErrorMsg(java.util.Map)}.
     */
    @Test
    public void testGetErrorMsg() {
        Map<String, String[]> errMap = new HashMap<String, String[]>();
        assertEquals("",PAUtil.getErrorMsg(errMap));
        String key = "key";
        String keyArr[] = {"k","e","y"};
        errMap.put(key, keyArr);
        assertEquals("key -  k, e, y ",PAUtil.getErrorMsg(errMap));
        
        
    }

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#containsIi(java.util.Map, gov.nih.nci.iso21090.Ii)}.
	 */
	@Test
	public void testContainsIi() {
		Map<Ii, Ii> iiMap = null;
		Ii iiKey = null;
		assertNull(PAUtil.containsIi(iiMap, iiKey));
		iiMap = new HashMap<Ii, Ii>();
		assertNull(PAUtil.containsIi(iiMap, iiKey));
		iiKey = IiConverter.convertToIi("1");
		assertNull(PAUtil.containsIi(iiMap, iiKey));
		iiMap.put(IiConverter.convertToIi("1"), IiConverter.convertToIi("1"));
		iiMap.put(IiConverter.convertToIi("2"), IiConverter.convertToIi("2"));
		iiKey = IiConverter.convertToIi("1");
		assertEquals(PAUtil.containsIi(iiMap, iiKey).getExtension(),"1");
		
	}
    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#readInputStream(InputStream )}.
     * @throws FileNotFoundException 
     */
    @Test
    public void testReadInputStream() throws Exception {
        File file= new File("test.txt");
        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos=new DataOutputStream(fos);
        dos.writeInt(2333);
        fos.close();
        file= new File("test.txt");
        FileInputStream fis = new FileInputStream(file);
        byte[] b = PAUtil.readInputStream(fis);
        fis.close();
        file.delete();
        assertEquals(b.length,4);
    }

	
	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#getFirstObj(java.util.List)}.
	 */
	@Test
	public void testGetFirstObj() {
		List<StudyDTO> studyList = null;
		assertNull(PAUtil.getFirstObj(studyList));
		StudyCheckoutDTO scDto = new StudyCheckoutDTO();
		studyList = new ArrayList<StudyDTO>();
		assertNull(PAUtil.getFirstObj(studyList));
		studyList.add(scDto);
		assertNotNull(PAUtil.getFirstObj(studyList));
	}
/*
	*//**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#getDocumentFilePath(java.lang.Long, java.lang.String, java.lang.String)}.
	 *//*
	@Test
	public void testGetDocumentFilePath() throws PAException {
		PAUtil.getDocumentFilePath(1L, "IRB.doc", "1");
	}*/

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isDateCurrentOrPast(java.lang.String)}.
	 */
	@Test
	public void testIsDateCurrentOrPastString() {
		assertFalse(PAUtil.isDateCurrentOrPast("10/29/2009"));
		assertTrue(PAUtil.isDateCurrentOrPast("10/29/2999"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isDateCurrentOrPast(java.sql.Timestamp)}.
	 */
	@Test
	public void testIsDateCurrentOrPastTimestamp() {
		assertFalse(PAUtil.isDateCurrentOrPast(new Timestamp(new Date().getTime())));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isValidDate(java.lang.String)}.
	 */
	@Test
	public void testIsValidDate() {
		assertFalse(PAUtil.isValidDate(""));
		assertFalse(PAUtil.isValidDate("abcbs"));
		assertTrue(PAUtil.isValidDate("01/01/2010"));
		assertFalse(PAUtil.isValidDate("31/01/2010"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isAbstractedAndAbove(gov.nih.nci.iso21090.Cd)}.
	 */
	@Test
	public void testIsAbstractedAndAbove() {
		assertTrue(
	      PAUtil.isAbstractedAndAbove(CdConverter.convertStringToCd(DocumentWorkflowStatusCode.ABSTRACTED.getCode())));
        assertTrue(
                PAUtil.isAbstractedAndAbove(CdConverter.convertStringToCd(
                        DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode())));
        assertTrue(
                PAUtil.isAbstractedAndAbove(CdConverter.convertStringToCd(
                        DocumentWorkflowStatusCode.VERIFICATION_PENDING.getCode())));
        assertTrue(
                PAUtil.isAbstractedAndAbove(CdConverter.convertStringToCd(
                        DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode())));
		assertFalse(
		          PAUtil.isAbstractedAndAbove(CdConverter.convertStringToCd(
		                  DocumentWorkflowStatusCode.ACCEPTED.getCode())));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isListNotEmpty(java.util.List)}.
	 */
	@Test
	public void testIsListNotEmpty() {
		assertFalse(PAUtil.isListNotEmpty(new ArrayList()));
        StudyCheckoutDTO scDto = new StudyCheckoutDTO();
        List<StudyDTO> studyList = null;
        studyList = new ArrayList<StudyDTO>();
        assertFalse(PAUtil.isListNotEmpty(studyList));
        studyList.add(scDto);
        assertTrue(PAUtil.isListNotEmpty(studyList));

	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isListEmpty(java.util.List)}.
	 */
	@Test
	public void testIsListEmpty() {
		assertFalse(PAUtil.isListNotEmpty(new ArrayList()));
        StudyCheckoutDTO scDto = new StudyCheckoutDTO();
        List<StudyDTO> studyList = null;
        studyList = new ArrayList<StudyDTO>();
        assertFalse(PAUtil.isListNotEmpty(studyList));
        studyList.add(scDto);
        assertTrue(PAUtil.isListNotEmpty(studyList));
	}
	

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#checkIfValueExists(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=Exception.class)
	public void testCheckIfValueExists() throws PAException {
		PADomainUtils.checkIfValueExists("GAS", "DoseForm", "code");
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertStringToDecimal(java.lang.String)}.
	 */
	@Test
	public void testConvertStringToDecimal() {
		assertNotNull(PAUtil.convertStringToDecimal("2"));
		assertNull(PAUtil.convertStringToDecimal(null));
		assertNull(PAUtil.convertStringToDecimal("abc"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isNumber(java.lang.String)}.
	 */
	@Test
	public void testIsNumber() {
		assertTrue(PAUtil.isNumber("3"));
		assertFalse(PAUtil.isNumber("3x"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertPqToUnit(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}.
	 */
	@Test
	public void testConvertPqToUnit() {
		assertNull(PAUtil.convertPqToUnit(null));
		JavaPq jPq = new JavaPq("kg", new BigDecimal("100"), new Integer(1));
		assertNotNull(PAUtil.convertPqToUnit(jPq));
		assertEquals(PAUtil.convertPqToUnit(jPq),"kg");
		jPq = new JavaPq(null, new BigDecimal("100"), new Integer(1));
        assertNull(PAUtil.convertPqToUnit(jPq));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertPqToDecimal(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}.
	 */
	@Test
	public void testConvertPqToDecimal() {
		assertNull(PAUtil.convertPqToDecimal(null));
		JavaPq jPq = new JavaPq(null, null, new Integer(1));
		assertNull(PAUtil.convertPqToDecimal(null));
		jPq = new JavaPq("kg", new BigDecimal("100"), new Integer(1));
        assertNotNull(PAUtil.convertPqToDecimal(jPq));
        assertEquals(PAUtil.convertPqToDecimal(jPq),new BigDecimal("100"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertPqToPrecision(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}.
	 */
	@Test
	public void testConvertPqToPrecision() {
		assertNull(PAUtil.convertPqToPrecision(null));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isTypeIntervention(gov.nih.nci.iso21090.Cd)}.
	 */
	@Test
	public void testIsTypeIntervention() {
		assertTrue(PAUtil.isTypeIntervention(CdConverter.convertStringToCd(ActivityCategoryCode.INTERVENTION.getCode())));
		assertTrue(PAUtil.isTypeIntervention(CdConverter.convertStringToCd(
		        ActivityCategoryCode.PLANNED_PROCEDURE.getCode())));
        assertTrue(PAUtil.isTypeIntervention(CdConverter.convertStringToCd(
                ActivityCategoryCode.SUBSTANCE_ADMINISTRATION.getCode())));
        assertFalse(PAUtil.isTypeIntervention(CdConverter.convertStringToCd(
                ActivityCategoryCode.COURSE.getCode())));

	}

	/**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#isValidNumber(String)}.
     */
    @Test
    public void testIsValidNumber() {
        assertTrue(PAUtil.isValidNumber("1"));
        assertFalse(PAUtil.isValidNumber("1a"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#isNumeric(String)}.
     */
    @Test
    public void testIsValidNumeric() {
        assertTrue(PAUtil.isNumeric("1"));
        assertFalse(PAUtil.isNumeric("1a"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#isPhoneValidForUSA(String)}.
     */
    @Test
    public void testIsPhoneValidForUSA() {
        assertTrue(PAUtil.isPhoneValidForUSA("111-111-1111"));
        assertFalse(PAUtil.isPhoneValidForUSA("test"));
        assertFalse(PAUtil.isPhoneValidForUSA(null));
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#isDSetTelNull(gov.nih.nci.iso21090.DSet<Tel>)}.
     */
    @Test
    public void testIsDSetTelNull() {
        assertTrue(PAUtil.isDSetTelNull(null));
        DSet<Tel> telecomAddresses = new DSet<Tel> ();
        assertTrue(PAUtil.isDSetTelNull(telecomAddresses));
        List<String> st = new ArrayList<String>();
        st.add("email:n.n.com");
        DSetConverter.convertListToDSet(st, "EMAIL", telecomAddresses);
        assertTrue(PAUtil.isDSetTelNull(telecomAddresses));
        st = new ArrayList<String>();
        st.add("tel:111-111-1111");
        DSetConverter.convertListToDSet(st, "PHONE", telecomAddresses);
        assertFalse(PAUtil.isDSetTelNull(telecomAddresses));        
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getEmail(gov.nih.nci.iso21090.DSet<Tel>)}.
     */
    @Test
    public void testGetEmail() {
        assertNull(PAUtil.getEmail(null));
        DSet<Tel> telecomAddresses = new DSet<Tel> ();
        assertNull(PAUtil.getEmail(telecomAddresses));
        List<String> st = new ArrayList<String>();
        st.add("email:n.n.com");
        DSetConverter.convertListToDSet(st, "EMAIL", telecomAddresses);
        assertNotNull(PAUtil.getEmail(telecomAddresses));
        assertEquals(PAUtil.getEmail(telecomAddresses),"email:n.n.com");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getPhone(gov.nih.nci.iso21090.DSet<Tel>)}.
     */
    @Test
    public void testGetPhone() {
        DSet<Tel> telecomAddresses = new DSet<Tel> ();
        assertNull(PAUtil.getEmail(telecomAddresses));
        List<String> st = new ArrayList<String>();
        st.add("email:n.n.com");
        DSetConverter.convertListToDSet(st, "EMAIL", telecomAddresses);
        assertNull(PAUtil.getPhone(telecomAddresses));
        st = new ArrayList<String>();
        st.add("tel:111-111-1111");
        DSetConverter.convertListToDSet(st, "PHONE", telecomAddresses);
        assertNotNull(PAUtil.getPhone(telecomAddresses));
        assertEquals(PAUtil.getPhone(telecomAddresses),"tel:111-111-1111");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getPhoneExtension(gov.nih.nci.iso21090.DSet<Tel>)}.
     */
    @Test
    public void testGetPhoneExtension() {
        DSet<Tel> telecomAddresses = new DSet<Tel> ();
        assertNull(PAUtil.getEmail(telecomAddresses));
        List<String> st = new ArrayList<String>();
        st.add("tel:111-111-1111");
        DSetConverter.convertListToDSet(st, "PHONE", telecomAddresses);
        assertEquals(PAUtil.getPhoneExtension(telecomAddresses),"");
        st = new ArrayList<String>();
        st.add("tel:111-111-1111;extn222");
        DSetConverter.convertListToDSet(st, "PHONE", telecomAddresses);
        assertNotNull(PAUtil.getPhoneExtension(telecomAddresses));
        assertEquals(PAUtil.getPhoneExtension(telecomAddresses),"222");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getPhone(String)}.
     */
    @Test
    public void testGetPhone1() {
        assertEquals(PAUtil.getPhone("tel:111-111-1111"),"tel:111-111-1111");
        assertEquals(PAUtil.getPhone("tel:111-111-1111extn1111"),"tel:111-111-1111");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getPhoneExtn(String)}.
     */
    @Test
    public void testGetPhoneExtn1() {
        assertEquals(PAUtil.getPhoneExtn("tel:111-111-1111"),"");
        assertEquals(PAUtil.getPhoneExtn("tel:111-111-1111extn2222"),"2222");
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#isUnitLessOrSame(String,String)}.
     */
    @Test
    public void testIsUnitLessOrSame() {
        assertTrue(PAUtil.isUnitLessOrSame("Days","Days"));
        assertTrue(PAUtil.isUnitLessOrSame("Days","Years"));
        assertTrue(PAUtil.isUnitLessOrSame("Weeks","Months"));
        assertTrue(PAUtil.isUnitLessOrSame("Weeks","Years"));
        assertTrue(PAUtil.isUnitLessOrSame("Days","Weeks"));
        assertTrue(PAUtil.isUnitLessOrSame("Days","Months"));
        assertTrue(PAUtil.isUnitLessOrSame("Days","Years"));
        assertTrue(PAUtil.isUnitLessOrSame("Hours","Days"));
        assertTrue(PAUtil.isUnitLessOrSame("Hours","Weeks"));
        assertTrue(PAUtil.isUnitLessOrSame("Hours","Months"));
        assertTrue(PAUtil.isUnitLessOrSame("Hours","Years"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes","Hours"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes","Days"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes","Weeks"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes","Months"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes","Years"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getAge(BigDecimal)}.
     */
    @Test
    public void testGetAge() {
        assertEquals(PAUtil.getAge(new BigDecimal("100.00")),"100.00");
        assertEquals(PAUtil.getAge(new BigDecimal("100")),"100");
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getAssignedIdentifierExtension(StudyProtocolDTO)}.
     */
    @Test
    public void getAssignedIdentifierExtension() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertEquals(PAUtil.getAssignedIdentifierExtension(spDto),"");
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getAssignedIdentifierExtension(spDto),"");
        iiSet.add(IiConverter.convertToIi("1"));
        iiSet.add(IiConverter.convertToStudyProtocolIi(new Long(2222)));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getAssignedIdentifierExtension(spDto),"2222");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getgetOtherIdentifiers(StudyProtocolDTO)}.
     */
    @Test
    public void getOtherIdentifiers() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertEquals(PAUtil.getOtherIdentifiers(spDto).size(),0);
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getOtherIdentifiers(spDto).size(),0);
        iiSet.add(IiConverter.convertToIi("1"));
        assertEquals(PAUtil.getOtherIdentifiers(spDto).size(),0);
        iiSet.add(IiConverter.convertToOtherIdentifierIi("2222"));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getOtherIdentifiers(spDto).size(),1);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getNonOtherIdentifiers(StudyProtocolDTO)}.
     */
    @Test
    public void getNonOtherIdentifiers() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertEquals(PAUtil.getNonOtherIdentifiers(spDto).getExtension(),null);
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getNonOtherIdentifiers(spDto).getExtension(),null);
        iiSet.add(IiConverter.convertToIi("2222"));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getNonOtherIdentifiers(spDto).getExtension(),"2222");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getAssignedIdentifier(StudyProtocolDTO)}.
     */
    @Test
    public void getAssignedIdentifier() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertEquals(PAUtil.getAssignedIdentifier(spDto).getExtension(),null);
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getAssignedIdentifier(spDto).getExtension(),null);
        
        iiSet.add(IiConverter.convertToIi("1"));
        iiSet.add(IiConverter.convertToStudyProtocolIi(new Long(2222)));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getAssignedIdentifier(spDto).getExtension(),"2222");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#checkAssignedIdentifierExists(StudyProtocolDTO)}.
     */
    @Test
    public void checkAssignedIdentifierExists() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertFalse(PAUtil.checkAssignedIdentifierExists(spDto));
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertFalse(PAUtil.checkAssignedIdentifierExists(spDto));
        iiSet.add(IiConverter.convertToIi("1"));
        iiSet.add(IiConverter.convertToStudyProtocolIi(new Long(2222)));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertTrue(PAUtil.checkAssignedIdentifierExists(spDto));
    }
    
    @Test
    public void testConvertPoOrganizationDTO() throws Exception{
        OrganizationDTO org = new OrganizationDTO();
        org.setName(EnOnConverter.convertToEnOn("org"));
        org.setIdentifier(IiConverter.convertToPoOrganizationalContactIi("1"));
        Ad address = AddressConverterUtil.create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
        org.setPostalAddress(address);
        TestSchema.reset();
        List<Country> con = TestSchema.countries;
        PaOrganizationDTO paOrgDTO = PADomainUtils.convertPoOrganizationDTO(org, con);
        assertEquals("Testing org name", "org", paOrgDTO.getName());
        assertEquals("Testing Country name", "USA", paOrgDTO.getCountry());
    }


    private PersonDTO setUpPerson() {
        PersonDTO poPerson = new PersonDTO();
        poPerson.setName(EnPnConverter.convertToEnPn("firstName", "middleName", "lastName", "prefix", "suffix"));
        poPerson.setIdentifier(IiConverter.convertToPoPersonIi("1"));
         List<String> phones = new ArrayList<String>();
         String phone="1111111111";
         String email="a@a.com";
            phones.add(phone);
            List<String> emails = new ArrayList<String>();
            emails.add(email);
            DSet<Tel> dsetList = null;
            dsetList =  DSetConverter.convertListToDSet(phones, "PHONE", dsetList);
            dsetList =  DSetConverter.convertListToDSet(emails, "EMAIL", dsetList);
          poPerson.setTelecomAddress(dsetList);
          Ad address = AddressConverterUtil.create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
          poPerson.setPostalAddress(address);
        return poPerson;
    }

    @Test
    public void testConvertToPaPerson() {
        PersonDTO poPerson = setUpPerson();
         Person person = PADomainUtils.convertToPaPerson(poPerson);
         assertNotNull(person);
         assertEquals("Testing first name","firstName",person.getFirstName());
    }

    @Test
    public void testConvertToPaPersonDTO() {
        PersonDTO poPerson = setUpPerson();
        PaPersonDTO paPersonDTO = PADomainUtils.convertToPaPersonDTO(poPerson);
        assertEquals("testing last name", "lastName",paPersonDTO.getLastName());
        assertEquals("testing last name","a@a.com",paPersonDTO.getEmail());
    }

    @Test
    public void testGetGridIdentityUsername() {
        String gridIdentity = "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor";
        assertEquals("abstractor", PAUtil.getGridIdentityUsername(gridIdentity));

        gridIdentity = "/O=caBIG/OU=caGrid/OU=Stage LOA1/OU=Dorian/CN=abstractor";
        assertEquals("abstractor", PAUtil.getGridIdentityUsername(gridIdentity));

        gridIdentity = "/O=SomeString/OU=Dorian/CN=abstractor";
        assertEquals("abstractor", PAUtil.getGridIdentityUsername(gridIdentity));

        gridIdentity = "/O=cdskjfjfdskfjdsU=Dorian/CN=abstractor";
        assertEquals("abstractor", PAUtil.getGridIdentityUsername(gridIdentity));

        gridIdentity = "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/cn=abstractor";
        assertEquals(gridIdentity, PAUtil.getGridIdentityUsername(gridIdentity));
    }
}
