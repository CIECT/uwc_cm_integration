package za.ac.uwc.www;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.aad.msal4j.*;
import coza.opencollab.sakai.cm.jobs.EnrollmentUpdateJob;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SakaiSoapProxy implements za.ac.uwc.www.SakaiSoap {
  private String _endpoint = null;
  private String _token = null;
  private za.ac.uwc.www.SakaiSoap sakaiSoap = null;

  public String SASI_WEBSERVICE_URL = "uwc.cm.sasi.webservice.url";
  public String PUBLIC_CLIENT_ID =  "uwc.cm.sasi.webservice.PUBLIC_CLIENT_ID";
  public String AUTHORITY =  "uwc.cm.sasi.webservice.AUTHORITY";

  public String TENANT_ID =  "uwc.cm.sasi.webservice.TENANT_ID";
  public String CLIENT_SECRET =  "uwc.cm.sasi.webservice.CLIENT_SECRET";

  public String CLIENT_SCOPE =  "uwc.cm.sasi.webservice.CLIENT_SCOPE";

  private static final Logger log = LoggerFactory.getLogger(EnrollmentUpdateJob.class);

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
    TENANT_ID = serverConfigurationService.getString(TENANT_ID);
    CLIENT_SCOPE = serverConfigurationService.getString(CLIENT_SCOPE);

    log.info("Setting scope.");
    String[] scopes = new String[] { CLIENT_SCOPE }; // Scope required for
    // accessing specific
    // API
    log.info("Setting credentials.");
    ClientSecretCredential credential = new ClientSecretCredentialBuilder()
            .clientId(PUBLIC_CLIENT_ID)
            .clientSecret(CLIENT_SECRET)
            .tenantId(TENANT_ID)
            .build();
    log.info("TokenRequestContext...");
    TokenRequestContext requestContext = new TokenRequestContext().addScopes(scopes);

    log.info("credential.getToken...");


    IAuthenticationResult result;
    try {
      PublicClientApplication application = PublicClientApplication
              .builder("clientId")
              .authority("authority")
              .build();

      SilentParameters parameters = SilentParameters
              .builder(Collections.singleton("scope"))
              .tenant(TENANT_ID)
              .authorityUrl(AUTHORITY)
              .build();

      result = application.acquireTokenSilently(parameters).join();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    if (result != null)
    {
      _token = String.valueOf(result);
    } else {
      _token = credential.getToken(requestContext).block().getToken();
    }
    log.info("Token: " + _token);


    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    String url = "https://az-jhb-uwc-apim-int-test-01.azure-api.net/rest_api/v1/api/DocumentUpload/GetApplicantDocuments/23MO26180O";


    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Authorization", "Bearer " + _token)
            .GET()
            .build();

    CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
            HttpResponse.BodyHandlers.ofString());

    // Handle the response asynchronously
    responseFuture.thenAccept(response -> {
      int statusCode = response.statusCode();
      String responseBody = response.body();
      System.out.println("Response Status Code: " + statusCode);
      System.out.println("Response Body: ");
      System.out.println(responseBody);
    }).join();

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