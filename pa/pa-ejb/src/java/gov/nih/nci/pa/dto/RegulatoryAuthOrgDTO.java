package gov.nih.nci.pa.dto;

/**
 * DTO object.
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class RegulatoryAuthOrgDTO {

        private long id;
        private String name;
        /**
         * @return the id
         */
        public long getId() {
                return id;
        }
        /**
         * @param id the id to set
         */
        public void setId(long id) {
                this.id = id;
        }
        /**
         * @return the name
         */
        public String getName() {
                return name;
        }
        /**
         * @param name the name to set
         */
        public void setName(String name) {
                this.name = name;
        }
        
}
