package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Sponsor Monitor code.
 * database is mapped to protocol.intent_code
 * @author Naveen Amiruddin
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum MonitorCode implements CodedEnum<String> {

    /**
     * .
     */
     CCR("CCR"), 
     /**
      * .
      */
     CTEP("CTEP") , 
     /**
      * .
      */
     DCB("DCB") ,
     /**
      * .
      */
     DCCPS("DCCPS") , 
     /**
      * .
      */
     DCEG("DCEG") ,
     /**
      * .
      */
     DTP("DTP") ,
     /**
      * .
      */
     DCP("DCP") ,
     /**
      * .
      */
     OD("OD") ,
     /**
      * .
      */
     OSB_SPOREs("OSB/SPOREs") ,
     /**
      * .
      */
     CIP("CIP") ,
     /**
      * .
      */
     CDP("CDP") ,
     /**
      * .
      */
     TRP("TRP") ,
     /**
      * .
      */
     RRP("RRP") ,     
     /**
      * .
      */
     DEA("DEA"),
     /**
      * .
      */
     NA("N/A");

     private String code;

     /**
      * Constructor for SponsorMonitorCode.
      * @param code
      */

     private MonitorCode(String code) {
         this.code = code;
         register(this);
     }

     /**
      * @return code coded value of enum
      */
     public String getCode() {
         return code;
     }


     /**
      *@return String DisplayName 
      */
     public String getDisplayName() {
         return sentenceCasedName(this);
     }

     /**
      * @return String display name
      */
     public String getName() {
         return name();
     }


     /**
      * @param code code
      * @return Study Type Code 
      */
     public static MonitorCode getByCode(String code) {
         return getByClassAndCode(MonitorCode.class, code);
     }
     
     /**
      * @return String[] display names of enums
      */
     public static String[]  getDisplayNames() {
         MonitorCode[] l = MonitorCode.values();
         String[] a = new String[l.length];
         for (int i = 0; i < l.length; i++) {
             a[i] = l[i].getCode();
         }
         return a;
     }     
}


