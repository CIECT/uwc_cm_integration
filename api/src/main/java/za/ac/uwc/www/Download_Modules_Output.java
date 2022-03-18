/**
 * Download_Modules_Output.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public class Download_Modules_Output  implements java.io.Serializable {
    private java.math.BigInteger status;

    private java.math.BigInteger year;

    private java.lang.String department_code;

    private java.lang.String module_code;

    private java.lang.String module;

    private java.lang.String[][] module_List;

    private java.lang.String message;

    private java.lang.String[] statusMessages;

    public Download_Modules_Output() {
    }

    public Download_Modules_Output(
           java.math.BigInteger status,
           java.math.BigInteger year,
           java.lang.String department_code,
           java.lang.String module_code,
           java.lang.String module,
           java.lang.String[][] module_List,
           java.lang.String message,
           java.lang.String[] statusMessages) {
           this.status = status;
           this.year = year;
           this.department_code = department_code;
           this.module_code = module_code;
           this.module = module;
           this.module_List = module_List;
           this.message = message;
           this.statusMessages = statusMessages;
    }


    /**
     * Gets the status value for this Download_Modules_Output.
     * 
     * @return status
     */
    public java.math.BigInteger getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Download_Modules_Output.
     * 
     * @param status
     */
    public void setStatus(java.math.BigInteger status) {
        this.status = status;
    }


    /**
     * Gets the year value for this Download_Modules_Output.
     * 
     * @return year
     */
    public java.math.BigInteger getYear() {
        return year;
    }


    /**
     * Sets the year value for this Download_Modules_Output.
     * 
     * @param year
     */
    public void setYear(java.math.BigInteger year) {
        this.year = year;
    }


    /**
     * Gets the department_code value for this Download_Modules_Output.
     * 
     * @return department_code
     */
    public java.lang.String getDepartment_code() {
        return department_code;
    }


    /**
     * Sets the department_code value for this Download_Modules_Output.
     * 
     * @param department_code
     */
    public void setDepartment_code(java.lang.String department_code) {
        this.department_code = department_code;
    }


    /**
     * Gets the module_code value for this Download_Modules_Output.
     * 
     * @return module_code
     */
    public java.lang.String getModule_code() {
        return module_code;
    }


    /**
     * Sets the module_code value for this Download_Modules_Output.
     * 
     * @param module_code
     */
    public void setModule_code(java.lang.String module_code) {
        this.module_code = module_code;
    }


    /**
     * Gets the module value for this Download_Modules_Output.
     * 
     * @return module
     */
    public java.lang.String getModule() {
        return module;
    }


    /**
     * Sets the module value for this Download_Modules_Output.
     * 
     * @param module
     */
    public void setModule(java.lang.String module) {
        this.module = module;
    }


    /**
     * Gets the module_List value for this Download_Modules_Output.
     * 
     * @return module_List
     */
    public java.lang.String[][] getModule_List() {
        return module_List;
    }


    /**
     * Sets the module_List value for this Download_Modules_Output.
     * 
     * @param module_List
     */
    public void setModule_List(java.lang.String[][] module_List) {
        this.module_List = module_List;
    }


    /**
     * Gets the message value for this Download_Modules_Output.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Download_Modules_Output.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the statusMessages value for this Download_Modules_Output.
     * 
     * @return statusMessages
     */
    public java.lang.String[] getStatusMessages() {
        return statusMessages;
    }


    /**
     * Sets the statusMessages value for this Download_Modules_Output.
     * 
     * @param statusMessages
     */
    public void setStatusMessages(java.lang.String[] statusMessages) {
        this.statusMessages = statusMessages;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_Modules_Output)) return false;
        Download_Modules_Output other = (Download_Modules_Output) obj;
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
              this.module.equals(other.getModule()))) &&
            ((this.module_List==null && other.getModule_List()==null) || 
             (this.module_List!=null &&
              java.util.Arrays.equals(this.module_List, other.getModule_List()))) &&
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
        if (getModule_List() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModule_List());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModule_List(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        new org.apache.axis.description.TypeDesc(Download_Modules_Output.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Download_Modules_Output"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("module_List");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Module_List"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "General_ListColumns"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "row"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "StatusMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "StatusMessage"));
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
