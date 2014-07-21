package gov.nih.nci.webservices.rest.client;

import gov.nih.nci.po.ws.common.types.Family;
import gov.nih.nci.po.ws.common.types.FamilyList;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

/**
 * Rest client for FamilyService
 * 
 * @author vinodh.rc
 * 
 */
public class FamilyRestServiceClientImpl implements FamilyRestServiceClient {

    private static final Logger LOG = Logger
            .getLogger(FamilyRestServiceClientImpl.class);

    /**
     * RestClient instance
     */
    private RestClient restClient;
    /**
     * RestClient properties placeholder instance
     */
    private RestClientProperties restClientProperties;

    /**
     * Constructor
     * 
     * @param restClient
     *            - RestClient instance
     * @param restClientProperties
     *            - RestClient properties placeholder instance
     */
    public FamilyRestServiceClientImpl(RestClient restClient,
            RestClientProperties restClientProperties) {
        super();
        this.restClient = restClient;
        this.restClientProperties = restClientProperties;
    }

    /*
     * {@inheritDoc}
     * 
     * @see
     * gov.nih.nci.webservices.rest.client.RestClientInterface#getRestClient()
     */
    @Override
    public RestClient getRestClient() {
        return restClient;
    }

    /*
     * {@inheritDoc}
     * 
     * @see
     * gov.nih.nci.webservices.rest.client.RestClientInterface#setRestClient
     * (gov.nih.nci.webservices.rest.client.RestClient)
     */
    @Override
    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /*
     * {@inheritDoc}
     * 
     * @see gov.nih.nci.webservices.rest.client.RestClientInterface#
     * getRestClientProperties()
     */
    @Override
    public RestClientProperties getRestClientProperties() {
        return restClientProperties;
    }

    /*
     * {@inheritDoc}
     * 
     * @see gov.nih.nci.webservices.rest.client.RestClientInterface#
     * setRestClientProperties
     * (gov.nih.nci.webservices.rest.client.RestClientProperties)
     */
    @Override
    public void setRestClientProperties(
            RestClientProperties restClientProperties) {
        this.restClientProperties = restClientProperties;
    }

    /**
     * {@inheritDoc}
     * 
     * @see gov.nih.nci.webservices.rest.client.FamilyRestServiceClient#search
     *      (gov.nih.nci.po.ws.common.types.Family)
     */
    @Override
    public List<Family> search(Family family) { // , LimitOffset
        String fsUrl = getRestClientProperties().getFamilyServiceUrl();

        if (family != null) {
            fsUrl = fsUrl + "?";
        }

        MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
        if (family.getId() != null) {
            vars.add("id", family.getId().toString());
            fsUrl = fsUrl + "id=" + family.getId().toString();
        }
        if (family.getName() != null) {
            vars.add("name", family.getName());
            fsUrl = fsUrl + "name=" + family.getName();
        }
        if (family.getMember() != null && !family.getMember().isEmpty()) {
            vars.add(
                    "organizationId",
                    String.valueOf(family.getMember().get(0)
                            .getOrganizationId()));
            fsUrl = fsUrl + "organizationId="
                    + family.getMember().get(0).getOrganizationId();
        }

        HttpEntity<MultiValueMap<String, String>> request = restClient
                .getJsonHttpHeaders(vars);

        ResponseEntity<FamilyList> output;
        try {
            output = getRestClient().getRestTemplate().exchange(fsUrl,
                    HttpMethod.GET, request, FamilyList.class, vars);
        } catch (RestClientException e) {
            LOG.info(e.getMessage(), e.getMostSpecificCause());
            return new ArrayList<Family>();
        }

        if (output.getStatusCode() == HttpStatus.OK) {
            FamilyList fl = output.getBody();
            return fl.getFamily();
        } else {
            LOG.info("Search returned with "
                    + output.getStatusCode().toString());
            return new ArrayList<Family>();
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see gov.nih.nci.webservices.rest.client.FamilyRestServiceClient#getFamily(java.lang.String)
     */
    @Override
    public Family getFamily(String id) {
        String fsUrl = getRestClientProperties().getFamilyServiceUrl();

        MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
        if (id != null) {
            vars.add("id", id);
            fsUrl = fsUrl + "?id=" + id;
        }

        Family family = null;
        ResponseEntity<FamilyList> output;
        try {
            HttpEntity<MultiValueMap<String, String>> request = restClient
                    .getJsonHttpHeaders(vars);
            output = getRestClient().getRestTemplate().exchange(fsUrl,
                    HttpMethod.GET, request, FamilyList.class, vars);
        } catch (RestClientException e) {
            LOG.info(e.getMessage(), e.getMostSpecificCause());
            return family;
        }
        if (output.getStatusCode() == HttpStatus.OK) {
            FamilyList fl = output.getBody();
            if (fl.getFamily() != null && !fl.getFamily().isEmpty()) {
                family = fl.getFamily().get(0);
            }
        } else {
            LOG.info("Search returned with "
                    + output.getStatusCode().toString());
            return family;
        }
        return family;
    }

    // public FamilyOrganizationRelationshipDTO
    // getFamilyOrganizationRelationship (String familyOrgRelId) {
    // return
    // gov.nih.nci.pa.util.PoRegistry.getFamilyService().getFamilyOrganizationRelationship(
    // IiConverter.convertToPoFamilyOrgRelationshipIi(familyOrgRelId.toString()));
    // }
    //
    // public List<FamilyOrganizationRelationshipDTO>
    // getActiveRelationships(Long id) {
    // return
    // gov.nih.nci.pa.util.PoRegistry.getFamilyService().getActiveRelationships(id);
    // }
    //
    //
    // public Map<Ii, FamilyDTO> getFamilies(Set<Ii> famOrgRelIiList) {
    // return
    // gov.nih.nci.pa.util.PoRegistry.getFamilyService().getFamilies(famOrgRelIiList);
    // }
    //
    // public FamilyP30DTO getP30Grant(Long id) {
    // return gov.nih.nci.pa.util.PoRegistry.getFamilyService().getP30Grant(id);
    // }

}
