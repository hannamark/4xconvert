package gov.nih.nci.coppa.po.grid.dto.transform;

/**
 * 
 * @author Ekagra Software Technologies
 * 
 * @param <T>
 * @param <S>
 */
public interface Transformer<T, S> {
    public S transform(T input) throws DtoTransformException;

    public S transform(T input, S res) throws DtoTransformException;

    /**
     * cannot do this public T transform(S input) throws DtoTransformException; public S transform(S input,T res) throws
     * DtoTransformException;
     */

}
