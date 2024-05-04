package com.atividade.agendaalimentacao;

import java.util.List;

public class Refeicao {
    int DiaSemana;
    String Refeicao;
    List<AlimentoModel> listaAlimentoModels;

    String Descricao;

    Refeicao(){

    }

    Refeicao(int diaSemana, String refeicao, List<AlimentoModel> listaAlimentoModels){
        this.DiaSemana = diaSemana;
        this.Refeicao = refeicao;
        this.listaAlimentoModels = listaAlimentoModels;
    }
}
