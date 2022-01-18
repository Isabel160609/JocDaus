package com.init.JocDausS.security.controller;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.Set;

import com.init.JocDausS.dto.Mensaje;
import com.init.JocDausS.security.dto.JwtDto;
import com.init.JocDausS.security.dto.LoginUsuario;
import com.init.JocDausS.security.dto.NuevoUsuario;
import com.init.JocDausS.security.entity.Rol;
import com.init.JocDausS.security.entity.Usuario;
import com.init.JocDausS.security.enums.RolNombre;
import com.init.JocDausS.security.jwt.JwtProvider;
import com.init.JocDausS.security.service.RolService;
import com.init.JocDausS.security.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	 	@Autowired(required=true)
	    PasswordEncoder passwordEncoder;

	    @Autowired(required=true)
	    AuthenticationManager authenticationManager;

	    @Autowired(required=true)
	    UsuarioService usuarioService;

	    @Autowired(required=true)
	    RolService rolService;

	    @Autowired(required=true)
	    JwtProvider jwtProvider;

	    @PostMapping("/nuevo")
	    public ResponseEntity<?> nuevo( @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
	        if(bindingResult.hasErrors())
	            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
	        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
	            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
	        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
	            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
	        Usuario usuario =
	                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
	                        passwordEncoder.encode(nuevoUsuario.getPassword()));
	        Set<Rol> roles = new HashSet<>();
	        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
	        if(nuevoUsuario.getRoles().contains("admin"))
	            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
	        usuario.setRoles(roles);
	        usuarioService.save(usuario);
	        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
	    }

	    @PostMapping("/login")
	    public ResponseEntity<JwtDto> login( @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
	        if(bindingResult.hasErrors())
	            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
	        Authentication authentication =
	                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
	        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
	        return new ResponseEntity(jwtDto, HttpStatus.OK);
	    }
	}
