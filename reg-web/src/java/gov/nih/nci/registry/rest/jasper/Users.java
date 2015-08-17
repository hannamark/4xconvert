//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.13 at 02:19:50 PM EDT 
//


package gov.nih.nci.registry.rest.jasper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="emailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="externallyDefined" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="fullName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="previousPasswordChangeTime" 
 *                   type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="roles" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="externallyDefined" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="roleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "user"
})
@XmlRootElement(name = "users")
public class Users {

    private List<Users.User> user;

    /**
     * Gets the value of the user property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the user property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Users.User }
     * 
     * @return - list of users
     */
    public List<Users.User> getUser() {
        if (user == null) {
            user = new ArrayList<Users.User>();
        }
        return this.user;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="emailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="externallyDefined" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="fullName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="previousPasswordChangeTime" 
     *         type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="roles" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="externallyDefined" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="roleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "emailAddress",
        "enabled",
        "externallyDefined",
        "fullName",
        "previousPasswordChangeTime",
        "roles",
        "username"
    })
    /**
     * 
     * @author vpoluri
     *
     */
    @XmlRootElement (name = "user")
    public static class User {

        private String emailAddress;
        @XmlElement(required = true)
        private String enabled;
        @XmlElement(required = true)
        private String externallyDefined;
        @XmlElement(required = true)
        private String fullName;
        @XmlSchemaType(name = "dateTime")
        private XMLGregorianCalendar previousPasswordChangeTime;
        private List<Users.User.Roles> roles;
        @XmlElement(required = true)
        private String username;

        /**
         * Gets the value of the emailAddress property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEmailAddress() {
            return emailAddress;
        }

        /**
         * Sets the value of the emailAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEmailAddress(String value) {
            this.emailAddress = value;
        }

        /**
         * Gets the value of the enabled property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEnabled() {
            return enabled;
        }

        /**
         * Sets the value of the enabled property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEnabled(String value) {
            this.enabled = value;
        }

        /**
         * Gets the value of the externallyDefined property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExternallyDefined() {
            return externallyDefined;
        }

        /**
         * Sets the value of the externallyDefined property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExternallyDefined(String value) {
            this.externallyDefined = value;
        }

        /**
         * Gets the value of the fullName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFullName() {
            return fullName;
        }

        /**
         * Sets the value of the fullName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFullName(String value) {
            this.fullName = value;
        }

        /**
         * Gets the value of the previousPasswordChangeTime property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getPreviousPasswordChangeTime() {
            return previousPasswordChangeTime;
        }

        /**
         * Sets the value of the previousPasswordChangeTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setPreviousPasswordChangeTime(XMLGregorianCalendar value) {
            this.previousPasswordChangeTime = value;
        }

        /**
         * Gets the value of the roles property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the roles property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRoles().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Users.User.Roles }
         * 
         * @return - returns list of roles
         */
        public List<Users.User.Roles> getRoles() {
            if (roles == null) {
                roles = new ArrayList<Users.User.Roles>();
            }
            return this.roles;
        }

        /**
         * Gets the value of the username property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the value of the username property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUsername(String value) {
            this.username = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="externallyDefined" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="roleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "externallyDefined",
            "roleName"
        })
        public static class Roles {

            @XmlElement(required = true)
            private String externallyDefined;
            @XmlElement(required = true)
            private String roleName;

            /**
             * Gets the value of the externallyDefined property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExternallyDefined() {
                return externallyDefined;
            }

            /**
             * Sets the value of the externallyDefined property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExternallyDefined(String value) {
                this.externallyDefined = value;
            }

            /**
             * Gets the value of the roleName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRoleName() {
                return roleName;
            }

            /**
             * Sets the value of the roleName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRoleName(String value) {
                this.roleName = value;
            }
            
            /**
             * overrides default equals
             * @param o - Roles
             * @return - returns true if rolenames are equal
             */
            public boolean equals(Roles o) {
            
            return getRoleName().equals(o.getRoleName());
            
            }

            /**
             * overrides hashcode
             * @return - int
             */
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }

        /**
         * overrides default equals
         * @param o - Roles
         * @return - returns true if rolenames are equal
         */
        public boolean equals(User o) {
        
            return getUsername().equals(o.getUsername());
        
        }
        /**
         * overrides hashcode
         * @return - int
         */
        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
