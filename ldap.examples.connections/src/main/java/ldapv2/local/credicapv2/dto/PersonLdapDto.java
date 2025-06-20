package ldapv2.local.credicapv2.dto;

public class PersonLdapDto {

	private String cn;
	private String account;
	private String description;
	private String memberOf;
	private String pass;
	private String accountControl;
	
	public PersonLdapDto() {
		
	}

	public PersonLdapDto(String cn, String account, String description, String memberOf) {
		this.cn = cn;
		this.account = account;
		this.description = description;
		this.memberOf = memberOf;
	}
	
	public PersonLdapDto(String cn, String pass) {
		this.cn = cn;
		this.pass = pass;
	}
	
	public PersonLdapDto(String cn, String account, String description, String memberOf, String accountControl) {
		this.cn = cn;
		this.account = account;
		this.description = description;
		this.memberOf = memberOf;
		this.accountControl = accountControl;
	}
	
	public PersonLdapDto(String cn, String account, String description, String memberOf,String pass ,String accountControl) {
		this.cn = cn;
		this.account = account;
		this.description = description;
		this.memberOf = memberOf;
		this.pass = pass;
		this.accountControl = accountControl;
	}
	
	public String getCn() {
		return cn;
	}

	public String getAccount() {
		return account;
	}

	public String getDescription() {
		return description;
	}
	
	public String getPass() {
		return pass;
	}

	public String getMemberOf() {
		return memberOf;
	}

	public String getAccountControl() {
		return accountControl;
	}

	public void setAccountControl(String accountControl) {
		this.accountControl = accountControl;
	}
	
}
