package ldapv2.local.credicapv2.context.service;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class ContextService {

	public DirContext conn(HttpSession session) {
		
		String user = (String)session.getAttribute("ldapUser");
		String pass = (String)session.getAttribute("ldapPass");
		
		try {
		Hashtable<String, String> prepared = new Hashtable<>();
		
		System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk-21\\lib\\security\\cacerts");
		
		prepared.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		prepared.put(Context.PROVIDER_URL, "ldaps://credicap.local:636");
		prepared.put(Context.SECURITY_PRINCIPAL, user +"@credicap.local");
		prepared.put(Context.SECURITY_CREDENTIALS, pass);
		prepared.put(Context.SECURITY_AUTHENTICATION, "simple");
		prepared.put(Context.SECURITY_PROTOCOL, "ssl");
		
		DirContext conn = new InitialDirContext(prepared);
		
		return conn;
		
		}catch(NamingException e) {
			e.getCause();
		}
		
		return null;
		
	}
}
