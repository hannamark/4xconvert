package gov.nih.nci.po.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Remote;
import javax.interceptor.AroundInvoke;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;

import com.fiveamsolutions.nci.commons.ejb.AuthorizationInterceptor;

/**
 * Build a proxy to a Session EJB, that will invoke declared interceptors.
 * TODO
 *  javax.ejb.ExcludeDefaultInterceptors not honored.
 *  javax.ejb.ExcludeClassInterceptors not honored
 * @author gax
 */
public class EjbInterceptorHandler implements InvocationHandler {

    private static final HashMap<Class<?>, Pair<Method[], Object[]>> CACHE =
        new HashMap<Class<?>, Pair<Method[], Object[]>>();

    private final Class<?>[] defaultInterceptors;
    private final Object bean;


    public EjbInterceptorHandler(Object bean, Class<?>... defaultInterceptors) {
        this.bean = bean;
        this.defaultInterceptors = defaultInterceptors;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method declaredMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (declaredMethod.getAnnotation(ExcludeDefaultInterceptors.class) != null) { throw new UnsupportedOperationException("TODO"); }
        if (declaredMethod.getAnnotation(ExcludeClassInterceptors.class) != null) { throw new UnsupportedOperationException("TODO"); }


        Pair<Method[], Object[]> entry = getChain(bean.getClass());
        DefaultContext ctx = new DefaultContext(entry.a, entry.b, bean, method, args);
        try {
            return ctx.proceed();
        } catch (Throwable ex) {
            if (ctx.error != null) { throw ctx.error; }
            throw ex;
        }
    }

    private Pair<Method[], Object[]> getChain(Class<?> beanClass) throws InstantiationException, IllegalAccessException {
        Pair<Method[], Object[]> entry = CACHE.get(beanClass);
        if (entry == null) {
            ArrayList<Class<?>> ics = new ArrayList<Class<?>>();
            ics.addAll(Arrays.asList(defaultInterceptors));
            getInterceptorClasses(ics, beanClass);
            ArrayList<Method> chain = new ArrayList<Method>();
            ArrayList<Object> ints = new ArrayList<Object>();
            for (Class<?> ic : ics) {
                Method[] ims = getInterceptorMethods(ic);
                for (Method m : ims) {
                    chain.add(m);
                    Object i = ic.newInstance();
                    if (i instanceof AuthorizationInterceptor) {
                        AuthorizationInterceptor ai = (AuthorizationInterceptor) i;
                        ai.setSessionContext(new TestSessionContext());
                    }
                    ints.add(i);
                }
            }

            // for interceptor methods on the bean itself.
            Method[] ims = getInterceptorMethods(beanClass);
            for (Method m : ims) {
                chain.add(m);
                ints.add(null);
            }
            entry = new Pair<Method[], Object[]>();
            entry.a = chain.toArray(new Method[chain.size()]);
            entry.b = ints.toArray();
            CACHE.put(beanClass, entry);
        }
        return entry;
    }

    private static void getInterceptorClasses(ArrayList<Class<?>> list, Class<?> clazz) {
        if (clazz == Object.class) { return; }
        if (clazz.getAnnotation(ExcludeDefaultInterceptors.class) != null) { throw new UnsupportedOperationException("TODO"); }
        if (clazz.getAnnotation(ExcludeClassInterceptors.class) != null) { throw new UnsupportedOperationException("TODO"); }

        getInterceptorClasses(list, clazz.getSuperclass());
        Class<?>[] cs = getDeclaredInterceptorClasses(clazz);
        if (cs == null || cs.length == 0) { return; }
        list.addAll(Arrays.asList(cs));
    }

    private static Class<?>[] getDeclaredInterceptorClasses(Class<?> clazz) {
        Interceptors i = clazz.getAnnotation(Interceptors.class);
        if (i == null) {
            return null;
        }
        return i.value();
    }

    private static Method[] getInterceptorMethods(Class<?> interceptorClass) {
        Method[] all = interceptorClass.getMethods();
        ArrayList<Method> l = new ArrayList<Method>();
        for(Method m: all) {
            AroundInvoke a = m.getAnnotation(AroundInvoke.class);
            if (a != null) {
                l.add(m);
            }
        }

        all = l.toArray(new Method[l.size()]);

        Arrays.sort(all, INTERCEPTOR_METHOD_COMPARATOR);
        return all;
    }

    public static Object makeInterceptorProxy(Object bean) {
        Class<?>[] remoteInterfaces = ReflectionUtil.getMarkedInterfaces(bean.getClass(), Remote.class);
        if (remoteInterfaces != null && remoteInterfaces.length > 0) {
            EjbInterceptorHandler interceptorHandler = new EjbInterceptorHandler(bean);
            Object remotebean = Proxy.newProxyInstance(bean.getClass().getClassLoader(), remoteInterfaces, interceptorHandler);
            return remotebean;
        }
        throw new Error(bean.getClass() + "has no remote interface");
    }

    private static class DefaultContext implements InvocationContext {

        private final Method[] chain;
        private final Object[] interceptors;
        private int index = 0;

        private final Object target;
        private Object[] parameters;
        private final Method method;

        Throwable error;

        public DefaultContext(Method[] chain, Object[] interceptors, Object target, Method method, Object[] parameters) {
            this.chain = chain;
            this.interceptors = interceptors;
            this.target = target;
            this.parameters = parameters;
            this.method = method;
        }



        public Map<String, Object> getContextData() {
            return null;
        }

        public Method getMethod() {
            return method;
        }

        public Object[] getParameters() {
            return parameters;
        }

        public Object getTarget() {
            return target;
        }

        public void setParameters(Object[] arg0) {
            this.parameters = arg0;
        }

        public Object proceed() throws Exception {
            try{
                if(index == chain.length) {
                    try{
                        return method.invoke(target, parameters);
                    }catch(InvocationTargetException ex){
                        error = ex.getCause(); // make bean exception available unwrapped.
                        throw ex;
                    }
                }
                int i = index++;
                Object invocationTarget = interceptors[i];
                // for interceptor methods on the bean itself.
                if (invocationTarget == null) {
                    invocationTarget = target;
                }
                assert invocationTarget != null : "invocationTarget is "+invocationTarget;
                return chain[i].invoke(invocationTarget, this);
            } catch (Exception e){
                throw e;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }



        @Override
        public Object getTimer() {            
            return null;
        }
    }

    private static final Comparator<Method> INTERCEPTOR_METHOD_COMPARATOR = new Comparator<Method>() {

            public int compare(Method o1, Method o2) {
                Class<?> c1 = o1.getDeclaringClass();
                Class<?> c2 = o2.getDeclaringClass();
                if (c1 == c2) {
                    // sub-order by name for some predicability.
                    return o1.getName().compareTo(o2.getName());
                } else if ( o1.getDeclaringClass().isAssignableFrom(o2.getDeclaringClass())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        };

    private static class Pair <A, B> {
        public A a;
        public B b;
    }
}
