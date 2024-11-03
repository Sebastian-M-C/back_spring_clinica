package com.segundop.clinicasystem.dto;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;

    private String nombreUsuario;

    private String email;

    private String contra;
}
