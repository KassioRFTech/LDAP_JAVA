package ldapv2.local.credicapv2.context.dto;

public class UserContextDto {
	
	private String username;
	private String password;
	
	public UserContextDto() {
		
	}
	
	public UserContextDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
