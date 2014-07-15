package gov.nih.nci.po.data.bo;

import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface Aliasable {

    /**
     *
     * @return A list of aliases for the object.
     */
    List<Alias> getAlias();

}
