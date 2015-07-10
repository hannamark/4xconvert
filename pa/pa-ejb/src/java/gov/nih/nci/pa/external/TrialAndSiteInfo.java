/**
 * 
 */
package gov.nih.nci.pa.external;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author vinodh
 *
 */
public class TrialAndSiteInfo {
    
    
    /**
     * 
     */
    private static final String NO = "No";
    /**
     * 
     */
    private static final String YES = "Yes";
    
    public static String NCI_ID = "nci_id";
    public static String NCT_ID = "nct_id";
    public static String IS_RSS_TRIAL = "is_rss_trial";
    public static String TRIAL_TYPE = "trial_type";
    public static String LEAD_ORG_FAM = "lead_org_family";
    public static String LEAD_ORG = "lead_org";
    public static String SUB_ORG = "submitting_org";
    public static String SUB_NM = "submitter_name";
    public static String SUM_4_FUND_CAT = "summary_4_funding_category";
    public static String CTEP_ID = "ctep_id";
    public static String DCP_ID = "dcp_id";
    public static String SP_ID = "study_protocol_identifier";
    public static String PROC_STS = "study_processing_status";
    public static String PROC_STS_DT = "study_processing_status_date";
    
    public static String STUDY_STS = "most_recent_study_status";
    public static String STUDY_STS_DT = "most_recent_study_status_date";
    public static String PS_ID = "participating_site_identifier";
    public static String PS_ORG_FAM = "participating_site_org_family";
    public static String PS_ORG_NM = "participating_site_org_name";
    
    public Map<String, Object> infoMap;
    
    public boolean isForPS = false;
    
    /**
     * @param infoMap
     */
    public TrialAndSiteInfo(boolean isForPS) {
        super();
        this.isForPS = isForPS;
        
        this.infoMap = new LinkedHashMap<String, Object>();
        
        this.infoMap.put(NCI_ID, "");
        this.infoMap.put(NCT_ID, "");
        this.infoMap.put(TRIAL_TYPE, "");
        this.infoMap.put(LEAD_ORG_FAM, "");
        this.infoMap.put(LEAD_ORG, "");
        this.infoMap.put(SUB_ORG, "");
        this.infoMap.put(SUB_NM, "");
        this.infoMap.put(SUM_4_FUND_CAT, "");
        if(isForPS) {
            this.infoMap.put(STUDY_STS, "");
            this.infoMap.put(PS_ID, "");
            this.infoMap.put(PS_ORG_FAM, "");
            this.infoMap.put(PS_ORG_NM, "");
        }
        this.infoMap.put(CTEP_ID, "");
        this.infoMap.put(DCP_ID, "");
        this.infoMap.put(SP_ID, "");
        this.infoMap.put(PROC_STS, "");
    }


    public void map(ResultSet rs) throws Exception {
        if (rs == null) {
            throw new Exception("Error encountered: ResultSet is Null");
        }
        Set<String> cols = this.infoMap.keySet();
        for (String col : cols) {
            this.infoMap.put(col, rs.getString(col));
        }
        
        if(isForPS) {
            this.infoMap.put(STUDY_STS_DT, rs.getDate(STUDY_STS_DT));
        }
        this.infoMap.put(IS_RSS_TRIAL, rs.getBoolean(IS_RSS_TRIAL) ? YES : NO);
        this.infoMap.put(PROC_STS_DT, rs.getDate(PROC_STS_DT));
    }
    
    public Object getColValue(String col) {
        return this.infoMap.get(col);
    }
    
}
