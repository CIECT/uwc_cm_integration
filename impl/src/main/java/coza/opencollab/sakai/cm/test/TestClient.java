package coza.opencollab.sakai.cm.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import coza.opencollab.sakai.cm.Utils;

public class TestClient implements SISClient {
	private String fileDirectory;

	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	public void init() {

	}

	public void destroy() {

	}

	public Collection<SISAcademicSession> getAcademicSessions() {
		File file = new File(fileDirectory, "AcademicSessions");
		try {
			List<SISAcademicSession> list = new ArrayList<SISAcademicSession>();
			while (file.hasLine()) {
				SISAcademicSession as = new SISAcademicSession(file.get(0),
						file.get(1), file.get(2), file.get(3),
						Utils.getDateFromString(file.get(4)),
						Utils.getDateFromString(file.get(5)), file.get(6));
				list.add(as);
			}
			return list;
		} finally {
			file.close();
		}
	}

	public Collection<SISCourseSet> getCourseSets(
			SISAcademicSession academicSession) {
		File file = new File(fileDirectory, "CourseSets");
		try {
			List<SISCourseSet> courseSets = new ArrayList<SISCourseSet>();
			while (file.hasLine()) {
				SISCourseSet cs = new SISCourseSet(file.get(0));
				cs.setId(file.get(1));
				cs.setTitle(file.get(2));
				cs.setDescription(file.get(3));
				cs.setCategory(file.get(4));
				courseSets.add(cs);
			}
			return courseSets;
		} finally {
			file.close();
		}
	}

	public Collection<SISMembership> getCourseSetMemberships(
			SISCourseSet courseSet) {
		File file = new File(fileDirectory, "CourseSetMemberships");
		try {
			return null;// XXX
		} finally {
			file.close();
		}
	}

	public Collection<SISCanonicalCourse> getCanonicalCourses(
			SISAcademicSession academicSession) {
		File file = new File(fileDirectory, "CanonicalCourses");
		try {
			List<SISCanonicalCourse> canonicaCourses = new ArrayList<SISCanonicalCourse>();
			while (file.hasLine()) {
				SISCanonicalCourse canonicalCourse = new SISCanonicalCourse(
						file.get(0));
				canonicalCourse.setId(file.get(1));
				canonicalCourse.setTitle(file.get(2));
				canonicalCourse.setDescription(file.get(3));
				canonicaCourses.add(canonicalCourse);
			}
			return canonicaCourses;
		} finally {
			file.close();
		}
	}

	public Collection<SISCourseOffering> getCourseOfferings(
			SISAcademicSession academicSession) {
		File file = new File(fileDirectory, "CourseOfferings");
		try {
			List<SISCourseOffering> courseOfferings = new ArrayList<SISCourseOffering>();
			while (file.hasLine()) {
				SISCourseOffering courseOffering = new SISCourseOffering(
						file.get(0));
				courseOffering.setId(file.get(1));
				courseOffering.setTitle(file.get(2));
				courseOffering.setDescription(file.get(3));
				courseOffering.setStatus(file.get(4));
				courseOffering.setAcademicSessionId(file.get(5));
				courseOffering.setCanonicalCourseId(file.get(6));
				courseOffering
						.setStartDate(Utils.getDateFromString(file.get(7)));
				courseOffering.setEndDate(Utils.getDateFromString(file.get(8)));
				courseOffering.addCourseSetIds(file.get(9));
				courseOfferings.add(courseOffering);
			}
			return courseOfferings;
		} finally {
			file.close();
		}
	}

	public Collection<SISMembership> getCourseOfferingMemberships(
			SISCourseOffering courseOffering) {
		File file = new File(fileDirectory, "CourseOfferingMemberships");
		try {
			return null;// XXX
		} finally {
			file.close();
		}
	}

	public Collection<SISEnrollmentSet> getEnrollmentSets(
			SISCourseOffering courseOffering) {
		File file = new File(fileDirectory, "EnrollmentSets");
		try {
			List<SISEnrollmentSet> enrollmentSets = new ArrayList<SISEnrollmentSet>();
			while (file.hasLine()) {
				SISEnrollmentSet enrollmentSet = new SISEnrollmentSet(
						file.get(0));
				enrollmentSet.setId(file.get(1));
				enrollmentSet.setTitle(file.get(2));
				enrollmentSet.setDescription(file.get(3));
				enrollmentSet.setCategory(file.get(4));
				enrollmentSet.setDefaultEnrollmentCredits(file.get(5));
				enrollmentSet.setCourseOfferingEid(file.get(6));
				enrollmentSet.addOfficialInstructor(file.get(7));
				enrollmentSets.add(enrollmentSet);
			}
			return enrollmentSets;
		} finally {
			file.close();
		}
	}

	public Collection<SISEnrollment> getEnrollments(
			SISEnrollmentSet enrollmentSet) {
		File file = new File(fileDirectory, "Enrollments");
		try {
			List<SISEnrollment> enrollments = new ArrayList<SISEnrollment>();
			while (file.hasLine()) {
				SISEnrollment enrollment = new SISEnrollment(file.get(0));
				enrollment.setStudentId(file.get(1));
				enrollment.setEnrollmentSetId(file.get(2));
				enrollment.setStatus(file.get(3));
				enrollment.setCredits(file.get(4));
				enrollment.setGradingScheme(file.get(5));
				enrollments.add(enrollment);
			}
			return enrollments;
		} finally {
			file.close();
		}
	}

	public Collection<SISSection> getSections(SISCourseOffering courseOffering) {
		File file = new File(fileDirectory, "Sections");
		try {
			List<SISSection> ss = new ArrayList<SISSection>();
			while (file.hasLine()) {
				SISSection section = new SISSection(file.get(0));
				section.setId(file.get(1));
				section.setTitle(file.get(2));
				section.setDescription(file.get(3));
				section.setCategoryCode(file.get(4));
				section.setCategoryDescription(file.get(5));
				section.setParentSectionId(file.get(6));
				section.setCourseOfferingId(file.get(7));
				section.setEnrollmentSetId(file.get(8));
				ss.add(section);
			}
			return ss;
		} finally {
			file.close();
		}
	}

	public Collection<SISMembership> getSectionMemberships(SISSection section) {
		File file = new File(fileDirectory, "SectionMemberships");
		try {
			List<SISMembership> memberships = new ArrayList<SISMembership>();
			while (file.hasLine()) {
				SISMembership membership = new SISMembership(file.get(0));
				membership.setUserId(file.get(1));
				membership.setRole(file.get(2));
				membership.setStatus(file.get(3));
				memberships.add(membership);
			}
			return memberships;
		} finally {
			file.close();
		}
	}

	public Collection<SISCourseChange> getCourseChanges() {
		// TODO Auto-generated method stub
		return null;
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

}
