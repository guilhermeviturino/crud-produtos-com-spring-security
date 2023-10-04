package com.produtosapi.model;

import com.produtosapi.constants.Perfil;

public record RegistrarUsuario (String login, String senha, Perfil perfil){

}
