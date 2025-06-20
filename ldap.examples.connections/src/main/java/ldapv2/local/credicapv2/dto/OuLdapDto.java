package ldapv2.local.credicapv2.dto;

public class OuLdapDto {

	private String servicePoint;
	
	public OuLdapDto() {
		
	}
	
	public OuLdapDto(String servicePoint) {
		this.servicePoint = servicePoint;
	}
	
	public String getServicePoint() {
		return servicePoint;
	}

	public void setServicePoint(String servicePoint) {
		this.servicePoint = servicePoint;
	}

	@Override
	public String toString() {
		return getServicePoint();
	}

}
