package org.prabhash.javabrains.messanger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.prabhash.javabrains.messanger.model.ErrorMessage;

@Provider
public class DataNotfoundExceptionMapper implements ExceptionMapper<DataNotFoundException>   {

	@Override
	public Response toResponse(DataNotFoundException arg) {
		// TODO Auto-generated method stub
		ErrorMessage errorMessage=new ErrorMessage(arg.getMessage(), 404, "techbulls.ca");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}

	
}
