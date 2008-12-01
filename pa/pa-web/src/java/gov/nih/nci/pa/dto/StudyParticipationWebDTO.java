/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;

/**
 * @author Hong Gao
 *
 */
public class StudyParticipationWebDTO {

        private String id;
        private String localProtocolIdentifier;
        private String summary4FundingSponsor;

        /**
         * @param iso StudyResourcingDTO object
         */
        public StudyParticipationWebDTO(StudyParticipationDTO iso) {
            super();
            this.localProtocolIdentifier = iso.getLocalStudyProtocolIdentifier().getValue();
            this.id = iso.getIdentifier().getExtension();
        }

        /** .
         *  Default Constructor
         */
        public StudyParticipationWebDTO() {
            super();
        }

        /**
         * @return fundingMechanismCode
         */
        public String getLocalProtocolIdentifier() {
            return localProtocolIdentifier;
        }

        /**
         * @param localProtocolIdentifier localProtocolIdentifier
         */
        public void setLocalProtocolIdentifier(String localProtocolIdentifier) {
            this.localProtocolIdentifier = localProtocolIdentifier;
        }

        /**
         * @return id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the summary4FundingSponsor
         */
        public String getSummary4FundingSponsor() {
            return summary4FundingSponsor;
        }

        /**
         * @param summary4FundingSponsor the summary4FundingSponsor to set
         */
        public void setSummary4FundingSponsor(String summary4FundingSponsor) {
            this.summary4FundingSponsor = summary4FundingSponsor;
        }

    }
