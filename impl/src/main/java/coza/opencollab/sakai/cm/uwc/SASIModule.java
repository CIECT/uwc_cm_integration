package coza.opencollab.sakai.cm.uwc;

import java.util.Date;

import coza.opencollab.sakai.cm.SISAcademicSession;
import coza.opencollab.sakai.cm.Utils;

public class SASIModule {

    private String moduleCode;

    private Date startDate;

    private Date endDate;

    private String description;

    private String academicSessionCode;

    private SISAcademicSession academicSession;

	public SASIModule() {
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcademicSessionCode() {
        return academicSessionCode;
    }

    public void setAcademicSessionCode(String academicSessionCode) {
        this.academicSessionCode = academicSessionCode;
    }

    public SISAcademicSession getAcademicSession() {
		return academicSession;
	}

	public void setAcademicSession(SISAcademicSession academicSession) {
		this.academicSession = academicSession;
	}

    /**
     * Course Management Helper method - CanonicalCourse eid example: ESS 112
     */
    public String getCanonicalCourseReference() {
        return moduleCode.substring(0, 3) + " " + moduleCode.substring(3, moduleCode.length());
    }

    /**
     * Course Management Helper method - CourseOffering eid example: ESS Y 112 2012
     */
    public String getCourseOfferingReference() {
        return getCanonicalCourseReference() + " " + academicSessionCode + " " + academicSession.getYear();
    }

    /**
     * Course Management Helper method - EnrollmentSet eid example: ESS112 2012 ES
     */
    public String getEnrollmentSetReference() {
        return getCourseOfferingReference() + " ES";
    }

    public String getYear() {
        return academicSession.getYear();
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode() + academicSession.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (obj instanceof SASIModule) {
            SASIModule other = (SASIModule) obj;
            return Utils.isEquals(moduleCode, other.moduleCode) && Utils.isEquals(academicSession, other.academicSession);
        }
        else {
            return false;
        }
    }
}
