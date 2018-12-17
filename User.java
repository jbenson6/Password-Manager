package application;

import java.util.ArrayList;
import java.lang.String;

public class User {
	
    private String username;
    private String firstname;
    private String lastname;
    private byte[] salt;
    private byte[] encyrptedPassword;
    
    private ArrayList<Site> siteList = new ArrayList<Site>();
    
    User(String username) {
        this.username = username;
    }

    public void addSite(Site site) {
    	siteList.add(site);
    }
    public String getUsername() {
        return this.username;
    }

	public void setUsername(String text) {
		// TODO Auto-generated method stub
		this.username = text;
		
	}
	public ArrayList<Site> getSites() {
		// TODO Auto-generated method stub
		return this.siteList;
		
	}
	public void setSalt(byte[] salt) {
		// TODO Auto-generated method stub
		this.salt = salt;
		
	}
	public void setEncryptedPassword(byte[] pass) {
		// TODO Auto-generated method stub
		this.encyrptedPassword = pass;
		
	}
	public byte[] getSalt() {
		// TODO Auto-generated method stub
		return this.salt;
		
	}
	public byte[] getEncryptedPassword() {
		// TODO Auto-generated method stub
		return this.encyrptedPassword;
		
	}
	public void setFirstName(String text) {
		// TODO Auto-generated method stub
		this.firstname = text;
		
	}
	public void setLastName(String text) {
		// TODO Auto-generated method stub
		this.lastname = text;
		
	}
	public String getFirstName() {
		// TODO Auto-generated method stub
		return this.firstname;
		
	}
	public String getLastName() {
		// TODO Auto-generated method stub
		return this.lastname;
		
	}

	public String getSize() {
		// TODO Auto-generated method stub
		int size = this.siteList.size();
		return Integer.toString(size);
	}

//	public String getUnsafe() {
//		// TODO Auto-generated method stub
//		this.unsafe = 0;
//		ArrayList<String> passwords = new ArrayList<String>();
//		for(int i = 0; i < this.siteList.size(); i++) {
//			if(!passwords.contains(this.siteList.get(i).getPassword())) {
//				passwords.add(this.siteList.get(i).getPassword());
//				
//				for(int j = i+1; j < this.siteList.size(); j++) {
//					if(this.siteList.get(i).getPassword().equals(this.siteList.get(j).getPassword())) {
//						unsafe+=1;
//					}
//				}
//			}
//			
//		}
//		return Integer.toString(unsafe);
//	}

	public void removeSite(Site site) {
		// TODO Auto-generated method stub
		this.siteList.remove(site);
		
	}
	
}