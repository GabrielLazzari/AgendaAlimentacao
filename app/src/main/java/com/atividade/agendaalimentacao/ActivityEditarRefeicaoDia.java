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

import com.atividade.agendaalimentacao.Repositorio.AgendaRepositorio;
import com.atividade.agendaalimentacao.Repositorio.AlimentoRepositorio;
import com.atividade.agendaalimentacao.model.Alimento;
import com.atividade.agendaalimentacao.model.Dia;
import com.atividade.agendaalimentacao.model.Refeicao;

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

    private AgendaRepositorio bancoAgenda;
    private AlimentoRepositorio bancoAlimento;

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

        bancoAlimento = new AlimentoRepositorio(this);
        bancoAgenda = new AgendaRepositorio(this);

        try{
            String refeicaoModal = getIntent().getStringExtra("Refeicao");
            String alimentoModal = getIntent().getStringExtra("AlimentoSelecionado");
            if (!refeicaoModal.isEmpty() && !alimentoModal.isEmpty()){
                bancoAgenda.InserirAlimentoRefeicaoBuffer(refeicaoModal, alimentoModal);
            }
        }catch (Exception e){

        }

        dia = bancoAgenda.RetornarRefeicaoBuffer(DiaSelecionado);
        dia = Dia.RetornarRefeicoesVazias(dia, DiaSelecionado);

        expandableListItems = Dia.ConverterDiaParaHasMap(dia);

        expandableListViewRefeicoesEditar = findViewById(R.id.expandableListViewRefeicoesEditar);

        expandableListTitulo = new ArrayList<>(expandableListItems.keySet());

        expandableListAdapterEditar = new ActivityEditarRefeicaoDia_Adapter
                (this, expandableListTitulo, expandableListItems, bancoAgenda);

        expandableListViewRefeicoesEditar.setAdapter(expandableListAdapterEditar);

    }

    public void DefiniListners(){
        btnCancelarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(ActivityEditarRefeicaoDia.this, MainActivity.class);

                main.putExtra("DiaSelecionado", DiaSelecionado);
                //editarDia.putExtra("DadosDia", expandableListDetail);
                bancoAgenda.LimparRefeicaoBuffer();

                startActivity(main);
            }
        });

        btnSalvarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(ActivityEditarRefeicaoDia.this, MainActivity.class);

                main.putExtra("DiaSelecionado", DiaSelecionado);
                Dia diaModalAux = bancoAgenda.RetornarRefeicaoBuffer(DiaSelecionado);
                bancoAgenda.AtualizarDia(diaModalAux);
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

    public void AbrirActivityVincularAlimento(String refeicao){
        ArrayList<String> nomesAlimentos = bancoAlimento.RetornarListaNomesAlimentos();
        AdicionarAlimentoDialogFragment dialogFragment = new AdicionarAlimentoDialogFragment(DiaSelecionado, refeicao, nomesAlimentos);
        dialogFragment.show(getSupportFragmentManager(), "AdicionarAlimentoDialogFragment");
    }

    public void AbrirActivityAlimentos(View v){
        Intent activityAlimentos = new Intent(ActivityEditarRefeicaoDia.this, AlimentoActivityEditar.class);
        activityAlimentos.putExtra("DiaSelecionado", DiaSelecionado);
        startActivity(activityAlimentos);
    }
}