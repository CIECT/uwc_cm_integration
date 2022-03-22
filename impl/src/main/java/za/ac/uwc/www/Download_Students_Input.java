/**
 * Download_Students_Input.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public class Download_Students_Input  implements java.io.Serializable {
    private java.lang.String module_code;

    private java.lang.String student;

    private java.math.BigInteger year;

    public Download_Students_Input() {
    }

    public Download_Students_Input(
           java.lang.String module_code,
           java.lang.String student,
           java.math.BigInteger year) {
           this.module_code = module_code;
           this.student = student;
           this.year = year;
    }


    /**
     * Gets the module_code value for this Download_Students_Input.
     * 
     * @return module_code
     */
    public java.lang.String getModule_code() {
        return module_code;
    }


    /**
     * Sets the module_code value for this Download_Students_Input.
     * 
     * @param module_code
     */
    public void setModule_code(java.lang.String module_code) {
        this.module_code = module_code;
    }


    /**
     * Gets the student value for this Download_Students_Input.
     * 
     * @return student
     */
    public java.lang.String getStudent() {
        return student;
    }


    /**
     * Sets the student value for this Download_Students_Input.
     * 
     * @param student
     */
    public void setStudent(java.lang.String student) {
        this.student = student;
    }


    /**
     * Gets the year value for this Download_Students_Input.
     * 
     * @return year
     */
    public java.math.BigInteger getYear() {
        return year;
    }


    /**
     * Sets the year value for this Download_Students_Input.
     * 
     * @param year
     */
    public void setYear(java.math.BigInteger year) {
        this.year = year;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_Students_Input)) return false;
        Download_Students_Input other = (Download_Students_Input) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.module_code==null && other.getModule_code()==null) || 
             (this.module_code!=null &&
              this.module_code.equals(other.getModule_code()))) &&
            ((this.student==null && other.getStudent()==null) || 
             (this.student!=null &&
              this.student.equals(other.getStudent()))) &&
            ((this.year==null && other.getYear()==null) || 
             (this.year!=null &&
              this.year.equals(other.getYear())));
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
        if (getModule_code() != null) {
            _hashCode += getModule_code().hashCode();
        }
        if (getStudent() != null) {
            _hashCode += getStudent().hashCode();
        }
        if (getYear() != null) {
            _hashCode += getYear().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Download_Students_Input.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Download_Students_Input"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("module_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Module_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("student");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Student"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
