/**
 * 
 */
package gov.nih.nci.pa.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.NotNull;

/**
 * @author Vrushali
 *
 */
@MappedSuperclass
public class OrganizationalStructuralRole extends StructuralRole {
    private Organization organization;
    /**
    *
    * @return organization
    */
   @ManyToOne
   @JoinColumn(name = "ORGANIZATION_IDENTIFIER", nullable = false)
   @NotNull
   public Organization getOrganization() {
       return organization;
   }
   /**
    *
    * @param organization organization
    */
   public void setOrganization(Organization organization) {
       this.organization = organization;
   }
}
