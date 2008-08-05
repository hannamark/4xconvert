package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * Country Reg Authority depicting the DTO. 
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class CountryRegAuthorityDTO implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        private long id;
        private String alpha2;
        private String alpha3;
        private String name;
        private String numeric;

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
         * @return the alpha2
         */
        public String getAlpha2() {
                return alpha2;
        }
        /**
         * @param alpha2 the alpha2 to set
         */
        public void setAlpha2(String alpha2) {
                this.alpha2 = alpha2;
        }
        /**
         * @return the alpha3
         */
        public String getAlpha3() {
                return alpha3;
        }
        /**
         * @param alpha3 the alpha3 to set
         */
        public void setAlpha3(String alpha3) {
                this.alpha3 = alpha3;
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
        /**
         * @return the numeric
         */
        public String getNumeric() {
                return numeric;
        }
        /**
         * @param numeric the numeric to set
         */
        public void setNumeric(String numeric) {
                this.numeric = numeric;
        }
}
