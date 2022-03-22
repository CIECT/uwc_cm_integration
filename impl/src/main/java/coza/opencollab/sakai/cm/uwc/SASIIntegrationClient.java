package coza.opencollab.sakai.cm.uwc;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sakaiproject.component.api.ServerConfigurationService;

import coza.opencollab.sakai.cm.SISAcademicSession;
import coza.opencollab.sakai.cm.SISCanonicalCourse;
import coza.opencollab.sakai.cm.SISClient;
import coza.opencollab.sakai.cm.SISCourseChange;
import coza.opencollab.sakai.cm.SISCourseOffering;
import coza.opencollab.sakai.cm.SISCourseSet;
import coza.opencollab.sakai.cm.SISEnrollment;
import coza.opencollab.sakai.cm.SISEnrollmentSet;
import coza.opencollab.sakai.cm.SISException;
import coza.opencollab.sakai.cm.SISSection;
import coza.opencollab.sakai.cm.SISMembership;
import coza.opencollab.sakai.cm.Utils;

import za.ac.uwc.www.Download_CalendarGroup_Input;
import za.ac.uwc.www.Download_CalendarGroup_Output;
import za.ac.uwc.www.Download_CourseChanges_Input;
import za.ac.uwc.www.Download_CourseChanges_Output;
import za.ac.uwc.www.Download_Department_Input;
import za.ac.uwc.www.Download_Department_Output;
import za.ac.uwc.www.Download_Faculty_Input;
import za.ac.uwc.www.Download_Faculty_Output;
import za.ac.uwc.www.Download_Modules_Input;
import za.ac.uwc.www.Download_Modules_Output;
import za.ac.uwc.www.Download_Students_Input;
import za.ac.uwc.www.Download_Students_Output;
import za.ac.uwc.www.SakaiSoapProxy;
import za.ac.uwc_academia.www.RequestPortProxy;

public class SASIIntegrationClient implements SISClient {
	public static final String CONFIG_YEAR = "uwc.coursemanagement.year";
	public static final String CONFIG_MODULE_LIMIT = "uwc.coursemanagement.module.limit";
	public static final String SASI_WEBSERVICE_URL = "uwc.cm.sasi.webservice.url";
	private ServerConfigurationService serverConfigurationService;
	private SakaiSoapProxy proxy;
	// all the current values
	private Map<String, SISAcademicSession> academicSessions = new HashMap<String, SISAcademicSession>();
	private Map<String, SISAcademicSession> academicSessions2 = new HashMap<String, SISAcademicSession>();
	private Map<String, SASIModule> modules = new HashMap<String, SASIModule>();
	private Map<String, SASIModule> modules2 = new HashMap<String, SASIModule>();
	private Map<SASIModule, List<String>> enrolledStudents = new HashMap<SASIModule, List<String>>();
	private boolean isCourseSetsDone = false;
	private boolean isCanonicalDone = false;
	private boolean limitModules = false;
	private int moduleLimit;
	
	public void setSakaiSoapProxy(SakaiSoapProxy proxy) {
		this.proxy = proxy;
	}

	public void init() {	
		
		moduleLimit = serverConfigurationService.getInt(CONFIG_MODULE_LIMIT, 0);
		if(moduleLimit > 0){
			limitModules = true;
		}

		setSakaiSoapProxy(new SakaiSoapProxy(serverConfigurationService.getString(SASI_WEBSERVICE_URL, "http://sasi.uwc.ac.za/applctns_sakai_WS/sakai.asmx")));
	}

	@Override
	public Collection<SISAcademicSession> getAcademicSessions() {
		ensureAcademicSessions();
		return academicSessions.values();
	}
	
	private void ensureAcademicSessions(){
		if (academicSessions.isEmpty()) {
			getAllAcademicSessions(getAcademicYear());
		}
	}
	
	private SISAcademicSession getAcademicSession(String key){
		ensureAcademicSessions();
		SISAcademicSession as = academicSessions.get(key);
		if(as == null){
			as = academicSessions2.get(key);
		}
		return as;
	}
	
	/**
	 * Get the academic year from sakai.properties, if it is not there the 
	 * default is current year.
	 * 
	 * @see coza.opencollab.sakai.cm.IntegrationClient#getAcademicYear()
	 */
	private String getAcademicYear() {
		int year = serverConfigurationService.getInt(CONFIG_YEAR, 0);
		return year != 0 ? String.valueOf(year) : String.valueOf(Calendar
				.getInstance().get(Calendar.YEAR));
	}
	
	private void ensureModules(){
		if (modules.isEmpty()) {
			getAllModules(getAcademicYear());
		}
	}
	
	private SASIModule getModule(String key){
		ensureModules();
		SASIModule m = modules.get(key);
		if(m == null){
			m = modules2.get(key);
		}
		return m;
	}

	@Override
	public Collection<SISCourseSet> getCourseSets(SISAcademicSession academicSession) {
		ensureModules();
		Set<String> ids = new HashSet<String>();
		for(SASIModule module: modules.values()){
		    if(!module.getAcademicSessionCode().equals(academicSession.getSISCode())){
                //only do modules for this academic session
                continue;
            }
			ids.add(module.getModuleCode().substring(0, 3));
		}
		Set<SISCourseSet> courseSets = new HashSet<SISCourseSet>();
		for(String id: ids){
			SISCourseSet cs = new SISCourseSet(id);
			//XXX Can we set this with better data?
			cs.setId(id);
			cs.setTitle(id);
			cs.setDescription(id);
			cs.setCategory("Module");
			courseSets.add(cs);
		}
		return courseSets;
	}
	
	public Collection<SISMembership> getCourseSetMemberships(SISCourseSet courseSet){
		return null;
	}
	
	@Override
	public Collection<SISCanonicalCourse> getCanonicalCourses(
			SISAcademicSession academicSession) {
		ensureModules();
		Set<SISCanonicalCourse> canonicalCourses = new HashSet<SISCanonicalCourse>();
		for(SASIModule module: modules.values()){
		    if(!module.getAcademicSessionCode().equals(academicSession.getSISCode())){
                //only do modules for this academic session
                continue;
            }
			String id = module.getCanonicalCourseReference();
			SISCanonicalCourse canonicalCourse = new SISCanonicalCourse(id);
			//XXX Can we set this with better values
			canonicalCourse.setId(id);
			canonicalCourse.setTitle(id);
			canonicalCourse.setDescription(id);
			canonicalCourses.add(canonicalCourse);
		}
		return canonicalCourses;
	}

	@Override
	public Collection<SISCourseOffering> getCourseOfferings(
			SISAcademicSession academicSession) {
		ensureModules();
		List<SISCourseOffering> courseOfferings = new ArrayList<SISCourseOffering>();
		for(SASIModule module: modules.values()){
			if(!module.getAcademicSessionCode().equals(academicSession.getSISCode())){
				//only do modules for this academic session
				continue;
			}
			SISCourseOffering courseOffering = new SISCourseOffering(module.getModuleCode());
			//XXX Can we set this with better values
			courseOffering.setId(module.getCourseOfferingReference());
			courseOffering.setTitle(module.getCanonicalCourseReference());
			courseOffering.setDescription(module.getDescription());
			courseOffering.setStatus("Active");
			courseOffering.setAcademicSessionId(academicSession.getId());
			courseOffering.setCanonicalCourseId(module.getCanonicalCourseReference());
			courseOffering.setStartDate(academicSession.getStartDate());
			courseOffering.setEndDate(academicSession.getEndDate());
			courseOffering.addCourseSetIds(module.getModuleCode().substring(0, 3));
			courseOfferings.add(courseOffering);
		}
		return courseOfferings;
	}
	
	@Override
	public Collection<SISMembership> getCourseOfferingMemberships(SISCourseOffering courseOffering){
		List<SISMembership> memberships = new ArrayList<SISMembership>();
		SASIModule module = getModule(courseOffering.getId());
		for (String studentId : getEnrolledStudents(module)) {
			SISMembership membership = new SISMembership(studentId);
			membership.setUserId(studentId);
			membership.setRole("S");
			membership.setStatus("Active");
			memberships.add(membership);
		}
		return memberships;
	}

	@Override
	public Collection<SISEnrollmentSet> getEnrollmentSets(SISCourseOffering courseOffering){
		SASIModule module = getModule(courseOffering.getSISCode());
		List<SISEnrollmentSet> enrollmentSets = new ArrayList<SISEnrollmentSet>();
		SISEnrollmentSet enrollmentSet = new SISEnrollmentSet(module.getEnrollmentSetReference());
		enrollmentSet.setId(module.getEnrollmentSetReference());
		enrollmentSet.setTitle(module.getEnrollmentSetReference());
		enrollmentSet.setDescription(module.getEnrollmentSetReference());
		enrollmentSet.setCategory("Students");
		enrollmentSet.setDefaultEnrollmentCredits("0");
		enrollmentSet.setCourseOfferingEid(courseOffering.getId());
		enrollmentSet.setOfficialInstructors(null);
		enrollmentSets.add(enrollmentSet);
		return enrollmentSets;
	}

	@Override
	public Collection<SISEnrollment> getEnrollments(SISEnrollmentSet enrollmentSet){
		List<SISEnrollment> enrollments = new ArrayList<SISEnrollment>();
		SASIModule module = getModule(enrollmentSet.getCourseOfferingEid());
		
		for (String studentId : getEnrolledStudents(module)) {
			SISEnrollment enrollment = new SISEnrollment(studentId);
			enrollment.setStudentId(studentId);
			enrollment.setEnrollmentSetId(enrollmentSet.getId());
			enrollment.setStatus("enrolled");
			enrollment.setCredits("0");
			enrollment.setGradingScheme("standard");
			enrollments.add(enrollment);
		}
		return enrollments;
	}

	@Override
	public Collection<SISSection> getSections(SISCourseOffering courseOffering){
		SASIModule module = getModule(courseOffering.getId());
		SISSection section = new SISSection(module.getCourseOfferingReference());
		section.setId(module.getCourseOfferingReference());
		section.setTitle(module.getCourseOfferingReference());
		section.setDescription(module.getCourseOfferingReference() + " Lecture");
		section.setCategoryCode("LCT");
		section.setCategoryDescription("Lecture");
		section.setParentSectionId(null);
		section.setCourseOfferingId(courseOffering.getId());
		section.setEnrollmentSetId(module.getEnrollmentSetReference());
		List<SISSection> ss = new ArrayList<SISSection>();
		ss.add(section);
		return ss;
	}
	
	@Override
	public Collection<SISMembership> getSectionMemberships(SISSection section){
		List<SISMembership> memberships = new ArrayList<SISMembership>();
		SASIModule module = getModule(section.getCourseOfferingId());
		for (String studentId : getEnrolledStudents(module)) {
			SISMembership membership = new SISMembership(studentId);
			membership.setUserId(studentId);
			membership.setRole("S");
			membership.setStatus("Active");
			memberships.add(membership);
		}
		return memberships;
	}

	private Map<String, SISAcademicSession> getAllAcademicSessions(String year) {
		Download_CalendarGroup_Input academicInput = new Download_CalendarGroup_Input();
		academicInput.setYear(BigInteger.valueOf(Long.valueOf(year)));
		Download_CalendarGroup_Output calendarOutput = null;
		SISAcademicSession academicSession = null;
		try {
			calendarOutput = proxy.getSakaiSoap().download_CalendarGroup(
					academicInput);
			String[][] academicCalendarArray = calendarOutput
					.getCalendar_Group_List();
			for (int i = 0; i < academicCalendarArray.length; i++) {
				if(academicCalendarArray[i].length < 5){
					break;
				}
				academicSession = new SISAcademicSession(
						academicCalendarArray[i][1],
						academicCalendarArray[i][1] + " " + academicCalendarArray[i][0],
						academicCalendarArray[i][2] + " " + academicCalendarArray[i][0],
						academicCalendarArray[i][0],
						Utils.getDateFromString(academicCalendarArray[i][3]),
						Utils.getDateFromString(academicCalendarArray[i][4]),
						"Academic Session for " + academicCalendarArray[i][0] + " "
						+ academicCalendarArray[i][2]);
				
				academicSessions.put(academicSession.getSISCode(), academicSession);
				academicSessions2.put(academicSession.getId(), academicSession);
			}
			return academicSessions;
		} catch (RemoteException e) {
			throw new SISException(e);
		}
	}

	private Map<String, SASIModule> getAllModules(String year) {
		Download_Modules_Input moduleInput = new Download_Modules_Input();
		Download_Modules_Output moduleOutput = null;
		moduleInput.setYear(BigInteger.valueOf(Long.valueOf(year)));
		List<String> deptCodeList = getDepartmentCodeList();
		SASIModule module = null;
		try {
			allout: for (String deptCode : deptCodeList) {
				moduleInput.setDepartment_code(deptCode);
				moduleOutput = proxy.getSakaiSoap().download_Modules(
						moduleInput);
				String[][] moduleArray = moduleOutput.getModule_List();
				for (int i = 0; i < moduleArray.length; i++) {
					if(limitModules && modules.size() > moduleLimit) break allout;
					if(moduleArray[i].length < 6){
						break;
					}
					module = new SASIModule();
					module.setModuleCode(moduleArray[i][3]);
					module.setDescription(moduleArray[i][4]);
					module.setAcademicSessionCode(moduleArray[i][5]);
					if (module.getModuleCode() != null) {
						module.setAcademicSession(getAcademicSession(module.getAcademicSessionCode()));
						modules.put(module.getModuleCode(), module);
						modules2.put(module.getCourseOfferingReference(), module);
					}
				}
			}
			return modules;
		} catch (RemoteException e) {
			throw new SISException(e);
		}
	}

	private List<String> getDepartmentCodeList() {
		List<String> facultyCodeList = getFacultyCodeList(getFaculties());
		return getDepartmentCodeList(getDepartments(facultyCodeList));
	}

	private List<Faculty> getFaculties() {
		List<Faculty> facultyList = new ArrayList<Faculty>();
		try {
			Download_Faculty_Output facultyOutput = proxy.getSakaiSoap()
					.download_Faculty(new Download_Faculty_Input());
			String[][] facultyArray = facultyOutput.getFaculty_List();
			Faculty faculty = null;
			for (int i = 0; i < facultyArray.length; i++) {
				if(facultyArray[i].length < 1){
					break;
				}
				faculty = new Faculty();
				faculty.setFacultyCode(facultyArray[i][0]);
				facultyList.add(faculty);
			}
			return facultyList;
		} catch (RemoteException e) {
			throw new SISException(e);
		}
	}

	private List<String> getFacultyCodeList(List<Faculty> facultyList) {
		List<String> facultyCodeList = new ArrayList<String>();
		for (Faculty integrationFaculty : facultyList) {
			if (integrationFaculty.getFacultyCode() != null
					&& integrationFaculty.getFacultyCode() != "") {
				facultyCodeList.add(integrationFaculty.getFacultyCode());
			}
		}
		return facultyCodeList;
	}

	private List<Department> getDepartments(List<String> facultyCodeList) {
		List<Department> departmentList = new ArrayList<Department>();
		Download_Department_Input departmentInput = new Download_Department_Input();
		Department department = null;
		for (String facultyCode : facultyCodeList) {
			departmentInput.setFaculty(facultyCode);
			try {
				Download_Department_Output departmentOutput = proxy
						.getSakaiSoap().download_Department(departmentInput);
				String[][] departmentArray = departmentOutput
						.getDepartment_List();
				for (int i = 0; i < departmentArray.length; i++) {
					if(departmentArray[i].length < 1){
						break;
					}
					department = new Department();
					department.setDepartmentCode(departmentArray[i][0]);
					departmentList.add(department);
				}
			} catch (RemoteException e) {
				throw new SISException(e);
			}
		}
		return departmentList;
	}

	private List<String> getDepartmentCodeList(List<Department> departmentList) {
		List<String> deptCodeList = new ArrayList<String>();
		for (Department integrationDepartment : departmentList) {
			if (integrationDepartment.getDepartmentCode() != null
					&& integrationDepartment.getDepartmentCode() != "") {
				deptCodeList.add(integrationDepartment.getDepartmentCode());
			}
		}
		return deptCodeList;
	}

	private List<String> getEnrolledStudents(SASIModule module) {
		List<String> studentClassList = enrolledStudents.get(module);
		if(studentClassList != null){
			return studentClassList;
		}
		studentClassList = new ArrayList<String>();
		Download_Students_Input classListInput = new Download_Students_Input();
		Download_Students_Output classListOutput = null;
		classListInput.setYear(BigInteger.valueOf(Long.valueOf(module.getAcademicSession().getYear())));
		classListInput.setModule_code(module.getModuleCode());
		try {
			classListOutput = proxy.getSakaiSoap().download_Students(
					classListInput);
			String[][] studentArray = classListOutput.getStudent_List();
			for (int i = 0; i < studentArray.length; i++) {
				if(studentArray[i].length < 1){
					break;
				}
				studentClassList.add(studentArray[i][0]);
			}
			enrolledStudents.put(module, studentClassList);
			return studentClassList;
		} catch (RemoteException e) {
			throw new SISException(e);
		}
	}

	@Override
	public Collection<SISCourseChange> getCourseChanges() {
		List<SISCourseChange> courseChangeList = new ArrayList<SISCourseChange>();
		Download_CourseChanges_Input courseChangeInput = new Download_CourseChanges_Input();
		courseChangeInput.setYear(BigInteger.valueOf(Long.valueOf(getAcademicYear())));
		Download_CourseChanges_Output courseChangeOutput = null;
		SISCourseChange courseChange = null;
		try {
			courseChangeOutput = proxy.getSakaiSoap().download_CourseChanges(
					courseChangeInput);
			String[][] courseChangeArray = courseChangeOutput
					.getCourse_Changes_List();
			for (int i = 0; i < courseChangeArray.length; i++) {
				if(courseChangeArray[i].length < 3){
					break;
				}

				courseChange = new SISCourseChange();
				courseChange.setStudentNum(courseChangeArray[i][0]);
				courseChange.setModuleCode(courseChangeArray[i][1]);
				courseChange.setMaintananceIndicator(courseChangeArray[i][2]);
                if (courseChange.getModuleCode() != null) {
                    courseChangeList.add(courseChange);
                }
			}
			return courseChangeList;
		} catch (RemoteException e) {
			throw new SISException(e);
		}
	}
	
	public void setServerConfigurationService(
			ServerConfigurationService serverConfigurationService) {
		this.serverConfigurationService = serverConfigurationService;
	}

	@Override
	public SISCourseOffering getCourseOffering(SISAcademicSession academicSession, SISCanonicalCourse canonicalCourse,
			String courseSetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SISMembership> getSISMemberships(Collection<String> studentList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getStudentList(SISCourseOffering courseOffering) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getLecturerList(SISCourseOffering courseOffering) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SISEnrollmentSet getEnrollmentSet(SISCourseOffering courseOffering) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SISEnrollment> getEnrollments(SISEnrollmentSet enrollmentSet, Collection<String> studentList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SISSection getSection(SISCourseOffering courseOffering) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void destroy() {
//
//		academicSessions.clear();
//		academicSessions2.clear();
//		modules.clear();
//		modules2.clear();
//		enrolledStudents.clear();	
//	}
}
