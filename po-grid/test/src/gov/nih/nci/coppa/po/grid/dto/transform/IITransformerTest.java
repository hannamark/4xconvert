package gov.nih.nci.coppa.po.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;

import org.iso._21090.II;


public class IITransformerTest extends AbstractTransformerTestBase<IITransformer,II,Ii>{
	 /**
     * The identifier name.
     */
    public static final String IDENTIFIER_NAME = "identifier name";

    /**
     * The ii root value.
     */
    public static final String ROOT = "2.16.840.1.113883.3.26.4.4.3";
 
	@Override
	public Ii makeDtoSimple() {
		  Ii id = new Ii();
	      id.setDisplayable(Boolean.TRUE);
		  id.setRoot(ROOT);
		  id.setIdentifierName(IDENTIFIER_NAME);
		  id.setExtension("123");
	      id.setNullFlavor(NullFlavor.OTH);
	      id.setReliability(IdentifierReliability.ISS);
	      id.setScope(IdentifierScope.VER);
		  return id;
	}

	@Override
	public II makeXmlSimple() {
		  II id = new II();
	      id.setDisplayable(Boolean.TRUE);
		  id.setRoot(ROOT);
		  id.setIdentifierName(IDENTIFIER_NAME);
		  id.setExtension("123");
	      id.setNullFlavor(org.iso._21090.NullFlavor.OTH);
	      id.setReliability(org.iso._21090.IdentifierReliability.ISS);
	      id.setScope(org.iso._21090.IdentifierScope.VER);
		  return id;
	}

	@Override
	public void verifyDtoSimple(Ii x) {
		assertEquals(x.getDisplayable(),Boolean.TRUE);
		assertEquals(x.getRoot(),ROOT);
		assertEquals(x.getIdentifierName(),IDENTIFIER_NAME);
		assertEquals(x.getExtension(), "123");
		assertEquals(x.getNullFlavor(),NullFlavor.OTH);
		assertEquals(x.getReliability(),IdentifierReliability.ISS);
		assertEquals(x.getScope(),IdentifierScope.VER);
		
	}

	@Override
	public void verifyXmlSimple(II x) {
		assertEquals(x.getRoot(),ROOT);
		assertEquals(x.getIdentifierName(),IDENTIFIER_NAME);
		assertEquals(x.getExtension(), "123");
		assertEquals(x.getNullFlavor(),org.iso._21090.NullFlavor.OTH);
		assertEquals(x.getReliability(),org.iso._21090.IdentifierReliability.ISS);
		assertEquals(x.getScope(),org.iso._21090.IdentifierScope.VER);
	}

}
