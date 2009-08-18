package gov.nih.nci.po.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.ejb.Remote;

/**
 *
 * @author gax
 */
public class RemoteBeanHandler implements InvocationHandler {

    private final Object localBean;

    public RemoteBeanHandler(Object localBean) {
        this.localBean = localBean;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object[] transportedParams = null;
        if (args != null) {
            transportedParams = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                transportedParams[i] = transport(args[i]);
            }
        }
        try{
            Object ret = method.invoke(localBean, transportedParams);
            return transport(ret);
        }catch(InvocationTargetException ite) {
            Throwable t = (Throwable) transport(ite.getTargetException());
            throw t;
        }
    }

    private Object transport(Object o) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

    public static Object makeRemoteProxy(Object bean) {
        Class<?>[] remoteInterfaces = ReflectionUtil.getMarkedInterfaces(bean.getClass(), Remote.class);
        if (remoteInterfaces != null && remoteInterfaces.length > 0) {
            RemoteBeanHandler remoteHandler = new RemoteBeanHandler(bean);
            // @todo bind name might be in annotation
            Object remotebean = Proxy.newProxyInstance(bean.getClass().getClassLoader(), remoteInterfaces, remoteHandler);
            return remotebean;
        }
        throw new Error(bean.getClass() + "has no remote interface");
    }
}
