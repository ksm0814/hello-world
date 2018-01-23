package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
	@Id
	@GeneratedValue
	Long questionId;
	
	private String writer;

	String title;
	String contents;
	public Question() {
		
	}
	public Question(String writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}
	public String getContents() {
		return contents;
	}
	
	

}
