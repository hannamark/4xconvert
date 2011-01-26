/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Property File reader which can be extended for passing in the run time properties for PA.
 *
 * @author Harsha
 * @since 09/14/2008
 *
 */
public class PaEarPropertyReader {
    private static final Logger LOG = Logger.getLogger(PaEarPropertyReader.class);
    private static final String RESOURCE_NAME = "paear.properties";
    private static Properties props = null;
    private static String docUploadPath = "doc.upload.path";
    private static String pdqUploadPath = "pdq.upload.path";
    private static String accrualBatchUploadPath = "accrual.batch.upload.path";
    private static String lookUpServer = "po.server.name";
    private static String lookUpPort = "po.port.number";
    private static String csmSubmitterGroup = "csm.submitter.group";
    private static String allowedUploadFileTypes = "allowed.uploadfile.types";
    private static String batchUploadPath = "batch.upload.path";
    private static String tooltipsPath = "tooltips.path";
    private static String rssUser = "cteprss.user";
    private static final String INVALID_DIRECTORY_ERROR_MSG = " is not a valid directory.";

    static {
        try {
            props = new Properties();
            props.load(PaEarPropertyReader.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        } catch (Exception e) {
            LOG.error("Unable to read paear.properties ", e);
            throw new IllegalStateException(e);
        }
    }

    /**
    *
    * @return tooltip folder path
    * @throws PAException e
    */
    public static String getTooltipsPath() throws PAException {
      String tooltipsFolderPath = props.getProperty(tooltipsPath);
      if (tooltipsFolderPath == null) {
          throw new PAException("tooltips.path does not have value in paear.properties");
      }
      File f = new File(tooltipsFolderPath);
      if (!f.isDirectory()) {
          throw new PAException(tooltipsFolderPath + INVALID_DIRECTORY_ERROR_MSG);
      }
      return tooltipsFolderPath;
    }

    /**
     *
     * @return folder path
     * @throws PAException e
     */
    public static String getDocUploadPath() throws PAException {
        String folderPath = props.getProperty(docUploadPath);
        if (folderPath == null) {
            throw new PAException("doc.upload.path does not have value in paear.properties");
        }
        File f = new File(folderPath);
        if (!f.isDirectory()) {
            throw new PAException(folderPath + INVALID_DIRECTORY_ERROR_MSG);
        }
        return folderPath;
    }

    /**
     *
     * @return folder path
     * @throws PAException e
     */
   public static String getPDQUploadPath() throws PAException {
       String folderPath = props.getProperty(pdqUploadPath);
       if (folderPath == null) {
           throw new PAException("pdq.upload.path does not have value in paear.properties");
       }
       File f = new File(folderPath);
       if (!f.isDirectory()) {
           throw new PAException(folderPath + INVALID_DIRECTORY_ERROR_MSG);
       }
       return folderPath;
   }

    /**
     *
     * @return batch upload docs folder path
     * @throws PAException e
     */
    public static String getBatchUploadPath() throws PAException {
        String folderPath = props.getProperty(batchUploadPath);
        if (folderPath == null) {
            throw new PAException("batch.upload.path does not have value in paear.properties");
        }
        File f = new File(folderPath);
        if (!f.isDirectory()) {
            throw new PAException(folderPath + INVALID_DIRECTORY_ERROR_MSG);
        }
        return folderPath;
    }

    /**
     * @return accrual batch upload docs folder path
     * @throws PAException e
     */
    public static String getAccrualBatchUploadPath() throws PAException {
        String folderPath = props.getProperty(accrualBatchUploadPath);
        if (folderPath == null) {
            throw new PAException("accrual.batch.upload.path does not have value in paear.properties");
        }
        File f = new File(folderPath);
        if (!f.isDirectory()) {
            throw new PAException(folderPath + INVALID_DIRECTORY_ERROR_MSG);
        }
        return folderPath;
    }

    /**
     *
     * @return String for the server name
     * @throws PAException on error
     */
    public static String getLookUpServerInfo() throws PAException {
        String server = props.getProperty(lookUpServer);
        if (server == null) {
            throw new PAException("'server' does not have value in paear.properties");
        }
        String port = props.getProperty(lookUpPort);
        if (port == null) {
            throw new PAException("'port' does not have value in paear.properties");
        }
        return server + ":" + port;
    }

    /**
     *
     * @return String for the CSM Trial Submitter Group
     * @throws PAException on error
     */
    public static String getCSMSubmitterGroup() throws PAException {
        String submitterGroupName = props.getProperty(csmSubmitterGroup);
        if (submitterGroupName == null) {
            throw new PAException("'submitterGroupName' does not have value in paear.properties");
        }
        return submitterGroupName;
    }

    /**
     * @return a comma separated String of allowed upload file types
     * @throws PAException on error
     */
    public static String getAllowedUploadFileTypes() throws PAException {
        String allowedFileTypes = props.getProperty(allowedUploadFileTypes);
        if (allowedFileTypes == null) {
            throw new PAException("'allowedUploadFileTypes' does not have value in paear.properties ");
        }
        return allowedFileTypes;
    }

    /**
     * Returns the ctep rss user, required owner of collaborative trials.
     * @return the name of the user
     * @throws PAException if the property is missing
     */
    public static String getRssUser() throws PAException {
        String user = props.getProperty(rssUser);
        if (user == null) {
            throw new PAException(rssUser + " does not have value in paear.properties ");
        }
        return user;
    }

    /**
     * @return the properties
     */
    public static Properties getProperties() {
        return props;
    }

}
