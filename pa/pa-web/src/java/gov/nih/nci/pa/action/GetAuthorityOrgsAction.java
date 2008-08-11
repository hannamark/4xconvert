package gov.nih.nci.pa.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.NoStudyProtocolFoundException;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class for getting a list of authority orgs for a selected country.
 *
 * @author Harsha
 * @since 08/05/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 *
 */
public class GetAuthorityOrgsAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private String lst = null;
    private List<RegulatoryAuthOrgDTO> regIdAuthOrgList = null;
    private List<String> lstList = null;
    private String trialOversgtAuthCountry = null;
    private String selectedAuthOrg = null;

    /**
     * @return String success or failure
     */
    public String execute() {
        try {
            regIdAuthOrgList = PaRegistry.getRegulatoryInformationService()
                    .getRegulatoryAuthorityNameId(Long.valueOf(getLst()));
            StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute(Constants.TRIAL_SUMMARY);
            RegulatoryAuthOrgDTO regulatoryAuthOrgDTO = PaRegistry.getRegulatoryInformationService()
                    .getRegulatoryAuthOrgForEdit(
                    spDTO.getStudyProtocolId());
            setSelectedAuthOrg(Long.valueOf(regulatoryAuthOrgDTO.getId()).toString());
        } catch (NoStudyProtocolFoundException nspfe) {
            return SUCCESS;

        } catch (PAException e) {
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * @return the lst
     */
    public String getLst() {
        return lst;
    }

    /**
     * @param lst
     *            the lst to set
     */
    public void setLst(String lst) {
        this.lst = lst;
    }

    /**
     * @return the lstList
     */
    public List<String> getLstList() {
        return lstList;
    }

    /**
     * @param lstList
     *            the lstList to set
     */
    public void setLstList(List<String> lstList) {
        this.lstList = lstList;
    }

    /**
     * @return the trialOversgtAuthCountry
     */
    public String getTrialOversgtAuthCountry() {
        return trialOversgtAuthCountry;
    }

    /**
     * @param trialOversgtAuthCountry
     *            the trialOversgtAuthCountry to set
     */
    public void setTrialOversgtAuthCountry(String trialOversgtAuthCountry) {
        this.trialOversgtAuthCountry = trialOversgtAuthCountry;
    }

    /**
     * @return the regIdAuthOrgList
     */
    public List<RegulatoryAuthOrgDTO> getRegIdAuthOrgList() {
        return regIdAuthOrgList;
    }

    /**
     * @param regIdAuthOrgList
     *            the regIdAuthOrgList to set
     */
    public void setRegIdAuthOrgList(List<RegulatoryAuthOrgDTO> regIdAuthOrgList) {
        this.regIdAuthOrgList = regIdAuthOrgList;
    }

    /**
     * @return the selectedAuthOrg
     */
    public String getSelectedAuthOrg() {
        return selectedAuthOrg;
    }

    /**
     * @param selectedAuthOrg
     *            the selectedAuthOrg to set
     */
    public void setSelectedAuthOrg(String selectedAuthOrg) {
        this.selectedAuthOrg = selectedAuthOrg;
    }
}
