package coza.opencollab.sakai.cm.jobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.coursemanagement.api.AcademicSession;
import org.sakaiproject.coursemanagement.api.CanonicalCourse;
import org.sakaiproject.coursemanagement.api.CourseManagementAdministration;
import org.sakaiproject.coursemanagement.api.CourseManagementService;
import org.sakaiproject.coursemanagement.api.CourseOffering;
import org.sakaiproject.coursemanagement.api.CourseSet;
import org.sakaiproject.coursemanagement.api.Enrollment;
import org.sakaiproject.coursemanagement.api.EnrollmentSet;
import org.sakaiproject.coursemanagement.api.Membership;
import org.sakaiproject.coursemanagement.api.Section;
import org.sakaiproject.coursemanagement.api.exception.IdNotFoundException;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;

import coza.opencollab.sakai.cm.SISAcademicSession;
import coza.opencollab.sakai.cm.SISCanonicalCourse;
import coza.opencollab.sakai.cm.SISClient;
import coza.opencollab.sakai.cm.SISCourseChange;
import coza.opencollab.sakai.cm.SISCourseOffering;
import coza.opencollab.sakai.cm.SISCourseSet;
import coza.opencollab.sakai.cm.SISEnrollment;
import coza.opencollab.sakai.cm.SISEnrollmentSet;
import coza.opencollab.sakai.cm.SISMembership;
import coza.opencollab.sakai.cm.SISSection;
import lombok.extern.slf4j.Slf4j;

/**
 * This base Course Management job. This class has the base code to run any
 * course management job.
 * 
 */
@Slf4j
public abstract class BaseCourseManagementJob implements Job {
	private static final String ADMIN_USER = "admin";
	private SessionManager sessionManager;
	private SecurityService securityService;
	private CourseManagementService cmService;
	private CourseManagementAdministration cmAdmin;
	private AuthzGroupService authzGroupService;

	private SISClient client;

	/**
	 * Log info messages using the job name.
	 */
	public void logInfo(String message) {
		log.info(getLogMessage(message));
	}

	/**
	 * Log error messages and throw a exception using the job name.
	 */
	public void logError(String message) throws JobExecutionException {
		String msg = getLogMessage(message);
		log.error(msg);
		throw new JobExecutionException(message);
	}

	private String getLogMessage(String message) {
		return getClass().getSimpleName() + ": " + message;
	}

	/**
	 * Get/Create the AcademicSession for the year and set it active/current
	 */
	public AcademicSession createAcademicSession(
			SISAcademicSession academicSession) {
		AcademicSession aSession = null;
		try {
			aSession = cmService.getAcademicSession(academicSession.getId());
			log.debug("Retrieved AcademicSession with id " + academicSession.getId());
		} catch (IdNotFoundException e) {
			log.info("Create AcademicSession: " + academicSession);
			aSession = cmAdmin.createAcademicSession(
					academicSession.getId(),
					academicSession.getTitle(),
					academicSession.getDescription(),
					academicSession.getStartDate(),
					academicSession.getEndDate());
			log.info("Inserted AcademicSession with id " + academicSession.getId());
		}
		return aSession;
	}
	
	/**
	 * Set the current active academic sessions.
	 * Call createAcademicSession before this method!
	 */
	public void setCurrentAcademicSessions(Collection<String> academicSessionEids){
		cmAdmin.setCurrentAcademicSessions(new ArrayList<String>(academicSessionEids));
	}
	
	public Set<CourseSet> getCourseSets(){
		return cmService.getCourseSets();
	}
	
	public void removeCourseSet(String courseSetEid){
		cmAdmin.removeCourseSet(courseSetEid);
	}
	
    public Set<CanonicalCourse> getCanonicalCourses(String courseSetEid) {
        return cmService.getCanonicalCourses(courseSetEid);
    }

    public void removeCanonicalCourseFromCourseSet(String courseSetEid, String canonicalCourseEid) {
        cmAdmin.removeCanonicalCourseFromCourseSet(courseSetEid, canonicalCourseEid);
    }
	
	/**
     * Create Course Sets for all the subject codes.
     * These are shown in the 'Subject' droplist of Sakai's course site setup.
     */
	public Collection<CourseSet> createCourseSets(Collection<SISCourseSet> courseSets){
		if(courseSets == null) return null;
		List<CourseSet> csList = new ArrayList<CourseSet>();
		for (SISCourseSet courseSet : courseSets) {
			csList.add(createCourseSet(courseSet));
		}
		return csList;
	}
	
	public CourseSet createCourseSet(SISCourseSet courseSet){
		if(courseSet == null) return null;
		CourseSet cs = null;
		if (cmService.isCourseSetDefined(courseSet.getId())) {
			cs = cmService.getCourseSet(courseSet.getId());
		}else{
			log.info("Create CourseSet: " + courseSet);
			cs = cmAdmin.createCourseSet(courseSet.getId(), courseSet.getTitle(), 
					courseSet.getDescription(), courseSet.getCategory(), null);
			log.info("Inserted CourseSet with id " + courseSet.getId());
		}
		return cs;
	}

	public Collection<Membership> createCourseSetMemberships(SISCourseSet courseSet, Collection<SISMembership> courseSetMemberships) {
		if(courseSetMemberships == null) return null;
		List<Membership> memberships = new ArrayList<Membership>();
		for (SISMembership courseSetMembership: courseSetMemberships) {
			memberships.add(cmAdmin.addOrUpdateCourseSetMembership(
					courseSetMembership.getUserId(),
					courseSetMembership.getRole(),
					courseSet.getId(),
					courseSetMembership.getStatus()));
			log.info("Added/Updated CourseSetMembership - " + courseSetMembership.getUserId() + " to "
					+ courseSet.getId());
		}
		return memberships;
	}
	
	public Collection<CanonicalCourse> createCanonicalCourses(Collection<SISCanonicalCourse> canonicalCourses){
		if(canonicalCourses == null) return null;
		List<CanonicalCourse> ccList = new ArrayList<CanonicalCourse>();
		for (SISCanonicalCourse canonicalCourse : canonicalCourses) {
        	ccList.add(createCanonicalCourse(canonicalCourse));
        }
		return ccList;
	}
	
	public CanonicalCourse createCanonicalCourse(SISCanonicalCourse canonicalCourse){
		if(canonicalCourse == null) return null;
		CanonicalCourse cc = null;
		if (cmService.isCanonicalCourseDefined(canonicalCourse.getId())) {
        	cc = cmService.getCanonicalCourse(canonicalCourse.getId());
        }else{
			log.info("Create CanonicalCourse: " + canonicalCourse);
			cc = cmAdmin.createCanonicalCourse(canonicalCourse.getId(),
					canonicalCourse.getTitle(),
					canonicalCourse.getDescription());
			log.info("Inserted CanonicalCourse with id " + canonicalCourse.getId());
        }
		return cc;
	}
	
	public Set<CourseOffering> getCourseOfferingsInCourseSet(String courseSetEid){
		return cmService.getCourseOfferingsInCourseSet(courseSetEid);
	}
	
	/**
	 * Create the course instances
	 * Call createAcademicSession, createCourseSet and createCanonicalCourse before this method!
	 */
	public Collection<CourseOffering> createCourseOfferings(Collection<SISCourseOffering> courseOfferings) {
		if(courseOfferings == null) return null;
		List<CourseOffering> coList = new ArrayList<CourseOffering>();
		for(SISCourseOffering courseOffering: courseOfferings){
			coList.add(createCourseOffering(courseOffering));
		}
		return coList;
	}
	
	public CourseOffering createCourseOffering(SISCourseOffering courseOffering){
		if(courseOffering == null) return null;
		CourseOffering co = null;
		if (cmService.isCourseOfferingDefined(courseOffering.getId())) {
			co = cmService.getCourseOffering(courseOffering.getId());
		}else{
			log.info("Create CourseOffering: " + courseOffering);
			co = cmAdmin.createCourseOffering(courseOffering.getId(),
					courseOffering.getTitle(),
					courseOffering.getDescription(), 
					courseOffering.getStatus(),
					courseOffering.getAcademicSessionId(),
					courseOffering.getCanonicalCourseId(),
					courseOffering.getStartDate(),
					courseOffering.getEndDate());
			log.info("Inserted CourseOffering with id " + courseOffering.getId());
		}
		addCourseOfferingsToCourseSets(courseOffering);
		return co;
	}

	private void addCourseOfferingsToCourseSets(SISCourseOffering courseOffering) {
		for (String courseSetId : courseOffering.getCourseSetIds()) {
			boolean linkExists = false;
			// Check if Offering is already linked to CourseSet
			Set<CourseOffering> linkedOfferings = cmService
					.getCourseOfferingsInCourseSet(courseSetId);
			for (CourseOffering linkedOffering : linkedOfferings) {
				if (linkedOffering.getEid().equals(courseOffering.getId())) {
					linkExists = true;
					break;
				}
			}
			if (!linkExists) {
				// Add if Offering is not already linked.
				cmAdmin.addCourseOfferingToCourseSet(courseSetId, courseOffering.getId());
				log.info("Added CourseOffering (" + courseOffering.getId()
						+ ") to CourseSet (" + courseSetId + ")");
			}
		}
	}

	public Collection<Membership> createCourseOfferingMemberships(SISCourseOffering courseOffering, Collection<SISMembership> courseOfferingMemberships) {
		if(courseOfferingMemberships == null) return null;
		List<Membership> memberships = new ArrayList<Membership>();
		for (SISMembership courseOfferingMembership: courseOfferingMemberships) {
			memberships.add(createCourseOfferingMembership(courseOffering, courseOfferingMembership));
		}
		return memberships;
	}
	
	public void manageCourseOfferingMemberships(SISCourseOffering courseOffering, Collection<SISMembership> courseOfferingMemberships) {
		if(courseOfferingMemberships == null) return;
		
		//Get current enrollments
		Set<Membership> courseOfferingMembershipSet = cmService.getCourseOfferingMemberships(courseOffering.getId());
		
		List<String> enrolledUsers = courseOfferingMembershipSet.stream()
			    .map(Membership::getUserId)
			    .collect(Collectors.toList());
		
		List<String> newUsers = courseOfferingMemberships.stream()
			    .map(SISMembership::getUserId)
			    .collect(Collectors.toList());		

		List<String> addList = newUsers.stream()
				.filter(user1 -> enrolledUsers.stream().noneMatch(user2 -> user2.equals(user1)))
				.collect(Collectors.toList());

		for (String userId : addList) {
			SISMembership sisMembership = courseOfferingMemberships.stream()
					  .filter(membership -> membership.getUserId().equals(userId))
					  .findAny()
					  .orElse(null);
			if(sisMembership != null) {
				createCourseOfferingMembership(courseOffering, sisMembership);
			}
		}
		
		List<String> removeList = enrolledUsers.stream()
				.filter(user1 -> newUsers.stream().noneMatch(user2 -> user2.equals(user1)))
				.collect(Collectors.toList());
		
		for (String userId : removeList) {
			deleteCourseOfferingMemberships(courseOffering, userId);
		}
	}
	
	public Membership createCourseOfferingMembership(SISCourseOffering courseOffering, SISMembership courseOfferingMembership) {
		if(courseOfferingMembership == null) return null;
		Membership membership = cmAdmin.addOrUpdateCourseOfferingMembership(
					courseOfferingMembership.getUserId(),
					courseOfferingMembership.getRole(),
					courseOffering.getId(),
					courseOfferingMembership.getStatus());
		log.info("Added/Updated CourseOfferingMemberships - " + courseOfferingMembership.getUserId() + " to courseOfferingId "
					+ courseOffering.getId());
		authzGroupService.refreshUser(courseOfferingMembership.getUserId());
		return membership;
	}
	
	/**
	 * Remove the memberships for the course offering
	 */
	public void deleteCourseOfferingMemberships(SISCourseOffering courseOffering, SISCourseChange courseChange){
		cmAdmin.removeCourseOfferingMembership(courseChange.getStudentNum(), courseOffering.getId());
		log.info("Removed CourseOfferingMemberships - " + courseChange.getStudentNum() + " to courseOfferingId "
				+ courseOffering.getId());
	}
	
	/**
	 * Remove the memberships for the course offering
	 */
	public void deleteCourseOfferingMemberships(SISCourseOffering courseOffering, String userId){
		cmAdmin.removeCourseOfferingMembership(userId, courseOffering.getId());
		log.info("Removed CourseOfferingMemberships - " + userId + " to courseOfferingId "
				+ courseOffering.getId());
		authzGroupService.refreshUser(userId);
	}
	
	/**
	 * Create the enrollmentSets.
	 * Call createCourseOfferings before this method!
	 */
	public Collection<EnrollmentSet> createEnrollmentSets(Collection<SISEnrollmentSet> enrollmentSets){
		if(enrollmentSets == null) return null;
		List<EnrollmentSet> esList = new ArrayList<EnrollmentSet>();
		for(SISEnrollmentSet enrollmentSet: enrollmentSets){
			esList.add(createEnrollmentSet(enrollmentSet));
		}
		return esList;
	}
	
	public EnrollmentSet createEnrollmentSet(SISEnrollmentSet enrollmentSet){
		if(enrollmentSet == null) return null;
		EnrollmentSet es = null;
		if(cmService.isEnrollmentSetDefined(enrollmentSet.getId())){
			es = cmService.getEnrollmentSet(enrollmentSet.getId());
		}else{
			log.info("Create EnrollmentSet: " + enrollmentSet);
			es = cmAdmin.createEnrollmentSet(enrollmentSet.getId(),
					enrollmentSet.getTitle(), enrollmentSet.getDescription(), 
					enrollmentSet.getCategory(), enrollmentSet.getDefaultEnrollmentCredits(), 
					enrollmentSet.getCourseOfferingEid(), enrollmentSet.getOfficialInstructors());
			log.info("Inserted EnrollmentSet with id " + enrollmentSet.getId());
		}
		return es;
	}
	
	public EnrollmentSet createAcademiaEnrollmentSet(SISEnrollmentSet enrollmentSet){
		if(enrollmentSet == null) return null;
		EnrollmentSet es = null;
		if(cmService.isEnrollmentSetDefined(enrollmentSet.getId())){
			
			es = cmService.getEnrollmentSet(enrollmentSet.getId());
			es.setOfficialInstructors(enrollmentSet.getOfficialInstructors());

            try {
                cmAdmin.updateEnrollmentSet(es);
            } catch (Exception e) {
                log.error("can't save instructor enrollment set", e);
            }
		}else{
			log.info("Create EnrollmentSet: " + enrollmentSet);
			es = cmAdmin.createEnrollmentSet(enrollmentSet.getId(),
					enrollmentSet.getTitle(), enrollmentSet.getDescription(), 
					enrollmentSet.getCategory(), enrollmentSet.getDefaultEnrollmentCredits(), 
					enrollmentSet.getCourseOfferingEid(), enrollmentSet.getOfficialInstructors());
			log.info("Inserted EnrollmentSet with id " + enrollmentSet.getId());
		}
		return es;
	}

	/**
	 * Only students should have Enrollments. Lecturers are added to the
	 * EnrollmentSet.
	 * Call createEnrollmentSets before this method!
	 */
	public Collection<Enrollment> createEnrollments(Collection<SISEnrollment> enrollments) {
		if(enrollments == null) return null;
		List<Enrollment> e = new ArrayList<Enrollment>();
		for (SISEnrollment sisEnrollment : enrollments) {
			e.add(createEnrollment(sisEnrollment));
		}
		return e;
	}
	
	public void manageEnrollments(SISCourseOffering courseOffering, EnrollmentSet enrollmentSet, Collection<SISEnrollment> enrollments) {
		// Get current enrollments
		Set<Enrollment> currentEnrollments = cmService.getEnrollments(enrollmentSet.getEid());

		List<String> enrolledUsers = currentEnrollments.stream().map(Enrollment::getUserId)
				.collect(Collectors.toList());

		List<String> newUsers = enrollments.stream().map(SISEnrollment::getStudentId)
				.collect(Collectors.toList());

		List<String> addList = newUsers.stream()
				.filter(user1 -> enrolledUsers.stream().noneMatch(user2 -> user2.equals(user1)))
				.collect(Collectors.toList());

		for (String userId : addList) {
			SISEnrollment sisEnrollment = enrollments.stream()
					.filter(enrollment -> enrollment.getStudentId().equals(userId)).findAny().orElse(null);
			if (sisEnrollment != null) {
				createEnrollment(sisEnrollment);
			}
		}

		List<String> removeList = enrolledUsers.stream()
				.filter(user1 -> newUsers.stream().noneMatch(user2 -> user2.equals(user1)))
				.collect(Collectors.toList());

		for (String userId : removeList) {
			deleteEnrollment(courseOffering, userId);
		}
	}
	
	public Enrollment createEnrollment(SISEnrollment enrollment) {
		Enrollment e = cmAdmin.addOrUpdateEnrollment(enrollment.getStudentId(),
				enrollment.getEnrollmentSetId(),
				enrollment.getStatus(),
				enrollment.getCredits(),
				enrollment.getGradingScheme());
		log.info("Added/Updated Enrollment for user id " + enrollment.getStudentId()
				+ " and enrollmentset " + enrollment.getEnrollmentSetId());
		authzGroupService.refreshUser(enrollment.getStudentId());
		return e;
	}
	
	/**
	 * remove the student from all the enrollment sets for the course
	 */
	public void deleteEnrollment(SISCourseOffering courseOffering, SISCourseChange courseChange){
		Set<EnrollmentSet> enrollmentSets = cmService.getEnrollmentSets(courseOffering.getId());
		for(EnrollmentSet enrollmentSet: enrollmentSets){
			cmAdmin.removeEnrollment(courseChange.getStudentNum(), enrollmentSet.getEid());
			log.info("Removed Enrollment for user id " + courseChange.getStudentNum()
				+ " and enrollmentset " + enrollmentSet.getEid());
		}
	}
	
	public void deleteEnrollment(SISCourseOffering courseOffering, String userId){
		Set<EnrollmentSet> enrollmentSets = cmService.getEnrollmentSets(courseOffering.getId());
		for(EnrollmentSet enrollmentSet: enrollmentSets){
			cmAdmin.removeEnrollment(userId, enrollmentSet.getEid());
			log.info("Removed Enrollment for user id " + userId
				+ " and enrollmentset " + enrollmentSet.getEid());
			authzGroupService.refreshUser(userId);
		}
	}

	/**
	 * Arbitrary section category title.
	 */
	private boolean createSectionCategory(String code, String description) {
		boolean exists = false;
		for (String categoryCode : cmService.getSectionCategories()) {
			if (code.equals(categoryCode)) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			cmAdmin.addSectionCategory(code, description);
			log.info("Section Category (" + code + " - " + description
					+ ") successfully inserted.");
		}
		return !exists;
	}

	/**
	 * Create the sections. The Section's description is displayed 
	 * (with checkbox) on the Course/Section Information screen of Sakai's course site setup.
	 * Call createEnrollmentSet before this method!
	 */
	public Collection<Section> createSections(Collection<SISSection> sections){
		if(sections == null) return null;
		List<Section> sList = new ArrayList<Section>();
		for(SISSection section: sections){
			sList.add(createSection(section));
		}
		return sList;
	}
	
	public Section createSection(SISSection section){
		if(section == null) return null;
		Section s = null;
		if(cmService.isSectionDefined(section.getId())){
			s = cmService.getSection(section.getId());
		}else{
			createSectionCategory(section.getCategoryCode(), section.getCategoryDescription());
			log.info("Create Section: " + section);
			s = cmAdmin.createSection(section.getId(),
					section.getTitle(), section.getDescription(),
					section.getCategoryCode(), section.getParentSectionId(),
					section.getCourseOfferingId(), section.getEnrollmentSetId());
			log.info("Inserted Section with id " + section.getId());
		}
		return s;
	}

	public Collection<Membership> createSectionMemberships(SISSection section, Collection<SISMembership> sectionMemberships) {
		if(sectionMemberships == null) return null;
		List<Membership> memberships = new ArrayList<Membership>();
		for (SISMembership sectionMembership: sectionMemberships) {
			memberships.add(createSectionMembership(section, sectionMembership));
		}
		return memberships;
	}
	
	public void manageSectionMemberships(SISCourseOffering sisCourseOffering, Section section, SISSection sisSection, Collection<SISMembership> sectionMemberships) {
		if(sectionMemberships == null) return;
		
		Set<Membership> currentSectionMemberships = cmService.getSectionMemberships(section.getEid());

		List<String> sectionUsers = currentSectionMemberships.stream().map(Membership::getUserId)
				.collect(Collectors.toList());

		List<String> newUsers = sectionMemberships.stream().map(SISMembership::getUserId)
				.collect(Collectors.toList());

		List<String> addList = newUsers.stream()
				.filter(user1 -> sectionUsers.stream().noneMatch(user2 -> user2.equals(user1)))
				.collect(Collectors.toList());

		for (String userId : addList) {
			SISMembership sisMembership = sectionMemberships.stream()
					.filter(membership -> membership.getUserId().equals(userId)).findAny().orElse(null);
			if (sisMembership != null) {
				createSectionMembership(sisSection, sisMembership);
			}
		}

		List<String> removeList = sectionUsers.stream()
				.filter(user1 -> newUsers.stream().noneMatch(user2 -> user2.equals(user1)))
				.collect(Collectors.toList());

		for (String userId : removeList) {
			deleteSectionMemberships(sisCourseOffering, userId);
		}
	}
	
	public Membership createSectionMembership(SISSection section, SISMembership sectionMembership) {
		if(sectionMembership == null) return null;
		Membership membership = cmAdmin.addOrUpdateSectionMembership(
					sectionMembership.getUserId(),
					sectionMembership.getRole(),
					section.getId(),
					sectionMembership.getStatus());
		log.info("Added/Updated SectionMembership - " + sectionMembership.getUserId() + " to sectionEid "
					+ section.getId());
		authzGroupService.refreshUser(sectionMembership.getUserId());
		return membership;
	}
	
	/**
	 * Remove memberships for the sections linked to the course.
	 */
	public void deleteSectionMemberships(SISCourseOffering courseOffering, SISCourseChange courseChange){
		Set<Section> sections = cmService.getSections(courseOffering.getId());
		for(Section section: sections){
			cmAdmin.removeSectionMembership(courseChange.getStudentNum(), section.getEid());
			log.info("Removed SectionMembership for user id " + courseChange.getStudentNum()
				+ " and sectionEid " + section.getEid());
		}
	}
	
	public void deleteSectionMemberships(SISCourseOffering courseOffering, String userId){
		Set<Section> sections = cmService.getSections(courseOffering.getId());
		for(Section section: sections){
			cmAdmin.removeSectionMembership(userId, section.getEid());
			log.info("Removed SectionMembership for user id " + userId
				+ " and sectionEid " + section.getEid());
			authzGroupService.refreshUser(userId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public final void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		logInfo("Job will now start.");
		client.init();
		if (isValidSession()) {
			executeJob(jobExecutionContext);
		}
		client.destroy();
		logInfo("Job has finished successfully.");
	}

	/**
	 * This method must be implemented by the actual job. The method will be
	 * Called after basic logging and session validity is done.
	 * 
	 * @param jobExecutionContext
	 * @throws JobExecutionException
	 */
	public abstract void executeJob(JobExecutionContext jobExecutionContext)
			throws JobExecutionException;

	private boolean isValidSession() throws JobExecutionException {
		Session session = sessionManager.getCurrentSession();
		if (session == null) {
			logError("There are no current session!");
		} else {
			session.setUserEid(ADMIN_USER);
			session.setUserId(ADMIN_USER);
			if (securityService.isSuperUser()) {
				return true;
			} else {
				logError("The user ("
						+ sessionManager.getCurrentSessionUserId()
						+ ") is not a superuser!");
			}
		}
		return false;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setCourseManagementService(CourseManagementService cmService) {
		this.cmService = cmService;
	}

	public void setCourseManagementAdmin(CourseManagementAdministration cmAdmin) {
		this.cmAdmin = cmAdmin;
	}
	
	public void setAuthzGroupService(AuthzGroupService authzGroupService) {
		this.authzGroupService = authzGroupService;
	}	

	public SISClient getClient() {
		return client;
	}

	public void setClient(SISClient client) {
		this.client = client;
	}
}
