package coza.opencollab.sakai.cm.jobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.coursemanagement.api.EnrollmentSet;

import coza.opencollab.sakai.cm.SISAcademicSession;
import coza.opencollab.sakai.cm.SISCourseChange;
import coza.opencollab.sakai.cm.SISCourseOffering;
import coza.opencollab.sakai.cm.SISEnrollment;
import coza.opencollab.sakai.cm.SISMembership;
import coza.opencollab.sakai.cm.SISSection;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a quartz job that will update Course sites with enrolled Student
 * data.
 */
@Slf4j
public class EnrollmentUpdateJob extends BaseCourseManagementJob implements Job {
	
	public void executeJob(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		log.info("EnrollmentUpdateJob.executeJob started.");
		
		Collection<SISAcademicSession> academicSessions = getClient()
				.getAcademicSessions();
        Collection<SISCourseChange> courseChanges = getClient()
                .getCourseChanges();		
        for (SISCourseChange courseChange : courseChanges) {
            updateCourseChanges(academicSessions, courseChange);
        }
		log.info("EnrollmentUpdateJob.executeJob finished.");
	}

    private void updateCourseChanges(Collection<SISAcademicSession> academicSessions,
            SISCourseChange courseChange) {
        for (SISAcademicSession academicSession : academicSessions) {
            // Course Changes
            Map<String, SISCourseOffering> courseOfferings = map(getClient().getCourseOfferings(
                academicSession));
            SISCourseOffering courseOffering = courseOfferings.get(courseChange.getModuleCode());
            if (courseOffering != null) {
                if ("ADD".equals(courseChange.getMaintananceIndicator())) {                	

            		log.info("ADD updateCourseChanges: ");
            		log.info(" 		academicSession: " + academicSession);
            		log.info(" 		courseOffering: " + courseOffering);
            		log.info(" 		courseChange: " + courseChange);
                	
                    // check if course management data exist for module in sakai
                    addData(academicSession, courseOffering, courseChange);
                }
                else if ("DEL".equals(courseChange.getMaintananceIndicator())) {
                	

            		log.info("DEL updateCourseChanges: ");
            		log.info(" 		academicSession: " + academicSession);
            		log.info(" 		courseOffering: " + courseOffering);
            		log.info(" 		courseChange: " + courseChange);
            		
                    removeData(academicSession, courseOffering, courseChange);
                }
            }
        }
    }

	private void addData(SISAcademicSession sisAcademicSession,
			SISCourseOffering sisCourseOffering, SISCourseChange sisCourseChange) {
		SISMembership membership = getMembership(sisCourseChange);
		// course set
		createCourseSets(getClient()
				.getCourseSets(sisAcademicSession));//XXX
		// canonical course
		createCanonicalCourses(getClient()
				.getCanonicalCourses(sisAcademicSession));//XXX
		// course offering
		createCourseOffering(sisCourseOffering);
		// course offering membership
		createCourseOfferingMembership(
				sisCourseOffering, membership);
		// enrollment set
		Collection<EnrollmentSet> enrollmentSets = createEnrollmentSets(getClient()
				.getEnrollmentSets(sisCourseOffering));
		// enrollment
		createEnrollments(getEnrollments(enrollmentSets, sisCourseChange));
		// section
		Collection<SISSection> sections = getClient().getSections(sisCourseOffering);
		createSections(sections);
		// section membership
		for(SISSection section: sections){
			createSectionMembership(section, membership);
		}
	}

	private List<SISEnrollment> getEnrollments(
			Collection<EnrollmentSet> enrollmentSets,
			SISCourseChange sisCourseChange) {
		List<SISEnrollment> enrollments = new ArrayList<SISEnrollment>();

		for (EnrollmentSet enrollmentSet : enrollmentSets) {
			SISEnrollment enrollment = new SISEnrollment(
					sisCourseChange.getStudentNum());
			enrollment.setStudentId(sisCourseChange.getStudentNum());
			enrollment.setEnrollmentSetId(enrollmentSet.getEid());
			enrollment.setStatus("enrolled");
			enrollment.setCredits("0");
			enrollment.setGradingScheme("standard");
			enrollments.add(enrollment);
		}
		return enrollments;
	}

	private SISMembership getMembership(
			SISCourseChange sisCourseChange) {
		SISMembership m = new SISMembership(sisCourseChange.getStudentNum());
		m.setUserId(sisCourseChange.getStudentNum());
		m.setRole("S");
		m.setStatus("Active");
		return m;
	}

	private void removeData(SISAcademicSession academicSession,
			SISCourseOffering courseOffering, SISCourseChange courseChange) {
		deleteEnrollment(courseOffering, courseChange);
		// deleteCourseSetMemberships(courseOffering, courseChange);?
		deleteCourseOfferingMemberships(courseOffering, courseChange);
		deleteSectionMemberships(courseOffering, courseChange);
	}

	private Map<String, SISCourseOffering> map(
			Collection<SISCourseOffering> courseOfferings) {
		Map<String, SISCourseOffering> map = new HashMap<String, SISCourseOffering>();
		for (SISCourseOffering courseOffering : courseOfferings) {
			map.put(courseOffering.getSISCode(), courseOffering);
		}
		return map;
	}
}
