/**
 * Download_Modules_Input.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public class Download_Modules_Input  implements java.io.Serializable {
    private java.math.BigInteger year;

    private java.lang.String department_code;

    private java.lang.String module_code;

    private java.lang.String module;

    public Download_Modules_Input() {
    }

    public Download_Modules_Input(
           java.math.BigInteger year,
           java.lang.String department_code,
           java.lang.String module_code,
           java.lang.String module) {
           this.year = year;
           this.department_code = department_code;
           this.module_code = module_code;
           this.module = module;
    }


    /**
     * Gets the year value for this Download_Modules_Input.
     * 
     * @return year
     */
    public java.math.BigInteger getYear() {
        return year;
    }


    /**
     * Sets the year value for this Download_Modules_Input.
     * 
     * @param year
     */
    public void setYear(java.math.BigInteger year) {
        this.year = year;
    }


    /**
     * Gets the department_code value for this Download_Modules_Input.
     * 
     * @return department_code
     */
    public java.lang.String getDepartment_code() {
        return department_code;
    }


    /**
     * Sets the department_code value for this Download_Modules_Input.
     * 
     * @param department_code
     */
    public void setDepartment_code(java.lang.String department_code) {
        this.department_code = department_code;
    }


    /**
     * Gets the module_code value for this Download_Modules_Input.
     * 
     * @return module_code
     */
    public java.lang.String getModule_code() {
        return module_code;
    }


    /**
     * Sets the module_code value for this Download_Modules_Input.
     * 
     * @param module_code
     */
    public void setModule_code(java.lang.String module_code) {
        this.module_code = module_code;
    }


    /**
     * Gets the module value for this Download_Modules_Input.
     * 
     * @return module
     */
    public java.lang.String getModule() {
        return module;
    }


    /**
     * Sets the module value for this Download_Modules_Input.
     * 
     * @param module
     */
    public void setModule(java.lang.String module) {
        this.module = module;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_Modules_Input)) return false;
        Download_Modules_Input other = (Download_Modules_Input) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.year==null && other.getYear()==null) || 
             (this.year!=null &&
              this.year.equals(other.getYear()))) &&
            ((this.department_code==null && other.getDepartment_code()==null) || 
             (this.department_code!=null &&
              this.department_code.equals(other.getDepartment_code()))) &&
            ((this.module_code==null && other.getModule_code()==null) || 
             (this.module_code!=null &&
              this.module_code.equals(other.getModule_code()))) &&
            ((this.module==null && other.getModule()==null) || 
             (this.module!=null &&
              this.module.equals(other.getModule())));
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
        if (getYear() != null) {
            _hashCode += getYear().hashCode();
        }
        if (getDepartment_code() != null) {
            _hashCode += getDepartment_code().hashCode();
        }
        if (getModule_code() != null) {
            _hashCode += getModule_code().hashCode();
        }
        if (getModule() != null) {
            _hashCode += getModule().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Download_Modules_Input.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Download_Modules_Input"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
        elemField.setFieldName("module_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Module_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("module");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Module"));
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
