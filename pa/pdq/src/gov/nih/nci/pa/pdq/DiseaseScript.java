package gov.nih.nci.pa.pdq;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.util.PAUtil;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author hreinhart
 *
 */
public class DiseaseScript extends BaseScript {
    private static final Logger LOG = Logger.getLogger(DiseaseScript.class);
    private static String fName = "disease.sql";
    private Integer dSqn = Integer.valueOf(100);
    private static DiseaseScript script = new DiseaseScript();
    private static HashMap<String, Integer> codeMap = new HashMap<String, Integer>();
    
    private class DisPar {
        String childCode;
        String parentCode;
        String parentType;
    }
    private static ArrayList<DisPar> parents = new ArrayList<DisPar>();
    
    public static DiseaseScript get() {
        return script;
    }


    /**
     * 
     */
    public DiseaseScript() {
        try{
            new FileOutputStream(fName);
            out = new PrintStream(new BufferedOutputStream(new FileOutputStream(fName, true)));
            out.println("TRUNCATE DISEASE_ALTERNAME;");
            out.println("TRUNCATE DISEASE_PARENT;");
            out.println("DELETE FROM DISEASE;");
          }  
          catch (IOException iox)
          {
              LOG.error("Error creatinig script file'"+fName+".  IO Error:  " + iox.toString());
              System.exit(0);
          }
    }
    public void add(Disease dis, List<DiseaseAltername> danList, String user) {
        if (PAUtil.isEmpty(dis.getPreferredName())) {
            LOG.error("Tried to create a disease with no name.  ");
            System.exit(1);
        }
        StringBuffer one = new StringBuffer("INSERT INTO DISEASE (IDENTIFIER,PREFERRED_NAME");  
        StringBuffer two = new StringBuffer(" VALUES (" + ++dSqn + ",'" + fix(dis.getPreferredName()) + "'");
        if(PAUtil.isNotEmpty(dis.getDiseaseCode())) {
            one.append(",DISEASE_CODE");
            two.append(",'" + fix(dis.getDiseaseCode()) + "'");
        }
        if(PAUtil.isNotEmpty(dis.getNtTermIdentifier())) {
            one.append(",NT_TERM_IDENTIFIER");
            two.append(",'" + fix(dis.getNtTermIdentifier()) + "'");
        }
        if(PAUtil.isNotEmpty(dis.getMenuDisplayName())) {
            one.append(",MENU_DISPLAY_NAME");
            two.append(",'" + fix(dis.getMenuDisplayName()) + "'");
        }
        one.append(",STATUS_CODE,STATUS_DATE_RANGE_LOW,USER_LAST_CREATED)");
        two.append(",'ACTIVE',now(),'" + user + "');");
        
        out.println(one.toString() + two.toString());
        codeMap.put(dis.getDiseaseCode(), dSqn);
        if (!danList.isEmpty()) {
            for (DiseaseAltername dan : danList) {
                alternameAdd(dan, dSqn);
            }
        }
    }
    public void addParent(String child, String parent, String type) {
        DisPar x = new DisPar();
        x.childCode = child;
        x.parentCode = parent;
        x.parentType = type;
        parents.add(x);
    }
    private void  alternameAdd(DiseaseAltername dan, Integer disId) {
        StringBuffer one = new StringBuffer("INSERT INTO DISEASE_ALTERNAME (ALTERNATE_NAME,DISEASE_IDENTIFIER,STATUS_CODE,STATUS_DATE_RANGE_LOW) ");
        StringBuffer two = new StringBuffer(" VALUES ('" + fix(dan.getAlternateName()) + "'," + disId + ",'ACTIVE',now());");
        out.println(one.toString() + two.toString());
    }

    public void close(String dateString) {
        for (DisPar dp : parents) {
            LOG.info(dp.childCode + " - " + dp.parentCode + " - " + dp.parentType);
            if (codeMap.containsKey(dp.childCode) && codeMap.containsKey(dp.parentCode)) {
                out.println("INSERT INTO DISEASE_PARENT (DISEASE_IDENTIFIER,PARENT_DISEASE_IDENTIFIER,PARENT_DISEASE_CODE,STATUS_CODE,STATUS_DATE_RANGE_LOW)"
                        + " VALUES (" + codeMap.get(dp.childCode) + "," + codeMap.get(dp.parentCode) + ",'" + fix(dp.parentType) + "','ACTIVE',now());");
            } else {
                LOG.error("BAD");
            }
        }
        out.println("UPDATE DISEASE SET DATE_LAST_CREATED = '" + dateString + "';");
        out.println("UPDATE DISEASE_ALTERNAME DA SET USER_LAST_CREATED = (SELECT USER_LAST_CREATED FROM DISEASE DD WHERE DD.IDENTIFIER = DA.DISEASE_IDENTIFIER);");
        out.println("UPDATE DISEASE_ALTERNAME SET DATE_LAST_CREATED = '" + dateString + "';");
        out.println("UPDATE DISEASE_PARENT DP SET USER_LAST_CREATED = (SELECT USER_LAST_CREATED FROM DISEASE DD WHERE DD.IDENTIFIER = DP.DISEASE_IDENTIFIER);");
        out.println("UPDATE DISEASE_PARENT SET DATE_LAST_CREATED = '" + dateString + "';");
        out.close();
    }
}
