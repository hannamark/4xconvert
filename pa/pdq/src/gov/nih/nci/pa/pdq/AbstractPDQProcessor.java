package gov.nih.nci.pa.pdq;

import org.w3c.dom.Document;

/**
 * @author hreinhart
 *
 */
public abstract class AbstractPDQProcessor {
    protected Document doc;
    protected Rule rule;
    protected String user;
    abstract void process(Document doc, Rule rule, String user);
}
