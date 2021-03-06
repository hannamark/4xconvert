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
 * <p>Java class for TimePerspectiveCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TimePerspectiveCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Prospective"/>
 *     &lt;enumeration value="Retrospective"/>
 *     &lt;enumeration value="Cross-sectional"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TimePerspectiveCode")
@XmlEnum
public enum TimePerspectiveCode {

    @XmlEnumValue("Prospective")
    PROSPECTIVE("Prospective"),
    @XmlEnumValue("Retrospective")
    RETROSPECTIVE("Retrospective"),
    @XmlEnumValue("Cross-sectional")
    CROSS_SECTIONAL("Cross-sectional"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    TimePerspectiveCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TimePerspectiveCode fromValue(String v) {
        for (TimePerspectiveCode c: TimePerspectiveCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
