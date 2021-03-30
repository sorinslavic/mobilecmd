package com.sorin.mobilecmd.ws;

import java.io.IOException;

import org.springframework.oxm.Marshaller;

import com.sorin.mobilecmd.services.WebService;
import com.sorin.mobilecmd.xml.schema.GetClientsRequest;
import com.sorin.mobilecmd.xml.schema.GetClientsResponse;
import com.sorin.mobilecmd.xml.schema.ObjectFactory;

public class GetClientsEndpoint extends BaseEndpoint {
	/**
	 * Creates a new ResponseEndpoint object - web-service-servlet.xml bean;
	 * @param marshaller
	 * @param exShellInvoker
	 * @throws IOException
	 */
	public GetClientsEndpoint(Marshaller marshaller, WebService service) throws IOException {
		super(marshaller, service);
	}

	/**
	 * The SOAPMessage Request is marshalled into an Object: <code>requestObject</code>.
	 * If there are no issues or exceptions we go ahead and invoke the desired service.
	 * @param requestObject the marshalled request
	 * @return an object <code>response</code>, as from the service method;
	 */
	protected Object invokeInternal(Object requestObject) throws Exception {
		GetClientsRequest request = (GetClientsRequest) requestObject;
		
		GetClientsResponse response = (new ObjectFactory()).createGetClientsResponse();
		response.getClients().addAll(service.getClients(request));
		
		return response;
	}
}
