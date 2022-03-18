/**
 * RequestPortServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc_academia.www;

public class RequestPortServiceLocator extends org.apache.axis.client.Service implements za.ac.uwc_academia.www.RequestPortService {

    public RequestPortServiceLocator() {
    }


    public RequestPortServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RequestPortServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RequestPortSoap11
    private java.lang.String RequestPortSoap11_address = "http://uwc-demo.southafricanorth.cloudapp.azure.com:8091/academia-ws/*";

    public java.lang.String getRequestPortSoap11Address() {
        return RequestPortSoap11_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RequestPortSoap11WSDDServiceName = "RequestPortSoap11";

    public java.lang.String getRequestPortSoap11WSDDServiceName() {
        return RequestPortSoap11WSDDServiceName;
    }

    public void setRequestPortSoap11WSDDServiceName(java.lang.String name) {
        RequestPortSoap11WSDDServiceName = name;
    }

    public za.ac.uwc_academia.www.RequestPort getRequestPortSoap11() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RequestPortSoap11_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRequestPortSoap11(endpoint);
    }

    public za.ac.uwc_academia.www.RequestPort getRequestPortSoap11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.uwc_academia.www.RequestPortSoap11Stub _stub = new za.ac.uwc_academia.www.RequestPortSoap11Stub(portAddress, this);
            _stub.setPortName(getRequestPortSoap11WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRequestPortSoap11EndpointAddress(java.lang.String address) {
        RequestPortSoap11_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.uwc_academia.www.RequestPort.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.uwc_academia.www.RequestPortSoap11Stub _stub = new za.ac.uwc_academia.www.RequestPortSoap11Stub(new java.net.URL(RequestPortSoap11_address), this);
                _stub.setPortName(getRequestPortSoap11WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("RequestPortSoap11".equals(inputPortName)) {
            return getRequestPortSoap11();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "RequestPortService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "RequestPortSoap11"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RequestPortSoap11".equals(portName)) {
            setRequestPortSoap11EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
