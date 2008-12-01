package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import gov.nih.nci.po.data.bo.Person;

import org.junit.Before;
import org.junit.Test;

public class PageSortParamsTest {

    PageSortParams<Person> params;

    @Before
    public void init() {
        params = new PageSortParams<Person>(1, 2, PersonSortCriterion.PERSON_FULLNAME.getOrderByList(), true);
        assertEquals(1, params.getPageSize());
        assertEquals(2, params.getIndex());
        assertEquals(PersonSortCriterion.PERSON_FULLNAME.getOrderByList(), params.getSortCriterion());
        assertEquals(true, params.isDesc());
    }

    @Test
    public void testConstructor() {
        params = new PageSortParams<Person>(1, 2, PersonSortCriterion.PERSON_FIRSTNAME, true);
        assertEquals(1, params.getPageSize());
        assertEquals(2, params.getIndex());
        assertEquals(Collections.singletonList(PersonSortCriterion.PERSON_FIRSTNAME), params.getSortCriterion());
        assertEquals(true, params.isDesc());
    }

    @Test
    public void testConstructor2() {
        params = new PageSortParams<Person>(1, 2, (SortCriterion<Person>) null, true);
        assertEquals(1, params.getPageSize());
        assertEquals(2, params.getIndex());
        assertEquals(null, params.getSortCriterion());
        assertEquals(true, params.isDesc());
    }

    @Test
    public void testConstructor3() {
        params = new PageSortParams<Person>(1, 2, PersonSortCriterion.PERSON_FULLNAME.getOrderByList(), true);
        assertEquals(1, params.getPageSize());
        assertEquals(2, params.getIndex());
        assertEquals(PersonSortCriterion.PERSON_FULLNAME.getOrderByList(), params.getSortCriterion());
        assertEquals(true, params.isDesc());
    }

    @Test
    public void testSetPageSize() {
        params.setPageSize(10);
        assertEquals(10, params.getPageSize());
    }

    @Test
    public void testSetIndex() {
        params.setIndex(10);
        assertEquals(10, params.getIndex());
    }

    @Test
    public void testSetSortCriterion() {
        params.setSortCriterion(PersonSortCriterion.PERSON_FIRSTNAME.getOrderByList());
        assertEquals(PersonSortCriterion.PERSON_FIRSTNAME.getOrderByList(), params.getSortCriterion());
    }

    @Test
    public void testSetDesc() {
        params.setDesc(true);
        assertEquals(true, params.isDesc());
    }

}
