package za.ac.uwc_academia.www;

public class RequestPortProxy implements za.ac.uwc_academia.www.RequestPort {
  private String _endpoint = null;
  private za.ac.uwc_academia.www.RequestPort requestPort = null;
  
  public RequestPortProxy() {
    _initRequestPortProxy();
  }
  
  public RequestPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initRequestPortProxy();
  }
  
  private void _initRequestPortProxy() {
    try {
      requestPort = (new za.ac.uwc_academia.www.RequestPortServiceLocator()).getRequestPortSoap11();
      if (requestPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)requestPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)requestPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (requestPort != null)
      ((javax.xml.rpc.Stub)requestPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public za.ac.uwc_academia.www.RequestPort getRequestPort() {
    if (requestPort == null)
      _initRequestPortProxy();
    return requestPort;
  }
  
  public za.ac.uwc_academia.www.Download_Students_Output download_Students(za.ac.uwc_academia.www.Download_Students_Input download_StudentsRequest) throws java.rmi.RemoteException{
    if (requestPort == null)
      _initRequestPortProxy();
    return requestPort.download_Students(download_StudentsRequest);
  }
  
  public za.ac.uwc_academia.www.DownloadIntakeOutput download_Intakes(za.ac.uwc_academia.www.Download_Intakes_Input download_IntakeRequest) throws java.rmi.RemoteException{
    if (requestPort == null)
      _initRequestPortProxy();
    return requestPort.download_Intakes(download_IntakeRequest);
  }
  
  public za.ac.uwc_academia.www.DownloadProgramsOutput download_Programs(za.ac.uwc_academia.www.Download_Programs_Input download_ProgramsRequest) throws java.rmi.RemoteException{
    if (requestPort == null)
      _initRequestPortProxy();
    return requestPort.download_Programs(download_ProgramsRequest);
  }
  
  public za.ac.uwc_academia.www.DownloadModulePeriodsOutput download_Module_Periods(za.ac.uwc_academia.www.Download_Module_Periods_Input download_Module_PeriodsRequest) throws java.rmi.RemoteException{
    if (requestPort == null)
      _initRequestPortProxy();
    return requestPort.download_Module_Periods(download_Module_PeriodsRequest);
  }
  
  public za.ac.uwc_academia.www.Download_LecturersOutput download_Lecturers(za.ac.uwc_academia.www.Download_Lecturers_Input download_LecturersRequest) throws java.rmi.RemoteException{
    if (requestPort == null)
      _initRequestPortProxy();
    return requestPort.download_Lecturers(download_LecturersRequest);
  }
  
  
}