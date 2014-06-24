package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IdConverterRegistry;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationSortCriterion;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.convert.bridg.OrganizationTransformer;
import gov.nih.nci.po.webservices.service.bo.OrganizationBoService;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("organizationBridgService")
public class OrganizationServiceImpl implements EntityService<Organization> {

    private static final int DEFAULT_MAX_HITS = 500;

    private final OrganizationBoService organizationBoService;
    private int maxHitsPerRequest = DEFAULT_MAX_HITS;

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    public OrganizationServiceImpl(OrganizationBoService boService) {
        this.organizationBoService = boService;
    }

    @Override
    public Id create(Organization definition) throws EntityValidationException {
        Id result = null;

        try {
            OrganizationDTO dto = OrganizationTransformer.INSTANCE.toDto(definition);

            gov.nih.nci.po.data.bo.Organization bo
                    = (gov.nih.nci.po.data.bo.Organization) PoXsnapshotHelper.createModel(dto);

            long id = organizationBoService.create(bo);

            Ii ii = new IdConverter.OrgIdConverter().convertToIi(id);

            result = IdTransformer.INSTANCE.toXml(ii);

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }


        return result;
    }

    @Override
    @SuppressWarnings("CPD-START")
    public List<Organization> query(Organization spec, LimitOffset limitOffset) throws TooManyResultsException {
        int limit = limitOffset.getLimit();
        int offset = limitOffset.getOffset();

        if (offset < 0) {
            throw new ServiceException("Offset can not be negative");
        }

        List<Organization> results = new ArrayList<Organization>();


        try {

            OrganizationDTO dto = OrganizationTransformer.INSTANCE.toDto(spec);

            gov.nih.nci.po.data.bo.Organization bo
                    = (gov.nih.nci.po.data.bo.Organization) PoXsnapshotHelper.createModel(dto);

            SearchCriteria<gov.nih.nci.po.data.bo.Organization> criteria
                    = new AnnotatedBeanSearchCriteria<gov.nih.nci.po.data.bo.Organization>(bo);

            PageSortParams<gov.nih.nci.po.data.bo.Organization> pageSortParams
                    = new PageSortParams<gov.nih.nci.po.data.bo.Organization>(
                    limit <= 0 ? Integer.MAX_VALUE : limit,
                    offset,
                    OrganizationSortCriterion.ORGANIZATION_NAME,
                    false);

            List<gov.nih.nci.po.data.bo.Organization> boHits
                    = organizationBoService.search(criteria, pageSortParams);

            //convert to bridge service model
            results.addAll(generateList(boHits));

            if (results.size() > this.maxHitsPerRequest) {
                throw new TooManyResultsException(this.maxHitsPerRequest);
            }

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        }

        return results;
    }

    @Override
    public void update(Organization definition) throws EntityValidationException {
        try {

            Long id = Long.parseLong(definition.getIdentifier().getExtension());
            Validate.notNull(id, "Can not update entity with null id.");

            OrganizationDTO dto = OrganizationTransformer.INSTANCE.toDto(definition);

            gov.nih.nci.po.data.bo.Organization instance
                    = (gov.nih.nci.po.data.bo.Organization) PoXsnapshotHelper.createModel(dto);

            organizationBoService.curate(instance);

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateStatus(Id targetId, Cd statusCode) throws EntityValidationException {

        Long id = Long.parseLong(targetId.getExtension());
        Validate.notNull(id, "Can not update entity with null id.");

        gov.nih.nci.po.data.bo.Organization instance
                = organizationBoService.getById(id);

        EntityStatus newStatus = EntityStatus.valueOf(statusCode.getCode());

        instance.setStatusCode(newStatus);

        try {
            organizationBoService.curate(instance);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public StringMap validate(Organization definition) {
        StringMap result = null;
        try {
            OrganizationDTO dto = OrganizationTransformer.INSTANCE.toDto(definition);

            gov.nih.nci.po.data.bo.Organization instance
                    = (gov.nih.nci.po.data.bo.Organization) PoXsnapshotHelper.createModel(dto);

            Map<String, String[]> errors = organizationBoService.validate(instance);

            result = StringMapHelper.toStringMap(errors);


        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public Organization getById(Id id) throws NullifiedEntityException {
        gov.nih.nci.po.data.bo.Organization hit
                = organizationBoService.getById(Long.parseLong(id.getExtension()));

        if (hit != null) {
            validateNotNullified(hit);
        }

        Organization result = null;
        try {
            result = OrganizationTransformer.INSTANCE.toXml(
                    (OrganizationDTO) PoXsnapshotHelper.createSnapshot(hit)
            );


        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    /**
     * @return The maximum number of hits that can be returned by a query.
     */
    @SuppressWarnings("CPD-END")
    public int getMaxHitsPerRequest() {
        return maxHitsPerRequest;
    }

    /**
     * @param maxHitsPerRequest The maximum number of hits that can be returned by a query.
     */
    public void setMaxHitsPerRequest(int maxHitsPerRequest) {
        this.maxHitsPerRequest = maxHitsPerRequest;
    }

    private List<Organization> generateList(
            Collection<gov.nih.nci.po.data.bo.Organization> boInstances
    ) {
        List<Organization> results = new ArrayList<Organization>();

        for (gov.nih.nci.po.data.bo.Organization hit : boInstances) {
            try {
                results.add(
                        OrganizationTransformer.INSTANCE.toXml(
                                (OrganizationDTO) PoXsnapshotHelper.createSnapshot(hit)
                        )
                );
            } catch (DtoTransformException e) {
                throw new ServiceException(e);
            }
        }

        return results;
    }

    private void validateNotNullified(gov.nih.nci.po.data.bo.Organization item)
            throws NullifiedEntityException {
        List<gov.nih.nci.po.data.bo.Organization> list = new ArrayList<gov.nih.nci.po.data.bo.Organization>();
        list.add(item);
        validateNotNullified(list);
    }

    private void validateNotNullified(Collection<gov.nih.nci.po.data.bo.Organization> items)
            throws NullifiedEntityException {
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();

        IdConverter idConverter = IdConverterRegistry.find(gov.nih.nci.po.data.bo.Organization.class);

        for (gov.nih.nci.po.data.bo.Organization item : items) {

            if (item.getStatusCode() == EntityStatus.NULLIFIED) {
                OrganizationDTO dto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(item);

                Long duplicatedEntityId = null;

                if (item.getDuplicateOf() != null) {
                    duplicatedEntityId = item.getDuplicateOf().getId();
                }

                nullifiedEntities.put(dto.getIdentifier(), idConverter.convertToIi(duplicatedEntityId));
            }

        }

        if (!nullifiedEntities.isEmpty()) {
            throw new NullifiedEntityException(nullifiedEntities);
        }
    }
}
