package com.produtosapi.constants;

public enum Perfil {
    
    ADMIN("admin"),
    USER("user");

    private String perfil;

    Perfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil () {
        return perfil;
    }
}
