/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nci.coppa.po.grid.ser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.xml.namespace.QName;
import org.apache.axis.encoding.SerializationContext;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import javax.xml.rpc.NamespaceConstants;

/**
 *
 * @author gax
 */
public class Filter implements ContentHandler {
    private SerializationContext context;

    private Stack<Map<String, String>> prefixes = new Stack<Map<String, String>>();
    private Map<String, String> head = new HashMap<String, String>();

    public Filter(SerializationContext context) {
        this.context = context;
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        head.put(prefix, uri);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        head.remove(prefix);
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        prefixes.push(head);
        head = new HashMap<String, String>();
        
        AttributesImpl fixed = new AttributesImpl();
        for (int i = 0; i <atts.getLength(); i++) {
            String au = atts.getURI(i);
            String av = atts.getValue(i);
            String aln = atts.getLocalName(i);
            if (NamespaceConstants.NSURI_SCHEMA_XSI.equals(au) && "type".equals(aln)) {
                int idx = av.indexOf(':');
                String p = av.substring(0, idx);
                String q = av.substring(av.indexOf(':') + 1);
                String ns = getNS(p);
                String axisPrefix = context.getPrefixForURI(ns);
                av = axisPrefix + ':' + q;

            }
            fixed.addAttribute(au, aln, atts.getQName(i), atts.getType(i), av);
        }
        
        try {
            context.startElement(new QName(uri, localName), fixed);
        } catch (IOException ioe) {
            throw new SAXException(ioe);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        head = prefixes.pop();
        try {
            context.endElement();
        } catch (IOException ioe) {
            throw new SAXException(ioe);
        }
    }

    private String getNS(String prefix) {
        String ns = null;
        for(int i = prefixes.size() - 1; i >= 0 && ns == null; i--) {
            ns = prefixes.get(i).get(prefix);
        }
        return ns;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            context.writeChars(ch, start, length);
        } catch (IOException ioe) {
            throw new SAXException(ioe);
        }
    }

    public void setDocumentLocator(Locator locator) {
        //
    }

    public void startDocument() throws SAXException {
        //
    }

    public void endDocument() throws SAXException {
        //
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        //
    }

    public void processingInstruction(String target, String data) throws SAXException {
        //
    }

    public void skippedEntity(String name) throws SAXException {
        //
    }

}
