package objectclasses;


/*
 * The password methods need to built out so that the password is NOT stored in plain text. 
 */


public class User {
	
	private String givenName;
	private String userName;
	private String password;
	private String emailAddress;
	
	
	public User(String givenName, String password) {

		this.givenName = givenName;
		this.userName = generateUserName(givenName);
		this.password = password;
		this.emailAddress = generateEmailAddress(userName);
		
		
	}
	
	
	public String getGivenName() {
		return this.givenName;
	}
	
	private void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	private void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	private void setPassword(String password) {
		this.password = password;
	}
	
	
	/* Requirement 1.0.4 */
	private String generateUserName(String givenName) {
		String[] names = givenName.split(" ");  // separate out the names, last name defined as last word in string 
		String user = "";
		
		/*  to implement unique ids a query to the db will have to be run  */
		
		// acount for names that are too short
		
		if (((names[0].length()) + (names[(names.length-1)].length()) < 8)) {
				user = givenName.substring(0, 8);
				
				return user;
			
		}
		
		// if the given name is not 8 letters in length, add "a" to the end
		if (givenName.length() < 8) {
			user = givenName;
			while (user.length() < 8) {
				user += "a";
			}
			
			
			
			return user;
		}
		
		// if first name is less than 2 add more last name
		if (names[0].length() < 2) {
			
			int len = names[0].length();  // get length of last name
			
			user = names[0] + names[(names.length-1)].substring(0, (8-len));
			
			return user;
		}
		
		// if last name is less than 6 add more first name
		if (names[names.length-1].length() < 7) {
			int len = names[(names.length-1)].length();  // get length of last name
			user = names[0].substring(0, (8-len)) + names[names.length-1];
			
			return user;
		}
		
		user += names[0].substring(0, 2) + names[(names.length-1)].substring(0, 6);

		return user.toLowerCase();
	}
	
	
	private String generateEmailAddress(String userName) {
		String user = userName + "@companyA.com";

		return user.toLowerCase();
	}
	
	
	@Override
	public String toString() {
		return String.format("givenName: %s  userName: %s", givenName, userName);
	}

}
