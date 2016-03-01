package com.mycompany;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author testuser
 * This is DTO class. It will be used for carrying one row of Contact Table
 */
@Root
public class Friend implements Serializable{
	@Element
	int contactId;
	//Using JSR303 bean validation
	@Element
	String firstName;
	@Element
	String lastName;
	@Element
	String email;
	public Friend(){
		
	}
	public Friend(int id, String firstName, String lastName, String email) {
		contactId = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	/**
	 * @return Returns the contactId.
	 */
	public int getContactId() {
		return contactId;
	}
	/**
	 * @param contactId The contactId to set.
	 */
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer contactStr = new StringBuffer();
		contactStr.append(firstName + " " + lastName + ", " + email);
		return contactStr.toString();
	}
}
