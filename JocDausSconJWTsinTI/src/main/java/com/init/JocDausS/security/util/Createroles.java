package com.init.JocDausS.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.init.JocDausS.security.entity.Rol;
import com.init.JocDausS.security.enums.RolNombre;
import com.init.JocDausS.security.service.RolService;

@Component
public class Createroles implements CommandLineRunner{

	@Autowired
	RolService rolService;
	
	@Override
	public void run(String... args) throws Exception {
		Rol rolAdmin=new Rol(RolNombre.ROLE_ADMIN);
		Rol rolusuario=new Rol(RolNombre.ROLE_USER);
		rolService.save(rolusuario);
		rolService.save(rolAdmin);
	}

}
