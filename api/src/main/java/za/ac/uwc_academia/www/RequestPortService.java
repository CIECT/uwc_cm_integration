/**
 * RequestPortService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc_academia.www;

public interface RequestPortService extends javax.xml.rpc.Service {
    public java.lang.String getRequestPortSoap11Address();

    public za.ac.uwc_academia.www.RequestPort getRequestPortSoap11() throws javax.xml.rpc.ServiceException;

    public za.ac.uwc_academia.www.RequestPort getRequestPortSoap11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
