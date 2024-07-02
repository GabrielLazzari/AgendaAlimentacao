package com.atividade.agendaalimentacao;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.atividade.agendaalimentacao.Repositorio.AgendaRepositorio;
import com.atividade.agendaalimentacao.model.Alimento;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlterarSugestaoAlimento_ItemAdapter extends RecyclerView.Adapter<FragmentAlterarSugestaoAlimento_ItemAdapter.Activity_RecyclerView_Item> {

    List<Alimento> listaAlimentosSugestao = new ArrayList<Alimento>();
    String TituloRefeicao = "";
    int Dia = 0;
    String nomeAlimentoSelecionado;
    private AgendaRepositorio bancoAgenda;

    public FragmentAlterarSugestaoAlimento_ItemAdapter(List<Alimento> alimentosSugestao, String tituloRefeicao, int dia, String nomeAlimento, AgendaRepositorio banco) {
        this.listaAlimentosSugestao = alimentosSugestao;
        this.TituloRefeicao = tituloRefeicao;
        this.Dia = dia;
        this.nomeAlimentoSelecionado = nomeAlimento;
        bancoAgenda = banco;
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

        itemView.btnAlterarAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listaAlimentosSugestao.remove(i);

                bancoAgenda.DesvincularAlimentoRemovidoSugestao(Dia, TituloRefeicao, nomeAlimentoSelecionado);
                bancoAgenda.InserirAlimentoSugerido(Dia, TituloRefeicao, alimentoModel.Nome);

                // O codigo comentado a baixo é se precisar fechar o fragment
                //if (view.getContext() instanceof AppCompatActivity) {
                //    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                //    activity.getSupportFragmentManager().popBackStack();
                //}

                Context context = view.getContext();
                Intent main = new Intent(context, MainActivity.class);
                main.putExtra("DiaSelecionado", Dia);
                context.startActivity(main);
            }
        });
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
