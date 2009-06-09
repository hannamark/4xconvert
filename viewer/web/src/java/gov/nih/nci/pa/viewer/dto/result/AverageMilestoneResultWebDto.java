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
*/
package gov.nih.nci.pa.viewer.dto.result;

import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.result.AverageMilestoneResultDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 06/08/2009
 */
@SuppressWarnings("PMD.TooManyFields")
public final class AverageMilestoneResultWebDto {

    private String milestoneCode;
    private Integer order;
    private Integer day01;
    private Integer day02;
    private Integer day03;
    private Integer day04;
    private Integer day05;
    private Integer day06;
    private Integer day07;
    private Integer day08;
    private Integer day09;
    private Integer day10;
    private Integer gtTenDays;
    private String average;
    private String low;
    private String high;

    /**
     * Static method for generating a list of web dto's from a list of service dto's.
     * @param serviceDtoList service dto list
     * @return web dto list
     */
    public static List<AverageMilestoneResultWebDto> getWebList(List<AverageMilestoneResultDto> serviceDtoList) {
        List<AverageMilestoneResultWebDto> resultList = new ArrayList<AverageMilestoneResultWebDto>();
        for (AverageMilestoneResultDto dto : serviceDtoList) {
            resultList.add(new AverageMilestoneResultWebDto(dto));
        }
        return resultList;
    }

    private AverageMilestoneResultWebDto(AverageMilestoneResultDto dto) {
        milestoneCode = CdConverter.convertCdToString(dto.getMilestoneCode());
        order = IntConverter.convertToInteger(dto.getOrder());
        day01 = IntConverter.convertToInteger(dto.getDay01());
        day02 = IntConverter.convertToInteger(dto.getDay02());
        day03 = IntConverter.convertToInteger(dto.getDay03());
        day04 = IntConverter.convertToInteger(dto.getDay04());
        day05 = IntConverter.convertToInteger(dto.getDay05());
        day06 = IntConverter.convertToInteger(dto.getDay06());
        day07 = IntConverter.convertToInteger(dto.getDay07());
        day08 = IntConverter.convertToInteger(dto.getDay08());
        day09 = IntConverter.convertToInteger(dto.getDay09());
        day10 = IntConverter.convertToInteger(dto.getDay10());
        gtTenDays = IntConverter.convertToInteger(dto.getGtTenDays());
        average = StConverter.convertToString(dto.getAverage());
        low = StConverter.convertToString(dto.getLow());
        high = StConverter.convertToString(dto.getHigh());
    }

    /**
     * @return the milestoneCode
     */
    public String getMilestoneCode() {
        return milestoneCode;
    }
    /**
     * @param milestoneCode the milestoneCode to set
     */
    public void setMilestoneCode(String milestoneCode) {
        this.milestoneCode = milestoneCode;
    }
    /**
     * @return the order
     */
    public Integer getOrder() {
        return order;
    }
    /**
     * @param order the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }
    /**
     * @return the day01
     */
    public Integer getDay01() {
        return day01;
    }
    /**
     * @param day01 the day01 to set
     */
    public void setDay01(Integer day01) {
        this.day01 = day01;
    }
    /**
     * @return the day02
     */
    public Integer getDay02() {
        return day02;
    }
    /**
     * @param day02 the day02 to set
     */
    public void setDay02(Integer day02) {
        this.day02 = day02;
    }
    /**
     * @return the day03
     */
    public Integer getDay03() {
        return day03;
    }
    /**
     * @param day03 the day03 to set
     */
    public void setDay03(Integer day03) {
        this.day03 = day03;
    }
    /**
     * @return the day04
     */
    public Integer getDay04() {
        return day04;
    }
    /**
     * @param day04 the day04 to set
     */
    public void setDay04(Integer day04) {
        this.day04 = day04;
    }
    /**
     * @return the day05
     */
    public Integer getDay05() {
        return day05;
    }
    /**
     * @param day05 the day05 to set
     */
    public void setDay05(Integer day05) {
        this.day05 = day05;
    }
    /**
     * @return the day06
     */
    public Integer getDay06() {
        return day06;
    }
    /**
     * @param day06 the day06 to set
     */
    public void setDay06(Integer day06) {
        this.day06 = day06;
    }
    /**
     * @return the day07
     */
    public Integer getDay07() {
        return day07;
    }
    /**
     * @param day07 the day07 to set
     */
    public void setDay07(Integer day07) {
        this.day07 = day07;
    }
    /**
     * @return the day08
     */
    public Integer getDay08() {
        return day08;
    }
    /**
     * @param day08 the day08 to set
     */
    public void setDay08(Integer day08) {
        this.day08 = day08;
    }
    /**
     * @return the day09
     */
    public Integer getDay09() {
        return day09;
    }
    /**
     * @param day09 the day09 to set
     */
    public void setDay09(Integer day09) {
        this.day09 = day09;
    }
    /**
     * @return the day10
     */
    public Integer getDay10() {
        return day10;
    }
    /**
     * @param day10 the day10 to set
     */
    public void setDay10(Integer day10) {
        this.day10 = day10;
    }
    /**
     * @return the gtTenDays
     */
    public Integer getGtTenDays() {
        return gtTenDays;
    }
    /**
     * @param gtTenDays the gtTenDays to set
     */
    public void setGtTenDays(Integer gtTenDays) {
        this.gtTenDays = gtTenDays;
    }
    /**
     * @return the average
     */
    public String getAverage() {
        return average;
    }
    /**
     * @param average the average to set
     */
    public void setAverage(String average) {
        this.average = average;
    }
    /**
     * @return the low
     */
    public String getLow() {
        return low;
    }
    /**
     * @param low the low to set
     */
    public void setLow(String low) {
        this.low = low;
    }
    /**
     * @return the high
     */
    public String getHigh() {
        return high;
    }
    /**
     * @param high the high to set
     */
    public void setHigh(String high) {
        this.high = high;
    }
}
