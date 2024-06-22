package com.atividade.agendaalimentacao.model;

import java.util.List;

public class Alimento {
    public int Id;
    public String Nome;
    public String Calorias;
    public String Tipo;
    public List<Alimento> ListaSugestoes;

    public Alimento(String nome, String calorias, String tipo){
        this.Nome = nome;
        this.Calorias = calorias;
        this.Tipo = tipo;
    }

    public Alimento(int id, String nome, String calorias, String tipo){
        this.Id = id;
        this.Nome = nome;
        this.Calorias = calorias;
        this.Tipo = tipo;
    }

    public int getId() {
        return Id;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getNome() {
        return Nome;
    }

    public void setCalorias(String calorias) {
        Calorias = calorias;
    }

    public String getCalorias() {
        return Calorias;
    }

    public void setListaSugestoes(List<Alimento> listaSugestoes) {
        ListaSugestoes = listaSugestoes;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public List<Alimento> getListaSugestoes() {
        return ListaSugestoes;
    }
}
