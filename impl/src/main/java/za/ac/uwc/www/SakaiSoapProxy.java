package za.ac.uwc.www;

import com.microsoft.aad.msal4j.*;
import org.sakaiproject.component.api.ServerConfigurationService;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SakaiSoapProxy implements za.ac.uwc.www.SakaiSoap {
  private String _endpoint = null;
  private String _token = null;
  private za.ac.uwc.www.SakaiSoap sakaiSoap = null;

  private String clientId = "";
  private IClientCredential credential = null;
  private Set<String> scope; // Replace with the appropriate scope for your API
  private String sakaiUrl = "";
  private SakaiSoapProxy sakaiSoapProxy;
  private IAuthenticationResult _Authtoken;

  public String PUBLIC_CLIENT_ID =  "uwc.cm.sasi.webservice.PUBLIC_CLIENT_ID";
  public String AUTHORITY =  "uwc.cm.sasi.webservice.AUTHORITY";
  public String CLIENT_SECRET =  "uwc.cm.sasi.webservice.CLIENT_SECRET";

  private ServerConfigurationService serverConfigurationService;
  
  public SakaiSoapProxy() {
    _initSakaiSoapProxy();
  }
  
  public SakaiSoapProxy(String endpoint, String token) {
    _endpoint = endpoint;
    _token = token;
    _initSakaiSoapProxy();
  }

  private void _initSakaiSoapProxy() {
    try {
        sakaiSoap = (new za.ac.uwc.www.SakaiLocator()).getsakaiSoap(new java.net.URL(_endpoint));
      if (sakaiSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sakaiSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sakaiSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
        if (_token != null) {

        }
        else
          setToken();
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
    catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
  
  public String getEndpoint() {
    return _endpoint;
  }

  public String getToken() {
    return _token;
  }

  public void setToken() throws MalformedURLException {

    PUBLIC_CLIENT_ID = serverConfigurationService.getString(PUBLIC_CLIENT_ID);
    AUTHORITY = serverConfigurationService.getString(AUTHORITY);
    CLIENT_SECRET = serverConfigurationService.getString(CLIENT_SECRET);

    IClientCredential credential = ClientCredentialFactory.createFromSecret(CLIENT_SECRET);
    ConfidentialClientApplication app = ConfidentialClientApplication.builder(PUBLIC_CLIENT_ID, credential).authority(AUTHORITY).build();
    Set<String> scopes = Set.of("api://a84227f1-0376-4f21-914a-82aff9fde5a5/ApiServices.Use");
    System.out.println("#####" + scopes.getClass().getName());
   // ClientCredentialParameters credentials = ClientCredentialParameters.builder(scopes).build();
    CompletableFuture<IAuthenticationResult> future = app.acquireToken(ClientCredentialParameters.builder(scopes).build());
    future.handle((authenticationResult, throwable) -> {
      if( throwable != null ) {
        System.out.println("throwable = " + throwable);
        return null;
      }

      _token = authenticationResult.accessToken();
      System.out.println("accessToken = " + _token);
      return _token;
    });

    System.out.println(_token);
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sakaiSoap != null)
      ((javax.xml.rpc.Stub)sakaiSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public za.ac.uwc.www.SakaiSoap getSakaiSoap() {
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap;
  }
  
  public za.ac.uwc.www.Download_Faculty_Output download_Faculty(Download_Faculty_Input download_FacultyRequest, String _token) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_Faculty(download_FacultyRequest, _token);
  }
  
  public za.ac.uwc.www.Download_Department_Output download_Department(Download_Department_Input download_DepartmentRequest, String _token) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_Department(download_DepartmentRequest, _token);
  }
  
  public za.ac.uwc.www.Download_CalendarGroup_Output download_CalendarGroup(Download_CalendarGroup_Input download_CalendarGroupRequest, String _token) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_CalendarGroup(download_CalendarGroupRequest, _token);
  }
  
  public za.ac.uwc.www.Download_Students_Output download_Students(Download_Students_Input download_StudentsRequest, String _token) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_Students(download_StudentsRequest, _token);
  }
  
  public za.ac.uwc.www.Download_CourseChanges_Output download_CourseChanges(za.ac.uwc.www.Download_CourseChanges_Input download_CourseChangesRequest, String _token) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_CourseChanges(download_CourseChangesRequest, _token);
  }
  
  public za.ac.uwc.www.Download_Modules_Output download_Modules(za.ac.uwc.www.Download_Modules_Input download_ModulesRequest, String _token) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_Modules(download_ModulesRequest, _token);
  }
  
  
}