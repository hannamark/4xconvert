package gov.nih.nci.po.webservices.convert.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains exclusively a static method used to return converters.
 * 
 * @author Rohit Gupta
 * 
 */
@SuppressWarnings({ "unchecked" })
public class Converters {
    private static Map<Class<?>, AbstractConverter<?, ?>> convtMap = new HashMap<Class<?>, AbstractConverter<?, ?>>();

    static {
        convtMap.put(PersonConverter.class, new PersonConverter());
        convtMap.put(OrganizationConverter.class, new OrganizationConverter());

        // PersonRole Converters
        convtMap.put(HealthCareProviderConverter.class,
                new HealthCareProviderConverter());
        convtMap.put(OrganizationalContactConverter.class,
                new OrganizationalContactConverter());
        convtMap.put(ClinicalResearchStaffConverter.class,
                new ClinicalResearchStaffConverter());

        // OrganizationRole Converters
        convtMap.put(ResearchOrganizationConverter.class,
                new ResearchOrganizationConverter());
        convtMap.put(OversightCommitteeConverter.class,
                new OversightCommitteeConverter());
        convtMap.put(HealthCareFacilityConverter.class,
                new HealthCareFacilityConverter());

        // Family Converters
        convtMap.put(FamilyConverter.class, new FamilyConverter());
        convtMap.put(FamilyMemberRelationshipConverter.class,
                new FamilyMemberRelationshipConverter());
    }

    /**
     * @param clazz
     *            class
     * @param <TYPE>
     *            the converter type to get
     * @return converter
     * @throws ConverterException
     */
    public static <TYPE extends AbstractConverter<?, ?>> TYPE get(
            Class<TYPE> clazz) {
        if (convtMap.containsKey(clazz)) {
            return (TYPE) convtMap.get(clazz);
        }
        throw new ConverterException(
                "Converter needs to be added to gov.nih.nci.po.webservices.convert.Converters.");
    }
}
