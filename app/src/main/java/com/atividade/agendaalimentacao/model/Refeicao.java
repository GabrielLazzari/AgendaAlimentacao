package com.atividade.agendaalimentacao.model;

import java.util.List;

public class Refeicao {
    public int DiaSemana;
    public String Refeicao;
    public List<Alimento> listaAlimentoModels;

    public String Descricao;

    public Refeicao(){

    }

    public Refeicao(int diaSemana, String refeicao, List<Alimento> listaAlimentoModels){
        this.DiaSemana = diaSemana;
        this.Refeicao = refeicao;
        this.listaAlimentoModels = listaAlimentoModels;
    }
}
