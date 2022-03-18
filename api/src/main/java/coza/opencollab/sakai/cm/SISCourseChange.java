package coza.opencollab.sakai.cm;

public class SISCourseChange {
    private String studentNum;
    private String moduleCode;
    private String maintananceIndicator;

    public SISCourseChange() {
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getMaintananceIndicator() {
        return maintananceIndicator;
    }

    public void setMaintananceIndicator(String maintananceIndicator) {
        this.maintananceIndicator = maintananceIndicator;
    }
    
    @Override
    public String toString() {    	
    	return "studentNum: " + studentNum + ", " + "moduleCode: " + moduleCode + ", " + "maintananceIndicator: " + maintananceIndicator;
    }
}
