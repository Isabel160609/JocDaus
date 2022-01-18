package com.init.JocDausS.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.init.JocDausS.security.entity.Rol;
import com.init.JocDausS.security.entity.Usuario;
import com.init.JocDausS.security.enums.RolNombre;
import com.init.JocDausS.security.repository.UsuarioRepository;
import com.init.JocDausS.security.service.UsuarioService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes=UsuarioServicelTest.class)
public class UsuarioServicelTest {

	@Mock
	private UsuarioRepository  usuarioRepository ;
	
	@InjectMocks
	UsuarioService usuarioService;
	
	Usuario usuario1;
	Usuario usuario2;
	Rol rol1;
	Rol rol2;
	
	@Test 
	@Order(1)
	public void test_getByNombreUsuario() {
		
		rol1=new Rol(1,RolNombre.ROLE_ADMIN);
		rol2=new Rol(2,RolNombre.ROLE_USER);
		
		Set<Rol> roles1=new  HashSet<Rol>();
		roles1.add(rol1);
		
		Set<Rol> roles2=new  HashSet<Rol>();
		roles2.add(rol1);
		roles2.add(rol2);
		usuario1=new Usuario(1,"user","user", "user@user.com", "user",roles1);
		usuario2=new Usuario(1,"admin","admin", "admin@admin.com", "admin",roles2);
		
		String nombreUsuario="user";
		when(usuarioRepository.findByNombreUsuario(nombreUsuario)).thenReturn(Optional.of(usuario1));
		
		assertEquals(usuario1,usuarioService.getByNombreUsuario(nombreUsuario).get());
	}
	
	@Test 
	@Order(2)
	public void test_existsByNombreUsuario() {
		
		rol1=new Rol(1,RolNombre.ROLE_ADMIN);
		rol2=new Rol(2,RolNombre.ROLE_USER);
		
		Set<Rol> roles1=new  HashSet<Rol>();
		roles1.add(rol1);
	
		usuario1=new Usuario(1,"user","user", "user@user.com", "user",roles1);
		
		
		String nombreUsuario="user";
		when(usuarioRepository.existsByNombreUsuario(nombreUsuario)).thenReturn(true);
		
		assertEquals(true,usuarioService.existsByNombreUsuario(nombreUsuario));
	}
	
	@Test 
	@Order(3)
	public void test_existsByEmail() {
		
		rol1=new Rol(1,RolNombre.ROLE_ADMIN);
		rol2=new Rol(2,RolNombre.ROLE_USER);
		
		Set<Rol> roles1=new  HashSet<Rol>();
		roles1.add(rol1);
	
		usuario1=new Usuario(1,"user","user", "user@user.com", "user",roles1);
		
		
		String email="user@user.com";
		when(usuarioRepository.existsByEmail(email)).thenReturn(true);
		
		assertEquals(true,usuarioService.existsByEmail(email));
	}
	
	@Test 
	@Order(3)
	public void test_save() {
		rol1=new Rol(1,RolNombre.ROLE_ADMIN);
		rol2=new Rol(2,RolNombre.ROLE_USER);
		
		Set<Rol> roles1=new  HashSet<Rol>();
		roles1.add(rol1);
	
		usuario1=new Usuario(1,"user","user", "user@user.com", "user",roles1);
		
		when(usuarioRepository.save(usuario1)).thenReturn(usuario1);
		
		assertEquals(usuario1,usuarioService.save(usuario1));
	}
}
