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
	int id;
	//Using JSR303 bean validation
	@Element
	String name;
	@Element
	String username;
	@Element
	String email;
	public Friend(){
		
	}
	public Friend(int id, String name, String username, String email) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.username = username;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
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
		contactStr.append(name + " " + username + ", " + email);
		return contactStr.toString();
	}
}
