package de.oglimmer.web.transfer;

import java.io.Serializable;

import de.oglimmer.db.entity.Person;
import de.oglimmer.web.util.DateConverter;

public class Edit1 implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String surname;
	private String firstname;

	private String street;
	private String zip;
	private String city;

	private String birthday;
	private String height;

	public Edit1(Person loadedPerson) {
		if (loadedPerson != null) {
			id = loadedPerson.getId();
			surname = loadedPerson.getSurname();
			firstname = loadedPerson.getFirstname();
			street = loadedPerson.getStreet();
			zip = loadedPerson.getZip();
			city = loadedPerson.getCity();
			if (loadedPerson.getBirthday() != null) {
				birthday = DateConverter.toString(loadedPerson.getBirthday());
			}
			if (loadedPerson.getHeight() != null) {
				height = Integer.toString(loadedPerson.getHeight());
			}
		}
	}

	public Person getAsPerson() {
		Person p = new Person();
		p.setId(id);
		p.setSurname(surname);
		p.setFirstname(firstname);
		if (street != null && !street.isEmpty()) {
			p.setStreet(street);
		}
		if (zip != null && !zip.isEmpty()) {
			p.setZip(zip);
		}
		if (city != null && !city.isEmpty()) {
			p.setCity(city);
		}
		if (birthday != null && !birthday.isEmpty()) {
			p.setBirthday(DateConverter.toSqlDate(birthday));
		}
		if (height != null && !height.isEmpty()) {
			p.setHeight(Integer.parseInt(height));
		}
		return p;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

}
