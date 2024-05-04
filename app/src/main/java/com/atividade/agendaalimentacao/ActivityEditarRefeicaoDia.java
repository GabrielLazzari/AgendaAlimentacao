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

    int DiaSelecionado = -1;
    Dia dia = new Dia();

    ImageButton btnCancelarDia; ImageButton btnSalvarDia;

    ExpandableListView expandableListViewRefeicoesEditar;
    ExpandableListAdapter expandableListAdapterEditar;
    List<String> expandableListTitulo = new ArrayList<String>();
    HashMap<String,List<AlimentoModel>> expandableListItems;

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
        AlimentoModel alimentoModel1 = new AlimentoModel(1, "Teste", "100kcal");
        AlimentoModel alimentoModel2 = new AlimentoModel(1, "Teste2", "200kcal");
        AlimentoModel alimentoModel3 = new AlimentoModel(1, "Teste3", "150kcal");
        AlimentoModel alimentoModel4 = new AlimentoModel(1, "Teste4", "500kcal");
        AlimentoModel alimentoModel5 = new AlimentoModel(1, "Teste5", "170kcal");

        Refeicao refeicao1 = new Refeicao(1, "Café", new ArrayList<AlimentoModel>(Arrays.asList(
                alimentoModel1, alimentoModel2
        )));

        Refeicao refeicao2 = new Refeicao(1, "Almoço", new ArrayList<AlimentoModel>(Arrays.asList(
                alimentoModel3
        )));

        List<AlimentoModel> listaSugestoes = new ArrayList<AlimentoModel>(Arrays.asList(
                alimentoModel1, alimentoModel3
        ));

        alimentoModel4.setListaSugestoes(listaSugestoes);

        Refeicao refeicao3 = new Refeicao(1, "Jantar", new ArrayList<AlimentoModel>(Arrays.asList(
                alimentoModel4, alimentoModel5
        )));

        Dia dia = new Dia(1, new ArrayList<Refeicao>(Arrays.asList(
                refeicao1, refeicao2, refeicao3
        )));

        return dia;
    }

    public void AbrirActivityVincularAlimento(){
        AdicionarAlimentoDialogFragment dialogFragment = new AdicionarAlimentoDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "AdicionarAlimentoDialogFragment");
    }

    public void AbrirActivityAlimentos(View v){
        Intent activityAlimentos = new Intent(ActivityEditarRefeicaoDia.this, AlimentoActivityEditar.class);
        activityAlimentos.putExtra("DiaSelecionado", DiaSelecionado);
        startActivity(activityAlimentos);
    }
}