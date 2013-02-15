package gov.nih.nci.registry.rest.domain;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 * @since Feb 11, 2013
 */
@XmlRootElement(name = "studyProtocol")
@XmlAccessorType(XmlAccessType.PROPERTY)
public final class StudyProtocolXmlLean implements Comparable<StudyProtocolXmlLean> {
    private static final Logger LOG = Logger.getLogger(StudyProtocolXmlLean.class);

    private String nciIdentifier;
    private String officialTitle;
    private String phaseName;
    private String leadOrganizationName;
    private String piFullName;
    private DocumentWorkflowStatusCode documentWorkflowStatusCode;
    private String nctNumber;

    /**
     * Generate a list of StudyProtocolXmlLean from search result list.
     * @param searchResultDtoList the search results
     * @return the list
     */
    public static List<StudyProtocolXmlLean> getList(List<StudyProtocolQueryDTO> searchResultDtoList) {
        List<StudyProtocolXmlLean> result = new ArrayList<StudyProtocolXmlLean>();
        if (searchResultDtoList != null) {
            for (StudyProtocolQueryDTO item : searchResultDtoList) {
                if (StringUtils.isNotEmpty(item.getNciIdentifier())) {
                    result.add(new StudyProtocolXmlLean(item));
                }
            }
        }
        return result;
    }

    private StudyProtocolXmlLean() {
        // required for to prevent JAXBMarshalException: IllegalAnnotationsException
    }

    /**
     * @param searchResultDto the dto from results service
     */
    private StudyProtocolXmlLean(StudyProtocolQueryDTO searchResultDto) {
        try {
            BeanUtils.copyProperties(this, searchResultDto);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    /**
     * @return the nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    /**
     * @param nciIdentifier the nciIdentifier to set
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }
    /**
     * @return the officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    /**
     * @return the leadOrganizationName
     */
    public String getLeadOrganizationName() {
        return leadOrganizationName;
    }
    /**
     * @param leadOrganizationName the leadOrganizationName to set
     */
    public void setLeadOrganizationName(String leadOrganizationName) {
        this.leadOrganizationName = leadOrganizationName;
    }
    /**
     * @return the piFullName
     */
    public String getPiFullName() {
        return piFullName;
    }
    /**
     * @param piFullName the piFullName to set
     */
    public void setPiFullName(String piFullName) {
        this.piFullName = piFullName;
    }
    /**
     * @return the nctNumber
     */
    public String getNctNumber() {
        return nctNumber;
    }
    /**
     * @param nctNumber the nctNumber to set
     */
    public void setNctNumber(String nctNumber) {
        this.nctNumber = nctNumber;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(StudyProtocolXmlLean that) {
        return nciIdentifier.compareTo(that.nciIdentifier);
    }
    /**
     * @return the documentWorkflowStatusCode
     */
    public DocumentWorkflowStatusCode getDocumentWorkflowStatusCode() {
        return documentWorkflowStatusCode;
    }
    /**
     * @param documentWorkflowStatusCode the documentWorkflowStatusCode to set
     */
    public void setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        this.documentWorkflowStatusCode = documentWorkflowStatusCode;
    }
    /**
     * @return the phaseName
     */
    public String getPhaseName() {
        return phaseName;
    }
    /**
     * @param phaseName the phaseName to set
     */
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
}
