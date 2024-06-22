package com.atividade.agendaalimentacao.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Dia {

    public int DiaSemana;
    public List<Refeicao> ListaRefeicoes;

    public Dia(){}

    public Dia(int diaSemana, List<Refeicao> listaRefeicoes){
        this.DiaSemana = diaSemana;
        this.ListaRefeicoes = listaRefeicoes;
    }

    public static HashMap<String, List<Alimento>> ConverterDiaParaHasMap(Dia dia){
        HashMap<String, List<Alimento>> expandableListDetail = new HashMap<>();

        if (dia.ListaRefeicoes == null){
            dia.ListaRefeicoes = new ArrayList<Refeicao>();
        }

        for (int c=0; c<dia.ListaRefeicoes.size(); c++){
            expandableListDetail.put(dia.ListaRefeicoes.get(c).Refeicao, dia.ListaRefeicoes.get(c).listaAlimentoModels);
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

        Alimento alimentoModel1 = new Alimento("Teste", "100kcal", "1");
        Alimento alimentoModel2 = new Alimento("Teste2", "200kcal", "2");
        List<Alimento> listaSugestoes = new ArrayList<Alimento>(Arrays.asList(
                alimentoModel1, alimentoModel2
        ));
        //cafe.listaAlimentoModels = listaSugestoes;

        if (dia.ListaRefeicoes == null){
            dia.ListaRefeicoes = new ArrayList<Refeicao>();
        }

        for (int c=0; c<dia.ListaRefeicoes.size(); c++){
            Refeicao refeicao = dia.ListaRefeicoes.get(c);
            if (refeicao.Refeicao.equals("Café")){
                cafe.listaAlimentoModels = refeicao.listaAlimentoModels;
            }else if (refeicao.Refeicao.equals("Lanche da Manhã")){
                lancheManha.listaAlimentoModels = refeicao.listaAlimentoModels;
            }else if (refeicao.Refeicao.equals("Almoço")){
                almoco.listaAlimentoModels = refeicao.listaAlimentoModels;
            }else if (refeicao.Refeicao.equals("Lanche da Tarde")){
                lancheTarde.listaAlimentoModels = refeicao.listaAlimentoModels;
            }else if (refeicao.Refeicao.equals("Jantar")){
                jantar.listaAlimentoModels = refeicao.listaAlimentoModels;
            }else if (refeicao.Refeicao.equals("Lanche da Noite")){
                lancheNoite.listaAlimentoModels = refeicao.listaAlimentoModels;
            }
        }

        Dia diaAux = new Dia(diaSemana, new ArrayList<Refeicao>(Arrays.asList(
                cafe, lancheManha, almoco, lancheTarde, jantar, lancheNoite
        )));

        return diaAux;
    }
}
