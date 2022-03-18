package coza.opencollab.sakai.cm.jobs;

import java.util.Iterator;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.coursemanagement.api.CanonicalCourse;
import org.sakaiproject.coursemanagement.api.CourseOffering;
import org.sakaiproject.coursemanagement.api.CourseSet;

public class FullPopulationFixJob extends BaseCourseManagementJob implements Job {

    public void executeJob(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        removeCourseSets();
    }

    private void removeCourseSets() {
        Set<CourseSet> courseSetSet = getCourseSets();
        for (CourseSet courseSet : courseSetSet) {
            Set<CourseOffering> courseOfferingSet = getCourseOfferingsInCourseSet(courseSet
                    .getEid());
            if (courseOfferingSet == null || courseOfferingSet.isEmpty()) {
                removeCourseSet(courseSet.getEid());
            }
            Set<CanonicalCourse> canonicalCourseSet = getCanonicalCourses(courseSet.getEid());
            for (CanonicalCourse canonicalCourse : canonicalCourseSet) {
                Set<String> courseSets = canonicalCourse.getCourseSetEids();
                Iterator<String> ita = courseSets.iterator();
                while (ita.hasNext()) {
                    String set = (String) ita.next();
                    removeCanonicalCourseFromCourseSet(set, canonicalCourse.getEid());
                }
            }
        }
    }
}
