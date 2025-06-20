package ldapv2.local.credicapv2.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import ldapv2.local.credicapv2.context.service.ContextService;
import ldapv2.local.credicapv2.dto.OuLdapDto;

@Service
public class OuLdapService {
	
	@Autowired
	private ContextService service;
	
	
	@Transactional(readOnly = true)
	public List<OuLdapDto> getAllOus(HttpSession session) throws NamingException{
		
		List<OuLdapDto> OuDto = new ArrayList<>();
		
		OuDto.clear();
		
		DirContext context = service.conn(session);
		
		SearchControls sc = new SearchControls();
		
		sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		
		try {
		NamingEnumeration<SearchResult> result = context.search("DC=credicap,DC=local", "objectClass=organizationalUnit", sc);
		
		while(result.hasMore()) {
			SearchResult results = result.next();
			
			String clearName = results.getName().replaceAll("(?i)OU=", "");
			OuDto.add(new OuLdapDto(clearName));
			
			OuDto.removeIf(o -> o.getServicePoint().equals("Domain Controllers"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Credicap"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Regionais"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Servidores"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Conselho Fiscal"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Conselho Administrativo"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Internet"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Teste LGTi"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Totem - Atendimento"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Home Office"));
			OuDto.removeIf(o -> o.getServicePoint().equals("WSUS"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Terceiros"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Unidade de Negocios - Canacap"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Diretoria"));
			OuDto.removeIf(o -> o.getServicePoint().equals("Unidade Digital"));
		}
		
		result.close();
		context.close();
		
		return OuDto;
		
		}catch(PartialResultException e) {
			
		}catch(NamingException i) {
			
		}
		
		return OuDto;
	}
}
