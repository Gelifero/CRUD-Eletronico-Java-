package com.template.model;

public class EletronicoDTO {
    private int id;
    private String nomeEletronico;
    private String modelo;
    private String cor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEletronico() {
        return nomeEletronico;
    }

    public void setNomeEletronico(String nomeEletronico) {
        this.nomeEletronico = nomeEletronico;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
