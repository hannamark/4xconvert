package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.StringMapType;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiDsetConverter;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 * @param <XML_TYPE> The type of the XML object.
 * @param <DTO_TYPE> The type of the DTO object.
 * @param <BO_TYPE> The type of the BO object.
 */
public abstract class AbstractRoleService
        <
                XML_TYPE extends gov.nih.nci.coppa.po.Correlation,
                DTO_TYPE extends CorrelationDto,
                BO_TYPE extends Correlation
        >
        implements RoleService<XML_TYPE> {

    private static final int DEFAULT_MAX_HITS_PER_REQUEST = 500;

    private int maxHitsPerRequest = DEFAULT_MAX_HITS_PER_REQUEST;

    private final GenericStructrualRoleServiceLocal<BO_TYPE> boService;

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    protected AbstractRoleService(GenericStructrualRoleServiceLocal<BO_TYPE> boService) {
        this.boService = boService;
    }


    @Override
    public Id create(XML_TYPE definition) throws EntityValidationException {

        Id result = null;

        try {
            DTO_TYPE dto = getTransformer().toDto(definition);

            BO_TYPE bo = (BO_TYPE) PoXsnapshotHelper.createModel(dto);

            long id = boService.create(bo);

            Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(id);

            result = IdTransformer.INSTANCE.toXml(ii);

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }


        return result;
    }


    @Override
    public List<XML_TYPE> query(XML_TYPE spec, LimitOffset limitOffset) throws TooManyResultsException {
        int limit = limitOffset.getLimit();
        int offset = limitOffset.getOffset();

        if (offset < 0) {
            throw new ServiceException("Offset can not be negative");
        }

        List<XML_TYPE> results = new ArrayList<XML_TYPE>();


        try {

            DTO_TYPE dto =  getTransformer().toDto(spec);

            BO_TYPE bo = (BO_TYPE) PoXsnapshotHelper.createModel(dto);

            SearchCriteria<BO_TYPE> criteria
                    = new AnnotatedBeanSearchCriteria<BO_TYPE>(bo);

            PageSortParams<BO_TYPE> pageSortParams
                    = new PageSortParams<BO_TYPE>(
                    limit <= 0 ? Integer.MAX_VALUE : limit,
                    offset,
                    getSortCriterion(),
                    false);

            List<BO_TYPE> boHits = boService.search(criteria, pageSortParams);

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
    public void update(XML_TYPE definition) throws EntityValidationException {

        try {
            DTO_TYPE dto = getTransformer().toDto(definition);

            BO_TYPE instance = (BO_TYPE) PoXsnapshotHelper.createModel(dto);

            boService.curate(instance);

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }
    }



    @Override
    public void updateStatus(Id targetId, Cd statusCode) throws EntityValidationException {
        BO_TYPE instance = boService.getById(Long.parseLong(targetId.getExtension()));

        RoleStatus newStatus = RoleStatus.valueOf(statusCode.getCode());

        instance.setStatus(newStatus);

        try {
            boService.curate(instance);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public StringMap validate(XML_TYPE definition) {

        StringMap result = new StringMap();
        try {
            DTO_TYPE dto = getTransformer().toDto(definition);

            BO_TYPE instance = (BO_TYPE) PoXsnapshotHelper.createModel(dto);

            Map<String, String[]> errors = boService.validate(instance);

            for (Map.Entry<String, String[]> entry : errors.entrySet()) {
                StringMapType.Entry errorEntry = new StringMapType.Entry();
                errorEntry.setKey(entry.getKey());
                errorEntry.getValue().addAll(Arrays.asList(entry.getValue()));
                result.getEntry().add(errorEntry);
            }

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        }


        return result;
    }

    @Override
    public List<XML_TYPE> getByPlayerIds(List<Id> ids) {

        Set<Long> idSet = new HashSet<Long>();
        for (Id id : ids) {
            idSet.add(Long.parseLong(id.getExtension()));
        }

        List<BO_TYPE> hits = boService.getByPlayerIds(idSet.toArray(new Long[idSet.size()]));

        return generateList(hits);
    }

    @Override
    public XML_TYPE getById(Id id) throws NullifiedRoleException {

        BO_TYPE hit = boService.getById(Long.parseLong(id.getExtension()));

        if (hit != null) {
            validateNotNullified(hit);
        }

        XML_TYPE result = null;
        try {
            result = getTransformer().toXml(
                    (DTO_TYPE) PoXsnapshotHelper.createSnapshot(hit)
            );


        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public List<XML_TYPE> getByIds(List<Id> ids) throws NullifiedRoleException {


        Set<Long> idSet = new HashSet<Long>();
        for (Id id : ids) {
            idSet.add(Long.parseLong(id.getExtension()));
        }
        List<BO_TYPE> hits = boService.getByIds(idSet.toArray(new Long[idSet.size()]));

        validateNotNullified(hits);

        List<XML_TYPE> result = generateList(hits);

        return result;
    }





    /**
     * Used to replicate behaviour of remote EJB.
     * @return The max number of hits allowed.
     */
    public int getMaxHitsPerRequest() {
        return maxHitsPerRequest;
    }

    /**
     * Used to replicate behaviour of remote EJB.
     * @param maxHitsPerRequest The max number of hits allowed.
     */
    public void setMaxHitsPerRequest(int maxHitsPerRequest) {
        this.maxHitsPerRequest = maxHitsPerRequest;
    }

    private List<XML_TYPE> generateList(
            Collection<BO_TYPE> boInstances
    ) {
        List<XML_TYPE> results = new ArrayList<XML_TYPE>();

        for (BO_TYPE hit : boInstances) {
            try {
                results.add(
                        getTransformer().toXml(
                                (DTO_TYPE) PoXsnapshotHelper.createSnapshot(hit)
                        )
                );
            } catch (DtoTransformException e) {
                throw new ServiceException(e);
            }
        }

        return results;
    }


    private void validateNotNullified(BO_TYPE item)
            throws NullifiedRoleException {
        List<BO_TYPE> list = new ArrayList<BO_TYPE>();
        list.add(item);
        validateNotNullified(list);
    }

    private void validateNotNullified(Collection<BO_TYPE> items)
            throws NullifiedRoleException {
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();

        for (BO_TYPE item : items) {

            if (item.getStatus() == RoleStatus.NULLIFIED) {
                DTO_TYPE dto = (DTO_TYPE) PoXsnapshotHelper.createSnapshot(item);
                nullifiedEntities.put(IiDsetConverter.convertToIi(dto.getIdentifier()), dto.getDuplicateOf());
            }

        }

        if (!nullifiedEntities.isEmpty()) {
            throw new NullifiedRoleException(nullifiedEntities);
        }
    }

    /**
     *
     * @return  The appropriate DTO transformer.
     */
    protected abstract Transformer<XML_TYPE, DTO_TYPE>  getTransformer();

    /**
     *
     * @return The appropriate sort criterion to use in a query.
     */
    protected abstract SortCriterion<BO_TYPE> getSortCriterion();
}
