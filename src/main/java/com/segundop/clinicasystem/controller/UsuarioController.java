package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.UsuarioDTO;
import com.segundop.clinicasystem.entity.Usuario;
import com.segundop.clinicasystem.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Metodo para convertir de Usuario a dto
    private UsuarioDTO convertToDto(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setContra(usuario.getContra());
        return usuarioDTO;
    }

    //Metodo para convertir de UsuarioDTO a Usario
    private Usuario convertToEntity(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setContra(usuarioDTO.getContra());
        usuario.setEmail(usuarioDTO.getEmail());
        return usuario;
    }

    //Obtener todos los usuarios}
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios(){
        List<Usuario> usuarios =usuarioService.findAll();
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
        for(Usuario usuario: usuarios){
            usuarioDTOS.add(convertToDto(usuario));
        }
        return ResponseEntity.ok(usuarioDTOS);
    }

    //Obtener un usuario por Id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        if(usuario.isPresent()){
            UsuarioDTO usuarioDTO = convertToDto(usuario.get());
            return ResponseEntity.ok(usuarioDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    //Crear un nuevo Usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = convertToEntity(usuarioDTO);
        Usuario nuevoUsuario = usuarioService.save(usuario);
        UsuarioDTO usuarioDTO1 = convertToDto(nuevoUsuario);
        return new ResponseEntity<>(usuarioDTO1, HttpStatus.CREATED);
    }

    //actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO){
        Optional<Usuario> usuarioExistente = usuarioService.findById(id);
        if(usuarioExistente.isPresent()){
            Usuario usuario = convertToEntity(usuarioDTO);
            usuario.setId(id); //establecemos el id para actualizar
            Usuario usuarioActualizado = usuarioService.save(usuario);
            UsuarioDTO usuarioDTO1 = convertToDto(usuarioActualizado);
            return ResponseEntity.ok(usuarioDTO1);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        if(usuario.isPresent()){
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOpt = usuarioService.findByNombreUsuario(usuarioDTO.getNombreUsuario());
    
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
        
        // Compara directamente las contraseñas (no recomendado si no están hasheadas)
        if (usuario.getContra().equals(usuarioDTO.getContra())) {
            // Si es correcto, devuelve los datos del usuario
            UsuarioDTO usuarioResponse = convertToDto(usuario);
            return ResponseEntity.ok(usuarioResponse);
        } else {
            // Contraseña incorrecta
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Contraseña incorrecta");
        }
        } else {
            // Usuario no encontrado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Usuario no encontrado");
        }
    }
}
