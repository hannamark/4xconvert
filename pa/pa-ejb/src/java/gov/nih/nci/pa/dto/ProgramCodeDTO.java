package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * Will represent a ProgramCode
 */
public class ProgramCodeDTO implements Serializable {
    private static final long serialVersionUID = 235503630643839412L;
    private Long id;
    private String programCode;
    private String programName;
    private boolean active;

    /**
     * Gets the id
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id
     * @param id - the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the programCode
     * @return - the programCode
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * Sets the programCode
     * @param programCode the programCode
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    /**
     * Gets the programName
     * @return the programName
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets the programName
     * @param programName the programName
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * Gets active status
     * @return  the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status
     * @param active  the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
