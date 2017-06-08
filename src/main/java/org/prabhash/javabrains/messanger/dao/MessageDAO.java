package org.prabhash.javabrains.messanger.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.prabhash.javabrains.messanger.exception.DataNotFoundException;
import org.prabhash.javabrains.messanger.model.Message;

public class MessageDAO {
	
public List<Message> getAllMessages(){
		System.out.println("Inside GatAllMessageDAO");
		//return new ArrayList<Message>(messages.values());
	Session session= SessionUtil.getSession();
	Query query= session.createQuery("from Message");
	
	return new ArrayList<Message>(query.list());
		
	}

	public Message getMessage(long id){
	
		Session session = SessionUtil.getSession();
		
		Message msg= (Message)session.get(Message.class, id);
	if(msg==null){
		throw new DataNotFoundException("message with Id"+id+" "+"Not Found");
	}
	return msg;
}	
	
public Message addMessage(Message message){
		System.out.println("Inside Add Message DAO");
	Message messages = new Message();
	Session session = SessionUtil.getSession();
	messages.setMessage(message.getMessage());
	messages.setAuthor(message.getAuthor());
	session.save(message);
		return messages;
	}

}
