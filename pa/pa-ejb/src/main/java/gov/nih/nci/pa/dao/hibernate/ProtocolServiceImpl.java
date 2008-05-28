package gov.nih.nci.pa.dao.hibernate;

import gov.nih.nci.pa.service.IProtocolService;

/**
 * @author Harsha
 */
public class ProtocolServiceImpl implements IProtocolService {

    /**
     * @param id object id
     * @return long title
     */
    public String getProtocolLongTitleText(long id) {
      return "A Phase I study of Taxol in refractory leukemia in children";
    }
}