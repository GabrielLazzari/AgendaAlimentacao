package com.atividade.agendaalimentacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Dia {

    int DiaSemana;
    List<Refeicao> ListaRefeicoes;

    Dia(){

    }

    Dia(int diaSemana, List<Refeicao> listaRefeicoes){
        this.DiaSemana = diaSemana;
        this.ListaRefeicoes = listaRefeicoes;
    }

    public static HashMap<String, List<Alimento>> ConverterDiaParaHasMap(Dia dia){
        HashMap<String, List<Alimento>> expandableListDetail = new HashMap<>();

        if (dia.ListaRefeicoes == null){
            dia.ListaRefeicoes = new ArrayList<Refeicao>();
        }

        for (int c=0; c<dia.ListaRefeicoes.size(); c++){
            expandableListDetail.put(dia.ListaRefeicoes.get(c).Refeicao, dia.ListaRefeicoes.get(c).ListaAlimentos);
        }

        return expandableListDetail;
    }

    public static Dia RetornarRefeicoesVazias(Dia dia, int diaSemana){
        Refeicao cafe = new Refeicao(1, "Café", new ArrayList<Alimento>());
        Refeicao lancheManha = new Refeicao(1, "Lanche da Manhã", new ArrayList<Alimento>());
        Refeicao almoco = new Refeicao(1, "Almoço", new ArrayList<Alimento>());
        Refeicao lancheTarde = new Refeicao(1, "Lanche da Tarde", new ArrayList<Alimento>());
        Refeicao jantar = new Refeicao(1, "Jantar", new ArrayList<Alimento>());
        Refeicao lancheNoite = new Refeicao(1, "Lanche da Noite", new ArrayList<Alimento>());

        Alimento alimento1 = new Alimento(1, "Teste", "100kcal");
        Alimento alimento2 = new Alimento(1, "Teste2", "200kcal");
        List<Alimento> listaSugestoes = new ArrayList<Alimento>(Arrays.asList(
                alimento1, alimento2
        ));
        cafe.ListaAlimentos = listaSugestoes;

        if (dia.ListaRefeicoes == null){
            dia.ListaRefeicoes = new ArrayList<Refeicao>();
        }

        for (int c=0; c<dia.ListaRefeicoes.size(); c++){
            if (dia.ListaRefeicoes.get(c).Refeicao == "Café"){
                //cafe.ListaAlimentos = dia.ListaRefeicoes.get(c).ListaAlimentos;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Lanche da Manhã"){
                lancheManha.ListaAlimentos = dia.ListaRefeicoes.get(c).ListaAlimentos;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Almoço"){
                almoco.ListaAlimentos = dia.ListaRefeicoes.get(c).ListaAlimentos;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Lanche da Tarde"){
                lancheTarde.ListaAlimentos = dia.ListaRefeicoes.get(c).ListaAlimentos;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Jantar"){
                jantar.ListaAlimentos = dia.ListaRefeicoes.get(c).ListaAlimentos;
            }else if (dia.ListaRefeicoes.get(c).Refeicao == "Lanche da Noite"){
                lancheNoite.ListaAlimentos = dia.ListaRefeicoes.get(c).ListaAlimentos;
            }
        }

        Dia diaAux = new Dia(diaSemana, new ArrayList<Refeicao>(Arrays.asList(
                cafe, lancheManha, almoco, lancheTarde, jantar, lancheNoite
        )));

        return diaAux;
    }
}
