//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.28 at 12:41:09 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StudyModelCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StudyModelCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Cohort"/>
 *     &lt;enumeration value="Case-control"/>
 *     &lt;enumeration value="Case-only"/>
 *     &lt;enumeration value="Case-crossover"/>
 *     &lt;enumeration value="Ecologic or community studies"/>
 *     &lt;enumeration value="Family-based"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StudyModelCode")
@XmlEnum
public enum StudyModelCode {

    @XmlEnumValue("Cohort")
    COHORT("Cohort"),
    @XmlEnumValue("Case-control")
    CASE_CONTROL("Case-control"),
    @XmlEnumValue("Case-only")
    CASE_ONLY("Case-only"),
    @XmlEnumValue("Case-crossover")
    CASE_CROSSOVER("Case-crossover"),
    @XmlEnumValue("Ecologic or community studies")
    ECOLOGIC_OR_COMMUNITY_STUDIES("Ecologic or community studies"),
    @XmlEnumValue("Family-based")
    FAMILY_BASED("Family-based"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    StudyModelCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StudyModelCode fromValue(String v) {
        for (StudyModelCode c: StudyModelCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
