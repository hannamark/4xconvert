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

import gov.nih.nci.accrual.service.BaseLookUpService;
import gov.nih.nci.accrual.web.dto.util.LookUpWebDto;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.domain.AnatomicSites;
import gov.nih.nci.pa.domain.AssessmentType;
import gov.nih.nci.pa.domain.DoseFrequency;
import gov.nih.nci.pa.domain.LesionLocationAnatomicSite;
import gov.nih.nci.pa.domain.ProcedureName;
import gov.nih.nci.pa.domain.RouteOfAdministration;
import gov.nih.nci.pa.domain.TumorMarker;
import gov.nih.nci.pa.domain.UnitOfMeasurement;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 * The Class LookUpAction.
 *
 * @author Kalpana Guthikonda
 * @since 11/18/2009
 */
@SuppressWarnings({"PMD" })
public class LookUpAction extends AbstractAccrualAction {

    private static final long serialVersionUID = 1L;
    private St searchText = null;
    private String type = null;
    private final List<LookUpWebDto> lookUpList = new ArrayList<LookUpWebDto>();
    private final String seacrhMsg = "Please provide some search values.";

    /**
     * {@inheritDoc}
     */
    @Override
    public Epoch getEpoch() {
        return Epoch.NO_CHANGE;
    }

    /**
     * Lookup for procedureName.
     *
     * @return the string
     * @throws RemoteException remote Exception
     */
    @SkipValidation
    public String procedureName() throws RemoteException {
        if (searchText == null) {
            searchText = new St();
            return SUCCESS;
        }

        String sTxt = searchText.getValue();
        if (sTxt == null || sTxt.length() == 0) {
            addActionError(seacrhMsg);
            return INPUT;
        }

        ProcedureName criteria = new ProcedureName();
        criteria.setCode(sTxt);
          List<ProcedureName> procedureNameList = new ArrayList<ProcedureName>();
          BaseLookUpService<ProcedureName> lookUpService =
              new BaseLookUpService<ProcedureName>(ProcedureName.class);
          procedureNameList.addAll(lookUpService.search(criteria));
          for (ProcedureName pn :  procedureNameList) {
             LookUpWebDto lookupdto = new LookUpWebDto();
             lookupdto.setCode(StConverter.convertToSt(pn.getCode()));
             lookupdto.setDescription(StConverter.convertToSt(pn.getDescription()));
             lookupdto.setDisplayName(StConverter.convertToSt(pn.getDisplayName()));
             lookupdto.setId(IiConverter.convertToIi(pn.getId()));
             lookupdto.setType(StConverter.convertToSt(type));
             lookUpList.add(lookupdto);
         }
         ServletActionContext.getRequest().setAttribute("lookUpList", lookUpList);
        return super.execute();
    }

    /**
     * Lookup for unitOfMeasurement.
     *
     * @return the string
     * @throws RemoteException remote Exception
     */
    @SkipValidation
    public String unitOfMeasurement() throws RemoteException {
        if (searchText == null) {
            searchText = new St();
            return SUCCESS;
        }

        String sTxt = searchText.getValue();
        if (sTxt == null || sTxt.length() == 0) {
            addActionError(seacrhMsg);
            return INPUT;
        }

        UnitOfMeasurement criteria = new UnitOfMeasurement();
        criteria.setCode(sTxt);
        List<UnitOfMeasurement> list = new ArrayList<UnitOfMeasurement>();
        BaseLookUpService<UnitOfMeasurement> lookUpService =
            new BaseLookUpService<UnitOfMeasurement>(UnitOfMeasurement.class);
        list.addAll(lookUpService.search(criteria));
        for (UnitOfMeasurement bean :  list) {
            LookUpWebDto lookupdto = new LookUpWebDto();
            lookupdto.setCode(StConverter.convertToSt(bean.getCode()));
            lookupdto.setDescription(StConverter.convertToSt(bean.getDescription()));
            lookupdto.setDisplayName(StConverter.convertToSt(bean.getDisplayName()));
            lookupdto.setId(IiConverter.convertToIi(bean.getId()));
            lookupdto.setType(StConverter.convertToSt(type));
            lookUpList.add(lookupdto);
        }
        ServletActionContext.getRequest().setAttribute("lookUpList", lookUpList);
        return super.execute();
    }

    /**
     * Lookup for routeOfAdministration.
     *
     * @return the string
     * @throws RemoteException remote Exception
     */
    @SkipValidation
    public String routeOfAdministration() throws RemoteException {
        if (searchText == null) {
            searchText = new St();
            return SUCCESS;
        }

        String sTxt = searchText.getValue();
        if (sTxt == null || sTxt.length() == 0) {
            addActionError(seacrhMsg);
            return INPUT;
        }

        RouteOfAdministration criteria = new RouteOfAdministration();
        criteria.setCode(sTxt);
        List<RouteOfAdministration> list = new ArrayList<RouteOfAdministration>();
        BaseLookUpService<RouteOfAdministration> lookUpService =
            new BaseLookUpService<RouteOfAdministration>(RouteOfAdministration.class);
        list.addAll(lookUpService.search(criteria));
        for (RouteOfAdministration bean :  list) {
            LookUpWebDto lookupdto = new LookUpWebDto();
            lookupdto.setCode(StConverter.convertToSt(bean.getCode()));
            lookupdto.setDescription(StConverter.convertToSt(bean.getDescription()));
            lookupdto.setDisplayName(StConverter.convertToSt(bean.getDisplayName()));
            lookupdto.setId(IiConverter.convertToIi(bean.getId()));
            lookupdto.setType(StConverter.convertToSt(type));
            lookUpList.add(lookupdto);
        }
        ServletActionContext.getRequest().setAttribute("lookUpList", lookUpList);
        return super.execute();
    }

    /**
     * Lookup for doseFrequency.
     *
     * @return the string
     * @throws RemoteException remote Exception
     */
    @SkipValidation
    public String doseFrequency() throws RemoteException {
        if (searchText == null) {
            searchText = new St();
            return SUCCESS;
        }

        String sTxt = searchText.getValue();
        if (sTxt == null || sTxt.length() == 0) {
            addActionError(seacrhMsg);
            return INPUT;
        }

        DoseFrequency criteria = new DoseFrequency();
        criteria.setCode(sTxt);
        List<DoseFrequency> list = new ArrayList<DoseFrequency>();
        BaseLookUpService<DoseFrequency> lookUpService =
            new BaseLookUpService<DoseFrequency>(DoseFrequency.class);
        list.addAll(lookUpService.search(criteria));
        for (DoseFrequency bean :  list) {
            LookUpWebDto lookupdto = new LookUpWebDto();
            lookupdto.setCode(StConverter.convertToSt(bean.getCode()));
            lookupdto.setDescription(StConverter.convertToSt(bean.getDescription()));
            lookupdto.setDisplayName(StConverter.convertToSt(bean.getDisplayName()));
            lookupdto.setId(IiConverter.convertToIi(bean.getId()));
            lookupdto.setType(StConverter.convertToSt(type));
            lookUpList.add(lookupdto);
        }
        ServletActionContext.getRequest().setAttribute("lookUpList", lookUpList);
        return super.execute();
    }

    /**
     * Lookup for anatomicSites.
     *
     * @return the string
     * @throws RemoteException remote Exception
     */
    @SkipValidation
    public String anatomicSites() throws RemoteException {
        if (searchText == null) {
            searchText = new St();
            return SUCCESS;
        }

        String sTxt = searchText.getValue();
        if (sTxt == null || sTxt.length() == 0) {
            addActionError(seacrhMsg);
            return INPUT;
        }

        AnatomicSites criteria = new AnatomicSites();
        criteria.setCode(sTxt);
        List<AnatomicSites> list = new ArrayList<AnatomicSites>();
        BaseLookUpService<AnatomicSites> lookUpService =
            new BaseLookUpService<AnatomicSites>(AnatomicSites.class);
        list.addAll(lookUpService.search(criteria));
        for (AnatomicSites bean :  list) {
            LookUpWebDto lookupdto = new LookUpWebDto();
            lookupdto.setCode(StConverter.convertToSt(bean.getCode()));
            lookupdto.setDescription(StConverter.convertToSt(bean.getDescription()));
            lookupdto.setDisplayName(StConverter.convertToSt(bean.getDisplayName()));
            lookupdto.setId(IiConverter.convertToIi(bean.getId()));
            lookupdto.setType(StConverter.convertToSt(type));
            lookUpList.add(lookupdto);
        }
        ServletActionContext.getRequest().setAttribute("lookUpList", lookUpList);
        return super.execute();
    }

    /**
     * Lookup for assessmentType.
     *
     * @return the string
     * @throws RemoteException remote Exception
     */
    @SkipValidation
    public String assessmentType() throws RemoteException {
        if (searchText == null) {
            searchText = new St();
            return SUCCESS;
        }

        String sTxt = searchText.getValue();
        if (sTxt == null || sTxt.length() == 0) {
            addActionError(seacrhMsg);
            return INPUT;
        }

        AssessmentType criteria = new AssessmentType();
        criteria.setCode(sTxt);
        List<AssessmentType> list = new ArrayList<AssessmentType>();
        BaseLookUpService<AssessmentType> lookUpService =
            new BaseLookUpService<AssessmentType>(AssessmentType.class);
        list.addAll(lookUpService.search(criteria));
        for (AssessmentType bean :  list) {
            LookUpWebDto lookupdto = new LookUpWebDto();
            lookupdto.setCode(StConverter.convertToSt(bean.getCode()));
            lookupdto.setDescription(StConverter.convertToSt(bean.getDescription()));
            lookupdto.setDisplayName(StConverter.convertToSt(bean.getDisplayName()));
            lookupdto.setId(IiConverter.convertToIi(bean.getId()));
            lookupdto.setType(StConverter.convertToSt(type));
            lookUpList.add(lookupdto);
        }
        ServletActionContext.getRequest().setAttribute("lookUpList", lookUpList);
        return super.execute();
    }

    /**
     * Lookup for tumorMarker.
     *
     * @return the string
     * @throws RemoteException remote Exception
     */
    @SkipValidation
    public String tumorMarker() throws RemoteException {
        if (searchText == null) {
            searchText = new St();
            return SUCCESS;
        }

        String sTxt = searchText.getValue();
        if (sTxt == null || sTxt.length() == 0) {
            addActionError(seacrhMsg);
            return INPUT;
        }

        TumorMarker criteria = new TumorMarker();
        criteria.setCode(sTxt);
        List<TumorMarker> list = new ArrayList<TumorMarker>();
        BaseLookUpService<TumorMarker> lookUpService =
            new BaseLookUpService<TumorMarker>(TumorMarker.class);
        list.addAll(lookUpService.search(criteria));
        for (TumorMarker bean :  list) {
            LookUpWebDto lookupdto = new LookUpWebDto();
            lookupdto.setCode(StConverter.convertToSt(bean.getCode()));
            lookupdto.setDescription(StConverter.convertToSt(bean.getDescription()));
            lookupdto.setDisplayName(StConverter.convertToSt(bean.getDisplayName()));
            lookupdto.setId(IiConverter.convertToIi(bean.getId()));
            lookupdto.setType(StConverter.convertToSt(type));
            lookUpList.add(lookupdto);
        }
        ServletActionContext.getRequest().setAttribute("lookUpList", lookUpList);
        return super.execute();
    }

    /**
     * Lookup for lesionLocationAnatomicSite.
     *
     * @return the string
     * @throws RemoteException remote Exception
     */
    @SkipValidation
    public String lesionLocationAnatomicSite() throws RemoteException {
        if (searchText == null) {
            searchText = new St();
            return SUCCESS;
        }

        String sTxt = searchText.getValue();
        if (sTxt == null || sTxt.length() == 0) {
            addActionError(seacrhMsg);
            return INPUT;
        }

        LesionLocationAnatomicSite criteria = new LesionLocationAnatomicSite();
        criteria.setCode(sTxt);
        List<LesionLocationAnatomicSite> list = new ArrayList<LesionLocationAnatomicSite>();
        BaseLookUpService<LesionLocationAnatomicSite> lookUpService =
            new BaseLookUpService<LesionLocationAnatomicSite>(LesionLocationAnatomicSite.class);
        list.addAll(lookUpService.search(criteria));
        for (LesionLocationAnatomicSite bean :  list) {
            LookUpWebDto lookupdto = new LookUpWebDto();
            lookupdto.setCode(StConverter.convertToSt(bean.getCode()));
            lookupdto.setDescription(StConverter.convertToSt(bean.getDescription()));
            lookupdto.setDisplayName(StConverter.convertToSt(bean.getDisplayName()));
            lookupdto.setId(IiConverter.convertToIi(bean.getId()));
            lookupdto.setType(StConverter.convertToSt(type));
            lookUpList.add(lookupdto);
        }
        ServletActionContext.getRequest().setAttribute("lookUpList", lookUpList);
        return super.execute();
    }

    /**
     * Gets the search text.
     * @return the search text
     */
    public St getSearchText() {
        return searchText;
    }

    /**
     * Sets the search text.
     * @param searchText the new search text
     */
    public void setSearchText(St searchText) {
        this.searchText = searchText;
    }

    /**
     * Gets the look up list.
     * @return the look up list
     */
    public List<LookUpWebDto> getLookUpList() {
        return lookUpList;
    }

    /**
     * Gets the type.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }
}
