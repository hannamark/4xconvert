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

package gov.nih.nci.accrual.web.action;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.zip.DataFormatException;

import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.web.dto.util.PerformanceStatusWebDto;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.util.PAUtil;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * @author Hugh Reinhart
 * @since Nov 5, 2009
 */
public class PerformanceStatusAction extends AbstractEditAccrualAction<Object> {

    private static final long serialVersionUID = -2561560856212211656L;

    private PerformanceStatusWebDto performance = new PerformanceStatusWebDto();
    
    private static final String ECOG = "ECOG";
    private static final String LANSKY = "Lansky";
    private static final String KARNOFSKY = "Karnofsky";

    /**
     * {@inheritDoc}
     */
    @Override
    public Epoch getEpoch() {
        return Epoch.PRE_TREATMENT;
    }
    
    /**
     * Had to break logic into small enough pieces to keep PMD happy.
     * 
     * @author lhebel
     */
    private class LoadHelper {
        private List<PerformedObservationDto> pd = null;
        private PerformedObservationDto po = null;
        private Cd method = null;
        private Cd rCode = null;
        private List<PerformedClinicalResultDto> cr = null;

        /**
         * Default constructor.
         */
        public LoadHelper() {
            // default constructor
        }

        /**
         * Setup the Observation list.
         * 
         * @throws ExecutionException determines whether to ask for input or flag success
         */
        public void getAllPo() throws ExecutionException {
            try {
                pd = performedActivitySvc.getPerformedObservationByStudySubject(getParticipantIi());
            } catch (RemoteException ex) {
                LOG.error(ex.getLocalizedMessage(), ex);
                addActionError("Service error. " + ex.getLocalizedMessage());
                throw new ExecutionException(INPUT, ex);
            }
        }

        /**
         * Set the Observation object.
         * 
         * @throws ExecutionException determines whether to ask for input or flag success
         */
        public void setupPo() throws ExecutionException {
            // Ignore everything that isn't the Performance Status
            for (PerformedObservationDto item : pd) {
                if (!item.getNameCode().getCode().equals(ActivityNameCode.PERFORMANCE_STATUS.getCode())) {
                    continue;
                }
                po = item;
                break;
            }
            if (po == null) {
                throw new ExecutionException("", null);
            }

            // Remember this Id should we need to update the status.
            performance.setId(po.getIdentifier());
        }
        
        /**
         * Get the Performance Status type. Either ECOG, Lansky or Karnofsky.
         * 
         * @throws ExecutionException determines whether to ask for input or flag success
         */
        public void getStatusType() throws ExecutionException {
            // Resolve the Status type
            List<Cd> cds =  DSetConverter.convertDsetToCdList(po.getMethodCode());
            if (cds != null) {
                for (Cd cd : cds) {
                    method = cd;
                    break;
                }
            }
            if (method == null) {
                addActionError("The Performance Method is missing.");
                throw new ExecutionException(INPUT, null);
            }
        }
        
        /**
         * Set the Clinical Result.
         * 
         * @throws ExecutionException determines whether to ask for input or flag success
         */
        public void setupCr() throws ExecutionException {
            // Get the Status value
            try {
                cr = performedObservationResultSvc.getPerformedClinicalResultByPerformedActivity(po.getIdentifier());
            } catch (RemoteException ex) {
                addActionError("Failed to retrieve " + method.getCode() + " Performance Status Code.");
                throw new ExecutionException(INPUT, ex);
            }
        }
        
        /**
         * Get the Performance Status value.
         * 
         * @throws ExecutionException determines whether to ask for input or flag success
         */
        public void getStatusValue() throws ExecutionException {
            for (PerformedClinicalResultDto result : cr) {
                rCode = result.getResultCode();
                break;
            }
            if (rCode == null) {
                addActionError("The Performance Status Code is missing.");
                throw new ExecutionException(INPUT, null);
            }
        }
        
        /**
         * Set the JSP value.
         * 
         * @throws ExecutionException determines whether to ask for input or flag success
         */
        public void moveDtoToWeb() throws ExecutionException {
            // Set the display appropriately.
            if (ECOG.equals(method.getCode())) {
                performance.setEcogStatus(rCode);
            } else if (LANSKY.equals(method.getCode())) {
                performance.setLanskyStatus(rCode);
            } else if (KARNOFSKY.equals(method.getCode())) {
                performance.setKarnofskyStatus(rCode);
            } else {
                addActionError("A Performance Method other than ECOG, Lansky or Karnofsky has been recorded. "
                        + "Can not display the current value.");
                throw new ExecutionException(INPUT, null);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        // Clear the web object
        performance = new PerformanceStatusWebDto();

        // Had to move logic to keep PMD happy.
        LoadHelper helper = new LoadHelper();
        try {
            helper.getAllPo();
            helper.setupPo();
            helper.getStatusType();
            helper.setupCr();
            helper.getStatusValue();
            helper.moveDtoToWeb();
        } catch (ExecutionException ex) {
            if (INPUT.equals(ex.getMessage())) {
                return INPUT;
            }
        }
        return super.execute();
    }

    /**
     * Helper class for save processing.
     * 
     * @author lhebel
     */
    private class SaveHelper {
        private PerformedObservationDto po = null;
        private PerformedClinicalResultDto cr = null;
        private boolean poUpdFlag = false;
        private boolean crUpdFlag = false;

        /**
         * Default constructor.
         */
        public SaveHelper() {
            // Default constructor
        }

        /**
         * Setup the Performed Observation object.
         * 
         * @throws RemoteException passed up from service
         */
        public void setupPo() throws RemoteException {
            if (PAUtil.isIiNull(performance.getId())) {
                // The Performance Status didn't exist when we started but time has passed and it may now...
                List<PerformedObservationDto> pd =
                    performedActivitySvc.getPerformedObservationByStudySubject(getParticipantIi());
                for (PerformedObservationDto item : pd) {

                    // Ignore everything that isn't the Performance Status
                    if (!item.getNameCode().getCode().equals(ActivityNameCode.PERFORMANCE_STATUS.getCode())) {
                        continue;
                    }

                    // Found one, someone else is working on this same Participant, sorry last one wins...
                    po = item;
                    poUpdFlag = true;
                    break;
                }
            } else {
                // The Performance Status was found when the JSP was first open
                po = performedActivitySvc.getPerformedObservation(performance.getId());
                poUpdFlag = true;
            }
            if (po == null) {
                // Creating new database entries because they don't exist yet
                po = new PerformedObservationDto();
                poUpdFlag = false;
            }
        }

        /**
         * Setup the Clinical Result object.
         * 
         * @throws RemoteException passed up from the service
         */
        public void setupCr() throws RemoteException {
            if (poUpdFlag) {
                // There is an existing Performance Status, find the Clinical Result holding the value.
                List<PerformedClinicalResultDto> pr =
                    performedObservationResultSvc.getPerformedClinicalResultByPerformedActivity(po.getIdentifier());
                for (PerformedClinicalResultDto result : pr) {
                    // There can be only 1.
                    cr = result;
                    crUpdFlag = true;
                    break;
                }
            }
            if (cr == null) {
                // No existing value so create it.
                cr = new PerformedClinicalResultDto();
                crUpdFlag = false;
            }
        }

        /**
         * Persist the Performed Observation object.
         * @throws RemoteException passed up from the service
         * @throws DataFormatException passed up from the service
         */
        public void persistPo() throws RemoteException, DataFormatException {
            // Update or create the Observation first.
            if (poUpdFlag) {
                performedActivitySvc.updatePerformedObservation(po);
            } else {
                po.setStudySubjectIdentifier(getParticipantIi());
                po.setStudyProtocolIdentifier(getSpIi());
                po = performedActivitySvc.createPerformedObservation(po);
            }
        }

        /**
         * Persist the Clinical Result object.
         * @throws RemoteException passed up from the service
         * @throws DataFormatException passed up from the service
         */
        public void persistCr() throws RemoteException, DataFormatException {
            // Update or create the Clinical Result last.
            if (crUpdFlag) {
                performedObservationResultSvc.updatePerformedClinicalResult(cr);
            } else {
                cr.setPerformedObservationIdentifier(po.getIdentifier());
                cr.setStudyProtocolIdentifier(getSpIi());
                performedObservationResultSvc.createPerformedClinicalResult(cr);
            }
        }
        
        /**
         * Always set the values to avoid any possible error in persistence. Persist the
         * nameCode, methodCode and resultCode.
         */
        public void moveWebToDto() {
            po.setNameCode(CdConverter.convertStringToCd(ActivityNameCode.PERFORMANCE_STATUS.getCode()));
            List<Cd> cds = new ArrayList<Cd>();
            String type = null;

            if (!PAUtil.isCdNull(performance.getEcogStatus())) {
                type = ECOG;
                cr.setResultCode(performance.getEcogStatus());
            } else if (!PAUtil.isCdNull(performance.getLanskyStatus())) {
                type = LANSKY;
                cr.setResultCode(performance.getLanskyStatus());
            } else {
                type = KARNOFSKY;
                cr.setResultCode(performance.getKarnofskyStatus());
            }
            cds.add(CdConverter.convertStringToCd(type));
            po.setMethodCode(DSetConverter.convertCdListToDSet(cds));
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public String save() {
        SaveHelper helper = new SaveHelper();

        try {
            // Setup the Observation and Result objects as needed
            helper.setupPo();
            helper.setupCr();

            // Always set the values to avoid any possible error in persistence. Persist the
            // nameCode, methodCode and resultCode
            helper.moveWebToDto();

            // Persist to the database
            helper.persistPo();
            helper.persistCr();

        } catch (RemoteException ex) {
            LOG.error(ex.toString(), ex);
            addActionError("Error in save().  " + ex.getLocalizedMessage());
            return INPUT;
        } catch (DataFormatException ex) {
            LOG.error(ex.toString(), ex);
            addActionError("Error in save().  " + ex.getLocalizedMessage());
            return INPUT;
        }

        return super.save();
    }

    /**
     * @param performance the performance to set
     */
    public void setPerformance(PerformanceStatusWebDto performance) {
        this.performance = performance;
    }

    /**
     * @return the performance
     */
    @VisitorFieldValidator(message = "> ")
    public PerformanceStatusWebDto getPerformance() {
        return performance;
    }
}
