package gov.nih.nci.po.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.Organization;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;


public class CGLIBUtilsTest {
    @Test
    @SuppressWarnings("unchecked")
    public void unEnhanceCBLIBClass() throws ClassNotFoundException {
        Object enhanced = enhance(Organization.class);
        Class<? extends PersistentObject> unEnhanceCBLIBClass = CGLIBUtils
                .unEnhanceCBLIBClass((Class<? extends PersistentObject>) enhanced.getClass());

        assertEquals(Organization.class.getName(), unEnhanceCBLIBClass.getName());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void unEnhanceClass() throws ClassNotFoundException {
        Object enhanced = new Organization();
        Class<? extends PersistentObject> unEnhanceCBLIBClass = CGLIBUtils
                .unEnhanceCBLIBClass((Class<? extends PersistentObject>) enhanced.getClass());

        assertEquals(Organization.class.getName(), unEnhanceCBLIBClass.getName());
    }

    private Object enhance(Class<?> class1) {
        Object create = Enhancer.create(class1, new Class[] {}, new MethodInterceptor() {

            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                Object retValFromSuper = null;
                try {
                    if (!Modifier.isAbstract(method.getModifiers())) {
                        retValFromSuper = proxy.invokeSuper(obj, args);
                    }
                } finally {

                }
                return retValFromSuper;
            }
        });
        return create;
    }
}
