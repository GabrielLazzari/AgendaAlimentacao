package com.atividade.agendaalimentacao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.atividade.agendaalimentacao.model.Alimento;
import com.atividade.agendaalimentacao.model.Dia;
import com.atividade.agendaalimentacao.model.Refeicao;

import com.atividade.agendaalimentacao.Repositorio.AgendaRepositorio;
import com.atividade.agendaalimentacao.Repositorio.AlimentoRepositorio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Controle para as datas
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    Calendar cal = Calendar.getInstance();

    // Dias
    int DiaSelecionado = -1;
    public List<Dia> diasSemana = new ArrayList<Dia>();

    private AgendaRepositorio bancoAgenda;
    private AlimentoRepositorio bancoAlimento;

    Button btnDia1; Button btnDia2; Button btnDia3; Button btnDia4; Button btnDia5;
    Button btnDia6; Button btnDia7; Button btnDataAtual; ImageButton btnEditarListaAlimentacao;
    ArrayList<Button> botoesDia;

    ExpandableListView expandableListViewRefeicoes;
    ExpandableListAdapter expandableListAdapter;
    MainActivity_RefeicaoAdapter mainActivityRefeicaoAdapter = null;
    List<String> expandableListTitulo = new ArrayList<String>();
    HashMap<String,List<Alimento>> expandableListItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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

        btnDia1 = findViewById(R.id.btnDiaSemana1);
        btnDia2 = findViewById(R.id.btnDiaSemana2);
        btnDia3 = findViewById(R.id.btnDiaSemana3);
        btnDia4 = findViewById(R.id.btnDiaSemana4);
        btnDia5 = findViewById(R.id.btnDiaSemana5);
        btnDia6 = findViewById(R.id.btnDiaSemana6);
        btnDia7 = findViewById(R.id.btnDiaSemana7);
        botoesDia = new ArrayList<>(Arrays.asList(
                btnDia1, btnDia2, btnDia3, btnDia4, btnDia5, btnDia6, btnDia7
        ));
        btnDataAtual = findViewById(R.id.btnDataAtual);
        btnEditarListaAlimentacao = findViewById(R.id.btnEditarListaAlimentacao);

        TextView textoDataAtual = findViewById(R.id.textoDataAtual);
        textoDataAtual.setText(dateFormat.format(date));

        DefinirListeners();
        AlterarPosicaoDia(DiaSelecionado, DiaSelecionado);


        //for (int c=0; c<7; c++){
            //this.diasSemana.add(this.RetornarDia());
        ///}

        bancoAlimento = new AlimentoRepositorio(this);
        boolean temRegistros = bancoAlimento.CriarPrimeirosRegistrosAlimento();
        bancoAgenda = new AgendaRepositorio(this);
        if (!temRegistros){
            bancoAgenda.CriarPrimeirosRegistros();
        }

        //Dia d = this.RetornarDia();
        //bancoAgenda.AtualizarDia(d);
        CarregarDiaTela();
    }

    public void CarregarDiaTela(){
        Dia dia = bancoAgenda.RetornarDiaSemana(DiaSelecionado);

        expandableListItems = Dia.ConverterDiaParaHasMap(dia);

        expandableListViewRefeicoes = findViewById(R.id.expandableListViewRefeicoes);

        expandableListTitulo = new ArrayList<>(expandableListItems.keySet());

        mainActivityRefeicaoAdapter = new MainActivity_RefeicaoAdapter
                (this, expandableListTitulo, expandableListItems, DiaSelecionado);
        expandableListAdapter = mainActivityRefeicaoAdapter;

        expandableListViewRefeicoes.setAdapter(expandableListAdapter);
    }

    public void DefinirListeners(){
        int dia = 1;
        for(Button botao : botoesDia){
            int finalDia = dia;
            botao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlterarPosicaoDia(finalDia, cal.get(Calendar.DAY_OF_WEEK));
                    CarregarDiaTela();
                    if (mainActivityRefeicaoAdapter != null){
                        mainActivityRefeicaoAdapter.AtualizarDiaSemana(finalDia);
                    }
                }
            });

            dia += 1;
        }

        btnDataAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlterarPosicaoDia(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.DAY_OF_WEEK));
                CarregarDiaTela();
                if (mainActivityRefeicaoAdapter != null){
                    mainActivityRefeicaoAdapter.AtualizarDiaSemana(cal.get(Calendar.DAY_OF_WEEK));
                }
            }
        });

        btnEditarListaAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editarDia = new Intent(MainActivity.this, ActivityEditarRefeicaoDia.class);

                Dia dia = bancoAgenda.RetornarDiaSemana(DiaSelecionado);
                expandableListItems = Dia.ConverterDiaParaHasMap(dia);

                editarDia.putExtra("DiaSelecionado", DiaSelecionado);
                bancoAgenda.InserirEditarPrimeiraVezRefeicaoBuffer(dia);
                //editarDia.putExtra("DadosDia", expandableListDetail);

                startActivity(editarDia);
            }
        });
    }

    public void AlterarPosicaoDia(int dia, int diaAtual){
        this.DiaSelecionado = dia;
        for (int c=1; c<8; c++){
            if (c == dia){
                botoesDia.get(c-1).setBackgroundColor(Color.parseColor("#da7735"));
            }else if(c == diaAtual){
                botoesDia.get(c-1).setBackgroundColor(Color.parseColor("#cccccc"));
            }else{
                botoesDia.get(c-1).setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }



    public void AbrirRelatorios(View v){
        Intent relatorios = new Intent(MainActivity.this, RelatorioActivity.class);
        relatorios.putExtra("DiaSelecionado", DiaSelecionado);
        startActivity(relatorios);
    }

    public void AbrirSugestaoAlimentos(List<Alimento> alimentosSugeridos, String tituloRefeicao, int dia){
        FragmentAlterarSugestaoAlimento fragmentAlterarSugestaoAlimento = new FragmentAlterarSugestaoAlimento(alimentosSugeridos, tituloRefeicao, dia);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentSugestao, fragmentAlterarSugestaoAlimento);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void FecharSugestaoAlimentos(View v){
        System.out.println("Fechou");
        getSupportFragmentManager().popBackStack();
    }

    public static void AlterarSugestaoAlimento(Alimento alimentoModel, String tituloRefeicao, int dia){
        System.out.println("Trocou");
        System.out.println(tituloRefeicao);
        System.out.println(dia);
        System.out.println(alimentoModel.Nome);
        //Dia diaAux = this.diasSemana.get(dia);

        //getSupportFragmentManager().popBackStack();
    }
}