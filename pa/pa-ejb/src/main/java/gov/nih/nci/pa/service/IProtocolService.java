package gov.nih.nci.pa.service;

/**
 * @author Harsha
 *
 */
public interface IProtocolService {
    /**
     * @param id object id
     * @return Long title of protocol
     */
    String getProtocolLongTitleText(long id);
}
