package com.init.JocDausS.security;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.init.JocDausS.dto.Mensaje;
import com.init.JocDausS.security.controller.AuthController;
import com.init.JocDausS.security.dto.JwtDto;
import com.init.JocDausS.security.dto.NuevoUsuario;
import com.init.JocDausS.security.entity.Rol;
import com.init.JocDausS.security.entity.Usuario;
import com.init.JocDausS.security.enums.RolNombre;
import com.init.JocDausS.security.jwt.JwtProvider;
import com.init.JocDausS.security.service.RolService;
import com.init.JocDausS.security.service.UsuarioService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = { AuthControlllerTest.class })
public class AuthControlllerTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	RolService rolService;
	@Mock
	UsuarioService usuarioService;
	@Mock
	JwtProvider jwtProvider;

	@InjectMocks
	AuthController authControlller;

	Usuario usuario1;
	Usuario usuario2;
	Rol rol1;
	Rol rol2;

//	@Test
//	@Order(1)
//	public void test_nuevo() throws Exception {
//		rol1 = new Rol(1, RolNombre.ROLE_ADMIN);
//		rol2 = new Rol(2, RolNombre.ROLE_USER);
//
//		Set<Rol> roles1 = new HashSet<Rol>();
//		roles1.add(rol1);
//
//		Set<Rol> roles2 = new HashSet<Rol>();
//		roles2.add(rol1);
//		roles2.add(rol2);
//		usuario1 = new Usuario(1, "user", "user", "user@user.com", "user", roles1);
//		usuario2 = new Usuario(1, "admin", "admin", "admin@admin.com", "admin", roles2);
//
//		Mensaje mensaje = new Mensaje("usuario guardado");
//		NuevoUsuario nuevoUsuario = null;
//		BindingResult bindingResult = null;
//		ResponseEntity<Mensaje> resposta = new ResponseEntity(mensaje, HttpStatus.CREATED);
//
//		when(usuarioService.save(usuario1)).thenReturn(usuario1);
//		this.mockMvc.perform(post("/nuevo"))
//		.andExpect(status().isCreated())
//		.andDo(print());
//
//	}
//	@Test
//	@Order(1)
//	public void test_login() {
//		JwtDto JwtDto=null;
//		when(authenticationManager.authenticate(null)).thenReturn(null, null)
//	}
	
}
