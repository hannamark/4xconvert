package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyResourcingConverter;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyResourcingTest {

    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }    
    /**
     * 
     */
    //@Test
    public void createStudyResourcingTest() {
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        StudyResourcing create = createStudyResourcingObj(sp);
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        Session session  = TestSchema.getSession();
        StudyResourcing saved = (StudyResourcing) session.load(StudyResourcing.class, create.getId());
        
        assertEquals("DateLastUpdated does not match " , create.getDateLastUpdated(), saved.getDateLastUpdated());
        assertEquals("Id does not match " , create.getId(),  saved.getId());
        assertEquals("Organization Id does not match " , create.getOrganizationIdentifier(),  saved.getOrganizationIdentifier());
        assertEquals("Resource Id does not match " , create.getResourceProviderIdentifier(),  saved.getResourceProviderIdentifier());
        assertEquals("Study Protocol  Id does not match " , create.getStudyProtocol().getId(),  saved.getStudyProtocol().getId());
        assertEquals("Summary4ReportedResourceIndicator does not match " , create.getSummary4ReportedResourceIndicator(),  saved.getSummary4ReportedResourceIndicator());
        assertEquals("TypeCode does not match " , create.getTypeCode().getCode(),  saved.getTypeCode().getCode());
        
        

    }
    /**
     * 
     */
    @Test
    public void summary4Funding(){
        StudyResourcingDTO studyResourcingDTO = null;
        Session session = TestSchema.getSession();
        StudyResourcing studyResourcing = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {

            Query query = null;
            
            // step 1: form the hql
            String hql = "select sr "
                       + "from StudyResourcing sr "
                       + "where sr.summary4ReportedResourceIndicator =  '" + Boolean.TRUE + "'";

            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
        }  catch (HibernateException hbe) {
            hbe.printStackTrace();
        }
        
        if (queryList.size() > 0) {
            studyResourcing = queryList.get(0);
            studyResourcingDTO = StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);
            
        }
        
    }
    
    
    
    public static StudyResourcing createStudyResourcingObj(StudyProtocol sp){
        StudyResourcing sr = new StudyResourcing();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sr.setDateLastUpdated(now);
        sr.setOrganizationIdentifier("po-001");
        sr.setResourceProviderIdentifier("rp-001");
        sr.setStudyProtocol(sp);
        sr.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        sr.setTypeCode(SummaryFourFundingCategoryCode.INDUSTRIAL);
        sr.setUserLastUpdated("test");
        
        return sr;
    }


}
