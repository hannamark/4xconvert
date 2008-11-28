package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Kalpana Guthikonda
 * @since 11/22/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class AbstractionCompletionAction extends ActionSupport {
   
  private List<AbstractionCompletionDTO> abstractionList = null;
  
  /**
   * @return result
   */
  public String query() {

    LOG.info("Entering query");
    try {
      Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
      getAttribute(Constants.STUDY_PROTOCOL_II);
      
      PoPaServiceBeanLookup.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
          IiConverter.convertToLong(studyProtocolIi));

      abstractionList = new ArrayList<AbstractionCompletionDTO>();
      abstractionList = PaRegistry.getAbstractionCompletionService().validateAbstractionCompletion(studyProtocolIi);
    } catch (Exception e) {
      addActionError(e.getLocalizedMessage());
    }
    return SUCCESS;
  }

  /**
   * @return abstractionList
   */
  public List<AbstractionCompletionDTO> getAbstractionList() {
    return abstractionList;
  }

  /**
   * @param abstractionList abstractionList
   */
  public void setAbstractionList(List<AbstractionCompletionDTO> abstractionList) {
    this.abstractionList = abstractionList;
  }
}
