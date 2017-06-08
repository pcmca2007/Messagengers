package org.prabhash.javabrains.messanger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GeneratorType;

@XmlRootElement
@Entity(name="message")
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String message;
	@Column
	private Date created;
	@Column
	private String author;
	
	private Map<Long,Comment> comments=new HashMap<>();
	private List<Link> link=new ArrayList<>();
	
	public Message(){}
	
	/*public Message(long id,String message,String author){
		
		this.id=id;
		this.message=message;
		this.created= new Date();
		this.author=author;
	}*/
	
public Message(String message,String author){
		
		//this.id=id;
		this.message=message;
		this.created= new Date();
		this.author=author;
	}
		
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@XmlTransient
	public Map<Long, Comment> getComments() {
		return comments;
	}

	public void setComments(Map<Long, Comment> comments) {
		this.comments = comments;
	}

	public List<Link> getLink() {
		return link;
	}

	public void setLink(List<Link> link) {
		this.link = link;
	}
	
	public void addLinks(String url,String rel){
		Link lin=new Link();
		lin.setLink(url);
		lin.setRel(rel);
		link.add(lin);
		
		
	}


}
