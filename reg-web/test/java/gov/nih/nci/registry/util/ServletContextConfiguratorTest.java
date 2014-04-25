package gov.nih.nci.registry.util;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

import org.junit.Test;

public class ServletContextConfiguratorTest {

    @Test
    public void testContext() {
        ServletContextConfigurator conf = new ServletContextConfigurator();
        ServletContextEvent event = new ServletContextEvent(new MockContext());
        conf.contextInitialized(event);
        assertEquals("/registry", event.getServletContext().getAttribute("staticPath"));
        
        conf.contextDestroyed(event);
        //test the do nothing function does nothing.
        assertNotNull(event);
    }
    
    private class MockContext implements ServletContext {
        HashMap<String, Object> attribute = new HashMap<String, Object>();

        @Override
        public Object getAttribute(String key) {
            return attribute.get(key);
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return (new Vector<String>(attribute.keySet())).elements();
        }

        @Override
        public ServletContext getContext(String arg0) {
            return this;
        }

        @Override
        public String getInitParameter(String arg0) {
            return null;
        }

        @Override
        public Enumeration getInitParameterNames() {
            return null;
        }

        @Override
        public int getMajorVersion() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public String getMimeType(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int getMinorVersion() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public RequestDispatcher getNamedDispatcher(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getRealPath(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public URL getResource(String arg0) throws MalformedURLException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public InputStream getResourceAsStream(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Set getResourcePaths(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getServerInfo() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Servlet getServlet(String arg0) throws ServletException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getServletContextName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Enumeration getServletNames() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Enumeration getServlets() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void log(String arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void log(Exception arg0, String arg1) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void log(String arg0, Throwable arg1) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void removeAttribute(String key) {
            attribute.remove(key);
            
        }

        @Override
        public void setAttribute(String key, Object value) {
            attribute.put(key, value);
        }
        
    }
}
