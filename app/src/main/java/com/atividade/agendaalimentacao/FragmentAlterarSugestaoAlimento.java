package com.atividade.agendaalimentacao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atividade.agendaalimentacao.Repositorio.AgendaRepositorio;
import com.atividade.agendaalimentacao.model.Alimento;

import java.util.ArrayList;
import java.util.List;


public class FragmentAlterarSugestaoAlimento extends Fragment {

    public FragmentAlterarSugestaoAlimento_ItemAdapter adpterItensSugeridos;

    List<Alimento> alimentosSugeridos = new ArrayList<Alimento>();
    String TituloRefeicao = "";
    String nomeAlimento;
    int Dia = 0;
    private AgendaRepositorio bancoAgenda;

    public FragmentAlterarSugestaoAlimento(List<Alimento> alimentosSugeridos, String tituloRefeicao, int dia, String nomeAlimento, AgendaRepositorio banco) {
        this.alimentosSugeridos = alimentosSugeridos;
        this.TituloRefeicao = tituloRefeicao;
        this.nomeAlimento = nomeAlimento;
        this.Dia = dia;
        bancoAgenda = banco;

        for (int c = 0; c<this.alimentosSugeridos.size(); c++){
            if (this.alimentosSugeridos.get(c).ListaSugestoes == null){
                this.alimentosSugeridos.get(c).ListaSugestoes = new ArrayList<Alimento>();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View infFragment = inflater.inflate(R.layout.fragment_alterar_sugestao_alimento, container, false);

        RecyclerView listRecycler = (RecyclerView) infFragment.findViewById(R.id.listaItensSugeridosRecyclerView);

        listRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        adpterItensSugeridos = new FragmentAlterarSugestaoAlimento_ItemAdapter(this.alimentosSugeridos, this.TituloRefeicao, this.Dia, this.nomeAlimento, this.bancoAgenda);
        listRecycler.setAdapter(adpterItensSugeridos);

        return infFragment;
    }
}
