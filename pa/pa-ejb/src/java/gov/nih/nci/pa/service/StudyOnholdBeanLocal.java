/**
 * 
 */
package gov.nih.nci.pa.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.iso.convert.StudyOnholdConverter;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.service.exception.PAFieldException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyOnholdBeanLocal extends AbstractStudyIsoService<StudyOnholdDTO, StudyOnhold, StudyOnholdConverter>
implements StudyOnholdServiceLocal {
   
    /** id for onholdReasonCode. */
    public static final int FN_REASON_CODE = 0;
    /** id for onholdDate.low. */
    public static final int FN_DATE_LOW = 1;
    /** id for onholdDate.high. */
    public static final int FN_DATE_HIGH = 2;

    /**
     * @param studyProtocolIi StudyProtocol identifier
     * @return if there is an active onhold record
     * @throws PAException exception
     */
    public Bl isOnhold(Ii studyProtocolIi) throws PAException {
        Bl result = new Bl();
        List<StudyOnholdDTO> list = getByStudyProtocol(studyProtocolIi);
        for (StudyOnholdDTO dto : list) {
            if (IvlConverter.convertTs().convertHigh(dto.getOnholdDate()) == null) {
                result.setValue(true);
                return result;
            }
        }
        result.setValue(false);
        return result;
    }

    /**
     * @param dto dto
     * @return dto
     * @throws PAException exception
     */
    @Override
    public StudyOnholdDTO create(StudyOnholdDTO dto) throws PAException {
        setTimeIfToday(dto);
        reasonRules(dto);
        dateRules(dto);
        return super.create(dto);
    }

    /**
     * @param dto dto
     * @return dto
     * @throws PAException exception
     */
    @Override
    public StudyOnholdDTO update(StudyOnholdDTO dto) throws PAException {
        StudyOnholdDTO wrkDto = super.get(dto.getIdentifier());
        if (wrkDto != null) {
        wrkDto.getOnholdDate().setHigh(dto.getOnholdDate().getHigh());
        setTimeIfToday(wrkDto);
        dateRules(wrkDto);
        } 
        return super.update(wrkDto);
    }

    private void setTimeIfToday(StudyOnholdDTO dto) {
        Timestamp now = new Timestamp(new Date().getTime());
        Timestamp tsLow = IvlConverter.convertTs().convertLow(dto.getOnholdDate());
        Timestamp tsHigh = IvlConverter.convertTs().convertHigh(dto.getOnholdDate());
        if (tsLow != null && tsLow.equals(PAUtil.dateStringToTimestamp(PAUtil.today()))) {
            tsLow = now;
        }
        if (tsHigh != null && tsHigh.equals(PAUtil.dateStringToTimestamp(PAUtil.today()))) {
            tsHigh = now;
        }
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(tsLow, tsHigh));
    }

    private void reasonRules(StudyOnholdDTO dto) throws PAException {
        if (PAUtil.isCdNull(dto.getOnholdReasonCode())) {
            throw new PAFieldException(FN_REASON_CODE,
                    "The On-hold reason code is a required field.");
        }
    }

    private void dateRules(StudyOnholdDTO dto) throws PAException {
        Timestamp low = IvlConverter.convertTs().convertLow(dto.getOnholdDate());
        if (low == null) {
            throw new PAFieldException(FN_DATE_LOW, "On-hold date is required.");
        }
        Timestamp now = new Timestamp(new Date().getTime());
        if (now.before(low)) {
            throw new PAFieldException(FN_DATE_LOW, "On-hold dates must be only past or current dates.");
        }
        Timestamp high = IvlConverter.convertTs().convertHigh(dto.getOnholdDate());
        if (high != null) {
            if (now.before(high)) {
                throw new PAFieldException(FN_DATE_HIGH, "Off-hold dates must be only past or current dates.");
            }
            if (high.before(low)) {
                throw new PAFieldException(FN_DATE_HIGH,
                        "Off-hold date must be bigger than on-hold date for the same on-hold record.");
            }
        }
    }

}
