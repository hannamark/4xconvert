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
 * Logging PO outgoing JMS messages.
 * 
 * @author dkrylov
 */

@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Table(name = "jms_messages")
public class JMSLogRecord implements gov.nih.nci.po.data.bo.Entity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String msg;
    private Date createdDate;

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
     * @return the msg
     */
    @Column(name = "msg")
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     *            the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
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

}
