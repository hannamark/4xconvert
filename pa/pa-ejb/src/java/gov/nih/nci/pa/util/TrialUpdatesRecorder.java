/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Records updates made to a trial during Update operation invoked by Registry
 * or Batch Protocol update. Recorded updates are then used to produce Trial
 * History/Updates tab content.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class TrialUpdatesRecorder {

    /**
     * Using a {@link ThreadLocal} to avoid having to pass an instance of
     * TrialUpdatesRecorder around.
     */
    private static ThreadLocal<LinkedHashSet<String>> holder = new ThreadLocal<LinkedHashSet<String>>();

    /**
     * 
     */
    public static final String DOC_UPDATED = "%s document was updated.";

    /**
     * 
     */
    public static final String SEPARATOR = "\r";

    /**
     * 
     */
    public static final String STATUS_DATES_UPDATED = "Trial Status and/or Trial Status Date were updated.";
    /**
     * 
     */
    public static final String RECRUITMENT_STATUS_DATE_UPDATED = "Participating site's Recruitment Status "
            + "and/or Status Date were updated.";
    /**
     * 
     */
    public static final String IND_IDE_UPDATED = "Ind Ide was updated.";
    /**
     * 
     */
    public static final String GRANT_INFORMATION_UPDATED = "Grant information was updated.";

    /**
     * 
     */
    public static final String IDENTIFIERS_ADDED = "Other Identifiers information was updated: "
            + "new identifier(s) added.";

    /**
     * 
     */
    public static final String START_DATE_CHANGED = "Trial Start Date was updated.";

    /**
     * 
     */
    public static final String PRIMARY_COMPLETION_DATE_CHANGED = "Trial Primary Completion Date was updated.";

    /**
     * 
     */
    public static final String COMPLETION_DATE_CHANGED = "Trial Completion Date was updated.";

    /**
     * 
     */
    public static final String PARTICIPATING_SITES_UPDATED = "Participating site's Program Code was updated.";
    
    private TrialUpdatesRecorder() {
    }

    /**
     * Reset recordings.
     */
    public static void reset() {
        holder.set(new LinkedHashSet<String>());
    }

    /**
     * recordUpdate.
     * 
     * @param original
     *            original
     * @param updated
     *            updated
     * @param msg
     *            msg
     */
    @SuppressWarnings("rawtypes")
    public static void recordUpdate(Collection original, Collection updated,
            String msg) {
        int sizeBefore = original != null ? original.size() : 0;
        int sizeAfter = updated != null ? updated.size() : 0;
        if (sizeBefore != sizeAfter) {
            add(msg);
        }
    }

    private static void add(String msg) {
        if (holder.get() == null) {
            reset();
        }
        holder.get().add(msg);
    }

    /**
     * recordUpdate.
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @param msg
     *            msg
     */
    public static void recordUpdate(Ts date1, Ts date2, String msg) {
        Date d1 = TsConverter.convertToTimestamp(date1);
        Date d2 = TsConverter.convertToTimestamp(date2);
        if ((d1 == null && d2 != null) || (d1 != null && d2 == null)
                || (d1 != null && d2 != null && !DateUtils.isSameDay(d1, d2))) {
            add(msg);
        }
    }

    /**
     * @param studyResourcingDTOs
     *            studyResourcingDTOs
     * @param grantInformationUpdated
     *            grantInformationUpdated
     * @throws PAException
     *             PAException
     */
    public static void recordUpdate(
            List<StudyResourcingDTO> studyResourcingDTOs,
            String grantInformationUpdated) throws PAException {
        if (CollectionUtils.isNotEmpty(studyResourcingDTOs)) {
            for (StudyResourcingDTO grantDto : studyResourcingDTOs) {
                if (ISOUtil.isIiNull(grantDto.getIdentifier())) {
                    add(grantInformationUpdated);
                    return;
                }
                StudyResourcingDTO dto = PaRegistry.getStudyResourcingService()
                        .get(grantDto.getIdentifier());
                if (isGrantUpdated(dto, grantDto)) {
                    add(grantInformationUpdated);
                    return;
                }
            }
        }
    }

    /**
     * Test if a grant has been updated.
     * 
     * @param existing
     *            The existing grant
     * @param updated
     *            The possibly updated grant
     * @return true if the grant has been updated
     */
    private static boolean isGrantUpdated(StudyResourcingDTO existing,
            StudyResourcingDTO updated) {
        return !(existing.getFundingMechanismCode().getCode().equals(updated
                .getFundingMechanismCode().getCode()))
                || !(existing.getNihInstitutionCode().getCode().equals(updated
                        .getNihInstitutionCode().getCode()))
                || !(existing.getSerialNumber().getValue().equals(updated
                        .getSerialNumber().getValue()))
                || !(existing.getNciDivisionProgramCode().getCode()
                        .equals(updated.getNciDivisionProgramCode().getCode())
                || !(ObjectUtils.equals(RealConverter.convertToDouble(existing.getFundingPercent()) 
                        , RealConverter.convertToDouble(updated.getFundingPercent())))
                );
    }

    /**
     * @param msg
     *            msg
     */
    public static void recordUpdate(String msg) {
        add(msg);
    }

    /**
     * @param studySiteDTOs
     *            studySiteDTOs
     * @param msg
     *            msg
     * @throws PAException
     *             PAException
     */
    public static void recordParticipatingSiteUpdate(
            List<StudySiteDTO> studySiteDTOs, String msg) throws PAException {
        if (studySiteDTOs != null) {
            for (StudySiteDTO newDTO : studySiteDTOs) {
                StudySiteDTO existentDTO = PaRegistry.getStudySiteService()
                        .get(newDTO.getIdentifier());
                if (!StringUtils
                        .defaultString(
                                StConverter.convertToString(newDTO
                                        .getProgramCodeText())).equals(
                                StringUtils.defaultString(StConverter
                                        .convertToString(existentDTO
                                                .getProgramCodeText())))) {
                    add(msg);
                }
            }
        }
    }

    /**
     * @param overallStatusDTO
     *            overallStatusDTO
     * @param msg
     *            msg
     * @throws PAException PAException
     */
    public static void recordUpdate(StudyOverallStatusDTO overallStatusDTO,
            String msg) throws PAException {
        if (PaRegistry.getStudyOverallStatusService()
                .isTrialStatusOrDateChanged(overallStatusDTO,
                        overallStatusDTO.getStudyProtocolIdentifier())) {
            add(msg);
        }
    }
    
    /**
     * @param existingNCT existingNCT
     * @param newNCT newNCT
     */
    public static void isNctUpdated(String existingNCT, String newNCT) {
        if (!StringUtils.equals(StringUtils.trim(existingNCT + ""), // NOPMD
                StringUtils.trim(newNCT + ""))) { // NOPMD
            add(StringUtils.isEmpty(existingNCT) ? "NCT Number was added." : "NCT Number was changed.");
        }
    }

    /**
     * All updates together as a single contatenated {@link String}.
     * 
     * @return All updates together as a single contatenated {@link String}.
     */
    public static String getRecordedUpdates() {
        return StringUtils.join(holder.get(), SEPARATOR);
    }

}
