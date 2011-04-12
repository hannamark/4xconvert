/**
 * Provides the classes necessary to represent and persist Business Objects managed by the PO system.
 * The classes are decorated with Hibernate annotation for persistence and caching. They may also be decorated with
 * XSnapshot xdoclet tags for conversion to/from DTOs.
 *
 * <h2>Relationship with DTO Layer</h2>
 * These classes are the <em>internal</em> representation and are not exposed by the remote interfaces.  Users who are
 * familiar with the COPPA Information Model should understand that there are important differences between that model
 * and the classes here.  In particular:
 * <ul>
 * <li>These classes do not use ISO data types directly.
 * <li>Fields on the ISO data types may be split into multiple fields, renamed, or otherwise transformed.
 * <li>Relationships between objects are not simply via II, but rather by true database foreign keys.
 * <li>NullFlavor is not represented directly at this layer.
 * </ul>
 *
 * Generally speaking, users of the remote interfaces should first look at the DTO layer and the <code>convert</code>
 * sub-package before looking at these classes.
 *
 * <h2>Change Requests</h2>
 * Also in this package are classes that end in <code>CR</code>.  CR stands for <b>C</b>change <b>R</b>equest.  Change
 * Requests are the internal mechanism that implement the pessimistic change model.  Change Requests have parallel
 * fields to their non-CR brethren, along with a link to the associated object.  The <code>ChangeRequest</code>
 * interface is implemented by these classes.  Change Requests have no corresponding DTO object, nor are they ever
 * exposed directly at the remote interface layer.
 *
 * @since 1.0
 */
package gov.nih.nci.po.data.bo;
