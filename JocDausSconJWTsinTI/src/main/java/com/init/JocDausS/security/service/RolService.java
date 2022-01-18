package com.init.JocDausS.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.init.JocDausS.security.entity.Rol;
import com.init.JocDausS.security.enums.RolNombre;
import com.init.JocDausS.security.repository.RolRepository;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public Rol save(Rol rol){
        rolRepository.save(rol);
        return rol;
    }
}
