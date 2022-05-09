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

import org.apache.commons.lang.StringUtils;
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
import coza.opencollab.sakai.cm.SISMembership;
import coza.opencollab.sakai.cm.SISSection;
import coza.opencollab.sakai.cm.Utils;
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
public class AcademiaIntegrationClient implements SISClient {

	public static final String ACADEMIA_CONFIG_YEAR = "uwc.cm.academia.year";
	public static final String ACADEMIA_WEBSERVICE_URL = "uwc.cm.academia.webservice.url";
	
	private ServerConfigurationService serverConfigurationService;
	private RequestPortProxy proxy;

	private Map<String, SISAcademicSession> academicSessions = new HashMap<String, SISAcademicSession>();
	private Map<String, AcademiaModule> modules = new HashMap<String, AcademiaModule>();

	public void setRequestPortProxy(RequestPortProxy proxy) {
		this.proxy = proxy;
	}

	@Override
	public void init() {
		setRequestPortProxy(new RequestPortProxy(serverConfigurationService.getString(ACADEMIA_WEBSERVICE_URL, 
				"http://uwc-demo.southafricanorth.cloudapp.azure.com:8091/academia-ws/RequestDefinition.wsdl")));
	}

	@Override
	public Collection<SISAcademicSession> getAcademicSessions() {
		if (academicSessions.isEmpty()) {
			populateAcademicSessions(getAcademicYear());
		}
		return academicSessions.values();
	}

	private SISAcademicSession getAcademicSession(String key) {
		return academicSessions.get(key);
	}

	/**
	 * Get the academic year from sakai.properties, if it is not there the default
	 * is current year.
	 * 
	 * @see coza.opencollab.sakai.cm.IntegrationClient#getAcademicYear()
	 */
	private String getAcademicYear() {
		int year = serverConfigurationService.getInt(ACADEMIA_CONFIG_YEAR, 0);
		return year != 0 ? String.valueOf(year) : String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	private AcademiaModule getModule(String key) {
		return modules.get(key);
	}

	public Collection<SISMembership> getCourseSetMemberships(SISCourseSet courseSet) {
		return null;
	}

	private void populateAcademicSessions(String year) {
		Download_Intakes_Input intakesInput = new Download_Intakes_Input();
		intakesInput.setYear(new BigInteger(year));
		DownloadIntakeOutput intakeOutput = null;
		SISAcademicSession academicSession = null;
		try {
			intakeOutput = proxy.getRequestPort().download_Intakes(intakesInput);
			String[][] intakeArray = intakeOutput.getIntake_List();
			for (int i = 0; i < intakeArray.length; i++) {
				academicSession = new SISAcademicSession(
						intakeArray[i][1] + " " + intakeArray[i][0],
						intakeArray[i][1], 
						intakeArray[i][2]  + " " + intakeArray[i][0],
						intakeArray[i][0],
						Utils.getDateFromString(intakeArray[i][3]), 
						Utils.getDateFromString(intakeArray[i][4]),
						"Academic Session for " + intakeArray[i][2] + " " + intakeArray[i][0]);

				academicSessions.put(academicSession.getId(), academicSession);
			}
		} catch (RemoteException e) {
			throw new SISException(e);
		}
	}

	@Override
	public Collection<SISCanonicalCourse> getCanonicalCourses(SISAcademicSession academicSession) {
		Download_Programs_Input moduleInput = new Download_Programs_Input();
		DownloadProgramsOutput moduleOutput = null;
		moduleInput.setYear(new BigInteger(academicSession.getYear()));
		moduleInput.setIntakeCode(new BigInteger(academicSession.getId()));
		AcademiaModule module = null;
		Set<SISCanonicalCourse> canonicalCourses = new HashSet<SISCanonicalCourse>();
		try {
			moduleOutput = proxy.getRequestPort().download_Programs(moduleInput);
			String[][] moduleArray = moduleOutput.getProgram_List();
			for (int i = 0; i < moduleArray.length; i++) {
				module = new AcademiaModule();
				module.setAcademicSessionCode(moduleArray[i][1]);
				module.setModulePeriodCode(moduleArray[i][2]);
				module.setModuleCode(moduleArray[i][3]);
				module.setDescription(moduleArray[i][4]);
				module.setAcademicSession(getAcademicSession(module.getAcademicSessionCode()));
				modules.put(module.getModuleCode(), module);

				SISCanonicalCourse canonicalCourse = new SISCanonicalCourse(module.getCanonicalCourseReference());
				canonicalCourse.setId(module.getCanonicalCourseReference());
				canonicalCourse.setTitle(module.getCanonicalCourseReference());
				canonicalCourse.setDescription(module.getDescription());
				canonicalCourses.add(canonicalCourse);
			}
		} catch (RemoteException e) {
			throw new SISException(e);
		}
		return canonicalCourses;
	}

	@Override
	public SISCourseOffering getCourseOffering(SISAcademicSession academicSession, SISCanonicalCourse canonicalCourse,
			String courseSetId) {

		Download_Module_Periods_Input modulePerdiodsInput = null;
		DownloadModulePeriodsOutput modulePerdiodsOutput = null;
		String[][] modulePeriodArray = null;

		modulePerdiodsInput = new Download_Module_Periods_Input();
		modulePerdiodsInput.setYear(new BigInteger(academicSession.getYear()));
		modulePerdiodsInput.setIntakeCode(new BigInteger(academicSession.getId()));

		AcademiaModule module = getModule(canonicalCourse.getId());
		if(module == null) return null;

		try {
			modulePerdiodsOutput = proxy.getRequestPort().download_Module_Periods(modulePerdiodsInput);
			modulePeriodArray = modulePerdiodsOutput.getModule_Period_List();

			for (int i = 0; i < modulePeriodArray.length; i++) {

				if (StringUtils.equals(module.getModulePeriodCode(), modulePeriodArray[i][0])) {

					module.setModulePeriodCodeDescr(modulePeriodArray[i][1]);
					modules.put(module.getModuleCode(), module);
					
					SISCourseOffering courseOffering = new SISCourseOffering(module.getModuleCode());
					courseOffering.setId(module.getCourseOfferingReference());
					courseOffering.setTitle(module.getCanonicalCourseReference());
					courseOffering.setDescription(module.getDescription());
					courseOffering.setStatus("Active");
					courseOffering.setAcademicSessionId(academicSession.getId());
					courseOffering.setCanonicalCourseId(module.getCanonicalCourseReference());
					courseOffering.setStartDate(academicSession.getStartDate());
					courseOffering.setEndDate(academicSession.getEndDate());
					courseOffering.setModulePeriodCode(module.getModulePeriodCode());
					courseOffering.setModulePeriodCodeDescr(modulePeriodArray[i][1]);
					courseOffering.addCourseSetIds(courseSetId);
					return courseOffering;
				}
			}

		} catch (RemoteException e) {
			throw new SISException(e);
		}
		return null;
	}

	@Override
	public Collection<SISMembership> getCourseOfferingMemberships(SISCourseOffering courseOffering) {
		List<SISMembership> memberships = new ArrayList<SISMembership>();
		AcademiaModule module = getModule(courseOffering.getCanonicalCourseId());
		if(module == null) return null;
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
	public Collection<SISEnrollmentSet> getEnrollmentSets(SISCourseOffering courseOffering) {
		AcademiaModule module = getModule(courseOffering.getSISCode());
		if(module == null) return null;
		List<SISEnrollmentSet> enrollmentSets = new ArrayList<SISEnrollmentSet>();
		SISEnrollmentSet enrollmentSet = new SISEnrollmentSet(module.getEnrollmentSetReference());
		enrollmentSet.setId(module.getEnrollmentSetReference());
		enrollmentSet.setTitle(module.getEnrollmentSetReference());
		enrollmentSet.setDescription(module.getEnrollmentSetReference());
		enrollmentSet.setCategory(courseOffering.getModulePeriodCodeDescr());
		enrollmentSet.setDefaultEnrollmentCredits("0");
		enrollmentSet.setCourseOfferingEid(courseOffering.getId());
		List<String> lecturers = getLecturers(module);
		enrollmentSet.setOfficialInstructors(lecturers == null ? null : new HashSet<String>(lecturers));
		enrollmentSets.add(enrollmentSet);
		return enrollmentSets;
	}

	@Override
	public SISEnrollmentSet getEnrollmentSet(SISCourseOffering courseOffering) {
		AcademiaModule module = getModule(courseOffering.getSISCode());
		if(module == null) return null;
		SISEnrollmentSet enrollmentSet = new SISEnrollmentSet(module.getEnrollmentSetReference());
		enrollmentSet.setId(module.getEnrollmentSetReference());
		enrollmentSet.setTitle(module.getEnrollmentSetReference());
		enrollmentSet.setDescription(module.getEnrollmentSetReference());
		enrollmentSet.setCategory(courseOffering.getModulePeriodCodeDescr());
		enrollmentSet.setDefaultEnrollmentCredits("0");
		enrollmentSet.setCourseOfferingEid(courseOffering.getId());
		List<String> lecturers = getLecturers(module);
		enrollmentSet.setOfficialInstructors(lecturers == null ? null : new HashSet<String>(lecturers));
		return enrollmentSet;
	}

	@Override
	public Collection<SISEnrollment> getEnrollments(SISEnrollmentSet enrollmentSet) {
		List<SISEnrollment> enrollments = new ArrayList<SISEnrollment>();
		AcademiaModule module = getModule(enrollmentSet.getCourseOfferingEid());
		if(module == null) return null;
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
	public Collection<SISEnrollment> getEnrollments(SISEnrollmentSet enrollmentSet, Collection<String> studentList) {
		List<SISEnrollment> enrollments = new ArrayList<SISEnrollment>();
		for (String studentId : studentList) {
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
	public Collection<SISSection> getSections(SISCourseOffering courseOffering) {
		AcademiaModule module = getModule(courseOffering.getCanonicalCourseId());
		if(module == null) return null;
		SISSection section = new SISSection(module.getCourseOfferingReference());
		section.setId(module.getCourseOfferingReference());
		section.setTitle(module.getCourseOfferingReference());
		section.setDescription(module.getCourseOfferingReference() + courseOffering.getModulePeriodCodeDescr());
		section.setCategoryCode(module.getModulePeriodCode());
		section.setCategoryDescription(courseOffering.getModulePeriodCodeDescr());
		section.setParentSectionId(null);
		section.setCourseOfferingId(courseOffering.getId());
		section.setEnrollmentSetId(module.getEnrollmentSetReference());
		List<SISSection> ss = new ArrayList<SISSection>();
		ss.add(section);
		return ss;
	}

	@Override
	public SISSection getSection(SISCourseOffering courseOffering) {
		AcademiaModule module = getModule(courseOffering.getCanonicalCourseId());
		if(module == null) return null;
		SISSection section = new SISSection(module.getCourseOfferingReference());
		section.setId(module.getCourseOfferingReference());
		section.setTitle(module.getCourseOfferingReference());
		section.setDescription(module.getCourseOfferingReference());
		section.setCategoryCode(module.getModulePeriodCode());
		section.setCategoryDescription(courseOffering.getModulePeriodCodeDescr());
		section.setParentSectionId(null);
		section.setCourseOfferingId(courseOffering.getId());
		section.setEnrollmentSetId(module.getEnrollmentSetReference());
		return section;
	}

	@Override
	public Collection<SISMembership> getSectionMemberships(SISSection section) {
		List<SISMembership> memberships = new ArrayList<SISMembership>();
		AcademiaModule module = getModule(section.getCourseOfferingId());
		if(module == null) return null;
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
	public Collection<String> getStudentList(SISCourseOffering courseOffering) {
		AcademiaModule module = getModule(courseOffering.getCanonicalCourseId());
		if(module == null) return null;
		return getEnrolledStudents(module);
	}

	@Override
	public Collection<String> getLecturerList(SISCourseOffering courseOffering) {
		AcademiaModule module = getModule(courseOffering.getCanonicalCourseId());
		if(module == null) return null;
		return getLecturers(module);
	}

	private List<String> getEnrolledStudents(AcademiaModule module) {

		List<String> studentClassList = new ArrayList<String>();
		Download_Students_Input studentsInput = null;
		Download_Students_Output studentsListOutput = null;

		studentsInput = new Download_Students_Input();
		studentsInput.setYear(new BigInteger(module.getAcademicSession().getYear()));
		studentsInput.setIntakeCode(module.getAcademicSessionCode());
		studentsInput.setModuleCode(module.getModuleCode());
		studentsInput.setPeriodCode(module.getModulePeriodCode());
		try {
			studentsListOutput = proxy.getRequestPort().download_Students(studentsInput);
			String[][] studentArray = studentsListOutput.getStudent_List();
			for (int i = 0; i < studentArray.length; i++) {
				if (studentArray[i].length < 1) {
					break;
				}
				studentClassList.add(studentArray[i][0]);
			}
		} catch (RemoteException e) {
			throw new SISException(e);
		}
		return studentClassList;
	}

	private List<String> getLecturers(AcademiaModule module) {

		List<String> lecturerList = new ArrayList<String>();
		Download_Lecturers_Input lecturersInput = null;
		Download_LecturersOutput lecturersOutput = null;

		lecturersInput = new Download_Lecturers_Input();
		lecturersInput.setYear(new BigInteger(module.getAcademicSession().getYear()));
		lecturersInput.setIntakeCode(new BigInteger(module.getAcademicSessionCode()));
		lecturersInput.setModuleCode(module.getModuleCode());
		try {
			lecturersOutput = proxy.getRequestPort().download_Lecturers(lecturersInput);
			String[][] lecturerArray = lecturersOutput.getLecturers_List();
			for (int i = 0; i < lecturerArray.length; i++) {
				if (lecturerArray[i].length < 1) {
					break;
				}
				lecturerList.add(lecturerArray[i][0]);
			}
		} catch (RemoteException e) {
			throw new SISException(e);
		}
		return lecturerList;
	}

	@Override
	public Collection<SISCourseChange> getCourseChanges() {
		return null;
	}

	public void setServerConfigurationService(ServerConfigurationService serverConfigurationService) {
		this.serverConfigurationService = serverConfigurationService;
	}

	@Override
	public Collection<SISCourseOffering> getCourseOfferings(SISAcademicSession academicSession) {
		return null;
	}

	@Override
	public Collection<SISCourseSet> getCourseSets(SISAcademicSession academicSession) {
		return null;
	}

	@Override
	public Collection<SISMembership> getSISMemberships(Collection<String> studentList) {
		List<SISMembership> memberships = new ArrayList<SISMembership>();
		for (String studentId : studentList) {
			SISMembership membership = new SISMembership(studentId);
			membership.setUserId(studentId);
			membership.setRole("S");
			membership.setStatus("Active");
			memberships.add(membership);
		}
		return memberships;
	}

	@Override
	public void destroy() {
		academicSessions.clear();
		modules.clear();
	}
}
