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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import za.ac.uwc_academia.www.DownloadIntakeOutput;
import za.ac.uwc_academia.www.DownloadModulePeriodsOutput;
import za.ac.uwc_academia.www.DownloadProgramsOutput;
import za.ac.uwc_academia.www.Download_Intakes_Input;
import za.ac.uwc_academia.www.Download_LecturersOutput;
import za.ac.uwc_academia.www.Download_Lecturers_Input;
import za.ac.uwc_academia.www.Download_Module_Periods_Input;
import za.ac.uwc_academia.www.Download_Programs_Input;
import za.ac.uwc_academia.www.Download_Students_Input;
import za.ac.uwc_academia.www.Download_Students_Output;
import za.ac.uwc_academia.www.RequestPortProxy;

/**
 * 
 * @author User
 *
 */
public class AcademiaSakaiWebserviceIntegrationTest {

    private static boolean writeToFile = false;
    
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        RequestPortProxy proxy = new RequestPortProxy(
                "http://uwc-demo.southafricanorth.cloudapp.azure.com:8091/academia-ws/RequestDefinition.wsdl");
        
        //Academic Sessions
        System.out.println("*** Academia intakes ***");
        System.out.println("*** Year: " + currentYear);
        System.out.println("");
        String[][] academicCalendarArray = getAcademiaIntakes(proxy, currentYear);        

        for (int i = 0; i < academicCalendarArray.length; i++) {
            System.out.println(" ************************************************************************* ");
        	String intakeCode = academicCalendarArray[i][1];
			System.out.println("	IntakeCode = " + intakeCode);
    		System.out.println("");
        	
            //Programs - Sakai modules
    		System.out.println("*** Academia Programs  ****");
    		System.out.println("");
        	String[][] programArray = getAcademiaProgramsForIntake(proxy, intakeCode, currentYear);        	

	        if(programArray == null || programArray.length == 0) {
		        System.out.println("*** programArray == null ***");
	        	continue;
	        }	   
	        
	        //Module Periods for Program - Sakai Rosters
            System.out.println("*** Academia Module Periods for Program  ***");
    		System.out.println("");
	        String[][] modulePeriodArray = getAcademiaModulePeriodsForProgram(proxy, intakeCode, currentYear);
	        
	        if(modulePeriodArray == null || modulePeriodArray.length == 0) {
		        System.out.println("*** modulePeriodArray == null ***");
	        	continue;
	        }	

	        for (int s = 0; s < programArray.length; s++) {
	        	String modulePeriodCode = programArray[s][2]; // Module Period Code
	        	String programCode = programArray[s][3]; // Program/Module Code		      		        

		        for (int t = 0; t < modulePeriodArray.length; t++) {
		        	String modulePeriodCodeArrayVal = modulePeriodArray[t][0];
		        	
		        	if(StringUtils.equals(modulePeriodCode, modulePeriodCodeArrayVal)) {
				        //Enrolled Students for Intake/Program/Module Period	        
				        System.out.println("*** Academia Enrolled Students ***");
				        System.out.println("");
				        getAcademiaEnrolledStudentsForIntakeProgramPeriod(proxy, intakeCode, programCode, modulePeriodCode, currentYear);
		        	}
		        }   
		        
		        //Lecturers for Intake/Program	        
		        System.out.println("*** Academia Lecturers ***");
		        System.out.println("");
		        getAcademiaLecturersForIntakeProgramCode(proxy, intakeCode, programCode, currentYear);
	        }
        }
    }

//    public static void main(String[] args) {
//        Calendar cal = Calendar.getInstance();
//        int currentYear = cal.get(Calendar.YEAR);
//        RequestPortProxy proxy = new RequestPortProxy(
//                "http://uwc-demo.southafricanorth.cloudapp.azure.com:8091/academia-ws/RequestDefinition.wsdl");
//        
//        //Academic Sessions
//        System.out.println("*** Academia intakes ***");
//        System.out.println("*** Year: " + currentYear);
//        System.out.println("");
//        String[][] academicCalendarArray = getAcademiaIntakes(proxy, currentYear);        
//
//        for (int i = 0; i < academicCalendarArray.length; i++) {
//            System.out.println(" ************************************************************************* ");
//        	String intakeCode = academicCalendarArray[i][1];
//			System.out.println("	IntakeCode = " + intakeCode);
//    		System.out.println("");
//        	
//            //Programs - Sakai modules
//    		System.out.println("*** Academia Programs  ****");
//    		System.out.println("");
//        	String[][] programArray = getAcademiaProgramsForIntake(proxy, intakeCode, currentYear);        	
//
//	        if(programArray == null || programArray.length == 0) {
//		        System.out.println("*** programArray == null ***");
//	        	continue;
//	        }
//	        
//	      //Module Periods for Program - Sakai Rosters
//            System.out.println("*** Academia Module Periods for Program  ***");
//    		System.out.println("");
//	        String[][] modulePeriodArray = getAcademiaModulePeriodsForProgramPeriod(proxy, intakeCode, currentYear);
//	        
//	        if(modulePeriodArray == null || modulePeriodArray.length == 0) {
//		        System.out.println("*** modulePeriodArray == null ***");
//	        	continue;
//	        }
//
////	        //Module Periods for Program - Sakai Rosters
////            System.out.println("*** Academia Module Periods for Program  ***");
////    		System.out.println("");
////	        String[][] modulePeriodArray = getAcademiaModulePeriodsForProgram(proxy, intakeCode, currentYear);
////	        
////	        if(modulePeriodArray == null || modulePeriodArray.length == 0) {
////		        System.out.println("*** modulePeriodArray == null ***");
////	        	continue;
////	        }
//	        
//	        //Enrolled Students for Intake/Program/Module Period	        
//	        System.out.println("*** Academia Enrolled Students ***");
//	        System.out.println("");
//	        getAcademiaEnrolledStudentsForIntakeProgramPeriod(proxy, intakeCode, programArray, modulePeriodArray, currentYear);
//	        
//	        //Lecturers for Intake/Program	        
//	        System.out.println("*** Academia Lecturers ***");
//	        System.out.println("");
//	        getAcademiaLecturersForIntakeProgram(proxy, intakeCode, programArray, currentYear);
//        }
//                
//        
//        //Programs - Sakai modules
////        Map<String, String[][]> programMap = getAcademiaPrograms(proxy, academicCalendarArray, currentYear);
//        
//        //Module Periods - Sakai Rosters
////        String[][] modulePerdiodArray = getAcademiaModulePeriods(proxy, programArray, currentYear);
//
//        //Enrolled Students
////        getAcademiaEnrolledStudents(proxy, modulePerdiodArray, currentYear);
//        //        getModules(proxy, deptCodeList, currentYear);
//    }


    private static void getAcademiaLecturersForIntakeProgramCode(RequestPortProxy proxy, String intakeCode,
			String programCode, int currentYear) {
		Download_Lecturers_Input lecturersInput = null;
		Download_LecturersOutput lecturersListOutput = null;
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			if (writeToFile) {
				out = new FileOutputStream("Lecturers.txt");
				p = new PrintStream(out);
			}
			boolean added = false;
			int rowCounter = 1;

			lecturersInput = new Download_Lecturers_Input();
			lecturersInput.setYear(BigInteger.valueOf(currentYear));
			lecturersInput.setIntakeCode(new BigInteger(intakeCode));
			lecturersInput.setModuleCode(programCode);

			System.out.println("	Academia Lecturers - Intake = " + intakeCode + "; Program = " + programCode);

			try {
				lecturersListOutput = proxy.getRequestPort().download_Lecturers(lecturersInput);
				String[][] lecturerArray = lecturersListOutput.getLecturers_List();
				for (int k = 0; k < lecturerArray.length; k++) {
					for (int l = 0; l < lecturerArray[k].length; l++) {
						String lecturer = lecturerArray[k][l];
						if (!lecturer.equals("")) {
							stringBuilder.append(lecturer);
							if (l + 1 < lecturerArray[k].length) {
								stringBuilder.append(", ");
							}
						}
					}
					System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
					rowCounter++;
					if (writeToFile && !stringBuilder.toString().equals("")) {
//								if (!added) {
//									added = true;
//									p.println("StudentList: " + moduleCode);
//								}
						p.println(stringBuilder);
					}
					stringBuilder = new StringBuilder("");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
			}
			added = false;
//					System.out.println("");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (p != null) {
				p.close();
			}
		}
//		System.out.println("");
		System.out.println("");
	}

	private static void getAcademiaLecturersForIntakeProgram(RequestPortProxy proxy, String intakeCode,
			String[][] programArray, int currentYear) {
		Download_Lecturers_Input lecturersInput = null;
		Download_LecturersOutput lecturersListOutput = null;
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			if (writeToFile) {
				out = new FileOutputStream("Lecturers.txt");
				p = new PrintStream(out);
			}
			boolean added = false;
			for (int i = 0; i < programArray.length; i++) {
				int rowCounter = 1;
				String programCode = programArray[i][3]; // moduleCode

				lecturersInput = new Download_Lecturers_Input();
				lecturersInput.setYear(BigInteger.valueOf(currentYear));
				lecturersInput.setIntakeCode(new BigInteger(intakeCode));
				lecturersInput.setModuleCode(programCode);

				System.out.println("	Academia Lecturers - Intake = " + intakeCode + "; Program = " + programCode);

				try {
					lecturersListOutput = proxy.getRequestPort().download_Lecturers(lecturersInput);
					String[][] lecturerArray = lecturersListOutput.getLecturers_List();
					for (int k = 0; k < lecturerArray.length; k++) {
						for (int l = 0; l < lecturerArray[k].length; l++) {
							String lecturer = lecturerArray[k][l];
							if (!lecturer.equals("")) {
								stringBuilder.append(lecturer);
								if (l + 1 < lecturerArray[k].length) {
									stringBuilder.append(", ");
								}
							}
						}
						System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
						rowCounter++;
						if (writeToFile && !stringBuilder.toString().equals("")) {
//								if (!added) {
//									added = true;
//									p.println("StudentList: " + moduleCode);
//								}
							p.println(stringBuilder);
						}
						stringBuilder = new StringBuilder("");
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
				}
				added = false;
//					System.out.println("");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (p != null) {
				p.close();
			}
		}
//		System.out.println("");
		System.out.println("");
	}

	private static void getAcademiaEnrolledStudentsForIntakeProgramPeriod(RequestPortProxy proxy, String intakeCode,
			String programCode, String modulePeriodCode, int currentYear) {

		Download_Students_Input studentsInput = null;
		Download_Students_Output studentsListOutput = null;
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			if (writeToFile) {
				out = new FileOutputStream("EnrolledStudents2.txt");
				p = new PrintStream(out);
			}
			boolean added = false;
			int rowCounter = 1;

			studentsInput = new Download_Students_Input();
			studentsInput.setYear(BigInteger.valueOf(currentYear));
			studentsInput.setIntakeCode(intakeCode);
			studentsInput.setModuleCode(programCode);
			studentsInput.setPeriodCode(modulePeriodCode);

			System.out.println("	Academia Enrolled Students - Intake = " + intakeCode + "; Program = " + programCode
					+ "; Period = " + modulePeriodCode);

			try {
				studentsListOutput = proxy.getRequestPort().download_Students(studentsInput);
				String[][] studentArray = studentsListOutput.getStudent_List();
				for (int k = 0; k < studentArray.length; k++) {
					for (int l = 0; l < studentArray[k].length; l++) {
						String student = studentArray[k][l];
						if (!student.equals("")) {
							stringBuilder.append(student);
							if (l + 1 < studentArray[k].length) {
								stringBuilder.append(", ");
							}
						}
					}
					System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
					rowCounter++;
					if (writeToFile && !stringBuilder.toString().equals("")) {
//								if (!added) {
//									added = true;
//									p.println("StudentList: " + moduleCode);
//								}
						p.println(stringBuilder);
					}
					stringBuilder = new StringBuilder("");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
			}
			added = false;
//					System.out.println("");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (p != null) {
				p.close();
			}
		}
//		System.out.println("");
		System.out.println("");
	}

	private static void getAcademiaEnrolledStudentsForIntakeProgramPeriod(RequestPortProxy proxy, String intakeCode, String[][] programArray, 
			String[][] modulePeriodArray, int currentYear) {
		Download_Students_Input studentsInput = null;
		Download_Students_Output studentsListOutput = null;
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			if (writeToFile) {
				out = new FileOutputStream("EnrolledStudents2.txt");
				p = new PrintStream(out);
			}
			boolean added = false;
			for (int i = 0; i < programArray.length; i++) {
				int rowCounter = 1;
				String programCode = programArray[i][3]; // moduleCode
				for (int j = 0; j < modulePeriodArray.length; j++) {
					String modulePeriodCode = modulePeriodArray[j][0];

					studentsInput = new Download_Students_Input();
					studentsInput.setYear(BigInteger.valueOf(currentYear));
					studentsInput.setIntakeCode(intakeCode);
					studentsInput.setModuleCode(programCode);
					studentsInput.setPeriodCode(modulePeriodCode);					

					System.out.println("	Academia Enrolled Students - Intake = " + intakeCode + "; Program = " + programCode + "; Period = " + modulePeriodCode);

					try {
						studentsListOutput = proxy.getRequestPort().download_Students(studentsInput);
						String[][] studentArray = studentsListOutput.getStudent_List();
						for (int k = 0; k < studentArray.length; k++) {
							for (int l = 0; l < studentArray[k].length; l++) {
								String student = studentArray[k][l];
								if (!student.equals("")) {
									stringBuilder.append(student);
		                  			if (l + 1 < studentArray[k].length) {
										stringBuilder.append(", ");
									}
								}
							}
							System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
							rowCounter++;
							if (writeToFile && !stringBuilder.toString().equals("")) {
//								if (!added) {
//									added = true;
//									p.println("StudentList: " + moduleCode);
//								}
								p.println(stringBuilder);
							}
							stringBuilder = new StringBuilder("");
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
					}
					added = false;
//					System.out.println("");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (p != null) {
				p.close();
			}
		}
//		System.out.println("");
		System.out.println("");
	}

    private static void getAcademiaEnrolledStudents(RequestPortProxy proxy, String[][] modulePerdiodArray, int currentYear) {
        System.out.println("*** Academia Enrolled Students ***");
        System.out.println("");
        Download_Students_Input studentsInput = new Download_Students_Input();
        Download_Students_Output studentsListOutput = null;
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            if (writeToFile) {
                out = new FileOutputStream("EnrolledStudents2.txt");
                p = new PrintStream(out);
            }
            boolean added = false;

            for (int i = 0; i < modulePerdiodArray.length; i++) {
                int rowCounter = 1;

            	String modulePeriod = modulePerdiodArray[i][1];
            
            	studentsInput.setYear(BigInteger.valueOf(currentYear));
//				studentsInput.setIntakeCode(intakeCode);
//				studentsInput.setModuleCode(programCode);
//				studentsInput.setPeriodCode(modulePeriodCode);				
//                try {
//                	studentsListOutput = proxy.getRequestPort().download_Students(studentsInput);
//                    String[][] studentArray = studentsListOutput.getStudent_List();
//                    for (int i = 0; i < studentArray.length; i++) {
//                        for (int j = 0; j < studentArray[i].length; j++) {
//                            String student = studentArray[i][j];
//                            if (!student.equals("")) {
//                                stringBuilder.append(student);
//                  			  if (k + 1 < studentArray[i].length) {
//                                    stringBuilder.append(", ");
//                                }
//                            }
//                        }
//                        System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
//                        rowCounter++;
//                        if (writeToFile && !stringBuilder.toString().equals("")) {
//                            if (!added) {
//                                added = true;
//                                p.println("Module: " + moduleCode);
//                            }
//                            p.println(stringBuilder);
//                        }
//                        stringBuilder = new StringBuilder("");
//                    }
//                }
//                catch (RemoteException e) {
//                    // TODO Auto-generated catch block
//                }
//                added = false;
//                System.out.println("");
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
    

    private static String[][]  getAcademiaModulePeriodsForProgram(RequestPortProxy proxy, String intakeCode, int currentYear) {
        StringBuilder stringBuilder = new StringBuilder("");
        Download_Module_Periods_Input modulePerdiodsInput = null;
        DownloadModulePeriodsOutput modulePerdiodsOutput = null;
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        String[][] modulePeriodArray = null;
		try {
			if (writeToFile) {
				out = new FileOutputStream("ModulePeriods2.txt");
				p = new PrintStream(out);
			}

			if (StringUtils.isNotBlank(intakeCode)) {
				modulePerdiodsInput = new Download_Module_Periods_Input();
				modulePerdiodsInput.setYear(BigInteger.valueOf(currentYear));
				modulePerdiodsInput.setIntakeCode(new BigInteger(intakeCode));
				try {
					modulePerdiodsOutput = proxy.getRequestPort().download_Module_Periods(modulePerdiodsInput);
					modulePeriodArray = modulePerdiodsOutput.getModule_Period_List();
					for (int j = 0; j < modulePeriodArray.length; j++) {
						int rowCounter = 1;
						for (int k = 0; k < modulePeriodArray[j].length; k++) {
							String modulePeriod = modulePeriodArray[j][k];
							stringBuilder.append(modulePeriod);
        					if (k + 1 < modulePeriodArray[j].length) {
								stringBuilder.append(", ");
							}
						}
						System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
						rowCounter++;
						if (writeToFile) {
							p.println(stringBuilder);
						}
						stringBuilder = new StringBuilder("");
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
				}
//				System.out.println("");
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
//        System.out.println("");
        System.out.println("");
        return modulePeriodArray;
    }

    private static String[][] getAcademiaModulePeriods(RequestPortProxy proxy, String[][] programArray, int currentYear) {
        System.out.println("*** Academia Module Periods  ***");
        System.out.println("");
        StringBuilder stringBuilder = new StringBuilder("");
        Download_Module_Periods_Input modulePerdiodsInput = null;
        DownloadModulePeriodsOutput modulePerdiodsOutput = null;
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        String[][] modulePeriodArray = null;
        try {
            if (writeToFile) {
                out = new FileOutputStream("ModulePeriods2.txt");
                p = new PrintStream(out);
            }

            modulePerdiodsInput = new Download_Module_Periods_Input();
            modulePerdiodsInput.setYear(BigInteger.valueOf(currentYear));

            for (int i = 0; i < programArray.length; i++) {
                int rowCounter = 1;

            	String intakeCode = programArray[i][1];
            	if(StringUtils.isNotBlank(intakeCode)) {
            		modulePerdiodsInput.setIntakeCode(new BigInteger(intakeCode));
                    try {
                    	modulePerdiodsOutput = proxy.getRequestPort().download_Module_Periods(modulePerdiodsInput);
                        modulePeriodArray = modulePerdiodsOutput.getModule_Period_List();
                        for (int j = 0; j < modulePeriodArray.length; j++) {
                            for (int k = 0; k < modulePeriodArray[j].length; k++) {
                                String modulePeriod = modulePeriodArray[j][k];
                                stringBuilder.append(modulePeriod);
            					if (k + 1 < modulePeriodArray[j].length) {
                                    stringBuilder.append(", ");
                                }
                            }
                            System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
                            rowCounter++;
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
        return modulePeriodArray;
    }

	private static String[][]  getAcademiaProgramsForIntake(RequestPortProxy proxy, String intakeCode,
			int currentYear) {
		StringBuilder stringBuilder = new StringBuilder("");
		Download_Programs_Input programInput = null;
		DownloadProgramsOutput programOutput = null;
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object
		String[][] programsArray = null;
		try {
			if (writeToFile) {
				out = new FileOutputStream("Programs2.txt");
				p = new PrintStream(out);
			}

			if (StringUtils.isNotBlank(intakeCode)) {
				programInput = new Download_Programs_Input();
				programInput.setYear(BigInteger.valueOf(currentYear));
				programInput.setIntakeCode(new BigInteger(intakeCode));

				programOutput = proxy.getRequestPort().download_Programs(programInput);
				programsArray = programOutput.getProgram_List();

				if (programsArray == null) {
					return null;
				}

				for (int j = 0; j < programsArray.length; j++) {
	                int rowCounter = 1;
					for (int k = 0; k < programsArray[j].length; k++) {
						String program = programsArray[j][k];
						stringBuilder.append(program);
						if (k + 1 < programsArray[j].length) {
							stringBuilder.append(", ");
						}
					}
					System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
					rowCounter++;
					if (writeToFile) {
						p.println(stringBuilder);
					}
					stringBuilder = new StringBuilder("");
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (p != null) {
				p.close();
			}
		}
//		System.out.println("");
		System.out.println("");
		return programsArray;
	}
    
    private static Map<String, String[][]> getAcademiaPrograms(RequestPortProxy proxy, String [][] intakeList, int currentYear) {
        System.out.println("*** Academia Programs  ****");
        System.out.println("");
        StringBuilder stringBuilder = new StringBuilder("");
        Download_Programs_Input programInput = null;
        DownloadProgramsOutput programOutput = null;
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        Map<String, String[][]> programsResultMap = new HashMap<String, String[][]>();
        try {
            if (writeToFile) {
                out = new FileOutputStream("Programs2.txt");
                p = new PrintStream(out);
            }
            
            programInput = new Download_Programs_Input();
            programInput.setYear(BigInteger.valueOf(currentYear));
            
            for (int i = 0; i < intakeList.length; i++) {
                int rowCounter = 1;
            	String intakeCode = intakeList[i][1];
            	if(StringUtils.isNotBlank(intakeCode)) {
                	programInput.setIntakeCode(new BigInteger(intakeCode));                	
                	programOutput = proxy.getRequestPort().download_Programs(programInput);
                	String[][] programsArray = programOutput.getProgram_List();
                	
                	if(programsArray == null || programsArray.length == 0) {
                		continue;
                	}
                	programsResultMap.put(intakeCode, programsArray);
                	
                    for (int j = 0; j < programsArray.length; j++) {
                        for (int k = 0; k < programsArray[j].length; k++) {
                            String program = programsArray[j][k];
                            stringBuilder.append(program);
                            if (k + 1 < programsArray[j].length) {
                                stringBuilder.append(", ");
                            }
                        }
                        System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
                        rowCounter++;
                        if (writeToFile) {
                            p.println(stringBuilder);
                        }
                        stringBuilder = new StringBuilder("");
                    }
            	}
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
        return programsResultMap;
    }
    
    private static String[][] getAcademiaIntakes(RequestPortProxy proxy, int currentYear) {
    	Download_Intakes_Input intakesInput = new Download_Intakes_Input();
    	intakesInput.setYear(BigInteger.valueOf(currentYear));
        DownloadIntakeOutput intakeOutput = null;
        StringBuilder stringBuilder = new StringBuilder("");
        FileOutputStream out; // declare a file output object
        PrintStream p = null; // declare a print stream object
        String[][] intakeArray = null;
        int rowCounter = 1;
        try {
            if (writeToFile) {
                out = new FileOutputStream("AcademicSession2.txt");
                p = new PrintStream(out);
            }
            intakeOutput = proxy.getRequestPort().download_Intakes(intakesInput);
    		intakeArray = intakeOutput.getIntake_List();
            for (int i = 0; i < intakeArray.length; i++) {
                for (int j = 0; j < intakeArray[i].length; j++) {
                    String academicCalendarSession = intakeArray[i][j];
                    stringBuilder.append(academicCalendarSession);
                    if (j + 1 < intakeArray[i].length) {
                        stringBuilder.append(", ");
                    }
                }
                System.out.println("	Row #" + rowCounter + " -> " + stringBuilder);
                rowCounter++;
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
		return intakeArray;
    }

    private static void sortModuleList(List<String> moduleCodeList) {
        Collections.sort(moduleCodeList, new Comparator<String>() {

            public int compare(String moduleCode1, String moduleCode2) {
                return moduleCode1.compareTo(moduleCode2);
            }
        });
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
