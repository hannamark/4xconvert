
package gov.nih.nci.po.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author gax
 */
public class ReflectionUtil {

    private ReflectionUtil() {
    }

    /**
     *  Get All interfaces that are marked by an annotation.
     * @param bean the class to inspect
     * @param marker the class level annotation.
     * @return all interfaces maked with the marker.
     */
    public static Class<?>[] getMarkedInterfaces(Class<?> bean, Class<? extends Annotation> marker) {
        ArrayList<Class<?>> interfaces = new ArrayList<Class<?>>();
        Class<?>[] ifrs = bean.getInterfaces();
        for (Class<?> i: ifrs) {
            if (hasMarker(i, marker)) {
                interfaces.add(i);
            }
        }
        return interfaces.toArray(new Class[interfaces.size()]);
    }

    public static boolean hasMarker(Class<?> aclass, Class<? extends Annotation> marker) {
        return aclass.getAnnotation(marker) != null;
    }

    public static Field[] getMarkedFields(Class<?> aclass, Class<? extends Annotation> marker) {
        HashSet<Field> allFields = getAllFields(aclass, null);
        ArrayList<Field> markedFields = new ArrayList<Field>();
        for (Field f: allFields) {
            if (f.getAnnotation(marker) != null) {
                markedFields.add(f);
            }
        }
        return markedFields.toArray(new Field[markedFields.size()]);
    }

    private static HashSet<Field> getAllFields(Class<?> aClass, HashSet<Field> fields) {
        if (fields == null) { fields = new HashSet<Field>(); }
        if (aClass == Object.class) { return fields; }
        for (Field f: aClass.getDeclaredFields()) {
            if ((f.getModifiers() & Modifier.STATIC) == 0) {
                fields.add(f);
            }
        }
        return getAllFields(aClass.getSuperclass(), fields);
    }
}
