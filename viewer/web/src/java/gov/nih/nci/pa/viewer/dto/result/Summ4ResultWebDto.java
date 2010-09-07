package gov.nih.nci.pa.viewer.dto.result;

import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.result.Summ4RepResultDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Max Shestopalov
 */
public final class Summ4ResultWebDto {
   
    /**
     * @return the sponsor
     */
    public String getSponsor() {
        return sponsor;
    }



    /**
     * @param sponsor the sponsor to set
     */
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }



    /**
     * @return the protoId
     */
    public String getProtoId() {
        return protoId;
    }



    /**
     * @param protoId the protoId to set
     */
    public void setProtoId(String protoId) {
        this.protoId = protoId;
    }



    /**
     * @return the pi
     */
    public String getPi() {
        return pi;
    }



    /**
     * @param pi the pi to set
     */
    public void setPi(String pi) {
        this.pi = pi;
    }



    /**
     * @return the programCode
     */
    public String getProgramCode() {
        return programCode;
    }



    /**
     * @param programCode the programCode to set
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }



    /**
     * @return the openDate
     */
    public Timestamp getOpenDate() {
        return openDate;
    }



    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(Timestamp openDate) {
        this.openDate = openDate;
    }



    /**
     * @return the closedDate
     */
    public Timestamp getClosedDate() {
        return closedDate;
    }



    /**
     * @param closedDate the closedDate to set
     */
    public void setClosedDate(Timestamp closedDate) {
        this.closedDate = closedDate;
    }



    /**
     * @return the phase
     */
    public String getPhase() {
        return phase;
    }



    /**
     * @param phase the phase to set
     */
    public void setPhase(String phase) {
        this.phase = phase;
    }



    /**
     * @return the type
     */
    public String getType() {
        return type;
    }



    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }



    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }



    /**
     * @param tit the title to set
     */
    public void setTitle(String tit) {
        this.title = tit;
    }



    /**
     * @return the target
     */
    public Integer getTarget() {
        return target;
    }



    /**
     * @param target the target to set
     */
    public void setTarget(Integer target) {
        this.target = target;
    }



    /**
     * @return the accrualCenter12m
     */
    public Integer getAccrualCenter12m() {
        return accrualCenter12m;
    }



    /**
     * @param accrualCenter12m the accrualCenter12m to set
     */
    public void setAccrualCenter12m(Integer accrualCenter12m) {
        this.accrualCenter12m = accrualCenter12m;
    }



    /**
     * @return the accrualCenterToDate
     */
    public Integer getAccrualCenterToDate() {
        return accrualCenterToDate;
    }



    /**
     * @param accrualCenterToDate the accrualCenterToDate to set
     */
    public void setAccrualCenterToDate(Integer accrualCenterToDate) {
        this.accrualCenterToDate = accrualCenterToDate;
    }



    /**
     * @return the sortCriteria
     */
    public String getSortCriteria() {
        return sortCriteria;
    }



    /**
     * @param sortCriteria the sortCriteria to set
     */
    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }



    /**
     * @return the subSortCriteria
     */
    public String getSubSortCriteria() {
        return subSortCriteria;
    }



    /**
     * @param subSortCriteria the subSortCriteria to set
     */
    public void setSubSortCriteria(String subSortCriteria) {
        this.subSortCriteria = subSortCriteria;
    }

    private String sponsor = "";
    private String protoId = "";
    private String pi = "";
    private String programCode = "";
    private Timestamp openDate = null;
    private Timestamp closedDate = null;
    private String phase = "";
    private String type = "";
    private String title = "";
    private Integer target = null;
    private Integer accrualCenter12m = null;
    private Integer accrualCenterToDate = null;
    private String sortCriteria = "";
    private String subSortCriteria = "";
    
    /**
     * Static method for generating a list of web dto's from a list of service dto's.
     * @param serviceDtoList service dto list
     * @return web dto list
     */
    public static List<Summ4ResultWebDto> getWebList(List<Summ4RepResultDto> serviceDtoList) {
        List<Summ4ResultWebDto> resultList = new ArrayList<Summ4ResultWebDto>();
        
        for (Summ4RepResultDto dto : serviceDtoList) {
            resultList.add(new Summ4ResultWebDto(dto));
        }
        return resultList;
    }

   

    /**
     * Default constructor.
     */
    private Summ4ResultWebDto() {
    }

    /**
     * Constructor using service dto.
     * @param dto the service iso dto
     */
    private Summ4ResultWebDto(Summ4RepResultDto dto) {
        if (dto == null) { return; }
        sponsor = StConverter.convertToString(dto.getSponsor());
        protoId = StConverter.convertToString(dto.getProtoId());
        pi = StConverter.convertToString(dto.getPi());
        programCode = StConverter.convertToString(dto.getProgramCode());
        openDate = TsConverter.convertToTimestamp(dto.getOpenDate());
        closedDate = TsConverter.convertToTimestamp(dto.getClosedDate());
        phase = StConverter.convertToString(dto.getPhase());
        type = StConverter.convertToString(dto.getType());
        title = StConverter.convertToString(dto.getTitle());
        target = IntConverter.convertToInteger(dto.getTarget());
        accrualCenter12m = IntConverter.convertToInteger(dto.getAccrualCenter12m());
        accrualCenterToDate = IntConverter.convertToInteger(dto.getAccrualCenterToDate());
        sortCriteria = StConverter.convertToString(dto.getSortCriteria());
        subSortCriteria = StConverter.convertToString(dto.getSubSortCriteria());
    }

   
}
