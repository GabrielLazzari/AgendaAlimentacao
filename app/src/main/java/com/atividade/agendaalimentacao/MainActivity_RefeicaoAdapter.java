package com.atividade.agendaalimentacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity_RefeicaoAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> expandableListTitle;
    private HashMap<String, List<Alimento>> expandableListDetail;

    public MainActivity_RefeicaoAdapter(Context mContext, List<String> expandableListTitle, HashMap<String, List<Alimento>> expandableListDetail) {
        this.mContext = mContext;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
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
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main_refeicao_alimento_titulo, null);
        }

        TextView textTituloRefeicao = convertView.findViewById(R.id.textTituloRefeicao);
        textTituloRefeicao.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Alimento alimento = (Alimento) getChild(groupPosition, childPosition);
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater)
                    this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main_refeicao_alimento_item, null);

        }

        TextView expandedListTextView  = convertView.findViewById(R.id.textNomeAlimento);
        expandedListTextView.setText(alimento.Nome);

        TextView textCaloriasAlimento = convertView.findViewById(R.id.textCaloriasAlimento);
        textCaloriasAlimento.setText(alimento.Calorias);

        if (alimento.ListaSugestoes == null){
            alimento.ListaSugestoes = new ArrayList<Alimento>();
        }

        Button butSugestaoSubstituicao = convertView.findViewById(R.id.butSugestaoSubstituicao);
        butSugestaoSubstituicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alimento alimento1 = new Alimento(1, "Teste", "100kcal");
                Alimento alimento2 = new Alimento(1, "Teste2", "200kcal");
                List<Alimento> listaSugestoes = new ArrayList<Alimento>(Arrays.asList(
                        alimento1, alimento2
                ));
                alimento.ListaSugestoes = listaSugestoes;
                ((MainActivity)mContext).AbrirSugestaoAlimentos(alimento.ListaSugestoes);
            }
        });
        if (alimento.ListaSugestoes.size() == 0){
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
