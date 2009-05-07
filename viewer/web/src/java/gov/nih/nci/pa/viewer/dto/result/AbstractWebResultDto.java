/**
 *
 */
package gov.nih.nci.pa.viewer.dto.result;

import gov.nih.nci.pa.viewer.dto.AbstractWebDto;

import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 05/06/2009
 *
 * @param <SERVICEDTO>  corresponding service iso dto
 */
public abstract class AbstractWebResultDto<SERVICEDTO> extends AbstractWebDto<SERVICEDTO> {
    abstract List<Object> getWebList(List<SERVICEDTO> serviceDtoList);
}
