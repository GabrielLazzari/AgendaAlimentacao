package com.atividade.agendaalimentacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atividade.agendaalimentacao.model.Alimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity_RefeicaoAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> expandableListTitle;
    private HashMap<String, List<Alimento>> expandableListDetail;
    int Dia = 0;

    public MainActivity_RefeicaoAdapter(Context mContext, List<String> expandableListTitle, HashMap<String, List<Alimento>> expandableListDetail, int dia) {
        this.mContext = mContext;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.Dia = dia;
    }

    public void AtualizarDiaSemana(int diaAtualizado){
        this.Dia = diaAtualizado;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String tituloRefeicao = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main_refeicao_alimento_titulo, null);
        }

        TextView textTituloRefeicao = convertView.findViewById(R.id.textTituloRefeicao);
        textTituloRefeicao.setText(tituloRefeicao);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String tituloRefeicao = (String) getGroup(groupPosition);
        Alimento alimentoModel = (Alimento) getChild(groupPosition, childPosition);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater)
                    this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main_refeicao_alimento_item, null);
        }

        TextView expandedListTextView  = convertView.findViewById(R.id.textNomeAlimento);
        expandedListTextView.setText(alimentoModel.Nome);

        TextView textCaloriasAlimento = convertView.findViewById(R.id.textCaloriasAlimento);
        textCaloriasAlimento.setText(alimentoModel.Calorias);

        if (alimentoModel.ListaSugestoes == null){
            alimentoModel.ListaSugestoes = new ArrayList<Alimento>();
        }

        Button butSugestaoSubstituicao = convertView.findViewById(R.id.butSugestaoSubstituicao);
        butSugestaoSubstituicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).AbrirSugestaoAlimentos(alimentoModel.ListaSugestoes, tituloRefeicao, Dia, alimentoModel.Nome);
            }
        });

        if (alimentoModel.ListaSugestoes.size() == 0){
            butSugestaoSubstituicao.setVisibility(butSugestaoSubstituicao.GONE);
        }else{
            butSugestaoSubstituicao.setVisibility(butSugestaoSubstituicao.VISIBLE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
