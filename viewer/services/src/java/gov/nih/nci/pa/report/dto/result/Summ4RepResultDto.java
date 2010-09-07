package gov.nih.nci.pa.report.dto.result;

import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

/**
 * @author Max Shestopalov
 */
public class Summ4RepResultDto {

    /**
     * @return the sponsor
     */
    public St getSponsor() {
        return sponsor;
    }
    /**
     * @param sponsor the sponsor to set
     */
    public void setSponsor(St sponsor) {
        this.sponsor = sponsor;
    }
    /**
     * @return the protoId
     */
    public St getProtoId() {
        return protoId;
    }
    /**
     * @param protoId the protoId to set
     */
    public void setProtoId(St protoId) {
        this.protoId = protoId;
    }
    /**
     * @return the pi
     */
    public St getPi() {
        return pi;
    }
    /**
     * @param pi the pi to set
     */
    public void setPi(St pi) {
        this.pi = pi;
    }
    /**
     * @return the programCode
     */
    public St getProgramCode() {
        return programCode;
    }
    /**
     * @param programCode the programCode to set
     */
    public void setProgramCode(St programCode) {
        this.programCode = programCode;
    }
  
    /**
     * @return the openDate
     */
    public Ts getOpenDate() {
        return openDate;
    }
    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(Ts openDate) {
        this.openDate = openDate;
    }
    /**
     * @return the closedDate
     */
    public Ts getClosedDate() {
        return closedDate;
    }
    /**
     * @param closedDate the closedDate to set
     */
    public void setClosedDate(Ts closedDate) {
        this.closedDate = closedDate;
    }
    /**
     * @return the phase
     */
    public St getPhase() {
        return phase;
    }
    /**
     * @param phase the phase to set
     */
    public void setPhase(St phase) {
        this.phase = phase;
    }
    /**
     * @return the type
     */
    public St getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(St type) {
        this.type = type;
    }
    /**
     * @return the title
     */
    public St getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(St title) {
        this.title = title;
    }
    /**
     * @return the target
     */
    public Int getTarget() {
        return target;
    }
    /**
     * @param target the target to set
     */
    public void setTarget(Int target) {
        this.target = target;
    }
    /**
     * @return the accrualCenter12m
     */
    public Int getAccrualCenter12m() {
        return accrualCenter12m;
    }
    /**
     * @param accrualCenter12m the accrualCenter12m to set
     */
    public void setAccrualCenter12m(Int accrualCenter12m) {
        this.accrualCenter12m = accrualCenter12m;
    }
    /**
     * @return the accrualCenterToDate
     */
    public Int getAccrualCenterToDate() {
        return accrualCenterToDate;
    }
    /**
     * @param accrualCenterToDate the accrualCenterToDate to set
     */
    public void setAccrualCenterToDate(Int accrualCenterToDate) {
        this.accrualCenterToDate = accrualCenterToDate;
    }
    
    /**
     * @return the sortCriteria
     */
    public St getSortCriteria() {
        return sortCriteria;
    }
    /**
     * @return the subSortCriteria
     */
    public St getSubSortCriteria() {
        return subSortCriteria;
    }
    
    /**
     * @param sortCriteria the sortCriteria to set
     */
    public void setSortCriteria(St sortCriteria) {
        this.sortCriteria = sortCriteria;
    }
    /**
     * @param subSortCriteria the subSortCriteria to set
     */
    public void setSubSortCriteria(St subSortCriteria) {
        this.subSortCriteria = subSortCriteria;
    }
    
    private St sponsor = StConverter.convertToSt(null);
    private St protoId = StConverter.convertToSt(null);
    private St pi = StConverter.convertToSt(null);
    private St programCode = StConverter.convertToSt(null);
    private Ts openDate = TsConverter.convertToTs(null);
    private Ts closedDate = TsConverter.convertToTs(null);
    private St phase = StConverter.convertToSt(null);
    private St type = StConverter.convertToSt(null);
    private St title = StConverter.convertToSt(null);
    private Int target = IntConverter.convertToInt((Integer) null);
    private Int accrualCenter12m = IntConverter.convertToInt((Integer) null);
    private Int accrualCenterToDate = IntConverter.convertToInt((Integer) null);
    private St sortCriteria = StConverter.convertToSt(null);
    private St subSortCriteria = StConverter.convertToSt(null);
    
    
}
