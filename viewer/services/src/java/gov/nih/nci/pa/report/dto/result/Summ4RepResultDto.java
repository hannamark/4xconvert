package gov.nih.nci.pa.report.dto.result;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;

/**
 * @author Max Shestopalov
 */
//need all these fields
@SuppressWarnings("PMD.TooManyFields")
public class Summ4RepResultDto {

    private St sponsor = new St();
    private St protoId = new St();
    private St pi = new St();
    private St programCode = new St();
    private St orgMember = new St();
    private Ts openDate = new Ts();
    private Ts closedDate = new Ts();
    private St phase = new St();
    private St type = new St();
    private St title = new St();
    private Int target = new Int();
    private Int accrualCenter12m = new Int();
    private Int accrualCenterToDate = new Int();
    private St sortCriteria = new St();
    private St subSortCriteria = new St();
    private DSet<Cd> anatomicSiteCodes = new DSet<Cd>();
    private St nciIdentifier = new St();
    private St nctIdentifier = new St();
    private St ctepIdentifier = new St();
    private St leadOrgName = new St();
    
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
     * @return the orgMember
     */
    public St getOrgMember() {
        return orgMember;
    }
    
    /**
     * @param orgMember the orgMember to set
     */
    public void setOrgMember(St orgMember) {
        this.orgMember = orgMember;
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
    
    /**
     * @param siteCodes the anatomic site codes to set
     */
    public void setAnatomicSiteCodes(DSet<Cd> siteCodes) {
        this.anatomicSiteCodes = siteCodes;
    }
    /**
     * @return dset of natomic site codes.
     */
    public DSet<Cd> getAnatomicSiteCodes() {
        return this.anatomicSiteCodes;
    }
    /**
     * @param nciIdentifier the nciIdentifier to set
     */
    public void setNciIdentifier(St nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }
    /**
     * @return the nciIdentifier
     */
    public St getNciIdentifier() {
        return nciIdentifier;
    }
    /**
     * @param nctIdentifier the nctIdentifier to set
     */
    public void setNctIdentifier(St nctIdentifier) {
        this.nctIdentifier = nctIdentifier;
    }
    /**
     * @return the nctIdentifier
     */
    public St getNctIdentifier() {
        return nctIdentifier;
    }
    /**
     * @param ctepIdentifier the ctepIdentifier to set
     */
    public void setCtepIdentifier(St ctepIdentifier) {
        this.ctepIdentifier = ctepIdentifier;
    }
    /**
     * @return the ctepIdentifier
     */
    public St getCtepIdentifier() {
        return ctepIdentifier;
    }
    /**
     * @param name the name to set
     */
    public void setLeadOrgName(St name) {
        this.leadOrgName = name;
    }
    /**
     * @return the name
     */
    public St getLeadOrgName() {
        return this.leadOrgName;
    }
}
