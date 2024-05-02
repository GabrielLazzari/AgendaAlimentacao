package com.atividade.agendaalimentacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActivityEditarRefeicaoDia extends AppCompatActivity {

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    Calendar cal = Calendar.getInstance();

    ImageButton btnCancelarDia; ImageButton btnSalvarDia;

    int DiaSelecionado = -1;

    Dia dia = new Dia();

    ExpandableListView expandableListViewRefeicoesEditar;
    ExpandableListAdapter expandableListAdapterEditar;
    List<String> expandableListTitulo = new ArrayList<String>();
    HashMap<String,List<Alimento>> expandableListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_refeicao_dia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try{
            DiaSelecionado = getIntent().getIntExtra("DiaSelecionado", -1);

        }catch (Exception e){

        }

        if (DiaSelecionado == -1){
            DiaSelecionado = cal.get(Calendar.DAY_OF_WEEK);
        }

        TextView textDiaSemanaEditar = findViewById(R.id.textDiaSemanaEditar);
        if (DiaSelecionado == cal.get(Calendar.DAY_OF_WEEK)){
            textDiaSemanaEditar.setText(RetornarDiaSemanaString(DiaSelecionado) + " - Hoje");
        }else{
            textDiaSemanaEditar.setText(RetornarDiaSemanaString(DiaSelecionado));
        }

        btnCancelarDia = findViewById(R.id.btnCancelarDia);
        btnSalvarDia = findViewById(R.id.btnSalvarDia);

        DefiniListners();

        dia = RetornarDia();
        dia = Dia.RetornarRefeicoesVazias(dia, DiaSelecionado);

        expandableListItems = Dia.ConverterDiaParaHasMap(dia);

        expandableListViewRefeicoesEditar = findViewById(R.id.expandableListViewRefeicoesEditar);

        expandableListTitulo = new ArrayList<>(expandableListItems.keySet());

        expandableListAdapterEditar = new ActivityEditarRefeicaoDia_Adapter
                (this, expandableListTitulo, expandableListItems);

        expandableListViewRefeicoesEditar.setAdapter(expandableListAdapterEditar);

    }

    public void DefiniListners(){
        btnCancelarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(ActivityEditarRefeicaoDia.this, MainActivity.class);

                main.putExtra("DiaSelecionado", DiaSelecionado);
                //editarDia.putExtra("DadosDia", expandableListDetail);

                startActivity(main);
            }
        });

        btnSalvarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(ActivityEditarRefeicaoDia.this, MainActivity.class);

                main.putExtra("DiaSelecionado", DiaSelecionado);
                //editarDia.putExtra("DadosDia", expandableListDetail);

                startActivity(main);
            }
        });
    }

    public String RetornarDiaSemanaString(int dia){
        if (dia == 2){
            return "Segunda-Feira";
        }else if (dia == 3){
            return "Terça-Feira";
        }else if (dia == 4){
            return "Quarta-Feira";
        }else if (dia == 5){
            return "Quinta-Feira";
        }else if (dia == 6){
            return "Sexta-Feira";
        }else if (dia == 7){
            return "Sábado";
        }else {
            return "Domingo";
        }
    }

    public Dia RetornarDia(){
        Alimento alimento1 = new Alimento(1, "Teste", "100kcal");
        Alimento alimento2 = new Alimento(1, "Teste2", "200kcal");
        Alimento alimento3 = new Alimento(1, "Teste3", "150kcal");
        Alimento alimento4 = new Alimento(1, "Teste4", "500kcal");
        Alimento alimento5 = new Alimento(1, "Teste5", "170kcal");

        Refeicao refeicao1 = new Refeicao(1, "Café", new ArrayList<Alimento>(Arrays.asList(
                alimento1, alimento2
        )));

        Refeicao refeicao2 = new Refeicao(1, "Almoço", new ArrayList<Alimento>(Arrays.asList(
                alimento3
        )));

        List<Alimento> listaSugestoes = new ArrayList<Alimento>(Arrays.asList(
                alimento1, alimento3
        ));

        alimento4.setListaSugestoes(listaSugestoes);

        Refeicao refeicao3 = new Refeicao(1, "Jantar", new ArrayList<Alimento>(Arrays.asList(
                alimento4, alimento5
        )));

        Dia dia = new Dia(1, new ArrayList<Refeicao>(Arrays.asList(
                refeicao1, refeicao2, refeicao3
        )));

        return dia;
    }

    public void teste(View v){
        System.out.println("aaaa s");
    }
}