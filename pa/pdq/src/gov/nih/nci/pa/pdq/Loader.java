package gov.nih.nci.pa.pdq;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author hreinhart
 *
 */
public class Loader {
    private static final Logger LOG = Logger.getLogger(Loader.class);
    private final static String DIRECTORY_NAME = "D:/dev/coppa/code/pa/pdq/Term_20081010";
    private final static String DATA_DUMP_DATE = "2008-10-10";

    /**
     * @param args
     */
    public static void main(String[] args) throws PDQException {
        ArrayList<String> invalid = new ArrayList<String>();
        SortedMap<Rule, Integer> counts = new TreeMap<Rule, Integer>();
        for (Rule r : Rule.values()) {
            counts.put(r, 0);
        }
        LOG.info("Starting load of PDQ data...");

        File dir = new File(DIRECTORY_NAME);

        String[] children = dir.list();
        if (children == null) {
            throw new PDQException("Either dir " + DIRECTORY_NAME + " does not exist or is not a directory.  ");
        } else {
            PDQIntervention pi = new PDQIntervention();
            PDQDisease pd = new PDQDisease();
            for (int i=0; i<children.length; i++) {
                // Get filename of file or directory
                boolean ok = true;
                String filename = children[i];
                Document doc = XMLFileParser.getParser().parseFile(DIRECTORY_NAME + "/"+ filename);
                try {
                    Rule rule = Interpret.getInterpreter().process(doc);
//                    LOG.info("file = " + filename + "  rule = " + rule);
                    counts.put(rule, counts.get(rule) + 1);
                    if (rule.equals(Rule.INVALID)) {
                        invalid.add(filename);
                    } else {
                        if (rule.clazz.toString().equals(PDQIntervention.class.toString())) {
                            pi.process(doc, rule, filename);
                        }
                        if (rule.clazz.toString().equals(PDQDisease.class.toString())) {
                            pd.process(doc, rule, filename);
                        }
                    }
                } catch (PDQException e) {
                    LOG.error(e);
                    ok = false;
                }
                if (!ok) {
                    Node root = doc.getDocumentElement();
                    XMLFileParser.getParser().writeDocumentToOutput(root, 0);
                    System.exit(0);
                }
            }
            InterventionScript.get().close(DATA_DUMP_DATE);
            DiseaseScript.get().close(DATA_DUMP_DATE);

            LOG.info("file count = " + Integer.valueOf(children.length));
            LOG.info(counts);
        }
        LOG.info("Done load of PDQ data...");
//        LOG.info("Got the following invalid files.");
//        for (String s : invalid) {
//            System.out.println(s);
//        }
    }

}
