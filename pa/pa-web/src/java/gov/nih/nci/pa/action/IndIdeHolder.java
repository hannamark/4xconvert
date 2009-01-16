/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;

import java.io.Serializable;

/**
* @author Hong Gao
* 
*/

    
public class IndIdeHolder implements Serializable {

        private static final long serialVersionUID = 1L;
        
        private String group3; //indldeType
        private String indidenumber;
        private String subCat; //grantor
        private String holderType;
        private String group4; //expandedAccessIndicator
        private String expandedStatus; //expandedAccessType
        private String programcodenihselectedvalue;
        private String programcodenciselectedvalue;
        private String id;

        
        /**
         * @param iso StudyResourcingDTO object
         */
        public IndIdeHolder(StudyIndldeDTO iso) {
            super();
              this.expandedStatus = iso.getExpandedAccessStatusCode().getCode();
              this.subCat = iso.getGrantorCode().getCode();
              if (iso.getExpandedAccessIndicator().getValue() != null) {
                if (iso.getExpandedAccessIndicator().getValue().toString().equalsIgnoreCase("true")) {
                  this.group4 = "Yes";
                } else {
                  this.group4 = "No";
                } 
              }
              //this.group4 = iso.getExpandedAccessIndicator().getValue().toString();
              this.indidenumber = iso.getIndldeNumber().getValue();
              this.group3 = iso.getIndldeTypeCode().getCode();
              this.holderType = iso.getHolderTypeCode().getCode();
              this.programcodenihselectedvalue = iso.getNihInstHolderCode().getCode();
              this.programcodenciselectedvalue = iso.getNciDivProgHolderCode().getCode();
              this.id = iso.getIdentifier().getExtension();
        }
        
        /** .
         *  Default Constructor
         */
        public IndIdeHolder() {
            super();
        }

        /**
         * @return the group3
         */
        public String getGroup3() {
            return group3;
        }
        /**
         * @param group3 the group3 to set
         */
        public void setGroup3(String group3) {
            this.group3 = group3;
        }
        /**
         * @return the indidenumber
         */
        public String getIndidenumber() {
            return indidenumber;
        }
        /**
         * @param indidenumber the indidenumber to set
         */
        public void setIndidenumber(String indidenumber) {
            this.indidenumber = indidenumber;
        }
        /**
         * @return the subCat
         */
        public String getSubCat() {
            return subCat;
        }
        /**
         * @param subCat the subCat to set
         */
        public void setSubCat(String subCat) {
            this.subCat = subCat;
        }
        /**
         * @return the holderType
         */
        public String getHolderType() {
            return holderType;
        }
        /**
         * @param holderType the holderType to set
         */
        public void setHolderType(String holderType) {
            this.holderType = holderType;
        }
        /**
         * @return the group4
         */
        public String getGroup4() {
            return group4;
        }
        /**
         * @param group4 the group4 to set
         */
        public void setGroup4(String group4) {
            this.group4 = group4;
        }
        /**
         * @return the expandedStatus
         */
        public String getExpandedStatus() {
            return expandedStatus;
        }
        /**
         * @param expandedStatus the expandedStatus to set
         */
        public void setExpandedStatus(String expandedStatus) {
            this.expandedStatus = expandedStatus;
        }
        /**
         * @return the programcodenihselectedvalue
         */
        public String getProgramcodenihselectedvalue() {
            return programcodenihselectedvalue;
        }
        /**
         * @param programcodenihselectedvalue the programcodenihselectedvalue to set
         */
        public void setProgramcodenihselectedvalue(String programcodenihselectedvalue) {
            this.programcodenihselectedvalue = programcodenihselectedvalue;
        }
        /**
         * @return the programcodenciselectedvalue
         */
        public String getProgramcodenciselectedvalue() {
            return programcodenciselectedvalue;
        }
        /**
         * @param programcodenciselectedvalue the programcodenciselectedvalue to set
         */
        public void setProgramcodenciselectedvalue(String programcodenciselectedvalue) {
            this.programcodenciselectedvalue = programcodenciselectedvalue;
        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }
        
}