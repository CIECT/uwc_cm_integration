package za.ac.uwc.www;

import java.net.MalformedURLException;

public class SakaiSoapProxy implements za.ac.uwc.www.SakaiSoap {
  private String _endpoint = null;
  private za.ac.uwc.www.SakaiSoap sakaiSoap = null;
  
  public SakaiSoapProxy() {
    _initSakaiSoapProxy();
  }
  
  public SakaiSoapProxy(String endpoint) {
    _endpoint = endpoint;
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
  
  public za.ac.uwc.www.Download_Faculty_Output download_Faculty(za.ac.uwc.www.Download_Faculty_Input download_FacultyRequest) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_Faculty(download_FacultyRequest);
  }
  
  public za.ac.uwc.www.Download_Department_Output download_Department(za.ac.uwc.www.Download_Department_Input download_DepartmentRequest) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_Department(download_DepartmentRequest);
  }
  
  public za.ac.uwc.www.Download_CalendarGroup_Output download_CalendarGroup(za.ac.uwc.www.Download_CalendarGroup_Input download_CalendarGroupRequest) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_CalendarGroup(download_CalendarGroupRequest);
  }
  
  public za.ac.uwc.www.Download_Students_Output download_Students(za.ac.uwc.www.Download_Students_Input download_StudentsRequest) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_Students(download_StudentsRequest);
  }
  
  public za.ac.uwc.www.Download_CourseChanges_Output download_CourseChanges(za.ac.uwc.www.Download_CourseChanges_Input download_CourseChangesRequest) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_CourseChanges(download_CourseChangesRequest);
  }
  
  public za.ac.uwc.www.Download_Modules_Output download_Modules(za.ac.uwc.www.Download_Modules_Input download_ModulesRequest) throws java.rmi.RemoteException{
    if (sakaiSoap == null)
      _initSakaiSoapProxy();
    return sakaiSoap.download_Modules(download_ModulesRequest);
  }
  
  
}