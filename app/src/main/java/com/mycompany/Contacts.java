package com.mycompany;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * returned from server
 * {"contactList":[{"id":"1","email":"contact1@hotmail.com","name":"contact1","username":"contact1last"},{"id":"2","email":"contact2@hotmail.com","name":"contact2","username":"contact2last"},{"id":"3","email":"contact3@hotmail.com","name":"contact3","username":"contact3last"}]}
 * @author ming
 *
 */
public class Contacts {
	@JsonProperty("contactList")
	List<Friend> contactList;

	public List<Friend> getContactList() {
		return contactList;
	}

	public void setContactList(List<Friend> contactList) {
		this.contactList = contactList;
	}
	
}
