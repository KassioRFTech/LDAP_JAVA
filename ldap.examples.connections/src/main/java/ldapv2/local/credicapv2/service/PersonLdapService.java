package ldapv2.local.credicapv2.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import ldapv2.local.credicapv2.context.service.ContextService;
import ldapv2.local.credicapv2.dto.PersonLdapDto;

@Service
public class PersonLdapService {
	
	@Autowired
	private ContextService service;
	
	String userDn;
	

	@Transactional(readOnly = true)
	public List<PersonLdapDto> getUsers(String ouname, HttpSession session){
		
		String cn;
		String account;
		String description;
		String memberOf;
		
		List<PersonLdapDto> p = new ArrayList<>();
		p.clear();
		
		try {
			
			DirContext context = service.conn(session);
			
			SearchControls sc = new SearchControls();
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			sc.setReturningAttributes(new String[] {"CN", "sAMAccountName", "Description", "MemberOf"});
					
			NamingEnumeration<SearchResult> result = context.search("OU=Usuarios,OU=" + ouname + ",DC=credicap,DC=local",
					"(objectCategory=person)", sc);
			
			while(result.hasMore()) {
				
				SearchResult results = result.next();
				
				cn = "" + results.getAttributes().get("CN");
				account = "" + results.getAttributes().get("sAMAccountName");
				description = "" + results.getAttributes().get("Description");
				memberOf = "" + results.getAttributes().get("MemberOf");
				
				List<String> cnList = new ArrayList<>();
				Pattern pattern = Pattern.compile("CN=([^,]+)");
				Matcher matcher = pattern.matcher(memberOf);

				while (matcher.find()) {
				    cnList.add(matcher.group(1));
				}

				String memberOfCleaned = String.join(", ", cnList);
				
				
				p.add(new PersonLdapDto(
						cn.replace("cn: ", ""), 
						account.replace("sAMAccountName: ", ""), 
						description.replace("description: ", ""),
						memberOfCleaned.replace("memberOf: ", "")));
			}
			
			result.close();
			context.close();
			
			return p;
			
		}catch(NamingException e) {
			System.out.println(e.getMessage());
		}
		
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<PersonLdapDto> getUsersSede(String ouname, HttpSession session){
		String cn;
		String account;
		String description;
		String memberOf;
		
		List<PersonLdapDto> p = new ArrayList<>();
		p.clear();
		
		try {
			
			DirContext context = service.conn(session);
			
			SearchControls sc = new SearchControls();
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			sc.setReturningAttributes(new String[] {"CN", "sAMAccountName", "Description", "MemberOf"});
					
			NamingEnumeration<SearchResult> result = context.search("OU=Usuarios, OU=" + ouname + ",DC=credicap,DC=local",
					"(objectCategory=person)", sc);
			
			while(result.hasMore()) {
				
				SearchResult results = result.next();
				
				cn = "" + results.getAttributes().get("CN");
				account = "" + results.getAttributes().get("sAMAccountName");
				description = "" + results.getAttributes().get("Description");
				memberOf = "" + results.getAttributes().get("MemberOf");
				
				List<String> cnList = new ArrayList<>();
				Pattern pattern = Pattern.compile("CN=([^,]+)");
				Matcher matcher = pattern.matcher(memberOf);

				while (matcher.find()) {
				    cnList.add(matcher.group(1));
				}

				String memberOfCleaned = String.join(", ", cnList);
				
				
				p.add(new PersonLdapDto(
						cn.replace("cn: ", ""), 
						account.replace("sAMAccountName: ", ""), 
						description.replace("description: ", ""),
						memberOfCleaned.replace("memberOf: ", "")));
			}
			
			result.close();
			context.close();
			
			return p;
			
		}catch(NamingException e) {
			System.out.println(e.getMessage());
		}
		
		return p;
	}
	
	
	public PersonLdapDto updateActOrDes(String login, PersonLdapDto dto, HttpSession session) throws NamingException {
		String cn;
		String account;
		String description;
		String memberOf;
		String accountControl;
		
		DirContext context = service.conn(session);
		
		SearchControls sc = new SearchControls();
		
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		sc.setReturningAttributes(new String[] {"CN", "MemberOf", "sAMAccountName","Description", "userAccountControl"});
		
		NamingEnumeration<SearchResult> rs = context.search("dc=credicap,dc=local", "(&(objectCategory=person)(objectClass=user)(sAMAccountName=" + login + "))", sc);
		
		try {
			
		while(rs.hasMore()) {
			
			SearchResult result = rs.next();
			userDn = result.getNameInNamespace();
		}
		
		}catch(PartialResultException e) {
			
		}finally {
			rs.close();
		}
		
		ModificationItem[] mod = new ModificationItem[2];
		
		mod[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl", dto.getAccountControl()));
		mod[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("Description", dto.getDescription()));
		
		context.modifyAttributes(userDn, mod);
		
		rs = context.search("dc=credicap,dc=local", "(&(objectCategory=person)(objectClass=user)(sAMAccountName=" + login + "))", sc);
		
		try {
		while(rs.hasMore()) {
			
			SearchResult result = rs.next();
			cn = "" + result.getAttributes().get("cn");
			description = "" + result.getAttributes().get("Description");
			account = "" + result.getAttributes().get("sAMAccountName");
			memberOf = "" + result.getAttributes().get("memberOf");
			accountControl = "" + result.getAttributes().get("userAccountControl");
			
			dto = new PersonLdapDto(cn.replace("cn: ", ""), 
					description.replace("description: ", ""), 
					account.replace("sAMAccountName: ", ""), 
					memberOf.replace("memberOf: ",""),
					accountControl.replace("userAccountControl: ", "")
					.replace("514", "Usuario desativado")
					.replace("512","Usuario ativado"));
		}
		
		}catch(PartialResultException e) {
			
		}finally {
			rs.close();
		}
		
		context.close();
		
		return dto;
	}
	
	public PersonLdapDto updatePass(String account, PersonLdapDto dto, HttpSession session) throws NamingException, UnsupportedEncodingException {

		PersonLdapDto person = new PersonLdapDto();
		String cn;
		String login;
		String description;
		String memberOf;
		
		DirContext context = service.conn(session);
		
		
		SearchControls sc = new SearchControls();
		
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		sc.setReturningAttributes(new String[] {"CN","sAMAccountName"});
		
		String pass = "\"" + dto.getPass()+ "\"";
		
		byte[] formatedPass = pass.getBytes("UTF-16LE");
		
		NamingEnumeration<SearchResult> result = context.search("dc=credicap, dc=local", "(&(objectCategory=person)(objectClass=user)(sAMAccountName="+account+"))", sc);
		
		try {
			
			while(result.hasMore()) {
				
				SearchResult rs = result.next();
				
				cn = "" + rs.getAttributes().get("cn");
				description = "" + rs.getAttributes().get("Description");
				login = "" + rs.getAttributes().get("sAMAccountName");
				memberOf = "" + rs.getAttributes().get("memberOf");
				
				person = new PersonLdapDto(cn.replace("cn: ", ""), 
						description.replace("description: ", ""),
						login.replace("sAMAccountName: ", ""), 
						memberOf.replace("memberOf: ",""));
				
				userDn = rs.getNameInNamespace();
				
			}
		}catch(PartialResultException e) {
			
		}finally {
			result.close();
		}
		
		ModificationItem[] mod = new ModificationItem[1];
		
		
		mod[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", formatedPass));
		context.modifyAttributes(userDn, mod);

		return person;
	}
}
