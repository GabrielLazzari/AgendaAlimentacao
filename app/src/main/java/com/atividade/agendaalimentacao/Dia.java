package com.atividade.agendaalimentacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Dia {

    int DiaSemana;
    List<Refeicao> ListaRefeicoes;

    Dia(){}

    Dia(int diaSemana, List<Refeicao> listaRefeicoes){
        this.DiaSemana = diaSemana;
        this.ListaRefeicoes = listaRefeicoes;
    }

    public static HashMap<String, List<AlimentoModel>> ConverterDiaParaHasMap(Dia dia){
        HashMap<String, List<AlimentoModel>> expandableListDetail = new HashMap<>();

        if (dia.ListaRefeicoes == null){
            dia.ListaRefeicoes = new ArrayList<Refeicao>();
        }

        for (int c=0; c<dia.ListaRefeicoes.size(); c++){
            expandableListDetail.put(dia.ListaRefeicoes.get(c).Refeicao, dia.ListaRefeicoes.get(c).listaAlimentoModels);
        }

        return expandableListDetail;
    }

    public static Dia RetornarRefeicoesVazias(Dia dia, int diaSemana){
        Refeicao cafe = new Refeicao(1, "Café", new ArrayList<AlimentoModel>());
        Refeicao lancheManha = new Refeicao(1, "Lanche da Manhã", new ArrayList<AlimentoModel>());
        Refeicao almoco = new Refeicao(1, "Almoço", new ArrayList<AlimentoModel>());
        Refeicao lancheTarde = new Refeicao(1, "Lanche da Tarde", new ArrayList<AlimentoModel>());
        Refeicao jantar = new Refeicao(1, "Jantar", new ArrayList<AlimentoModel>());
        Refeicao lancheNoite = new Refeicao(1, "Lanche da Noite", new ArrayList<AlimentoModel>());

        AlimentoModel alimentoModel1 = new AlimentoModel(1, "Teste", "100kcal");
        AlimentoModel alimentoModel2 = new AlimentoModel(2, "Teste2", "200kcal");
        List<AlimentoModel> listaSugestoes = new ArrayList<AlimentoModel>(Arrays.asList(
                alimentoModel1, alimentoModel2
        ));
        cafe.listaAlimentoModels = listaSugestoes;

        if (dia.ListaRefeicoes == null){
            dia.ListaRefeicoes = new ArrayList<Refeicao>();
        }

        for (int c=0; c<dia.ListaRefeicoes.size(); c++){
            if (dia.ListaRefeicoes.get(c).Refeicao == "Café"){
                cafe.listaAlimentoModels = dia.ListaRefeicoes.get(c).listaAlimentoModels;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Lanche da Manhã"){
                lancheManha.listaAlimentoModels = dia.ListaRefeicoes.get(c).listaAlimentoModels;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Almoço"){
                almoco.listaAlimentoModels = dia.ListaRefeicoes.get(c).listaAlimentoModels;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Lanche da Tarde"){
                lancheTarde.listaAlimentoModels = dia.ListaRefeicoes.get(c).listaAlimentoModels;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Jantar"){
                jantar.listaAlimentoModels = dia.ListaRefeicoes.get(c).listaAlimentoModels;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Lanche da Noite"){
                lancheNoite.listaAlimentoModels = dia.ListaRefeicoes.get(c).listaAlimentoModels;
            }
        }

        Dia diaAux = new Dia(diaSemana, new ArrayList<Refeicao>(Arrays.asList(
                cafe, lancheManha, almoco, lancheTarde, jantar, lancheNoite
        )));

        return diaAux;
    }
}
