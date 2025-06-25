package coza.opencollab.sakai.cm.jobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.coursemanagement.api.AcademicSession;
import org.sakaiproject.coursemanagement.api.EnrollmentSet;
import org.sakaiproject.coursemanagement.api.Section;

import coza.opencollab.sakai.cm.SISAcademicSession;
import coza.opencollab.sakai.cm.SISCanonicalCourse;
import coza.opencollab.sakai.cm.SISCourseOffering;
import coza.opencollab.sakai.cm.SISCourseSet;
import coza.opencollab.sakai.cm.SISEnrollment;
import coza.opencollab.sakai.cm.SISEnrollmentSet;
import coza.opencollab.sakai.cm.SISMembership;
import coza.opencollab.sakai.cm.SISSection;

/**
 * 
 * This is a quartz job that will do a full population of Course Management data from Academia
 */
public class AcademiaCoursePopulationJob extends BaseCourseManagementJob implements Job {	

	public static final String COURSE_SET_ID = "UWC_SHORT_COURSE";

	public void executeJob(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		createAcademiaCourseManagement();
	}

	private void createAcademiaCourseManagement() {
		// setup academic sessions
		Collection<SISAcademicSession> academicSessions = getClient().getAcademicSessions();
		List<String> asEids = new ArrayList<String>();		

		//Create Academia Short Course set - create CourseSet
		SISCourseSet sisCourseSet = new SISCourseSet(COURSE_SET_ID);
		sisCourseSet.setId(COURSE_SET_ID);
		sisCourseSet.setTitle("Short Course");
		sisCourseSet.setDescription("UWC Academia Short Course");
		sisCourseSet.setCategory("main");
		createCourseSet(sisCourseSet);
		
		// setup modules
		for (SISAcademicSession sisAcademicSession : academicSessions) {
			AcademicSession academicSession = createAcademicSession(sisAcademicSession);
			asEids.add(academicSession.getEid());
			createCourseOfferings(sisAcademicSession);
		}
		setCurrentAcademicSessions(asEids);
	}

	private void createCourseOfferings(SISAcademicSession sisAcademicSession) {
		
		Collection<SISCanonicalCourse> sisCanonicalCourses = getClient().getCanonicalCourses(sisAcademicSession);
		createCanonicalCourses(sisCanonicalCourses);

		if(CollectionUtils.isNotEmpty(sisCanonicalCourses)) {

			for (SISCanonicalCourse sisCanonicalCourse : sisCanonicalCourses) {

				SISCourseOffering sisCourseOffering = getClient().getCourseOffering(sisAcademicSession, sisCanonicalCourse, COURSE_SET_ID);			
				if (sisCourseOffering != null) {

					createCourseOffering(sisCourseOffering);
					
					Collection<String> studentList = getClient().getStudentList(sisCourseOffering);
					Collection<SISMembership> sisMemberships = getClient().getSISMemberships(studentList);
					
					manageCourseOfferingMemberships(sisCourseOffering, sisMemberships);					
					createEnrollmentSets(sisCourseOffering, studentList);
					createSections(sisCourseOffering, sisMemberships);
				}
			}
		}
	}

	private void createEnrollmentSets(SISCourseOffering sisCourseOffering, Collection<String> studentList) {
		SISEnrollmentSet sisEnrollmentSet = getClient().getEnrollmentSet(sisCourseOffering);
		EnrollmentSet enrollmentSet = createAcademiaEnrollmentSet(sisEnrollmentSet);
		Collection<SISEnrollment> sisEnrollments = getClient().getEnrollments(sisEnrollmentSet, studentList);
		manageEnrollments(sisCourseOffering, enrollmentSet, sisEnrollments);
	}

	private void createSections(SISCourseOffering sisCourseOffering, Collection<SISMembership> sisMemberships) {
		SISSection sisSection = getClient().getSection(sisCourseOffering);
		Section section = createSection(sisSection);
		manageSectionMemberships(sisCourseOffering, section, sisSection, sisMemberships);
	}
}