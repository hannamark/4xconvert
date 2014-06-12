package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Index;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class DbTestUtil {

    protected static SimpleNamingContextBuilder contextBuilder = new SimpleNamingContextBuilder();

    static {
        try {
            contextBuilder.activate();
        } catch (Exception e) {
            throw new Error(e);
        }
    }



    public static final DbTestUtil getInstance() {
        return new DbTestUtil();
    }

    private SchemaExport se;
    private Configuration cfg;
    private Transaction transaction;

    private ServiceLocator oldLocator;

    public void setup() {
        try {
            oldLocator = PoRegistry.getInstance().getServiceLocator();
            PoRegistry.getInstance().setServiceLocator(new TestServiceLocator());

            initDb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        transaction = PoHibernateUtil.getHibernateHelper().beginTransaction();


    }

    public void tearDown() {
        PoHibernateUtil.getHibernateHelper().rollbackTransaction(transaction);
        transaction = null;
        PoRegistry.getInstance().setServiceLocator(oldLocator);
    }


    private void initDb() throws SQLException {
        // First drop the audit sequence (& associated table, see
        // http://opensource.atlassian.com/projects/hibernate/browse/HHH-2472)
        Transaction tx = PoHibernateUtil.getHibernateHelper().beginTransaction();
        Statement s = PoHibernateUtil.getCurrentSession().connection().createStatement();
        try {
            s.execute("drop sequence AUDIT_ID_SEQ");
            s.execute("drop table if exists dual_AUDIT_ID_SEQ");
        } catch (SQLException e) {
            // expected
        }
        tx.commit();

        tx = PoHibernateUtil.getHibernateHelper().beginTransaction();
        List<Long> counts = null;
        try {
            counts = PoHibernateUtil.getCurrentSession().createQuery(
                    "select count(*) from " + Object.class.getName()).list();
        } catch (Exception e) {
            // counts unable to get, assume that this was because the schema has never been created before
            SchemaExport se = getSchemaExporter();
            se.create(false, true);
            counts = new ArrayList<Long>();
        }

        // create sequence and fake table for selecting from
        s = PoHibernateUtil.getCurrentSession().connection().createStatement();
        s.execute("create sequence AUDIT_ID_SEQ");
        s.execute("create table dual_AUDIT_ID_SEQ(test boolean)");
        tx.commit();
        for (Long count : counts) {
            if (count > 0) {
                SchemaExport se = getSchemaExporter();
                se.drop(false, true);
                se.create(false, true);
                //new ConfigurationBeanLocal().reloadCountries();
                break;
            }
        }
    }

    private SchemaExport getSchemaExporter() {
        if (se == null) {
            se = new SchemaExport(getConfigurationForSchemaExport());
        }
        return se;
    }

    private Configuration getConfigurationForSchemaExport() {
        if (cfg == null) {
            cfg = PoHibernateUtil.getHibernateHelper().getConfiguration();
            handleAutoNamingIndexes(cfg);
        }
        return cfg;
    }

    private void handleAutoNamingIndexes(Configuration configuration) {
        @SuppressWarnings("unchecked")
        Iterator<Table> iter = configuration.getTableMappings();
        while (iter.hasNext()) {
            Table table = iter.next();
            processTableIndexNames(table);
        }
    }

    private void processTableIndexNames(Table table) {
        if (table.isPhysicalTable()) {
            @SuppressWarnings("unchecked")
            Iterator<Index> subIter = table.getIndexIterator();
            while (subIter.hasNext()) {
                // for each index that has no name, generate a unique name
                Index index = subIter.next();
                if (index.getName().startsWith(PoRegistry.GENERATE_INDEX_NAME_PREFIX)) {
                    index.setName(generateIndexName(table, index));
                }
            }
        }
    }

    private String generateIndexName(Table table, Index index) {
        List<String> columnNames = new ArrayList<String>();
        @SuppressWarnings("unchecked")
        Iterator<Column> columns = index.getColumnIterator();
        while (columns.hasNext()) {
            columnNames.add(columns.next().getName());
        }
        return "IX" + table.uniqueColumnString(columnNames.iterator());
    }
}
