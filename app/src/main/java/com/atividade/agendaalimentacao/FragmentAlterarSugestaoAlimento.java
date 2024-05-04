package com.atividade.agendaalimentacao;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.carousel.MaskableFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentAlterarSugestaoAlimento extends Fragment {

    public FragmentAlterarSugestaoAlimento_ItemAdapter adpterItensSugeridos;

    List<Alimento> AlimentosSugeridos = new ArrayList<Alimento>();
    String TituloRefeicao = "";
    int Dia = 0;

    public FragmentAlterarSugestaoAlimento(List<Alimento> alimentosSugeridos, String tituloRefeicao, int dia) {
        this.AlimentosSugeridos = alimentosSugeridos;
        this.TituloRefeicao = tituloRefeicao;
        this.Dia = dia;

        for (int c=0; c<this.AlimentosSugeridos.size(); c++){
            if (this.AlimentosSugeridos.get(c).ListaSugestoes == null){
                this.AlimentosSugeridos.get(c).ListaSugestoes = new ArrayList<Alimento>();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View infFragment = inflater.inflate(R.layout.fragment_alterar_sugestao_alimento, container, false);

        RecyclerView listRecycler = (RecyclerView) infFragment.findViewById(R.id.listaItensSugeridosRecyclerView);

        listRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        adpterItensSugeridos = new FragmentAlterarSugestaoAlimento_ItemAdapter(this.AlimentosSugeridos, this.TituloRefeicao, this.Dia);
        listRecycler.setAdapter(adpterItensSugeridos);

        return infFragment;
    }
}
