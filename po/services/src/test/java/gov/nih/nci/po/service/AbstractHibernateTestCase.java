/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.service;

import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Index;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

/**
 * @author Scott Miller
 *
 */
public abstract class AbstractHibernateTestCase {

    private static Configuration cfg;
    private static SchemaExport se;
    protected static SimpleNamingContextBuilder contextBuilder = new SimpleNamingContextBuilder();
    static {
        try {
            contextBuilder.activate();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    protected Transaction transaction;


    /**
     * In JUnit3x you would normally override the tearDown() and add your own functionality locally however, in JUnit4
     * to override simply define your method and give it the <code>@After annotation</code>. Doing so will cause that
     * method to be invoked after the parent class's tearDown().
     */
    @After
    public final void tearDown() {
        try {
            transaction.commit();
        } catch (Exception e) {
            PoHibernateUtil.getHibernateHelper().rollbackTransaction(transaction);
        }
    }

    @Before
    @SuppressWarnings("unchecked")
    final public void initDbIfNeeded() throws HibernateException, SQLException {
        setUpTest();
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
        
        transaction = PoHibernateUtil.getHibernateHelper().beginTransaction();
        
        loadData();
    }
    
    protected void loadData() {
        // NOOPD
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

    ServiceLocator oldLocator = null;

   
    protected void setUpTest() {
        oldLocator = PoRegistry.getInstance().getServiceLocator();
        PoRegistry.getInstance().setServiceLocator(new TestServiceLocator());
    }

    @After
    public void tearDownTest() {
        PoRegistry.getInstance().setServiceLocator(oldLocator);
    }
}