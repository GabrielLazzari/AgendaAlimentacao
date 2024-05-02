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

public class ActivityEditarRefeicaoDia_Adapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> expandableListTitle;
    private HashMap<String, List<Alimento>> expandableListDetail;

    public ActivityEditarRefeicaoDia_Adapter(Context mContext, List<String> expandableListTitle, HashMap<String, List<Alimento>> expandableListDetail) {
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
            convertView = inflater.inflate(R.layout.activity_editar_refeicao_dia_titulo, null);
        }

        TextView textTituloRefeicaoEditar = convertView.findViewById(R.id.textTituloRefeicaoEditar);
        textTituloRefeicaoEditar.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Alimento alimento = (Alimento) getChild(groupPosition, childPosition);
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater)
                    this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_editar_refeicao_dia_item, null);

        }

        TextView expandedListTextView  = convertView.findViewById(R.id.textNomeAlimentoEditar);
        expandedListTextView.setText(alimento.Nome);

        convertView.findViewById(R.id.btnExcluirAlimentoRefeicao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Alimento> selectedGroupList = expandableListDetail.get(expandableListTitle.get(groupPosition));
                selectedGroupList.remove(childPosition);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
