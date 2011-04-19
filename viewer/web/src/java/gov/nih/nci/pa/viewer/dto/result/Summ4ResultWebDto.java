package gov.nih.nci.pa.viewer.dto.result;

import gov.nih.nci.pa.iso.util.DSetConverter;
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
    private List<String> anatomicSites = null;
    
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
        anatomicSites = DSetConverter.convertDSetCdToList(dto.getAnatomicSiteCodes());
    }
    
    /**
     * @return the sponsor
     */
    public String getSponsor() {
        return sponsor;
    }

    /**
     * @return the protoId
     */
    public String getProtoId() {
        return protoId;
    }

    /**
     * @return the pi
     */
    public String getPi() {
        return pi;
    }

    /**
     * @return the programCode
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * @return the openDate
     */
    public Timestamp getOpenDate() {
        return openDate;
    }

    /**
     * @return the closedDate
     */
    public Timestamp getClosedDate() {
        return closedDate;
    }

    /**
     * @return the phase
     */
    public String getPhase() {
        return phase;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the target
     */
    public Integer getTarget() {
        return target;
    }

    /**
     * @return the accrualCenter12m
     */
    public Integer getAccrualCenter12m() {
        return accrualCenter12m;
    }

    /**
     * @return the accrualCenterToDate
     */
    public Integer getAccrualCenterToDate() {
        return accrualCenterToDate;
    }

    /**
     * @return the sortCriteria
     */
    public String getSortCriteria() {
        return sortCriteria;
    }

    /**
     * @return the subSortCriteria
     */
    public String getSubSortCriteria() {
        return subSortCriteria;
    }
    
    /**
     * @return the anatomicSite
     */
    public List<String> getAnatomicSites() {
        return this.anatomicSites;
    }
    
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
 
}
