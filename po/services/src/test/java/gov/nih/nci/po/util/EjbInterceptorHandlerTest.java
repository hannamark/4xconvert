package gov.nih.nci.po.util;

import java.util.ArrayList;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gax
 */
public class EjbInterceptorHandlerTest {

    private static ArrayList<String> callOrder = new ArrayList<String>();
    @Before
    public void reset(){
        callOrder.clear();
    }

    @Test
    public void testOrder() throws Throwable {
        ChildBean b = new ChildBean();
        EjbInterceptorHandler h = new EjbInterceptorHandler(b, InterD.class);
        h.invoke(null, ChildBean.class.getMethod("doSomethingChildish"), null);

        Object[] expected = {
                "InterD.interceptD1", //default interceptor
                "InterX.interceptX1", "InterA.interceptA1", "InterA.interceptX2", // for InterA
                "InterX.interceptX1", "InterX.interceptX2", "InterB.interceptB1", // for InterB
                "InterX.interceptX1", "InterX.interceptX2", "InterC.interceptC1", // for InterC
                "ParentBean.interceptParentBean", "ChildBean.interceptChildBean", // interceptor methods on the bean.
                "ChildBean.doSomethingChildish"}; // bean does work


        Assert.assertArrayEquals(expected, callOrder.toArray());


    }

    @Interceptors({InterA.class, InterB.class})
    public static class ParentBean {
        @AroundInvoke
        public Object interceptParentBean(InvocationContext ctx) throws Exception {
            callOrder.add("ParentBean.interceptParentBean");
            return ctx.proceed();
        }
    }

    @Interceptors({InterC.class})
    public static class ChildBean extends ParentBean {
        public void doSomethingChildish() {
            EjbInterceptorHandlerTest.callOrder.add("ChildBean.doSomethingChildish");
        }
        @AroundInvoke
        public Object interceptChildBean(InvocationContext ctx) throws Exception {
            callOrder.add("ChildBean.interceptChildBean");
            return ctx.proceed();
        }
    }

    public static class InterX {
        @AroundInvoke
        public Object interceptX1(InvocationContext ctx) throws Exception {
            callOrder.add("InterX.interceptX1");
            return ctx.proceed();
        }

        @AroundInvoke
        public Object interceptX2(InvocationContext ctx) throws Exception {
            callOrder.add("InterX.interceptX2");
            return ctx.proceed();
        }
    }

    public static class InterA extends InterX {
        @AroundInvoke
        public Object interceptA1(InvocationContext ctx) throws Exception {
            callOrder.add("InterA.interceptA1");
            return ctx.proceed();
        }

        @Override
        @AroundInvoke
        public Object interceptX2(InvocationContext ctx) throws Exception {
            callOrder.add("InterA.interceptX2");
            return ctx.proceed();
        }
    }

    public static class InterB extends InterX {
        @AroundInvoke
        public Object interceptB1(InvocationContext ctx) throws Exception {
            callOrder.add("InterB.interceptB1");
            return ctx.proceed();
        }
    }

    public static class InterC extends InterX {
        @AroundInvoke
        public Object interceptC1(InvocationContext ctx) throws Exception {
            callOrder.add("InterC.interceptC1");
            return ctx.proceed();
        }
    }

    public static class InterD {
        @AroundInvoke
        public Object interceptD1(InvocationContext ctx) throws Exception {
            callOrder.add("InterD.interceptD1");
            return ctx.proceed();
        }
    }

}
