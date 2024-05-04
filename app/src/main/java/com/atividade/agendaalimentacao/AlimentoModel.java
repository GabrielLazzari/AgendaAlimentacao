package com.atividade.agendaalimentacao;

import java.util.List;

public class AlimentoModel {
    public int Id;
    public String Nome;
    public String Calorias;

    public List<AlimentoModel> ListaSugestoes;

    AlimentoModel(int id, String nome, String calorias){
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

    public void setListaSugestoes(List<AlimentoModel> listaSugestoes) {
        ListaSugestoes = listaSugestoes;
    }

    public List<AlimentoModel> getListaSugestoes() {
        return ListaSugestoes;
    }
}
