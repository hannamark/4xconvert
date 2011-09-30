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
// need all these fields
@SuppressWarnings("PMD.TooManyFields")
public final class Summ4ResultWebDto {

    private String sponsor = "";
    private String protoId = "";
    private String pi = "";
    private String programCode = "";
    private String orgMember = "";
    private Timestamp openDate = null;
    private Timestamp closedDate = null;
    private String phase = "";
    private String type = "";
    private String title = "";
    private Integer target = null;
    private Integer accrualCenterLO12m = null;
    private Integer accrualCenterTS12m = null;
    private Integer accrualCenterLOToDate = null;
    private Integer accrualCenterTSToDate = null;
    private String sortCriteria = "";
    private String subSortCriteria = "";
    private String anatomicSites = "";
    private String nciIdentifier = "";
    private String nctIdentifier = "";
    private String ctepIdentifier = "";
    private String leadOrgName = "";

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
        orgMember = StConverter.convertToString(dto.getOrgMember());
        openDate = TsConverter.convertToTimestamp(dto.getOpenDate());
        closedDate = TsConverter.convertToTimestamp(dto.getClosedDate());
        phase = StConverter.convertToString(dto.getPhase());
        type = StConverter.convertToString(dto.getType());
        title = StConverter.convertToString(dto.getTitle());
        target = IntConverter.convertToInteger(dto.getTarget());
        accrualCenterLO12m = IntConverter.convertToInteger(dto.getAccrualCenterLeadOrg12m());
        accrualCenterLOToDate = IntConverter.convertToInteger(dto.getAccrualCenterLeadOrgToDate());
        accrualCenterTS12m = IntConverter.convertToInteger(dto.getAccrualCenterTreatOrg12m());
        accrualCenterTSToDate = IntConverter.convertToInteger(dto.getAccrualCenterTreatOrgToDate());
        sortCriteria = StConverter.convertToString(dto.getSortCriteria());
        subSortCriteria = StConverter.convertToString(dto.getSubSortCriteria());
        anatomicSites = StConverter.convertToString(dto.getAnatomicSiteCodes());
        nciIdentifier = StConverter.convertToString(dto.getNciIdentifier());
        nctIdentifier = StConverter.convertToString(dto.getNctIdentifier());
        leadOrgName = StConverter.convertToString(dto.getLeadOrgName());
        ctepIdentifier = StConverter.convertToString(dto.getCtepIdentifier());
    }

    /**
     * @return the nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }

    /**
     * @return the nctIdentifier
     */
    public String getNctIdentifier() {
        return nctIdentifier;
    }

    /**
     * @return the ctepIdentifier
     */
    public String getCtepIdentifier() {
        return ctepIdentifier;
    }

    /**
     * @return the leadOrgName
     */
    public String getLeadOrgName() {
        return leadOrgName;
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
     * @return the orgMember
     */
    public String getOrgMember() {
        return orgMember;
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
     * @return the accrualCenterLo12m
     */
    public Integer getAccrualCenterLO12m() {
        return accrualCenterLO12m;
    }

    /**
     * @return the accrualCenterLoToDate
     */
    public Integer getAccrualCenterLOToDate() {
        return accrualCenterLOToDate;
    }

    /**
     * @return the accrualCenterTs12m
     */
    public Integer getAccrualCenterTS12m() {
        return accrualCenterTS12m;
    }

    /**
     * @return the accrualCenterTsToDate
     */
    public Integer getAccrualCenterTSToDate() {
        return accrualCenterTSToDate;
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
    public String getAnatomicSites() {
        return anatomicSites;
    }

    /**
     * Static method for generating a list of web dto's from a list of service dto's.
     *
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
