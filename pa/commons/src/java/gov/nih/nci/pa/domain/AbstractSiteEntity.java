/**
 * 
 */
package gov.nih.nci.pa.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.NotNull;

/**
 * Abstract class which should be extended for PA domain objects which
 * have an attribute studySite for a many to one association with StudySite.
 * 
 * @author Vrushali
 *
 */
@MappedSuperclass
public class AbstractSiteEntity extends AbstractEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** . */
    protected StudySite studySite;

    /**
     * @return the studySite
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_SITE_IDENTIFIER", updatable = false)
    @NotNull
    public StudySite getStudySite() {
        return studySite;
    }

    /**
     * @param studySite the studySite to set
     */
    public void setStudySite(StudySite studySite) {
        this.studySite = studySite;
    }

}
