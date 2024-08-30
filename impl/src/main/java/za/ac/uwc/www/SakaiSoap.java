/**
 * SakaiSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public interface SakaiSoap extends java.rmi.Remote {

    /**
     * Web Service method for Download_Faculty.<inputParameters><inputParameter
     * name='Faculty' length='1' hostDataType='alpha' /></inputParameters>
     */
    public za.ac.uwc.www.Download_Faculty_Output download_Faculty(Download_Faculty_Input download_FacultyRequest, String s) throws java.rmi.RemoteException;

    /**
     * Web Service method for Download_Department.<inputParameters><inputParameter
     * name='Department' length='1' hostDataType='alpha' /><inputParameter
     * name='Department_code' length='6' hostDataType='alpha' /><inputParameter
     * name='Faculty' length='3' hostDataType='alpha' /></inputParameters>
     */
    public za.ac.uwc.www.Download_Department_Output download_Department(Download_Department_Input download_DepartmentRequest, String _token) throws java.rmi.RemoteException;

    /**
     * Web Service method for Download_CalendarGroup.<inputParameters><inputParameter
     * name='Year' length='4' hostDataType='number' /><inputParameter name='Calendar_Group'
     * length='1' hostDataType='alpha' /></inputParameters>
     */
    public za.ac.uwc.www.Download_CalendarGroup_Output download_CalendarGroup(Download_CalendarGroup_Input download_CalendarGroupRequest, String _token) throws java.rmi.RemoteException;

    /**
     * Web Service method for Download_Students.<inputParameters><inputParameter
     * name='Module_code' length='9' hostDataType='alpha' /><inputParameter
     * name='Student' length='1' hostDataType='alpha' /><inputParameter name='Year'
     * length='4' hostDataType='number' /></inputParameters>
     */
    public za.ac.uwc.www.Download_Students_Output download_Students(Download_Students_Input download_StudentsRequest, String _token) throws java.rmi.RemoteException;

    /**
     * Web Service method for Download_CourseChanges.<inputParameters><inputParameter
     * name='Year' length='4' hostDataType='number' /><inputParameter name='Course_Changes'
     * length='1' hostDataType='alpha' /></inputParameters>
     */
    public za.ac.uwc.www.Download_CourseChanges_Output download_CourseChanges(Download_CourseChanges_Input download_CourseChangesRequest, String _token) throws java.rmi.RemoteException;

    /**
     * Web Service method for Download_Modules.<inputParameters><inputParameter
     * name='Year' length='4' hostDataType='number' /><inputParameter name='Department_code'
     * length='6' hostDataType='alpha' /><inputParameter name='Module_code'
     * length='9' hostDataType='alpha' /><inputParameter name='Module' length='1'
     * hostDataType='alpha' /></inputParameters>
     */
    public za.ac.uwc.www.Download_Modules_Output download_Modules(Download_Modules_Input download_ModulesRequest, String _token) throws java.rmi.RemoteException;
}
