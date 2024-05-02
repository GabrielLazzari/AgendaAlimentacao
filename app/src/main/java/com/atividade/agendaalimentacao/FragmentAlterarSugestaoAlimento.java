package com.atividade.agendaalimentacao;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.carousel.MaskableFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentAlterarSugestaoAlimento extends Fragment {

    public FragmentAlterarSugestaoAlimento_ItemAdapter adpterItensSugeridos;

    public FragmentAlterarSugestaoAlimento(List<Alimento> alimentosSugeridos) {
        // Required empty public constructor
        System.out.println("Ola");
        for (int c=0; c<alimentosSugeridos.size(); c++){
            System.out.println(alimentosSugeridos.get(c).Nome);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View infFragment = inflater.inflate(R.layout.fragment_alterar_sugestao_alimento, container, false);

        ConstraintLayout contraint = new ConstraintLayout();
        contraint = R.layout.activity_main;

        RecyclerView listRecycler = (RecyclerView) infFragment.findViewById(R.id.listaItensSugeridosRecyclerView);

        listRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        Alimento alimento1 = new Alimento(1, "Teste", "100kcal");
        Alimento alimento2 = new Alimento(1, "Teste2", "200kcal");
        List<Alimento> listaSugestoes = new ArrayList<Alimento>(Arrays.asList(
                alimento1, alimento2
        ));


        adpterItensSugeridos = new FragmentAlterarSugestaoAlimento_ItemAdapter(listaSugestoes);
        listRecycler.setAdapter(adpterItensSugeridos);

        return infFragment;
    }
}