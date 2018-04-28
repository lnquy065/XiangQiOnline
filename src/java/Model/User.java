/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author nguye
 */
public class User {
        private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
	private String username;
	private String password;
 
	public User() {
		super();
	}
 
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
 
	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
}
