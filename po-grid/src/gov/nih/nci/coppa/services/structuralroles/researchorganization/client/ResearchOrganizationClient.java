/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po-grid
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This po-grid Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the po-grid Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po-grid Software; (ii) distribute and 
 * have distributed to and by third parties the po-grid Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.coppa.services.structuralroles.researchorganization.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.client.util.ClientParameterHelper;
import gov.nih.nci.coppa.services.entities.organization.client.OrganizationClient;
import gov.nih.nci.coppa.services.grid.util.GridTestMethod;
import gov.nih.nci.coppa.services.structuralroles.researchorganization.common.ResearchOrganizationI;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.extensions.Id;

import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;
import org.iso._21090.II;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 *
 * @created by Introduce Toolkit version 1.2
 */
public class ResearchOrganizationClient extends ResearchOrganizationClientBase implements ResearchOrganizationI {

    private static ClientParameterHelper<ResearchOrganizationClient> helper = 
        new ClientParameterHelper<ResearchOrganizationClient>(ResearchOrganizationClient.class);
    
    /**
     * The identifier name for for Research org.
     */
    public static final String RESEARCH_ORG_IDENTIFIER_NAME = "Research org research";

    /**
     * The ii root value for Research org.
     */
    public static final String RESEARCH_ORG_ROOT = Constants.NCI_OID + ".4.6";

    public ResearchOrganizationClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public ResearchOrganizationClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public ResearchOrganizationClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public ResearchOrganizationClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }
 
    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try {
            String[] localArgs = new String[] {"-getId", "-playerId", "-playerId2"};          
            helper.setLocalArgs(localArgs);
            helper.setupParams(args);
            
            ResearchOrganizationClient client = new ResearchOrganizationClient(helper.getArgument("-url"));

            for (Method method : helper.getRunMethods()) {
                System.out.println("Running " + method.getName());
                method.invoke(null, client);
            }
                
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    @GridTestMethod
    private static void testUpdate(ResearchOrganizationClient client) throws RemoteException {        
        Id id = new Id();
        id.setRoot(RESEARCH_ORG_ROOT);
        id.setIdentifierName(RESEARCH_ORG_IDENTIFIER_NAME);
        id.setExtension(helper.getArgument("-getId", "1"));
        ResearchOrganization result = client.getById(id);
        
        result.getFundingMechanism().setCode("U01");
        client.update(result);
    }
    
    @GridTestMethod
    private static void getResearchOrg(ResearchOrganizationClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(RESEARCH_ORG_ROOT);
        id.setIdentifierName(RESEARCH_ORG_IDENTIFIER_NAME);
        id.setExtension(helper.getArgument("-getId", "1"));
        ResearchOrganization result = client.getById(id);
        ClientUtils.handleResult(result);
        if (result != null && result.getIdentifier() != null && result.getIdentifier().getItem() != null) {
            for (II ii : result.getIdentifier().getItem()) {
                System.out.println(ToStringBuilder.reflectionToString(ii, ToStringStyle.MULTI_LINE_STYLE));
            }
        }
    }

    @GridTestMethod
    private static void getResearchOrgsByPlayerIds(ResearchOrganizationClient client) {           
        Id id1 = new Id();
        id1.setRoot(OrganizationClient.ORG_ROOT);
        id1.setIdentifierName(OrganizationClient.ORG_IDENTIFIER_NAME);
        id1.setExtension(helper.getArgument("-playerId", "1"));

        Id id2 = new Id();
        id2.setRoot(OrganizationClient.ORG_ROOT);
        id2.setIdentifierName(OrganizationClient.ORG_IDENTIFIER_NAME);
        id2.setExtension(helper.getArgument("-playerId2", "2"));

        try {
            ResearchOrganization[] results = client.getByPlayerIds(new Id[] {id1, id2});
            ClientUtils.handleSearchResults(results);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @GridTestMethod
    private static void searchResearchOrg(ResearchOrganizationClient client) throws RemoteException {
        ResearchOrganization criteria = createCriteria();
        ResearchOrganization[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }

    /**
     * @return
     */
    private static ResearchOrganization createCriteria() {
        ResearchOrganization criteria = new ResearchOrganization();
        CD statusCode = new CD();
        statusCode.setCode("active");
        criteria.setStatus(statusCode);
        return criteria;
    }

    @GridTestMethod
    private static void queryResearchOrg(ResearchOrganizationClient client) throws RemoteException {
        System.out.println("query");
        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(1);
        limitOffset.setOffset(0);
        ResearchOrganization criteria = createCriteria();
        ResearchOrganization[] results = client.query(criteria, limitOffset);
        ClientUtils.handleSearchResults(results);
    }

  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getResearchOrganization();
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] getByIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByIds");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsResponse boxedResult = portType.getByIds(params);
    return boxedResult.getResearchOrganization();
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] search(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getResearchOrganization();
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

  public void update(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] query(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequestLimitOffset();
    limitOffsetContainer.setLimitOffset(limitOffset);
    params.setLimitOffset(limitOffsetContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryResponse boxedResult = portType.query(params);
    return boxedResult.getResearchOrganization();
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] getByPlayerIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByPlayerIds");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsResponse boxedResult = portType.getByPlayerIds(params);
    return boxedResult.getResearchOrganization();
    }
  }

}
