package com.init.JocDausS.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import com.init.JocDausS.security.entity.Rol;
import com.init.JocDausS.security.entity.Usuario;
import com.init.JocDausS.security.enums.RolNombre;
import com.init.JocDausS.security.repository.UsuarioRepository;
import com.init.JocDausS.security.service.UserDetailsServiceImpl;
import com.init.JocDausS.security.service.UsuarioService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes=UserDetailsServiceImpl.class)
public class UserDetailsServiceImplTest {
	
	@Mock
	UsuarioService usuarioService;
	
	@InjectMocks
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Mock
	private UsuarioRepository  usuarioRepository ;
	
	
	
	Usuario usuario1;
	Usuario usuario2;
	Rol rol1;
	Rol rol2;
	
//	@Test 
//	@Order(1)
//	public void test_loadUserByUsername() {
//		
//		rol1=new Rol(1,RolNombre.ROLE_ADMIN);
//		rol2=new Rol(2,RolNombre.ROLE_USER);
//		
//		Set<Rol> roles1=new  HashSet<Rol>();
//		roles1.add(rol1);
//		
//		Set<Rol> roles2=new  HashSet<Rol>();
//		roles2.add(rol1);
//		roles2.add(rol2);
//		usuario1=new Usuario(1,"user","user", "user@user.com", "user",roles1);
//		usuario2=new Usuario(1,"admin","admin", "admin@admin.com", "admin",roles2);
//		
//		String nombreUsuario="user";
//		
//		when(usuarioService.getByNombreUsuario(nombreUsuario)).thenReturn(Optional.of(usuario1));
//		
//	//	assertEquals("user",userDetailsServiceImpl.loadUserByUsername(nombreUsuario).getUsername());
//	}

}
