/**
 * 
 */
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PAUtilTest {
 
	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIiNull(gov.nih.nci.coppa.iso.Ii)}.
	 */
	@Test
	public void testIsIiNull() {
		Ii ii = null;
		assertTrue(PAUtil.isIiNull(ii));
		
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIiNotNull(gov.nih.nci.coppa.iso.Ii)}.
	 */
	@Test
	public void testIsIiNotNull() {
		assertTrue(PAUtil.isIiNotNull(IiConverter.convertToIi("1")));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isValidIi(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.coppa.iso.Ii)}.
	 */
	@Test
	public void testIsValidIi() throws  PAException {
		assertTrue(PAUtil.isValidIi(IiConverter.convertToStudyProtocolIi(1L) , IiConverter.convertToStudyProtocolIi(null)));
	}
	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isCdNull(gov.nih.nci.coppa.iso.Cd)}.
	 */
	@Test
	public void testIsCdNull() {
		Cd cd = null;
		assertTrue(PAUtil.isCdNull(cd));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isStNull(gov.nih.nci.coppa.iso.St)}.
	 */
	@Test
	public void testIsStNull() {
		St st = null;
		assertTrue(PAUtil.isStNull(st));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isTsNull(gov.nih.nci.coppa.iso.Ts)}.
	 */
	@Test
	public void testIsTsNull() {
		Ts ts = null;
		assertTrue(PAUtil.isTsNull(ts));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isBlNull(gov.nih.nci.coppa.iso.Bl)}.
	 */
	@Test
	public void testIsBlNull() {
		Bl bl = null;
		assertTrue(PAUtil.isBlNull(bl));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIntNull(gov.nih.nci.coppa.iso.Int)}.
	 */
	@Test
	public void testIsIntNull() {
		Int in = null;
		assertTrue(PAUtil.isIntNull(in));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isPqValueNull(gov.nih.nci.coppa.iso.Pq)}.
	 */
	@Test
	public void testIsPqValueNull() {
		Pq pq =null;
		assertTrue(PAUtil.isPqValueNull(pq));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isPqUnitNull(gov.nih.nci.coppa.iso.Pq)}.
	 */
	@Test
	public void testIsPqUnitNull() {
		Pq pq =null;
		assertTrue(PAUtil.isPqValueNull(pq));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIvlHighNull(gov.nih.nci.coppa.iso.Ivl)}.
	 */
	@Test
	public void testIsIvlHighNull() {
		Ivl<Pq> ivl = new Ivl<Pq>();
		Pq pqHigh = null;
		Pq pqLow = new Pq();
		ivl.setHigh(pqHigh);
		ivl.setLow(pqLow);
		assertTrue(PAUtil.isIvlHighNull(ivl));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIvlLowNull(gov.nih.nci.coppa.iso.Ivl)}.
	 */
	@Test
	public void testIsIvlLowNull() {
		Ivl<Pq> ivl = new Ivl<Pq>();
		Pq pqHigh = new Pq();
		Pq pqLow = null;
		ivl.setHigh(pqHigh);
		ivl.setLow(pqLow);
		assertTrue(PAUtil.isIvlLowNull(ivl));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isIvlUnitNull(gov.nih.nci.coppa.iso.Ivl)}.
	 */
	@Test
	public void testIsIvlUnitNull() {
		Ivl<Pq> ivl = new Ivl<Pq>();
		Pq pqHigh = new Pq();
		pqHigh.setUnit(null);
		Pq pqLow = new Pq();
		ivl.setHigh(pqHigh);
		ivl.setLow(pqLow);
		assertTrue(PAUtil.isIvlUnitNull(ivl));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertTsToFormarttedDate(gov.nih.nci.coppa.iso.Ts, java.lang.String)}.
	 */
	@Test
	public void testConvertTsToFormarttedDate() {
		String date = 
			PAUtil.convertTsToFormarttedDate(TsConverter.convertToTs(new Timestamp(new Date("11/16/2009").getTime())), "yyyy-MM");
		assertEquals("2009-11",date);
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#normalizeDateString(java.lang.String)}.
	 */
	@Test
	public void testNormalizeDateString() {
		assertEquals("01/31/2001", PAUtil.normalizeDateString("1/31/2001abcdefg"));
        assertEquals("01/31/2001", PAUtil.normalizeDateString("2001-01-31abcdefg"));
        assertNull(PAUtil.normalizeDateString("Tuesday"));
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
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isNotEmpty(java.lang.String)}.
	 */
	@Test
	public void testIsNotEmpty() {
		assertTrue(PAUtil.isNotEmpty("hello"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#stringSetter(java.lang.String, int)}.
	 */
	@Test
	public void testStringSetterStringInt() {
		assertEquals("He",PAUtil.stringSetter("Hello",2));
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
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#trim(java.lang.String, int)}.
	 */
	@Test
	public void testTrim() {
		assertNull(PAUtil.trim(null, 2));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#wildcardCriteria(java.lang.String)}.
	 */
	@Test
	public void testWildcardCriteria() {
		assertEquals("",PAUtil.wildcardCriteria(null));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isGreatenThan(gov.nih.nci.coppa.iso.St, int)}.
	 */
	@Test
	public void testIsGreatenThan() {
		assertTrue(PAUtil.isGreatenThan(StConverter.convertToSt("hello"), 2));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isWithinRange(gov.nih.nci.coppa.iso.St, int, int)}.
	 */
	@Test
	public void testIsWithinRange() {
		assertTrue(PAUtil.isWithinRange(StConverter.convertToSt("hello"), 2 , 6));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#getIiExtension(gov.nih.nci.coppa.iso.Ii)}.
	 */
	@Test
	public void testGetIiExtension() {
		assertEquals("1", PAUtil.getIiExtension(IiConverter.convertToIi("1")));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#getErrorMsg(java.util.Map)}.
	 */
	@Test
	public void testGetErrorMsg() {
		Map<String, String[]> errMap = new HashMap<String, String[]>();
		assertEquals("",PAUtil.getErrorMsg(errMap));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#containsIi(java.util.Map, gov.nih.nci.coppa.iso.Ii)}.
	 */
	@Test
	public void testContainsIi() {
		Map<Ii, Ii> map = null;
		Ii key = null;
		assertNull(PAUtil.containsIi(map, key));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#getFirstObj(java.util.List)}.
	 */
	@Test
	public void testGetFirstObj() {
		List<StudyDTO> studyList = null;
		assertNull(PAUtil.getFirstObj(studyList));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#getDocumentFilePath(java.lang.Long, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetDocumentFilePath() throws PAException {
		PAUtil.getDocumentFilePath(1L, "IRB.doc", "1");
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isDateCurrentOrPast(java.lang.String)}.
	 */
	@Test
	public void testIsDateCurrentOrPastString() {
		assertFalse(PAUtil.isDateCurrentOrPast("10/29/2009"));
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
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isAbstractedAndAbove(gov.nih.nci.coppa.iso.Cd)}.
	 */
	@Test
	public void testIsAbstractedAndAbove() {
		assertTrue(
	      PAUtil.isAbstractedAndAbove(CdConverter.convertStringToCd(DocumentWorkflowStatusCode.ABSTRACTED.getCode())));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isListNotEmpty(java.util.List)}.
	 */
	@Test
	public void testIsListNotEmpty() {
		assertFalse(PAUtil.isListNotEmpty(new ArrayList()));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isListEmpty(java.util.List)}.
	 */
	@Test
	public void testIsListEmpty() {
		assertFalse(PAUtil.isListNotEmpty(new ArrayList()));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#checkIfValueExists(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=Exception.class)
	public void testCheckIfValueExists() throws PAException {
		PAUtil.checkIfValueExists("GAS", "DoseForm", "code");
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertStringToDecimal(java.lang.String)}.
	 */
	@Test
	public void testConvertStringToDecimal() {
		assertNotNull(PAUtil.convertStringToDecimal("2"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isNumber(java.lang.String)}.
	 */
	@Test
	public void testIsNumber() {
		assertTrue(PAUtil.isNumber("3"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertPqToUnit(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}.
	 */
	@Test
	public void testConvertPqToUnit() {
		assertNull(PAUtil.convertPqToUnit(null));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertPqToDecimal(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}.
	 */
	@Test
	public void testConvertPqToDecimal() {
		assertNull(PAUtil.convertPqToDecimal(null));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#convertPqToPrecision(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}.
	 */
	@Test
	public void testConvertPqToPrecision() {
		assertNull(PAUtil.convertPqToPrecision(null));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.util.PAUtil#isTypeIntervention(gov.nih.nci.coppa.iso.Cd)}.
	 */
	@Test
	public void testIsTypeIntervention() {
		assertTrue(PAUtil.isTypeIntervention(CdConverter.convertStringToCd(ActivityCategoryCode.INTERVENTION.getCode())));
	}

}
