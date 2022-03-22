/**
 * Download_Students_Output.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc_academia.www;

public class Download_Students_Output  implements java.io.Serializable {
    private java.math.BigInteger status;

    private java.lang.String module_code;

    private java.lang.String student;

    private java.lang.String[][] student_List;

    private java.math.BigInteger year;

    private java.lang.String message;

    private java.lang.String[] statusMessages;

    public Download_Students_Output() {
    }

    public Download_Students_Output(
           java.math.BigInteger status,
           java.lang.String module_code,
           java.lang.String student,
           java.lang.String[][] student_List,
           java.math.BigInteger year,
           java.lang.String message,
           java.lang.String[] statusMessages) {
           this.status = status;
           this.module_code = module_code;
           this.student = student;
           this.student_List = student_List;
           this.year = year;
           this.message = message;
           this.statusMessages = statusMessages;
    }


    /**
     * Gets the status value for this Download_Students_Output.
     * 
     * @return status
     */
    public java.math.BigInteger getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Download_Students_Output.
     * 
     * @param status
     */
    public void setStatus(java.math.BigInteger status) {
        this.status = status;
    }


    /**
     * Gets the module_code value for this Download_Students_Output.
     * 
     * @return module_code
     */
    public java.lang.String getModule_code() {
        return module_code;
    }


    /**
     * Sets the module_code value for this Download_Students_Output.
     * 
     * @param module_code
     */
    public void setModule_code(java.lang.String module_code) {
        this.module_code = module_code;
    }


    /**
     * Gets the student value for this Download_Students_Output.
     * 
     * @return student
     */
    public java.lang.String getStudent() {
        return student;
    }


    /**
     * Sets the student value for this Download_Students_Output.
     * 
     * @param student
     */
    public void setStudent(java.lang.String student) {
        this.student = student;
    }


    /**
     * Gets the student_List value for this Download_Students_Output.
     * 
     * @return student_List
     */
    public java.lang.String[][] getStudent_List() {
        return student_List;
    }


    /**
     * Sets the student_List value for this Download_Students_Output.
     * 
     * @param student_List
     */
    public void setStudent_List(java.lang.String[][] student_List) {
        this.student_List = student_List;
    }


    /**
     * Gets the year value for this Download_Students_Output.
     * 
     * @return year
     */
    public java.math.BigInteger getYear() {
        return year;
    }


    /**
     * Sets the year value for this Download_Students_Output.
     * 
     * @param year
     */
    public void setYear(java.math.BigInteger year) {
        this.year = year;
    }


    /**
     * Gets the message value for this Download_Students_Output.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Download_Students_Output.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the statusMessages value for this Download_Students_Output.
     * 
     * @return statusMessages
     */
    public java.lang.String[] getStatusMessages() {
        return statusMessages;
    }


    /**
     * Sets the statusMessages value for this Download_Students_Output.
     * 
     * @param statusMessages
     */
    public void setStatusMessages(java.lang.String[] statusMessages) {
        this.statusMessages = statusMessages;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_Students_Output)) return false;
        Download_Students_Output other = (Download_Students_Output) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.module_code==null && other.getModule_code()==null) || 
             (this.module_code!=null &&
              this.module_code.equals(other.getModule_code()))) &&
            ((this.student==null && other.getStudent()==null) || 
             (this.student!=null &&
              this.student.equals(other.getStudent()))) &&
            ((this.student_List==null && other.getStudent_List()==null) || 
             (this.student_List!=null &&
              java.util.Arrays.equals(this.student_List, other.getStudent_List()))) &&
            ((this.year==null && other.getYear()==null) || 
             (this.year!=null &&
              this.year.equals(other.getYear()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.statusMessages==null && other.getStatusMessages()==null) || 
             (this.statusMessages!=null &&
              java.util.Arrays.equals(this.statusMessages, other.getStatusMessages())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getModule_code() != null) {
            _hashCode += getModule_code().hashCode();
        }
        if (getStudent() != null) {
            _hashCode += getStudent().hashCode();
        }
        if (getStudent_List() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudent_List());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudent_List(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getYear() != null) {
            _hashCode += getYear().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getStatusMessages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStatusMessages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStatusMessages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Download_Students_Output.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Students_Output"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("module_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Module_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("student");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Student"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("student_List");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Student_List"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "General_ListColumns"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "row"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "StatusMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "StatusMessage"));
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
