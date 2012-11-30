package gov.nih.nci.pa.service.util;

import java.io.IOException;

import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;

/**
 * @author Denis G. Krylov
 */
@Local
public interface CTGovUploadServiceLocal {

    /**
     * Runs nightly FTP upload of trials to CT.Gov.
     * 
     * @throws PAException
     *             PAException
     * @throws IOException  IOException
     */
    void uploadToCTGov() throws PAException, IOException;
}
