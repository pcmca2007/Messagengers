package org.prabhash.javabrains.messanger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.prabhash.javabrains.messanger.database.DatabaseClass;
import org.prabhash.javabrains.messanger.exception.DataNotFoundException;
import org.prabhash.javabrains.messanger.model.Message;

public class MessageService {
	
	private Map<Long,Message> messages= DatabaseClass.getMessages();
	
	
	public MessageService(){
		
		new Message("Prabhash Loves Jaya","Jaya Singh");
		new Message("Jaya Loves Prabhash","Prabhash Mishra");
		
		///messages.put(1L, new Message(1,"Prabhash Loves Jaya","Jaya Singh"));
		//messages.put(2L, new Message(2,"Jaya Loves Prabhash","Prabhash Mishra"));
	}
	
	/*public List<Message> getAllMessages(){
		
		Message m1= new Message(1L,"Prabhash Loves Jaya","Jaya Singh");
		Message m2= new Message(1L,"Jaya Loves Prabhash","Prabhash Mishra");
		List<Message> list= new ArrayList<>();
		list.add(m1);
		list.add(m2);
		
		return list;
	}*/
	
	public List<Message> getAllMessagesforYear(int year){
		
		List<Message> messageForYear= new ArrayList<>();
		Calendar cal=Calendar.getInstance();
		for(Message message: messages.values()){
			
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year){
				
				messageForYear.add(message);
			}
		}
		
		return messageForYear;
		
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		
		List<Message> list=new ArrayList<Message>(messages.values());
		
		return list.subList(start, start+size);
	}
	
	
	public List<Message> getAllMessages(){
		
		return new ArrayList<Message>(messages.values());
		
	}
	
	public Message getMessage(long id){
		
		Message msg= messages.get(id);
		if(msg==null){
			throw new DataNotFoundException("message with Id"+id+" "+"Not Found");
		}
		return msg;
	}
	
	public Message addMessage(Message message){
		
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message){
		
		if(message.getId()<= 0){
			return null;
		}
		
		messages.put(message.getId(), message);
		
		return message;
	}
	
	public Message removeMessage(long id){
		
		return messages.remove(id);
	}

}
