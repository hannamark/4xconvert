package gov.nih.nci.accrual.dto;

import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.collections.comparators.NullComparator;

/**
 * @author Hugh Reinhart
 * @since Jul 23, 2012
 */
public class HistoricalSubmissionDto implements Comparable<HistoricalSubmissionDto>, Serializable {

    private static final long serialVersionUID = -3377821023341924359L;

    private Long batchFileIdentifier;
    private String nciNumber;
    private String fileHtml;
    private AccrualSubmissionTypeCode submissionType;
    private Timestamp date;
    private String username;
    private String result;
    private Long completeTrialId;
    private Long abbreviatedTrialId;
    private String fileName;

    /**
     * @return the batchFileIdentifier
     */
    public Long getBatchFileIdentifier() {
        return batchFileIdentifier;
    }
    /**
     * @param batchFileIdentifier the batchFileIdentifier to set
     */
    public void setBatchFileIdentifier(Long batchFileIdentifier) {
        this.batchFileIdentifier = batchFileIdentifier;
    }
    /**
     * @return the nciNumber
     */
    public String getNciNumber() {
        return nciNumber;
    }
    /**
     * @param nciNumber the nciNumber to set
     */
    public void setNciNumber(String nciNumber) {
        this.nciNumber = nciNumber;
    }
    /**
     * @return the fileHtml
     */
    public String getFileHtml() {
        return fileHtml;
    }
    /**
     * @param fileHtml the fileHtml to set
     */
    public void setFileHtml(String fileHtml) {
        this.fileHtml = fileHtml;
    }
    /**
     * @return the submissionType
     */
    public AccrualSubmissionTypeCode getSubmissionType() {
        return submissionType;
    }
    /**
     * @param submissionType the submissionType to set
     */
    public void setSubmissionType(AccrualSubmissionTypeCode submissionType) {
        this.submissionType = submissionType;
    }
    /**
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }
    /**
     * @param date the date to set
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }
    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the completeTrialId
     */
    public Long getCompleteTrialId() {
        return completeTrialId;
    }
    /**
     * @param completeTrialId the completeTrialId to set
     */
    public void setCompleteTrialId(Long completeTrialId) {
        this.completeTrialId = completeTrialId;
    }
    /**
     * @return the abbreviatedTrialId
     */
    public Long getAbbreviatedTrialId() {
        return abbreviatedTrialId;
    }
    /**
     * @param abbreviatedTrialId the abbreviatedTrialId to set
     */
    public void setAbbreviatedTrialId(Long abbreviatedTrialId) {
        this.abbreviatedTrialId = abbreviatedTrialId;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public int compareTo(HistoricalSubmissionDto that) {
        NullComparator nc = new NullComparator();
        int comparison = nc.compare(getDate(), that.getDate());
        if (comparison != 0) {
           return comparison;
        }
        return nc.compare(getNciNumber(), that.getNciNumber());
    }
}
