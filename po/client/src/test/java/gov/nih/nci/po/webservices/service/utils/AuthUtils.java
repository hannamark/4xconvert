package gov.nih.nci.po.webservices.service.utils;

import sun.net.www.protocol.http.AuthCacheImpl;
import sun.net.www.protocol.http.AuthCacheValue;

import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class AuthUtils {

    public static final void addWsSecurityUTSupport(Binding binding, String username, String password) {
        List<Handler> handlers = new ArrayList<Handler>();

        handlers.add(new WsSecuritySOAPHandler(username, password));

        binding.setHandlerChain(handlers);
    }

    public static final void addBasicAuthSupport(final String username, final String password) {
        removeBasicAuthSupport();
        java.net.Authenticator.setDefault(new java.net.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        username,
                        password.toCharArray()
                );
            }
        });
    }


    public static final void removeBasicAuthSupport() {
        AuthCacheValue.setAuthCache(new AuthCacheImpl());
        java.net.Authenticator.setDefault(null);
    }


}
