/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.service.PAException;

/**
 * Class contains exclusively a static method used to return converters for iso dto's.
 * @author Hugh Reinhart
 * @since 11/06/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class Converters {
    private static ArmConverter arm = new ArmConverter();
    private static PlannedActivityConverter plannedActivity = new PlannedActivityConverter();
    private static StratumGroupConverter sg = new StratumGroupConverter();
    private static DocumentWorkflowStatusConverter dws = new DocumentWorkflowStatusConverter();
    private static InterventionConverter intervention = new InterventionConverter();
    private static InterventionAlternateNameConverter intervAltName = new InterventionAlternateNameConverter();
    private static StudyParticipationConverter sParticipation = new StudyParticipationConverter();
    private static DiseaseConverter diseaseConverter = new DiseaseConverter();
    private static DiseaseAlternameConverter diseaseAlternameConverter = new DiseaseAlternameConverter();
    private static DiseaseParentConverter diseaseParentConverter = new DiseaseParentConverter();
    private static StudyDiseaseConverter studyDiseaseConverter = new StudyDiseaseConverter();

    /**
     * @param clazz class
     * @return converter
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    public static AbstractConverter get(Class clazz)  throws PAException {
        if (clazz.equals(ArmConverter.class)) {
            return arm;
        }
        if (clazz.equals(PlannedActivityConverter.class)) {
            return plannedActivity;
        }
        if (clazz.equals(StratumGroupConverter.class)) {
            return sg;
        }
        if (clazz.equals(DocumentWorkflowStatusConverter.class)) {
            return dws;
        }
        if (clazz.equals(InterventionConverter.class)) {
            return intervention;
        }
        if (clazz.equals(InterventionAlternateNameConverter.class)) {
            return intervAltName;
        }
        if (clazz.equals(StudyParticipationConverter.class)) {
            return sParticipation;
        }
        if (clazz.equals(DiseaseConverter.class)) {
            return diseaseConverter;
        }
        if (clazz.equals(DiseaseAlternameConverter.class)) {
            return diseaseAlternameConverter;
        }
        if (clazz.equals(DiseaseParentConverter.class)) {
            return diseaseParentConverter;
        }
        if (clazz.equals(StudyDiseaseConverter.class)) {
            return studyDiseaseConverter;
        }
        throw new PAException("Converter needs to be added to gov.nih.nci.pa.iso.convert.Converters.  ");
    }
}
