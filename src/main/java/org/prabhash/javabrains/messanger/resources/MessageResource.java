package org.prabhash.javabrains.messanger.resources;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.prabhash.javabrains.messanger.dao.MessageDAO;
import org.prabhash.javabrains.messanger.model.Message;
import org.prabhash.javabrains.messanger.resources.bean.MessageFilterBean;
import org.prabhash.javabrains.messanger.service.MessageService;

@Path("/messages")
public class MessageResource {
	
	MessageService messageService= new MessageService();
	MessageDAO messageDao=new MessageDAO();
	
	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessage(@QueryParam("year") int year,
									@QueryParam("start") int start,
									@QueryParam("size") int size){
			
		if(year>0){
			return messageService.getAllMessagesforYear(year);
		}
		
		if(size >=0 && size >=0){
			
			return messageService.getAllMessagesPaginated(start, size);
		}
		
		return messageService.getAllMessages();
	}*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessage(@BeanParam MessageFilterBean filterBean){
			
		if(filterBean.getYear()>0){
			return messageService.getAllMessagesforYear(filterBean.getYear());
		}
		
		if(filterBean.getStart() >=0 && filterBean.getSize() >=0){
			
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		System.out.println("INSIDE GET REAL");
		//return messageService.getAllMessages();
		return messageDao.getAllMessages();
	}
	
	
	
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException{
		Message newMessage= messageDao.addMessage(message);
		//Message newMessage=messageService.addMessage(message);
		// Response.status(Status.CREATED).entity(newMessage).build();
		//Response.created(new URI("/messenger/webapi/messages"+ newMessage.getId())).entity(newMessage).build();
		String neId=String.valueOf(newMessage.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(neId).build();
		return Response.created(uri).entity(newMessage).build();
	}
	
	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id ,Message message){
		
			message.setId(id);
			return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteMessage(@PathParam("messageId") long id){
		
		messageService.removeMessage(id);
		
	}
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long Id,@Context UriInfo uriInfo){
		
		Message message= messageService.getMessage(Id);
		
		String uri = getUriForSelf(uriInfo, message);
		String profileuri=getUriForProfile(uriInfo,message);
		String commentUri= getUriForComments(uriInfo,message);
					
		message.addLinks(uri, "self");
		message.addLinks(profileuri, "profile");
		message.addLinks(commentUri, "comments");
		return message;
	}


	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri=uriInfo.getAbsolutePathBuilder()
						  .path(MessageResource.class)
						  .path(MessageResource.class, "getCommentResource")
						  .path(CommentResource.class)
						  .resolveTemplate("messageId", message.getId())
						  .build();
		return uri.toString();
	}






	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri= uriInfo.getAbsolutePathBuilder()
						  .path(ProfileResource.class) 
						  .path(message.getAuthor())
						  .build().toString();
		return uri;
	}






	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri=uriInfo.getAbsolutePathBuilder()            //http://localhost:8010/webapi/
				.path(MessageResource.class)        // /messages
				.path(Long.toString(message.getId()))
				.build().toString();
		return uri;
	}
	
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}

}
