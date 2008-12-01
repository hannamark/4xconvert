package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class DiseaseAlternameTest {
    private static Session sess;
    
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        sess = TestSchema.getSession();
    }
    @Test
    public void getTest() {
        Disease toe = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(0));
        List<DiseaseAltername> daList = toe.getDiseaseAlternames();
        assertTrue(daList.size() > 0);
        Long id = daList.get(0).getId();
        
        DiseaseAltername toeAn = (DiseaseAltername) sess.get(DiseaseAltername.class, id);
        assertEquals(daList.get(0), toeAn);
    }
    @Test
    public void deleteTest() {
        Disease toe = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(0));
        List<DiseaseAltername> daList = toe.getDiseaseAlternames();
        int oldSize = daList.size();
        assertTrue(oldSize > 0);
        sess.delete(toe.getDiseaseAlternames().get(0));
        sess.flush();
        sess.refresh(toe);
        assertEquals(oldSize - 1, toe.getDiseaseAlternames().size());
    }
    @Test
    public void mergeTest() {
        Disease toe = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(0));
        List<DiseaseAltername> daList = toe.getDiseaseAlternames();
        int oldSize = daList.size();
        sess.merge(createDiseaseAlternameObj("Digit Cancer", toe));
        sess.flush();
        sess.refresh(toe);
        assertEquals(oldSize + 1, toe.getDiseaseAlternames().size());
    }
    
    public static DiseaseAltername createDiseaseAlternameObj(String alternateName, Disease disease) {
        DiseaseAltername create = new DiseaseAltername();
        create.setAlternateName(alternateName);
        create.setDisease(disease);
        create.setStatusCode(ActiveInactiveCode.ACTIVE);
        create.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        create.setUserLastCreated("userLastCreated");
        create.setDateLastCreated(new Timestamp(new Date().getTime()));
        create.setUserLastUpdated("userLastUpdated");
        create.setDateLastUpdated(new Timestamp(new Date().getTime()));
        return create;
    }
}
