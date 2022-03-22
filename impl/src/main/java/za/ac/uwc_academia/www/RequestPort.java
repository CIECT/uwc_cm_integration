/**
 * RequestPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc_academia.www;

public interface RequestPort extends java.rmi.Remote {
    public za.ac.uwc_academia.www.Download_Students_Output download_Students(za.ac.uwc_academia.www.Download_Students_Input download_StudentsRequest) throws java.rmi.RemoteException;
    public za.ac.uwc_academia.www.DownloadIntakeOutput download_Intakes(za.ac.uwc_academia.www.Download_Intakes_Input download_IntakeRequest) throws java.rmi.RemoteException;
    public za.ac.uwc_academia.www.DownloadProgramsOutput download_Programs(za.ac.uwc_academia.www.Download_Programs_Input download_ProgramsRequest) throws java.rmi.RemoteException;
    public za.ac.uwc_academia.www.DownloadModulePeriodsOutput download_Module_Periods(za.ac.uwc_academia.www.Download_Module_Periods_Input download_Module_PeriodsRequest) throws java.rmi.RemoteException;
    public za.ac.uwc_academia.www.Download_LecturersOutput download_Lecturers(za.ac.uwc_academia.www.Download_Lecturers_Input download_LecturersRequest) throws java.rmi.RemoteException;
}
