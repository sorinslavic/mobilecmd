//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.08 at 08:17:20 PM EEST 
//


package com.sorin.mobilecmd.xml.schema;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sorin.mobilecmd.xml.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sorin.mobilecmd.xml.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Client }
     * 
     */
    public Client createClient() {
        return new Client();
    }

    /**
     * Create an instance of {@link GetClientsResponse }
     * 
     */
    public GetClientsResponse createGetClientsResponse() {
        return new GetClientsResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link GetClientFilesResponse }
     * 
     */
    public GetClientFilesResponse createGetClientFilesResponse() {
        return new GetClientFilesResponse();
    }

    /**
     * Create an instance of {@link LoginRequest }
     * 
     */
    public LoginRequest createLoginRequest() {
        return new LoginRequest();
    }

    /**
     * Create an instance of {@link ClientFile }
     * 
     */
    public ClientFile createClientFile() {
        return new ClientFile();
    }

    /**
     * Create an instance of {@link GetClientFilesRequest }
     * 
     */
    public GetClientFilesRequest createGetClientFilesRequest() {
        return new GetClientFilesRequest();
    }

    /**
     * Create an instance of {@link GetClientsRequest }
     * 
     */
    public GetClientsRequest createGetClientsRequest() {
        return new GetClientsRequest();
    }

    /**
     * Create an instance of {@link RunResponse }
     * 
     */
    public RunResponse createRunResponse() {
        return new RunResponse();
    }

    /**
     * Create an instance of {@link RunCommand }
     * 
     */
    public RunCommand createRunCommand() {
        return new RunCommand();
    }

}
