package com.atividade.agendaalimentacao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class FragmentAlterarSugestaoAlimento_ItemAdapter  extends RecyclerView.Adapter<FragmentAlterarSugestaoAlimento_ItemAdapter.Activity_RecyclerView_Item> {

    List<Alimento> listaAlimentosSugestao = new ArrayList<Alimento>();

    public FragmentAlterarSugestaoAlimento_ItemAdapter(List<Alimento> alimentosSugestao) {
        this.listaAlimentosSugestao = alimentosSugestao;
    }

    @Override
    public Activity_RecyclerView_Item onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_alterar_sugestao_alimento_item, viewGroup, false);

        return new Activity_RecyclerView_Item(view);
    }

    @Override
    public void onBindViewHolder(Activity_RecyclerView_Item itemView, int i) {

        Alimento item = this.listaAlimentosSugestao.get(i);
        itemView.textNomeItemAlterar.setText(item.Nome);
        itemView.textCaloriasItemAlterar.setText(item.Calorias);

        itemView.btnAlterarAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listaAlimentosSugestao.remove(i);
                //notifyDataSetChanged();
            }
        });

        /*itemView.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaAlimentosSugestao.remove(i);
                notifyDataSetChanged();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listaAlimentosSugestao.size();
    }

    public class Activity_RecyclerView_Item extends RecyclerView.ViewHolder{

        TextView textNomeItemAlterar;
        TextView textCaloriasItemAlterar;
        ImageButton btnAlterarAlimento;

        public Activity_RecyclerView_Item(View itemView) {
            super(itemView);

            textNomeItemAlterar = itemView.findViewById(R.id.textNomeItemAlterar);
            textCaloriasItemAlterar = itemView.findViewById(R.id.textCaloriasItemAlterar);
            btnAlterarAlimento = itemView.findViewById(R.id.btnAlterarAlimento);
        }
    }
}
