/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nci.po.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.CuratableEntity;
import gov.nih.nci.po.data.bo.CuratableRole;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PlayedRole;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.ScopedRole;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 *
 * @author gax
 */
public class RoleStatusChangeValidatorTest {

    static private Map<EntityStatus, Hashtable<RoleStatus, Boolean>> values = new Hashtable<EntityStatus, Hashtable<RoleStatus, Boolean>>();

        @BeforeClass
    public static void setUpClass() throws Exception {
        for(EntityStatus es: EntityStatus.values()) {
            values.put(es, new Hashtable<RoleStatus, Boolean>());
        }
        values.get(EntityStatus.PENDING).put(RoleStatus.PENDING, true);
        values.get(EntityStatus.PENDING).put(RoleStatus.ACTIVE, false);
        values.get(EntityStatus.PENDING).put(RoleStatus.SUSPENDED, false);
        values.get(EntityStatus.PENDING).put(RoleStatus.NULLIFIED, true);

        values.get(EntityStatus.ACTIVE).put(RoleStatus.PENDING, true);
        values.get(EntityStatus.ACTIVE).put(RoleStatus.ACTIVE, true);
        values.get(EntityStatus.ACTIVE).put(RoleStatus.SUSPENDED, true);
        values.get(EntityStatus.ACTIVE).put(RoleStatus.NULLIFIED, true);

        values.get(EntityStatus.NULLIFIED).put(RoleStatus.PENDING, false);
        values.get(EntityStatus.NULLIFIED).put(RoleStatus.ACTIVE, false);
        values.get(EntityStatus.NULLIFIED).put(RoleStatus.SUSPENDED, false);
        values.get(EntityStatus.NULLIFIED).put(RoleStatus.NULLIFIED, true);

        values.get(EntityStatus.INACTIVE).put(RoleStatus.PENDING, true);
        values.get(EntityStatus.INACTIVE).put(RoleStatus.ACTIVE, false);
        values.get(EntityStatus.INACTIVE).put(RoleStatus.SUSPENDED, true);
        values.get(EntityStatus.INACTIVE).put(RoleStatus.NULLIFIED, true);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        values = null;
    }

    /**
     * Test of isValid method, of class RoleStatusChangeValidator.
     */
    @Test
    public void testIsValid() {
        for(EntityStatus es: EntityStatus.values()) {
            for(RoleStatus rs: RoleStatus.values()) {
                testPlayer(es, rs);
                testScoper(es, rs);
            }
            testPlayer(es, null);
            testScoper(es, null);
        }
    }

    private void testPlayer(EntityStatus es, final RoleStatus rs) {
        final Organization entity = new Organization();
        entity.setStatusCode(es);
        class Played extends Base implements PlayedRole {
            private static final long serialVersionUID = 1L;
            public CuratableEntity getPlayer() {
                return entity;
            }
            public RoleStatus getStatus() {
                return rs;
            }
            public void setPlayer(CuratableEntity e) {
                
            }
        }
        RoleStatusChangeValidator v = new RoleStatusChangeValidator();
        boolean expected = (rs == null) ? true : values.get(es).get(rs).booleanValue();
        assertEquals(expected, v.isValid(new Played()));
    }

    private void testScoper(EntityStatus es, final RoleStatus rs) {
        final Organization entity = new Organization();
        entity.setStatusCode(es);
        class Scoped extends Base implements ScopedRole {
            private static final long serialVersionUID = 1L;
            public Organization getScoper() {
                return entity;
            }
            public RoleStatus getStatus() {
                return rs;
            }
            public void setScoper(Organization o) {
                
            }
        }
        RoleStatusChangeValidator v = new RoleStatusChangeValidator();
        boolean expected = (rs == null) ? true : values.get(es).get(rs).booleanValue();
        assertEquals(expected, v.isValid(new Scoped()));
    }

    @Test
    public void testPlayerNull() {
        class Played extends Base implements PlayedRole {
            private static final long serialVersionUID = 1L;
            public CuratableEntity getPlayer() {
                return null;
            }
            public RoleStatus getStatus() {
                return null;
            }
            public void setPlayer(CuratableEntity e) {
                
            }
        }
        RoleStatusChangeValidator v = new RoleStatusChangeValidator();
        assertEquals(true, v.isValid(new Played()));
    }

    @Test
    public void testScoperNull() {
        class Scoped extends Base implements ScopedRole {
            private static final long serialVersionUID = 1L;
            public Organization getScoper() {
                return null;
            }
            public RoleStatus getStatus() {
                return null;
            }
            public void setScoper(Organization o) {
                
            }
        }
        RoleStatusChangeValidator v = new RoleStatusChangeValidator();
        assertEquals(true, v.isValid(new Scoped()));
    }

    private static abstract class Base implements CuratableRole {
        private static final long serialVersionUID = 1L;

        public RoleStatus getPriorStatus() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public PersistentObject getDuplicateOf() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Set getChangeRequests() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Date getStatusDate() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setStatusDate(Date statusDate) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Long getId() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setStatus(RoleStatus status) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
