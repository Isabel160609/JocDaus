package com.init.JocDausS.security;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.init.JocDausS.serviceTest;
import com.init.JocDausS.security.entity.Rol;
import com.init.JocDausS.security.enums.RolNombre;
import com.init.JocDausS.security.repository.RolRepository;
import com.init.JocDausS.security.service.RolService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes=RolServiceTest.class)
public class RolServiceTest {

	@Mock
	private RolRepository rolRepository;
	
	@InjectMocks
	RolService rolService;
	
	Rol rol1;
	Rol rol2;
	
	
	@Test @Order(1)
	public void test_getByRolNombre(){
		
		rol1=new Rol(1,RolNombre.ROLE_ADMIN);
		rol2=new Rol(2,RolNombre.ROLE_USER);
		
		List<Rol> roles=new ArrayList<Rol>();
		
		roles.add(rol1);
		roles.add(rol2);
		
		RolNombre rolNombre=RolNombre.ROLE_ADMIN;
		when(rolRepository.findByRolNombre(rolNombre)).thenReturn(Optional.of(rol1));
		
		assertEquals(rolNombre,rolService.getByRolNombre(rolNombre).get().getRolNombre());
	}
	
	@Test @Order(2)
	public void test_save() {
		 rol1=new Rol(1,RolNombre.ROLE_ADMIN);
		
		when(rolRepository.save(rol1)).thenReturn(rol1);
	
		assertEquals(rol1,rolService.save(rol1));
	}
}
