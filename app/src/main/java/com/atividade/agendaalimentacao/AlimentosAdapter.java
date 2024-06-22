package com.atividade.agendaalimentacao;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.atividade.agendaalimentacao.model.Alimento;

import java.util.List;

public class AlimentosAdapter extends RecyclerView.Adapter<AlimentosAdapter.ViewHolder> {

    private Context mContext;
    private List<Alimento> dataList;

    int DiaSelecionado;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1, textView2;
        public ImageButton imageButton;

        public ViewHolder(View view) {
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            imageButton = view.findViewById(R.id.imageButton);
        }
    }
    public AlimentosAdapter(Context context, List<Alimento> dataList, int dia) {
        this.mContext = context;
        this.dataList = dataList;
        this.DiaSelecionado = dia;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alimentos_recyclerview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alimento alimento = dataList.get(position);
        holder.textView1.setText(alimento.Nome);
        holder.textView2.setText(alimento.Calorias);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AlimentoActivityCadastrar.class);

                intent.putExtra("nome", alimento.getNome());
                intent.putExtra("calorias", alimento.getCalorias());
                intent.putExtra("tipo", alimento.getTipo());
                intent.putExtra("idAlimento", alimento.getId());
                intent.putExtra("DiaSelecionado", DiaSelecionado);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

