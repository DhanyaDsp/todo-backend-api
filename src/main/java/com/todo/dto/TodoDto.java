package com.todo.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@Data
public class TodoDto extends RepresentationModel<TodoDto>{

    @Id
    private int id;
 
    @NotBlank(message = "user is required")
    private String user;
 
    @Size(min = 9, max = 20, message = "length should be more than 9 character and less than 20")
    private String description;
 
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date targetDate;

    private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    

	
}
