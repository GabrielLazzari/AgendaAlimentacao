package com.atividade.agendaalimentacao;

import java.util.List;

public class Refeicao {
    int DiaSemana;
    String Refeicao;
    List<Alimento> ListaAlimentos;

    Refeicao(){

    }

    Refeicao(int diaSemana, String refeicao, List<Alimento> listaAlimentos){
        this.DiaSemana = diaSemana;
        this.Refeicao = refeicao;
        this.ListaAlimentos = listaAlimentos;
    }
}
