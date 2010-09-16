package gov.nih.nci.services;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Entity;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.AbstractOrganizationDTOHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * EntityNodeConverter transforms from EntityNode to EntityNodeDto.
 *
 * @author ludetc
 *
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class EntityNodeDtoConverter extends AbstractOrganizationDTOHelper {

    /**
     * Converts an array of Entity object to array of EntityNodeDTO object.
     * @param entities The entities to be converted
     * @param players the players that should also be converted
     * @param scopers the scopers that should also be converted
     * @return array of EntityNodeDTO
     */
    public static List<EntityNodeDto> convertToEntityNodeDtoList(
        List<Entity> entities, Cd[] players, Cd[] scopers) {

        if (entities == null) {
           return null;
        }

        List<EntityNodeDto> results = new ArrayList<EntityNodeDto>();

        for (Entity entity : entities) {
            results.add(convertToEntityNodeDto(entity, players, scopers));
        }

        return results;
    }

    /**
     * If _either_ players or scopers individually is greater than 500 (use Utils.MAX_SEARCH_RESULTS),
     * then just that array is null and overflow is set.
     *
     * Examples:
     * if overflow is set, players != null, scopers == null, then there were more than 500 scopers
     * if overflow is set, players == null, scopers == null, then there were more than 500 scopers and 500 players
     *
     * @param entity an instance of a poEntity (Person / Organization)
     * @param playerFilters a list of classes to filter players by
     * @param scoperFilters a list of classes to filter scopers by
     * @return The EntityNodeDto.
     */
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public static EntityNodeDto convertToEntityNodeDto(
            Entity entity, Cd[] playerFilters, Cd[] scoperFilters) {
        EntityNodeDto entityNodeDto = new EntityNodeDto();

        Set<String> playerFilterColl = new HashSet<String>();
        if (playerFilters != null) {
            for (Cd cd : playerFilters) {
                if (cd.getCode() != null) {
                    playerFilterColl.add(cd.getCode());
                }
            }
        }
        Set<String> scoperFilterColl = new HashSet<String>();
        if (scoperFilters != null) {
            for (Cd cd : scoperFilters) {
                if (cd.getCode() != null) {
                    scoperFilterColl.add(cd.getCode());
                }
            }
        }
        entityNodeDto.setEntityDto((EntityDto) PoXsnapshotHelper.createSnapshot(entity));

        if (entity instanceof Person) {
            convertPersonToEntityNodeDto(entityNodeDto, (Person) entity, playerFilterColl);
        } else if (entity instanceof Organization) {
            convertOrganizationToEntityNodeDto(
                    entityNodeDto, (Organization) entity, playerFilterColl, scoperFilterColl);
        }

        return entityNodeDto;
    }

    private static void convertOrganizationToEntityNodeDto(
        EntityNodeDto entityNodeDto, Organization organization,
        Set<String> playerFilters, Set<String> scoperFilters) {

        Set<CorrelationDto> playerDtoList = new HashSet<CorrelationDto>();
        Set<CorrelationDto> scoperDtoList = new HashSet<CorrelationDto>();

        addToListByFilter(playerDtoList, PoXsnapshotHelper.createSnapshotList(
                organization.getResearchOrganizations()),
                playerFilters, RoleList.RESEARCH_ORGANIZATION.toString());

        addToListByFilter(playerDtoList, PoXsnapshotHelper.createSnapshotList(
                organization.getHealthCareFacilities()),
                playerFilters, RoleList.HEALTH_CARE_FACILITY.toString());

        addToListByFilter(playerDtoList, PoXsnapshotHelper.createSnapshotList(
                organization.getOversightCommittees()),
                playerFilters, RoleList.OVERSIGHT_COMMITTEE.toString());

        addToListByFilter(scoperDtoList, PoXsnapshotHelper.createSnapshotList(
                organization.getOrganizationalContacts()),
                scoperFilters, RoleList.ORGANIZATIONAL_CONTACT.toString());

        addToListByFilter(scoperDtoList, PoXsnapshotHelper.createSnapshotList(
                organization.getClinicalResearchStaff()),
                scoperFilters, RoleList.CLINICAL_RESEARCH_STAFF.toString());

        addToListByFilter(scoperDtoList, PoXsnapshotHelper.createSnapshotList(
                organization.getIdentifiedPersons()),
                scoperFilters, RoleList.IDENTIFIED_PERSON.toString());

        addToListByFilter(scoperDtoList, PoXsnapshotHelper.createSnapshotList(
                organization.getHealthCareProviders()),
                scoperFilters, RoleList.HEALTH_CARE_PROVIDER.toString());

        for (IdentifiedOrganization idOrg : organization.getIdentifiedOrganizations()) {
            if (idOrg.getPlayer().equals(organization)
                    && playerFilters.contains(RoleList.IDENTIFIED_ORGANIZATION.toString())) {
                playerDtoList.add((CorrelationDto) PoXsnapshotHelper.createSnapshot(idOrg));
            }
            if (idOrg.getScoper().equals(organization)
                    && playerFilters.contains(RoleList.IDENTIFIED_ORGANIZATION.toString())) {
                scoperDtoList.add((CorrelationDto) PoXsnapshotHelper.createSnapshot(idOrg));
            }
        }

        setCorrelationOverflow(entityNodeDto, playerDtoList, scoperDtoList);
    }

    private static void setCorrelationOverflow(EntityNodeDto entityNodeDto, Set<CorrelationDto> playerDtoList,
           Set<CorrelationDto> scoperDtoList) {
        Bl bl = new Bl();
        bl.setValue(false);
        if (playerDtoList.size() > Utils.MAX_SEARCH_RESULTS) {
            bl.setValue(true);
            entityNodeDto.setPlayers(null);
        } else {
            entityNodeDto.setPlayers(listToArray(playerDtoList));
        }
        if (scoperDtoList.size() > Utils.MAX_SEARCH_RESULTS) {
            bl.setValue(true);
            entityNodeDto.setScopers(null);
        } else {
            entityNodeDto.setScopers(listToArray(scoperDtoList));
        }
        entityNodeDto.setCorrelationOverflow(bl);
    }

    @SuppressWarnings("unchecked")
    private static void addToListByFilter(Set<CorrelationDto> dtoList, List addList, Set<String> filters, String type) {
        if (filters.contains(type)) {
            dtoList.addAll(addList);
        }
    }

    private static CorrelationDto[] listToArray(Set<CorrelationDto> list) {
        return list.toArray(new CorrelationDto[list.size()]);
    }

    private static void convertPersonToEntityNodeDto(
            EntityNodeDto entityNodeDto, Person person, Set<String> filters) {
        Set<CorrelationDto> playerDtoList = new HashSet<CorrelationDto>();

        addToListByFilter(playerDtoList,
                    PoXsnapshotHelper.createSnapshotList(person.getOrganizationalContacts()),
                    filters, RoleList.ORGANIZATIONAL_CONTACT.toString());
        addToListByFilter(playerDtoList,
                        PoXsnapshotHelper.createSnapshotList(person.getClinicalResearchStaff()),
                        filters, RoleList.CLINICAL_RESEARCH_STAFF.toString());
        addToListByFilter(playerDtoList,
                        PoXsnapshotHelper.createSnapshotList(person.getHealthCareProviders()),
                        filters, RoleList.HEALTH_CARE_PROVIDER.toString());
        addToListByFilter(playerDtoList,
                        PoXsnapshotHelper.createSnapshotList(person.getIdentifiedPersons()),
                        filters, RoleList.IDENTIFIED_PERSON.toString());

        Bl bl = new Bl();
        if (playerDtoList.size() > Utils.MAX_SEARCH_RESULTS) {
            bl.setValue(true);
            entityNodeDto.setPlayers(null);
        } else {
            bl.setValue(false);
            entityNodeDto.setPlayers(listToArray(playerDtoList));
        }
        entityNodeDto.setCorrelationOverflow(bl);
        entityNodeDto.setScopers(new CorrelationDto[0]);
    }

    /**
     *
     * @param entityNodeDto EntityNodeDto
     * @return PoEntity
     */
    public static Entity convertToEntity(EntityNodeDto entityNodeDto) {
        Entity entity = (Entity) PoXsnapshotHelper.createModel(entityNodeDto.getEntityDto());

        CorrelationDto[] players = entityNodeDto.getPlayers();
        CorrelationDto[] scopers = entityNodeDto.getScopers();

        if (entity instanceof Person) {
            attachCorrelationToPerson((Person) entity, players);
        } else if (entity instanceof Organization) {
            attachCorrelationToOrganization((Organization) entity, players);
            attachCorrelationToOrganization((Organization) entity, scopers);
        }

        return entity;
    }

    /**
     * Attach correlations to an Organization.
     *
     * @param organization to attach to
     * @param correlations to attach
     */
    private static void attachCorrelationToOrganization(Organization organization, CorrelationDto[] correlations) {
        for (CorrelationDto correlation : correlations) {
            if (correlation instanceof OrganizationalContactDTO) {
                organization.getOrganizationalContacts()
                    .add((OrganizationalContact) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof ResearchOrganizationDTO) {
                organization.getResearchOrganizations()
                .add((ResearchOrganization) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof OversightCommitteeDTO) {
                organization.getOversightCommittees()
                .add((OversightCommittee) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof HealthCareFacilityDTO) {
                organization.getHealthCareFacilities()
                .add((HealthCareFacility) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof IdentifiedOrganizationDTO) {
                organization.getIdentifiedOrganizations()
                .add((IdentifiedOrganization) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof IdentifiedPersonDTO) {
                organization.getIdentifiedPersons()
                .add((IdentifiedPerson) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof HealthCareProviderDTO) {
                organization.getHealthCareProviders()
                .add((HealthCareProvider) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof ClinicalResearchStaffDTO) {
                organization.getClinicalResearchStaff()
                .add((ClinicalResearchStaff) PoXsnapshotHelper.createModel(correlation));
            }
        }
    }

    /**
     * Attach correlations to a player.
     *
     * @param person to attach to
     * @param correlations to attach
     */
    private static void attachCorrelationToPerson(Person person, CorrelationDto[] correlations) {
        for (CorrelationDto correlation : correlations) {
            if (correlation instanceof OrganizationalContactDTO) {
                person.getOrganizationalContacts()
                    .add((OrganizationalContact) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof ClinicalResearchStaffDTO) {
                person.getClinicalResearchStaff()
                .add((ClinicalResearchStaff) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof HealthCareProviderDTO) {
                person.getHealthCareProviders()
                .add((HealthCareProvider) PoXsnapshotHelper.createModel(correlation));
            } else if (correlation instanceof IdentifiedPersonDTO) {
                person.getIdentifiedPersons()
                .add((IdentifiedPerson) PoXsnapshotHelper.createModel(correlation));
            }
        }
    }

}
