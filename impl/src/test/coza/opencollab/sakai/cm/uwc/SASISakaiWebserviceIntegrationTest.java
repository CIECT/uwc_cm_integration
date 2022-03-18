package coza.opencollab.sakai.cm.uwc;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import za.ac.uwc.www.Download_CalendarGroup_Input;
import za.ac.uwc.www.Download_CalendarGroup_Output;
import za.ac.uwc.www.Download_Department_Input;
import za.ac.uwc.www.Download_Department_Output;
import za.ac.uwc.www.Download_Faculty_Input;
import za.ac.uwc.www.Download_Faculty_Output;
import za.ac.uwc.www.Download_Modules_Input;
import za.ac.uwc.www.Download_Modules_Output;
import za.ac.uwc.www.Download_Students_Input;
import za.ac.uwc.www.Download_Students_Output;
import za.ac.uwc.www.SakaiSoapProxy;

public class SASISakaiWebserviceIntegrationTest {

    private static boolean writeToFile = false;

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        SakaiSoapProxy proxy = new SakaiSoapProxy(
                "http://196.11.235.60/applctns_sakai_WS/sakai.asmx");
        //Academic Sessions
        getSASIAcademicSessions(proxy, currentYear);
        //Faculties
//        List<String> facultyCodeList = getSASIFaculties(proxy);
//        //Faculties
//        List<String> deptCodeList = getSASIDepartments(proxy, facultyCodeList);
//        //Modules
//        List<String> moduleCodeList = getSASIModules(proxy, deptCodeList, currentYear);
//        //Enrolled Students
//        getSASIEnrolledStudents(proxy, moduleCodeList, currentYear);
        //        getModules(proxy, deptCodeList, currentYear);
    }

    private static void getModules(SakaiSoapProxy proxy, List<String> deptCodeList, int currentYear) {
        System.out.println("*****   UWC Modules   ********");
        System.out.println("");
        Download_Modules_Input moduleInput = new Download_Modules_Input();
        moduleInput.setYear(BigInteger.valueOf(currentYear));
        Download_Modules_Output moduleOutput = null;
        StringBuilder stringBuilder = new StringBuilder("");
        List<String> moduleCodeList = new ArrayList<String>();
        try {
            for (String deptCode : deptCodeList) {
                moduleInput.setDepartment_code(deptCode);
                moduleOutput = proxy.getSakaiSoap().download_Modules(moduleInput);
                String[][] moduleArray = moduleOutput.getModule_List();
                for (int i = 0; i < moduleArray.length; i++) {
                    //                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < moduleArray[i].length; j++) {
                        //                    for (int j = 0; j < 5; j++) {
                        String module = moduleArray[i][j];
                        if (j == 0) {
                            moduleCodeList.add(module);
                        }
                        stringBuilder.append(module);
                        if (j != moduleArray[i].length) {
                            stringBuilder.append(", ");
                        }
                    }
                    System.out.println(stringBuilder);
                    stringBuilder = new StringBuilder("");
                }
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
        sortModuleList(moduleCodeList);
    }

    private static void sortModuleList(List<String> moduleCodeList) {
        Collections.sort(moduleCodeList, new Comparator<String>() {

            public int compare(String moduleCode1, String moduleCode2) {
                return moduleCode1.compareTo(moduleCode2);
            }
        });
    }

    private static void getSASIEnrolledStudents(SakaiSoapProxy proxy, List<String> moduleCodeList,
            int currentYear) {
        System.out.println("*****   UWC Enrolled Students   ********");
        System.out.println("");
        Download_Students_Input classListInput = new Download_Students_Input();
        Download_Students_Output classListOutput = null;
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            if (writeToFile) {
                out = new FileOutputStream("EnrolledStudents2.txt");
                p = new PrintStream(out);
            }
            boolean added = false;
            for (String moduleCode : moduleCodeList) {
                System.out.println("*****  Module: " + moduleCode);
                classListInput.setYear(BigInteger.valueOf(currentYear));
                classListInput.setModule_code(moduleCode);
                try {
                    classListOutput = proxy.getSakaiSoap().download_Students(classListInput);
                    String[][] studentArray = classListOutput.getStudent_List();
                    for (int i = 0; i < studentArray.length; i++) {
                        for (int j = 0; j < studentArray[i].length; j++) {
                            String student = studentArray[i][j];
                            if (!student.equals("")) {
                                stringBuilder.append(student);
                                if (j != studentArray[i].length) {
                                    stringBuilder.append(", ");
                                }
                            }
                        }
                        System.out.println(stringBuilder);
                        if (writeToFile && !stringBuilder.toString().equals("")) {
                            if (!added) {
                                added = true;
                                p.println("Module: " + moduleCode);
                            }
                            p.println(stringBuilder);
                        }
                        stringBuilder = new StringBuilder("");
                    }
                }
                catch (RemoteException e) {
                    // TODO Auto-generated catch block
                }
                added = false;
                System.out.println("");
            }
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (p != null) {
                p.close();
            }
        }
        System.out.println("");
        System.out.println("");
    }

    private static List<String> getSASIModules(SakaiSoapProxy proxy, List<String> deptCodeList,
            int currentYear) {
        List<String> moduleCodeList = new ArrayList<String>();
        System.out.println("*****   UWC Modules   ********");
        System.out.println("");
        Download_Modules_Input moduleInput = new Download_Modules_Input();
        Download_Modules_Output moduleOutput = null;
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            if (writeToFile) {
                out = new FileOutputStream("Modules2.txt");
                p = new PrintStream(out);
            }
            for (String deptCode : deptCodeList) {
                //                System.out.println("*****  Factulty: " + facultyCode);
                moduleInput.setYear(BigInteger.valueOf(currentYear));
                //                moduleInput.setFaculty(facultyCode);
                System.out.println("*****  Department: " + deptCode);
                moduleInput.setDepartment_code(deptCode);
                try {
                    moduleOutput = proxy.getSakaiSoap().download_Modules(moduleInput);
                    String[][] moduleArray = moduleOutput.getModule_List();
                    for (int i = 0; i < moduleArray.length; i++) {
                        //                for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < moduleArray[i].length; j++) {
                            //                    for (int j = 0; j < 5; j++) {
                            String module = moduleArray[i][j];
                            stringBuilder.append(module);
                            if (j == 3) {
                                moduleCodeList.add(module);
                            }
                            if (j != moduleArray[i].length) {
                                stringBuilder.append(", ");
                            }
                        }
                        System.out.println(stringBuilder);
                        if (writeToFile) {
                            p.println(stringBuilder);
                        }
                        stringBuilder = new StringBuilder("");
                    }
                }
                catch (RemoteException e) {
                    // TODO Auto-generated catch block
                }
                System.out.println("");
            }
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (p != null) {
                p.close();
            }
        }
        System.out.println("");
        System.out.println("");
        return moduleCodeList;
    }

    private static List<String> getSASIDepartments(SakaiSoapProxy proxy,
            List<String> facultyCodeList) {
        List<String> deptCodeList = new ArrayList<String>();
        System.out.println("*****   UWC Departments   ********");
        System.out.println("");
        StringBuilder stringBuilder = new StringBuilder("");
        Download_Department_Input departmentInput = new Download_Department_Input();
        Download_Department_Output departmentOutput = null;
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        try {
            if (writeToFile) {
                out = new FileOutputStream("Departments2.txt");
                p = new PrintStream(out);
            }
            for (String facultyCode : facultyCodeList) {
                System.out.println("*****  Factulty: " + facultyCode);
                //        departmentInput.setDepartment("*");
                departmentInput.setFaculty(facultyCode);
                try {
                    departmentOutput = proxy.getSakaiSoap().download_Department(departmentInput);
                    String[][] departmentArray = departmentOutput.getDepartment_List();
                    for (int i = 0; i < departmentArray.length; i++) {
                        //                for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < departmentArray[i].length; j++) {
                            //                    for (int j = 0; j < 5; j++) {
                            String department = departmentArray[i][j];
                            if (j == 0) {
                                deptCodeList.add(department);
                            }
                            stringBuilder.append(department);
                            if (j != departmentArray[i].length) {
                                stringBuilder.append(", ");
                            }
                        }
                        System.out.println(stringBuilder);
                        if (writeToFile) {
                            p.println(stringBuilder);
                        }
                        stringBuilder = new StringBuilder("");
                    }
                }
                catch (RemoteException e) {
                    // TODO Auto-generated catch block
                }
                System.out.println("");
            }
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (p != null) {
                p.close();
            }
        }
        System.out.println("");
        System.out.println("");
        return deptCodeList;
    }

    private static List<String> getSASIFaculties(SakaiSoapProxy proxy) {
        List<String> facultyCodeList = new ArrayList<String>();
        System.out.println("*****   UWC Faculties   ********");
        System.out.println("");
        StringBuilder stringBuilder = new StringBuilder("");
        Download_Faculty_Input facultyInput = new Download_Faculty_Input();
        Download_Faculty_Output facultyOutput = null;
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        try {
            if (writeToFile) {
                out = new FileOutputStream("Faculties2.txt");
                p = new PrintStream(out);
            }
            facultyOutput = proxy.getSakaiSoap().download_Faculty(facultyInput);
            String[][] facultyArray = facultyOutput.getFaculty_List();
            for (int i = 0; i < facultyArray.length; i++) {
                //            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < facultyArray[i].length; j++) {
                    //                for (int j = 0; j < 5; j++) {
                    String faculty = facultyArray[i][j];
                    if (j == 0) {
                        facultyCodeList.add(faculty);
                    }
                    stringBuilder.append(faculty);
                    if (j != facultyArray[i].length) {
                        stringBuilder.append(", ");
                    }
                }
                System.out.println(stringBuilder);
                if (writeToFile) {
                    p.println(stringBuilder);
                }
                stringBuilder = new StringBuilder("");
            }
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (p != null) {
                p.close();
            }
        }
        System.out.println("");
        System.out.println("");
        return facultyCodeList;
    }

    private static void getSASIAcademicSessions(SakaiSoapProxy proxy, int currentYear) {
        Download_CalendarGroup_Input academicInput = new Download_CalendarGroup_Input();
        academicInput.setYear(BigInteger.valueOf(currentYear));
        Download_CalendarGroup_Output calendarOutput = null;
        System.out.println("*****   UWC Academic Sessions   ********");
        System.out.println("*****   Year: " + currentYear);
        System.out.println("");
        StringBuilder stringBuilder = new StringBuilder("");
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        try {
            if (writeToFile) {
                out = new FileOutputStream("AcademicSession2.txt");
                p = new PrintStream(out);
            }
            calendarOutput = proxy.getSakaiSoap().download_CalendarGroup(academicInput);
            String[][] academicCalendarArray = calendarOutput.getCalendar_Group_List();
            for (int i = 0; i < academicCalendarArray.length; i++) {
                //            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < academicCalendarArray[i].length; j++) {
                    //                for (int j = 0; j < 5; j++) {
                    String academicCalendarSession = academicCalendarArray[i][j];
                    stringBuilder.append(academicCalendarSession);
                    if (j != academicCalendarArray[i].length) {
                        stringBuilder.append(", ");
                    }
                }
                System.out.println(stringBuilder);
                if (writeToFile) {
                    p.println(stringBuilder);
                }
                stringBuilder = new StringBuilder("");
            }
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (p != null) {
                p.close();
            }
        }
        System.out.println("");
        System.out.println("");
    }

    private void writeToFile(String fileName, String content) {
        FileOutputStream out; // declare a file output object
        PrintStream p; // declare a print stream object
        try {
            // Create a new file output stream
            // connected to "DevFile.txt"
            // Connect print stream to the output stream
        }
        catch (Exception e) {
            System.err.println("Error writing to file");
        }
    }
}
