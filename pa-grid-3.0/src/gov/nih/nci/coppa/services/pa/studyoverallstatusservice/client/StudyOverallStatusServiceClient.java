/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The PA Grid
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This PA Grid Software License (the License) is between NCI and You. You (or
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
 * its rights in the PA Grid Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the PA Grid Software; (ii) distribute and
 * have distributed to and by third parties the PA Grid Software and any
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
package gov.nih.nci.coppa.services.pa.studyoverallstatusservice.client;

import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.StudyOverallStatus;
import gov.nih.nci.coppa.services.pa.studyoverallstatusservice.common.StudyOverallStatusServiceI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 *
 * @created by Introduce Toolkit version 1.3
 */
public class StudyOverallStatusServiceClient extends StudyOverallStatusServiceClientBase implements StudyOverallStatusServiceI {

    public StudyOverallStatusServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public StudyOverallStatusServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public StudyOverallStatusServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public StudyOverallStatusServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(StudyOverallStatusServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              StudyOverallStatusServiceClient client = new StudyOverallStatusServiceClient(args[1]);

              System.out.println("Testing StudyOverallStatus.copy...");
              try {
                  client.copy(new Id(), new Id());
                  System.out.println("Not yet implemented exception should have been thrown.");
              } catch(RemoteException e) {
                  // expected behavior
              }

              System.out.println("Testing StudyOverallStatus.create...");
              try {
                  client.create(new StudyOverallStatus());
                  System.out.println("Not yet implemented exception should have been thrown.");
              } catch(RemoteException e) {
                  // expected behavior
              }

              System.out.println("Testing StudyOverallStatus.delete...");
              try {
                  client.delete(new Id());
                  System.out.println("Not yet implemented exception should have been thrown.");
              } catch(RemoteException e) {
                  // expected behavior
              }

              System.out.println("Testing StudyOverallStatus.update...");
              try {
                  client.update(new StudyOverallStatus());
                  System.out.println("Not yet implemented exception should have been thrown.");
              } catch(RemoteException e) {
                  // expected behavior
              }

              Id id = new Id();
              id.setExtension("1");
              System.out.println("Testing StudySiteAccrualStatus.getByStudyProtocol...");
              StudyOverallStatus[] rList = client.getByStudyProtocol(id);
              System.out.println(rList);

              System.out.println("Testing StudySiteAccrualStatus.getCurrentByStudyProtocol...");
              StudyOverallStatus result = client.getCurrentByStudyProtocol(id);
              System.out.println(result);

              System.out.println("Testing StudySiteAccrualStatus.get...");
              result = client.get(id);
              System.out.println(result);

              // place client calls here if you want to use this main as a
              // test....
            } else {
                usage();
                System.exit(1);
            }
        } else {
            usage();
            System.exit(1);
        }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

  public gov.nih.nci.coppa.services.pa.StudyOverallStatus[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolResponse boxedResult = portType.getByStudyProtocol(params);
    return boxedResult.getStudyOverallStatus();
    }
  }

  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"copy");
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyRequest params = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyRequest();
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyRequestFromStudyProtocolId fromStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyRequestFromStudyProtocolId();
    fromStudyProtocolIdContainer.setId(fromStudyProtocolId);
    params.setFromStudyProtocolId(fromStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyRequestToStudyProtocolId toStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyRequestToStudyProtocolId();
    toStudyProtocolIdContainer.setId(toStudyProtocolId);
    params.setToStudyProtocolId(toStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyResponse boxedResult = portType.copy(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyOverallStatus getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCurrentByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolResponse boxedResult = portType.getCurrentByStudyProtocol(params);
    return boxedResult.getStudyOverallStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyOverallStatus get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getStudyOverallStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyOverallStatus create(gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateRequestStudyOverallStatus studyOverallStatusContainer = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateRequestStudyOverallStatus();
    studyOverallStatusContainer.setStudyOverallStatus(studyOverallStatus);
    params.setStudyOverallStatus(studyOverallStatusContainer);
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getStudyOverallStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyOverallStatus update(gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateRequestStudyOverallStatus studyOverallStatusContainer = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateRequestStudyOverallStatus();
    studyOverallStatusContainer.setStudyOverallStatus(studyOverallStatus);
    params.setStudyOverallStatus(studyOverallStatusContainer);
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getStudyOverallStatus();
    }
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

}
