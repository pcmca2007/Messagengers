package org.prabhash.javabrains.messanger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.prabhash.javabrains.messanger.model.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable e) {
		// TODO Auto-generated method stub
		ErrorMessage errorMessage=new ErrorMessage(e.getMessage(), 500, "https://techbulls.ca");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				
				.entity(errorMessage)
				.build();
	}

}
