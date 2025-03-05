package coza.opencollab.sakai.cm.jobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.coursemanagement.api.AcademicSession;
import org.sakaiproject.coursemanagement.api.CanonicalCourse;
import org.sakaiproject.coursemanagement.api.CourseOffering;
import org.sakaiproject.coursemanagement.api.CourseSet;
import org.sakaiproject.coursemanagement.api.EnrollmentSet;
import org.sakaiproject.coursemanagement.api.Section;

import coza.opencollab.sakai.cm.SISAcademicSession;
import coza.opencollab.sakai.cm.SISCourseOffering;
import coza.opencollab.sakai.cm.SISCourseSet;
import coza.opencollab.sakai.cm.SISEnrollment;
import coza.opencollab.sakai.cm.SISEnrollmentSet;
import coza.opencollab.sakai.cm.SISMembership;
import coza.opencollab.sakai.cm.SISSection;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a quartz job that will do a full population of Course Management
 * data.
 */
@Slf4j
public class FullPopulationJob extends BaseCourseManagementJob implements Job {

	public void executeJob(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		
//		String token = getClient().setOAuthToken();

//		if(StringUtils.isEmpty(token)) {
//			log.error("FullPopulationJob.executeJob token is null.");
//		} else {
			createAcademicSessions();
//		}
	}

	private void createAcademicSessions() {
		// setup academic sessions
		Collection<SISAcademicSession> academicSessions = getClient()
				.getAcademicSessions();
		List<String> asEids = new ArrayList<String>();
		// setup modules
		for (SISAcademicSession sisAcademicSession : academicSessions) {
			AcademicSession academicSession = createAcademicSession(sisAcademicSession);
			asEids.add(academicSession.getEid());
			createCourseOfferings(academicSession, sisAcademicSession);
		}
		setCurrentAcademicSessions(asEids);
	}

	private void createCourseOfferings(AcademicSession academicSession,
			SISAcademicSession sisAcademicSession) {
		createCourseSets(sisAcademicSession);

		createCanonicalCourses(sisAcademicSession);

		Collection<SISCourseOffering> sisCourseOfferings = getClient()
				.getCourseOfferings(sisAcademicSession);
		if (sisCourseOfferings == null)
			return;

		for (SISCourseOffering sisCourseOffering : sisCourseOfferings) {
			CourseOffering courseOffering = createCourseOffering(sisCourseOffering);
			createCourseOfferingMemberships(sisCourseOffering, getClient()
					.getCourseOfferingMemberships(sisCourseOffering));

			createEnrollmentSets(sisCourseOffering);

			createSections(sisCourseOffering);
		}
	}

	private void createCourseSets(SISAcademicSession sisAcademicSession) {
		Collection<SISCourseSet> sisCourceSets = getClient().getCourseSets(
				sisAcademicSession);
		if (sisCourceSets == null)
			return;
		for (SISCourseSet sisCourseSet : sisCourceSets) {
			CourseSet courseSet = createCourseSet(sisCourseSet);
			createCourseSetMemberships(sisCourseSet, getClient()
					.getCourseSetMemberships(sisCourseSet));
		}
	}

	private void createCanonicalCourses(SISAcademicSession sisAcademicSession) {
		Collection<CanonicalCourse> canonicalCourses = createCanonicalCourses(getClient()
				.getCanonicalCourses(sisAcademicSession));
	}

	private void createEnrollmentSets(SISCourseOffering sisCourseOffering) {
		Collection<SISEnrollmentSet> sisEnrollmentSets = getClient()
				.getEnrollmentSets(sisCourseOffering);
		if(sisEnrollmentSets == null) return;
		for (SISEnrollmentSet sisEnrollmentSet : sisEnrollmentSets) {
			EnrollmentSet enrollmentSet = createEnrollmentSet(sisEnrollmentSet);
			Collection<SISEnrollment> sisEnrollments = getClient()
					.getEnrollments(sisEnrollmentSet);
			createEnrollments(sisEnrollments);
		}
	}

	private void createSections(SISCourseOffering sisCourseOffering) {
		Collection<SISSection> sisSections = getClient().getSections(
				sisCourseOffering);
		if(sisSections == null) return;
		for (SISSection sisSection : sisSections) {
			Section section = createSection(sisSection);
			Collection<SISMembership> sisSectionMemberships = getClient()
					.getSectionMemberships(sisSection);
			createSectionMemberships(sisSection, sisSectionMemberships);
		}
	}
}