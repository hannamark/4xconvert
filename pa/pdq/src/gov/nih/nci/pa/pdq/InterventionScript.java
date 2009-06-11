package gov.nih.nci.pa.pdq;

import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.util.PAUtil;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author hreinhart
 *
 */
public final class InterventionScript extends BaseScript {
    private static final Logger LOG = Logger.getLogger(InterventionScript.class);
    private static String fName = "intervention.sql";
    private Integer iSqn = Integer.valueOf(100);
    private static InterventionScript script = new InterventionScript();
    
    public static InterventionScript get() {
        return script;
    }

    /**
     * 
     */
    private InterventionScript() {
        try{
            new FileOutputStream(fName);
            out = new PrintStream(new BufferedOutputStream(new FileOutputStream(fName, true)));
            out.println("TRUNCATE INTERVENTION_ALTERNATE_NAME;");
            out.println("DELETE FROM INTERVENTION;");
        }  
        catch (IOException iox)
        {
            LOG.error("Error creatinig script file'"+fName+".  IO Error:  " + iox.toString());
            System.exit(1);
        }
    }
    
    public void add(Intervention inter, List<InterventionAlternateName> ianList, String user) {
        if (PAUtil.isEmpty(inter.getName())) {
            LOG.error("Tried to create an intervention with no name.  ");
            System.exit(1);
        }
        if (inter.getTypeCode() == null) {
            LOG.error("Tried to create an intervention with no type code.  ");
            System.exit(1);
        }

        StringBuffer one = new StringBuffer("INSERT INTO INTERVENTION (IDENTIFIER,NAME,TYPE_CODE");
        StringBuffer two = new StringBuffer(" VALUES (" + ++iSqn + ",'" + fix(inter.getName()) + "','" + inter.getTypeCode() + "'");
        if(PAUtil.isNotEmpty(inter.getDescriptionText())) {
            one.append(",DESCRIPTION_TEXT");
            two.append(",'" + fix(inter.getDescriptionText(), Intervention.DESCRIPTION_TEXT_LENGTH) + "'");
        }
        if(PAUtil.isNotEmpty(inter.getPdqTermIdentifier())) {
            one.append(",PDQ_TERM_IDENTIFIER");
            two.append(",'" + inter.getPdqTermIdentifier() + "'");
        }
        if(PAUtil.isNotEmpty(inter.getPdqTermIdentifier())) {
            one.append(",NT_TERM_IDENTIFIER");
            two.append(",'" + inter.getNtTermIdentifier() + "'");
        }
        one.append(",STATUS_CODE,STATUS_DATE_RANGE_LOW,USER_LAST_CREATED)");
        two.append(",'ACTIVE',now(),'" + user + "');");
        
        out.println(one.toString() + two.toString());
        if (!ianList.isEmpty()) {
            for (InterventionAlternateName ian : ianList) {
                alternateNameAdd(ian, iSqn);
            }
        }
    }
    
    private void alternateNameAdd(InterventionAlternateName ian, Integer intvId) {
        StringBuffer one = new StringBuffer("INSERT INTO INTERVENTION_ALTERNATE_NAME (NAME,INTERVENTION_IDENTIFIER,STATUS_CODE,STATUS_DATE_RANGE_LOW) ");
        StringBuffer two = new StringBuffer(" VALUES ('" + fix(ian.getName()) + "'," + intvId + ",'ACTIVE',now());");
        out.println(one.toString() + two.toString());
    }
    
    public void close(String dateString) {
        out.println("UPDATE INTERVENTION SET DATE_LAST_CREATED = '" + dateString + "';");
        out.println("UPDATE INTERVENTION_ALTERNATE_NAME IA SET USER_LAST_CREATED = (SELECT USER_LAST_CREATED FROM INTERVENTION II WHERE II.IDENTIFIER = IA.INTERVENTION_IDENTIFIER);");
        out.println("UPDATE INTERVENTION_ALTERNATE_NAME SET DATE_LAST_CREATED = '" + dateString + "';");
        out.close();
    }
}
