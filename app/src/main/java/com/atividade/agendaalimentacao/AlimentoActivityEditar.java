package com.atividade.agendaalimentacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atividade.agendaalimentacao.Repositorio.AlimentoRepositorio;
import com.atividade.agendaalimentacao.model.Alimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlimentoActivityEditar extends AppCompatActivity {

    Calendar calendario = Calendar.getInstance();
    int DiaSelecionado = 0;

    private AlimentoRepositorio bancoAlimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alimento_editar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try{
            DiaSelecionado = getIntent().getIntExtra("DiaSelecionado", -1);
        }catch (Exception e){
            DiaSelecionado = calendario.get(Calendar.DAY_OF_WEEK);
        }

        //List<Alimento> dataList = new ArrayList<Alimento>();
        //dataList.add(new Alimento("Item 1", "kcal", "1"));
        //dataList.add(new Alimento("Item 2", "kcal", "2"));
        //dataList.add(new Alimento("Item 3", "kcal", "3"));

        bancoAlimento = new AlimentoRepositorio(this);
        List<Alimento> listaAlimentos = bancoAlimento.RetornarTodosAlimentos();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AlimentosAdapter adapter = new AlimentosAdapter(this, listaAlimentos, this.DiaSelecionado);
        recyclerView.setAdapter(adapter);


        ImageButton imagebutton_voltar = findViewById(R.id.imagebutton_voltar);

        imagebutton_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityEditarRefeicaoDia = new Intent(AlimentoActivityEditar.this, ActivityEditarRefeicaoDia.class);

                activityEditarRefeicaoDia.putExtra("DiaSelecionado", DiaSelecionado);
                startActivity(activityEditarRefeicaoDia);
            }
        });


        ImageButton imagebutton_adicionar = findViewById(R.id.imagebutton_adicionar);

        imagebutton_adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityAlimentoCadastrar = new Intent(AlimentoActivityEditar.this, AlimentoActivityCadastrar.class);

                activityAlimentoCadastrar.putExtra("DiaSelecionado", DiaSelecionado);
                startActivity(activityAlimentoCadastrar);
            }
        });
    }
}