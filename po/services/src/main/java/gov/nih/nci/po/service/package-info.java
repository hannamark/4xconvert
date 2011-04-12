/**
 * Provides the EJB local interfaces and beans to manage the Business Objects managed by the PO system.  The local bean 
 * are called by the UI actions and remote services to support CRUD operations on the Business Objects.
 *
 * <h2>Implementation hierarchy</h2>
 * Different abstract bean implementations are provided to support the different categories of Business Objects.  
 * <ul>
 * <li>{@link AbstractBaseServiceBean} - provides a search and validate API.  Most (if not all) bean implementations 
 * inherit from this class.  
 * <li>{@link AbstractAdminServiceBean} - extends AbstractBaseServiceBean - used by UI and Remote Services - provides 
 * a persistence (create/update) API for Business Objects that are neither curated nor processed via change requests 
 * (CRs).
 * <li>{@link AbstractCRServiceBean} - extends AbstractBaseServiceBean - used by Remote Services - provides a 
 * persistence API to the remote services for change requests (CRs).
 * <li>{@link AbstractCuratableServiceBean} - extends AbstractBaseServiceBean - used by UI and Remote Services - 
 * provides a persistence API for Business Objects that are curated, as well as additional curation-relevant search 
 * APIs.  Announcements are published for each call to persist a curatable Business Object. 
 * <li>{@link AbstractCuratableEntityServiceBean} - extends AbstractCuratableServiceBean - used by UI and Remote 
 * Services - provides additional persistence/search APIs for a Persons and Organizations. 
 * </ul>
 *
 * @since 3.5
 */
package gov.nih.nci.po.service;