/**
 * RequestPortSoap11Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc_academia.www;

public class RequestPortSoap11Stub extends org.apache.axis.client.Stub implements za.ac.uwc_academia.www.RequestPort {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[5];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Download_Students");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_StudentsRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Students_Input"), za.ac.uwc_academia.www.Download_Students_Input.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Students_Output"));
        oper.setReturnClass(za.ac.uwc_academia.www.Download_Students_Output.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_StudentsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Download_Intakes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_IntakeRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Intakes_Input"), za.ac.uwc_academia.www.Download_Intakes_Input.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "DownloadIntakeOutput"));
        oper.setReturnClass(za.ac.uwc_academia.www.DownloadIntakeOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_IntakeResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Download_Programs");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_ProgramsRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Programs_Input"), za.ac.uwc_academia.www.Download_Programs_Input.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "DownloadProgramsOutput"));
        oper.setReturnClass(za.ac.uwc_academia.www.DownloadProgramsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_ProgramsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Download_Module_Periods");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Module_PeriodsRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Module_Periods_Input"), za.ac.uwc_academia.www.Download_Module_Periods_Input.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "DownloadModulePeriodsOutput"));
        oper.setReturnClass(za.ac.uwc_academia.www.DownloadModulePeriodsOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Module_PeriodsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Download_Lecturers");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_LecturersRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Lecturers_Input"), za.ac.uwc_academia.www.Download_Lecturers_Input.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_LecturersOutput"));
        oper.setReturnClass(za.ac.uwc_academia.www.Download_LecturersOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_LecturersResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

    }

    public RequestPortSoap11Stub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public RequestPortSoap11Stub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public RequestPortSoap11Stub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Intakes_Input");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.Download_Intakes_Input.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Lecturers_Input");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.Download_Lecturers_Input.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_LecturersOutput");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.Download_LecturersOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Module_Periods_Input");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.Download_Module_Periods_Input.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Programs_Input");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.Download_Programs_Input.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Students_Input");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.Download_Students_Input.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Students_Output");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.Download_Students_Output.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "DownloadIntakeOutput");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.DownloadIntakeOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "DownloadModulePeriodsOutput");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.DownloadModulePeriodsOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "DownloadProgramsOutput");
            cachedSerQNames.add(qName);
            cls = za.ac.uwc_academia.www.DownloadProgramsOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "General_ListColumns");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "column");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "General_ListItems");
            cachedSerQNames.add(qName);
            cls = java.lang.String[][].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "General_ListColumns");
            qName2 = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "row");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "General_StatusMessages");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "StatusMessage");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public za.ac.uwc_academia.www.Download_Students_Output download_Students(za.ac.uwc_academia.www.Download_Students_Input download_StudentsRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Students"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {download_StudentsRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (za.ac.uwc_academia.www.Download_Students_Output) _resp;
            } catch (java.lang.Exception _exception) {
                return (za.ac.uwc_academia.www.Download_Students_Output) org.apache.axis.utils.JavaUtils.convert(_resp, za.ac.uwc_academia.www.Download_Students_Output.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public za.ac.uwc_academia.www.DownloadIntakeOutput download_Intakes(za.ac.uwc_academia.www.Download_Intakes_Input download_IntakeRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Intakes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {download_IntakeRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (za.ac.uwc_academia.www.DownloadIntakeOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (za.ac.uwc_academia.www.DownloadIntakeOutput) org.apache.axis.utils.JavaUtils.convert(_resp, za.ac.uwc_academia.www.DownloadIntakeOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public za.ac.uwc_academia.www.DownloadProgramsOutput download_Programs(za.ac.uwc_academia.www.Download_Programs_Input download_ProgramsRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Programs"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {download_ProgramsRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (za.ac.uwc_academia.www.DownloadProgramsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (za.ac.uwc_academia.www.DownloadProgramsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, za.ac.uwc_academia.www.DownloadProgramsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public za.ac.uwc_academia.www.DownloadModulePeriodsOutput download_Module_Periods(za.ac.uwc_academia.www.Download_Module_Periods_Input download_Module_PeriodsRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Module_Periods"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {download_Module_PeriodsRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (za.ac.uwc_academia.www.DownloadModulePeriodsOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (za.ac.uwc_academia.www.DownloadModulePeriodsOutput) org.apache.axis.utils.JavaUtils.convert(_resp, za.ac.uwc_academia.www.DownloadModulePeriodsOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public za.ac.uwc_academia.www.Download_LecturersOutput download_Lecturers(za.ac.uwc_academia.www.Download_Lecturers_Input download_LecturersRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Lecturers"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {download_LecturersRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (za.ac.uwc_academia.www.Download_LecturersOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (za.ac.uwc_academia.www.Download_LecturersOutput) org.apache.axis.utils.JavaUtils.convert(_resp, za.ac.uwc_academia.www.Download_LecturersOutput.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
