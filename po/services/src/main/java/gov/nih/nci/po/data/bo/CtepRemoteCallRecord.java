package gov.nih.nci.po.data.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Logging PO interactions with CTEP ECM Remote EJB API.
 * 
 * @author dkrylov
 */

@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Table(name = "ctepremotecallsrecord")
public class CtepRemoteCallRecord implements gov.nih.nci.po.data.bo.Entity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date createdDate;
    private String operation;
    private String arguments;
    private String result;

    /**
     * @return the id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the createdDate
     */
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the operation
     */
    @Column(name = "operation")
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation
     *            the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the arguments
     */
    @Column(name = "arguments")
    public String getArguments() {
        return arguments;
    }

    /**
     * @param arguments
     *            the arguments to set
     */
    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    /**
     * @return the result
     */
    @Column(name = "result")
    public String getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

}
