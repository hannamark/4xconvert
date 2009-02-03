package gov.nih.nci.coppa.po.grid.dto.transform;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartQualifier;

public class ObjectTransformer {
    private static Logger logger = LogManager.getLogger(ObjectTransformer.class);
    protected static Set<String> ignorableProperties = new HashSet<String>();
    protected static Set<Class> simpleClasses = new HashSet<Class>();
    static {
        simpleClasses.add(String.class);
        simpleClasses.add(Boolean.class);
        simpleClasses.add(Integer.class);
        ignorableProperties.add("class");
    }

    public <S, T> Collection<T> transformArray(S[] input, Collection<T> res, Class<T> clazz)
            throws DtoTransformException {
        try {
            if (input == null)
                return null;
            if (res == null)
                return res;
            for (S s : input) {
                T res_2 = (T) ConstructorUtils.invokeConstructor(clazz, new Object[] {});
                res_2 = (T) transform(s, res_2);
                res.add(res_2);
            }
        } catch (Exception e) {
            logger.error("Error transforming array.", e);
            throw new DtoTransformException("Error transforming array.", e);
        }
        return res;

    }

    public Object transform(Object input, Object res) throws DtoTransformException {
        if (input == null)
            return null;

        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(input);
        try {
            for (PropertyDescriptor pd : pds) {
                PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(res, pd.getName());
                Object inputValue = PropertyUtils.getProperty(input, pd.getName());
                if (inputValue == null) {
                    ;
                } else if (ignorableProperties.contains(pd.getName())) {
                    ;
                } else if ((simpleClasses.contains(pd.getPropertyType())) || pd.getPropertyType().isPrimitive()) {
                    BeanUtils.copyProperty(res, pd.getName(), BeanUtils.getProperty(input, pd.getName()));
                } else if (pd.getPropertyType().isEnum()) {
                    Enum en = (Enum) inputValue;
                    if (en != null) {
                        String value = en.toString();
                        Object newVal = ConstructorUtils.invokeConstructor(propertyDescriptor.getPropertyType(),
                                new String[] { value });
                        BeanUtils.setProperty(res, pd.getName(), newVal);
                    }
                } else if (propertyDescriptor.getPropertyType().isEnum()) {
                    String value = inputValue.toString();
                    Object res_2 = Enum.valueOf((Class<Enum>) propertyDescriptor.getPropertyType(), value);
                    BeanUtils.setProperty(res, pd.getName(), res_2);
                } else if (pd.getPropertyType().isArray()) {
                    if (pd.getPropertyType().getName().contains(ENXP.class.getName())) {
                        Collection coll = (Collection) inputValue;
                        transformArray((Object[]) inputValue, coll, gov.nih.nci.coppa.iso.Enxp.class);
                    }
                    if (pd.getPropertyType().getName().contains(EntityNamePartQualifier.class.getName())) {
                        Collection coll = (Collection) PropertyUtils.getProperty(res, pd.getName());
                        if (coll == null) {
                            coll = new HashSet<gov.nih.nci.coppa.iso.EntityNamePartQualifier>();
                        }
                        transformArray((Object[]) inputValue, coll, gov.nih.nci.coppa.iso.EntityNamePartQualifier.class);
                        BeanUtils.setProperty(res, pd.getName(), coll);
                    }
                } else {
                    Object res_2 = ConstructorUtils.invokeConstructor(propertyDescriptor.getPropertyType(),
                            new Object[] {});
                    res_2 = transform(PropertyUtils.getProperty(input, pd.getName()), res_2);
                    PropertyUtils.setProperty(res, pd.getName(), res_2);
                }
            }
        } catch (Exception e) {
            logger.error("Error converting DTO.", e);
            throw new DtoTransformException("Error converting DTO.", e);
        }
        return res;
    }
}

/****
 * else if (NullFlavor.class.equals(pd.getPropertyType())){ NullFlavor nullFlavor =
 * (NullFlavor)PropertyUtils.getProperty(input, pd.getName()); if (nullFlavor !=null){ BeanUtils.setProperty(res,
 * pd.getName(), gov.nih.nci.coppa.iso.NullFlavor.valueOf(nullFlavor.getValue())); } } else if
 * (gov.nih.nci.coppa.iso.NullFlavor.class.equals(pd.getPropertyType())){ gov.nih.nci.coppa.iso.NullFlavor nf_iso =
 * (gov.nih.nci.coppa.iso.NullFlavor)PropertyUtils.getProperty(input, pd.getName()); if (nf_iso!=null){
 * BeanUtils.setProperty(res, pd.getName(), NullFlavor.fromString(nf_iso.toString())); } } else if
 * (IdentifierReliability.class.equals(pd.getPropertyType())){ IdentifierReliability idr =
 * (IdentifierReliability)PropertyUtils.getProperty(input, pd.getName()); if (idr !=null){ BeanUtils.setProperty(res,
 * pd.getName(), gov.nih.nci.coppa.iso.IdentifierReliability.valueOf(idr.getValue())); } } else if
 * (gov.nih.nci.coppa.iso.IdentifierReliability.class.equals(pd.getPropertyType())){
 * gov.nih.nci.coppa.iso.IdentifierReliability idr_iso =
 * (gov.nih.nci.coppa.iso.IdentifierReliability)PropertyUtils.getProperty(input, pd.getName()); if (idr_iso!=null){
 * BeanUtils.setProperty(res, pd.getName(), IdentifierReliability.fromString(idr_iso.toString())); } } else if
 * (IdentifierScope.class.equals(pd.getPropertyType())){ IdentifierScope is =
 * (IdentifierScope)PropertyUtils.getProperty(input, pd.getName()); if (is !=null){ BeanUtils.setProperty(res,
 * pd.getName(), gov.nih.nci.coppa.iso.IdentifierScope.valueOf(is.getValue())); } } else if
 * (gov.nih.nci.coppa.iso.IdentifierScope.class.equals(pd.getPropertyType())){ gov.nih.nci.coppa.iso.IdentifierScope
 * is_iso = (gov.nih.nci.coppa.iso.IdentifierScope)PropertyUtils.getProperty(input, pd.getName()); if (is_iso!=null){
 * BeanUtils.setProperty(res, pd.getName(), IdentifierScope.fromString(is_iso.toString())); } }
 ****/
