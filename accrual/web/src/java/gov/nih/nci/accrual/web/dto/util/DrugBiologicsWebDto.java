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

package gov.nih.nci.accrual.web.dto.util;

import java.io.Serializable;

/**
 * The Class DrugBiologicsWebDto.
 * 
 * @author lhebel
 * @since 10/28/2009
 */
@SuppressWarnings({"PMD.TooManyFields" })
public class DrugBiologicsWebDto implements Serializable {

    private static final long serialVersionUID = -1395879742701837870L;
    
    private String id;    
    private String drugName;    
    private String nscNumber;    
    private String lotNumber;    
     private String dose;    
    private String doseUom;    
    private String doseRoute;    
    private String doseFreq;    
    private String doseDur;    
     private String doseDurUom;    
    private String height;    
    private String heightUom;    
     private String weight;    
    private String weightUom;    
    private String bsa;    
    private String doseTotalPerCourse;    
     private String doseModType;

    /**
     * Instantiates a new drug biologics web dto.
     */
    public DrugBiologicsWebDto() {
        // default constructor
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the drugName
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * @param drugName the drugName to set
     */
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    /**
     * @return the nscNumber
     */
    public String getNscNumber() {
        return nscNumber;
    }

    /**
     * @param nscNumber the nscNumber to set
     */
    public void setNscNumber(String nscNumber) {
        this.nscNumber = nscNumber;
    }

    /**
     * @return the lotNumber
     */
    public String getLotNumber() {
        return lotNumber;
    }

    /**
     * @param lotNumber the lotNumber to set
     */
    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    /**
     * @return the dose
     */
    public String getDose() {
        return dose;
    }

    /**
     * @param dose the dose to set
     */
    public void setDose(String dose) {
        this.dose = dose;
    }

    /**
     * @return the doseUom
     */
    public String getDoseUom() {
        return doseUom;
    }

    /**
     * @param doseUom the doseUom to set
     */
    public void setDoseUom(String doseUom) {
        this.doseUom = doseUom;
    }

    /**
     * @return the doseRoute
     */
    public String getDoseRoute() {
        return doseRoute;
    }

    /**
     * @param doseRoute the doseRoute to set
     */
    public void setDoseRoute(String doseRoute) {
        this.doseRoute = doseRoute;
    }

    /**
     * @return the doseFreq
     */
    public String getDoseFreq() {
        return doseFreq;
    }

    /**
     * @param doseFreq the doseFreq to set
     */
    public void setDoseFreq(String doseFreq) {
        this.doseFreq = doseFreq;
    }

    /**
     * @return the doseDur
     */
    public String getDoseDur() {
        return doseDur;
    }

    /**
     * @param doseDur the doseDur to set
     */
    public void setDoseDur(String doseDur) {
        this.doseDur = doseDur;
    }

    /**
     * @return the doseDurUom
     */
    public String getDoseDurUom() {
        return doseDurUom;
    }

    /**
     * @param doseDurUom the doseDurUom to set
     */
    public void setDoseDurUom(String doseDurUom) {
        this.doseDurUom = doseDurUom;
    }

    /**
     * @return the height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return the heightUom
     */
    public String getHeightUom() {
        return heightUom;
    }

    /**
     * @param heightUom the heightUom to set
     */
    public void setHeightUom(String heightUom) {
        this.heightUom = heightUom;
    }

    /**
     * @return the weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * @return the weightUom
     */
    public String getWeightUom() {
        return weightUom;
    }

    /**
     * @param weightUom the weightUom to set
     */
    public void setWeightUom(String weightUom) {
        this.weightUom = weightUom;
    }

    /**
     * @return the bsa
     */
    public String getBsa() {
        return bsa;
    }

    /**
     * @param bsa the bsa to set
     */
    public void setBsa(String bsa) {
        this.bsa = bsa;
    }

    /**
     * @return the doseTotalPerCourse
     */
    public String getDoseTotalPerCourse() {
        return doseTotalPerCourse;
    }

    /**
     * @param doseTotalPerCourse the doseTotalPerCourse to set
     */
    public void setDoseTotalPerCourse(String doseTotalPerCourse) {
        this.doseTotalPerCourse = doseTotalPerCourse;
    }

    /**
     * @return the doseModType
     */
    public String getDoseModType() {
        return doseModType;
    }

    /**
     * @param doseModType the doseModType to set
     */
    public void setDoseModType(String doseModType) {
        this.doseModType = doseModType;
    }
}
