/**
 * Download_Department_Input.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public class Download_Department_Input  implements java.io.Serializable {
    private java.lang.String department;

    private java.lang.String department_code;

    private java.lang.String faculty;

    public Download_Department_Input() {
    }

    public Download_Department_Input(
           java.lang.String department,
           java.lang.String department_code,
           java.lang.String faculty) {
           this.department = department;
           this.department_code = department_code;
           this.faculty = faculty;
    }


    /**
     * Gets the department value for this Download_Department_Input.
     * 
     * @return department
     */
    public java.lang.String getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this Download_Department_Input.
     * 
     * @param department
     */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }


    /**
     * Gets the department_code value for this Download_Department_Input.
     * 
     * @return department_code
     */
    public java.lang.String getDepartment_code() {
        return department_code;
    }


    /**
     * Sets the department_code value for this Download_Department_Input.
     * 
     * @param department_code
     */
    public void setDepartment_code(java.lang.String department_code) {
        this.department_code = department_code;
    }


    /**
     * Gets the faculty value for this Download_Department_Input.
     * 
     * @return faculty
     */
    public java.lang.String getFaculty() {
        return faculty;
    }


    /**
     * Sets the faculty value for this Download_Department_Input.
     * 
     * @param faculty
     */
    public void setFaculty(java.lang.String faculty) {
        this.faculty = faculty;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_Department_Input)) return false;
        Download_Department_Input other = (Download_Department_Input) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.department==null && other.getDepartment()==null) || 
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            ((this.department_code==null && other.getDepartment_code()==null) || 
             (this.department_code!=null &&
              this.department_code.equals(other.getDepartment_code()))) &&
            ((this.faculty==null && other.getFaculty()==null) || 
             (this.faculty!=null &&
              this.faculty.equals(other.getFaculty())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        if (getDepartment_code() != null) {
            _hashCode += getDepartment_code().hashCode();
        }
        if (getFaculty() != null) {
            _hashCode += getFaculty().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Download_Department_Input.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Download_Department_Input"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Department_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faculty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Faculty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
