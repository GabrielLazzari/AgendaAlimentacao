package com.atividade.agendaalimentacao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.atividade.agendaalimentacao.model.Alimento;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlterarSugestaoAlimento_ItemAdapter extends RecyclerView.Adapter<FragmentAlterarSugestaoAlimento_ItemAdapter.Activity_RecyclerView_Item> {

    List<Alimento> listaAlimentosSugestao = new ArrayList<Alimento>();
    String TituloRefeicao = "";
    int Dia = 0;

    public FragmentAlterarSugestaoAlimento_ItemAdapter(List<Alimento> alimentosSugestao, String tituloRefeicao, int dia) {
        this.listaAlimentosSugestao = alimentosSugestao;
        this.TituloRefeicao = tituloRefeicao;
        this.Dia = dia;
    }

    @Override
    public Activity_RecyclerView_Item onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_alterar_sugestao_alimento_item, viewGroup, false);

        return new Activity_RecyclerView_Item(view);
    }

    @Override
    public void onBindViewHolder(Activity_RecyclerView_Item itemView, int i) {

        Alimento alimentoModel = this.listaAlimentosSugestao.get(i);
        itemView.textNomeItemAlterar.setText(alimentoModel.Nome);
        itemView.textCaloriasItemAlterar.setText(alimentoModel.Calorias);

        //itemView.btnAlterarAlimento.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //listaAlimentosSugestao.remove(i);
                //notifyDataSetChanged();


                // Quando formos implementar as logicas, tem que fazer isso salvar no banco
                // depois fechar o fragment
                // e por fim atualizar a tela principal
                // ja que nao esta sendo possivel atualizar por conta do conflito com o metodo estatico

                //MainActivity.AlterarSugestaoAlimento(alimento, TituloRefeicao, Dia);

            //}
        //});
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
