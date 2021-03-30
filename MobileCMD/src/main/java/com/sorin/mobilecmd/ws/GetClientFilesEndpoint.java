package com.sorin.mobilecmd.ws;

import java.io.IOException;

import org.springframework.oxm.Marshaller;

import com.sorin.mobilecmd.services.WebService;
import com.sorin.mobilecmd.xml.schema.GetClientFilesRequest;
import com.sorin.mobilecmd.xml.schema.GetClientFilesResponse;
import com.sorin.mobilecmd.xml.schema.ObjectFactory;

public class GetClientFilesEndpoint extends BaseEndpoint {
	/**
	 * Creates a new ResponseEndpoint object - web-service-servlet.xml bean;
	 * @param marshaller
	 * @param exShellInvoker
	 * @throws IOException
	 */
	public GetClientFilesEndpoint(Marshaller marshaller, WebService service) throws IOException {
		super(marshaller, service);
	}

	/**
	 * The SOAPMessage Request is marshalled into an Object: <code>requestObject</code>.
	 * If there are no issues or exceptions we go ahead and invoke the desired service.
	 * @param requestObject the marshalled request
	 * @return an object <code>response</code>, as from the service method;
	 */
	protected Object invokeInternal(Object requestObject) throws Exception {
		GetClientFilesRequest request = (GetClientFilesRequest) requestObject;
		
		GetClientFilesResponse response = (new ObjectFactory()).createGetClientFilesResponse();
		response.getFiles().addAll(service.getClientFiles(request));
		
		return response;
	}
}
