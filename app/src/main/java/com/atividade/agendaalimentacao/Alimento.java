package com.atividade.agendaalimentacao;

import java.util.List;

public class Alimento {
    public int Id;
    public String Nome;
    public String Calorias;

    public List<Alimento> ListaSugestoes;

    Alimento(int id, String nome, String calorias){
        this.Id = id;
        this.Nome = nome;
        this.Calorias = calorias;
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

    public List<Alimento> getListaSugestoes() {
        return ListaSugestoes;
    }
}
