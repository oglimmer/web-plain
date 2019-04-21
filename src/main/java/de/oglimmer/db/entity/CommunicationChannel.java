package de.oglimmer.db.entity;

import java.io.Serializable;

public class CommunicationChannel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer personId;

	private String type;
	private String data;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
