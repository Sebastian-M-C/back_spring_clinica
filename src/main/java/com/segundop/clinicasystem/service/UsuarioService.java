package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Usuario;
import com.segundop.clinicasystem.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Metodo para encontrar un usuario por su nombre de usuario
    public Optional<Usuario> findByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    //Metodo para guardar un nuevo usuario
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    //metodo para obtener todos los usuarios
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    //metodo para encontrar un usuario por ID
    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    //Metodo para eliminar un usuario por ID
    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

}
