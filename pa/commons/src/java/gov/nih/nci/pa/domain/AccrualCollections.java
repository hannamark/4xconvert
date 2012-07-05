package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotNull;

/**
 * @author Hugh Reinhart
 * @since Jul 3, 2012
 */
public class AccrualCollections extends AbstractStudyEntity {

    private static final long serialVersionUID = 4897356017387688389L;

    private BatchFile batchFile;
    private boolean passedValidation = false;
    private char changeCode;
    private String results;

    /**
     * @return the batchFile
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_file_identifier", updatable = false)
    @NotNull
    public BatchFile getBatchFile() {
        return batchFile;
    }

    /**
     * @param batchFile the batchFile to set
     */
    public void setBatchFile(BatchFile batchFile) {
        this.batchFile = batchFile;
    }

    /**
     * @return the passedValidation
     */
    @Column(name = "passed_validation")
    @NotNull
    public boolean isPassedValidation() {
        return passedValidation;
    }

    /**
     * @param passedValidation the passedValidation to set
     */
    public void setPassedValidation(boolean passedValidation) {
        this.passedValidation = passedValidation;
    }

    /**
     * @return the changeCode
     */
    @Column(name = "change_code")
    public char getChangeCode() {
        return changeCode;
    }

    /**
     * @param changeCode the changeCode to set
     */
    public void setChangeCode(char changeCode) {
        this.changeCode = changeCode;
    }

    /**
     * @return the results
     */
    @Column(name = "results")
    public String getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(String results) {
        this.results = results;
    }
}
