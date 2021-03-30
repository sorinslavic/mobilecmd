package com.sorin.mobilecmd.ws;

import java.io.IOException;

import org.springframework.oxm.Marshaller;

import com.sorin.mobilecmd.services.WebService;
import com.sorin.mobilecmd.xml.schema.RunCommand;
import com.sorin.mobilecmd.xml.schema.RunResponse;

public class RunCommandEndpoint extends BaseEndpoint {

	/**
	 * Creates a new ResponseEndpoint object - web-service-servlet.xml bean;
	 * @param marshaller
	 * @param exShellInvoker
	 * @throws IOException
	 */
	public RunCommandEndpoint(Marshaller marshaller, WebService service) throws IOException {
		super(marshaller, service);
	}

	/**
	 * The SOAPMessage Request is marshalled into an Object: <code>requestObject</code>.
	 * If there are no issues or exceptions we go ahead and invoke the desired service.
	 * @param requestObject the marshalled request
	 * @return an object <code>response</code>, as from the service method;
	 */
	protected Object invokeInternal(Object requestObject) throws Exception {
		RunCommand request = (RunCommand) requestObject;
		
		RunResponse response = service.runCommand(request);	
			
		return response;
	}

}
