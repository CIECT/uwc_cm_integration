package coza.opencollab.sakai.cm;

import java.util.Collection;

public interface SISClient {
	
	/**
	 * Called before a integration session is run.
	 */
	public void init();
	/**
	 * Called after a integration session is run to fo cleanup.
	 */
	public void destroy();
    
//    /**
//     * Set the OAuth Token
//     */
//    public String setOAuthToken();
    
    /**
     * Retrieves the academic sessions
     */
    public Collection<SISAcademicSession> getAcademicSessions();
    
    /**
     * Retrieve the course sets for the given academic session.
     * Course Sets are related to departments/schools/faculties
     */
    public Collection<SISCourseSet> getCourseSets(SISAcademicSession academicSession);
    
    /**
     * Retrieve the membership for the given course set
     */
    public Collection<SISMembership> getCourseSetMemberships(SISCourseSet courseSet);
    
    /**
     * Retrieve the canonical course for the given academic session.
     * Canonical sources relate to repeatable courses, like math and economics.
     */
    public Collection<SISCanonicalCourse> getCanonicalCourses(SISAcademicSession academicSession);
    
    /**
     * Retrieve the actual course instances for the academic session.
     */
    public Collection<SISCourseOffering> getCourseOfferings(SISAcademicSession academicSession);
    

	public SISCourseOffering getCourseOffering(SISAcademicSession academicSession, SISCanonicalCourse canonicalCourse, String courseSetId);
    
    /**
     * Retrieve the membership for the given course Offering
     */
    public Collection<SISMembership> getCourseOfferingMemberships(SISCourseOffering courseOffering);
    
    public Collection<SISMembership> getSISMemberships(Collection<String> studentList);

    public Collection<String> getStudentList(SISCourseOffering courseOffering);

    public Collection<String> getLecturerList(SISCourseOffering courseOffering);
    
    /**
     * Retrieve the enrollment set for the offering. This includes the instructors
     */
    public Collection<SISEnrollmentSet> getEnrollmentSets(SISCourseOffering courseOffering);

    public SISEnrollmentSet getEnrollmentSet(SISCourseOffering courseOffering);
    
    /**
     * Retrieve the student enrollments for the enrollment set.
     */
    public Collection<SISEnrollment> getEnrollments(SISEnrollmentSet enrollmentSet);

    public Collection<SISEnrollment> getEnrollments(SISEnrollmentSet enrollmentSet, Collection<String> studentList);
    
    /**
     * Retrieve the sections for the course offering
     */
    public Collection<SISSection> getSections(SISCourseOffering courseOffering);
    
    public SISSection getSection(SISCourseOffering courseOffering);
    
    /**
     * Retrieve the membership for the given section
     */
    public Collection<SISMembership> getSectionMemberships(SISSection section);

    /**
     * Retrieve the changes to courses for a academic session
     * XXX not sure if this is super?
     */
    public Collection<SISCourseChange> getCourseChanges();
}
