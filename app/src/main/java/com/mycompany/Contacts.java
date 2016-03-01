package com.mycompany;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * returned from server
 * {"contactList":[{"contactId":"1","email":"contact1@hotmail.com","firstName":"contact1","lastName":"contact1last"},{"contactId":"2","email":"contact2@hotmail.com","firstName":"contact2","lastName":"contact2last"},{"contactId":"3","email":"contact3@hotmail.com","firstName":"contact3","lastName":"contact3last"}]}
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
