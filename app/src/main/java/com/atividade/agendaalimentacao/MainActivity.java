package com.atividade.agendaalimentacao;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    Calendar cal = Calendar.getInstance();

    int DiaSelecionado = -1;

    Button btnDia1; Button btnDia2; Button btnDia3; Button btnDia4; Button btnDia5;
    Button btnDia6; Button btnDia7; Button btnDataAtual; ImageButton btnEditarListaAlimentacao;

    ArrayList<Button> botoesDia;

    ExpandableListView expandableListViewRefeicoes;
    ExpandableListAdapter expandableListAdapter;
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

        DefinirListenerPosicaoDia();
        AlterarPosicaoDia(DiaSelecionado, DiaSelecionado);



        Dia dia = this.RetornarDia();

        expandableListItems = Dia.ConverterDiaParaHasMap(dia);

        expandableListViewRefeicoes = findViewById(R.id.expandableListViewRefeicoes);

        //expandableListDetail = this.getData();

        expandableListTitulo = new ArrayList<>(expandableListItems.keySet());

        expandableListAdapter = new MainActivity_RefeicaoAdapter
                (this, expandableListTitulo, expandableListItems);

        expandableListViewRefeicoes.setAdapter(expandableListAdapter);
    }

    public void EditarDia(int dia){

    }

    public void DefinirListenerPosicaoDia(){
        int dia = 1;
        for(Button botao : botoesDia){
            int finalDia = dia;
            botao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlterarPosicaoDia(finalDia, cal.get(Calendar.DAY_OF_WEEK));
                }
            });

            dia += 1;
        }

        btnDataAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlterarPosicaoDia(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.DAY_OF_WEEK));
            }
        });

        btnEditarListaAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editarDia = new Intent(MainActivity.this, ActivityEditarRefeicaoDia.class);

                Dia dia = RetornarDia();
                expandableListItems = Dia.ConverterDiaParaHasMap(dia);

                editarDia.putExtra("DiaSelecionado", DiaSelecionado);
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

    public static HashMap<String, List<String>> getData(){

        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String> cricket = new ArrayList<>();
        cricket.add("India");
        cricket.add("Pakistan");
        cricket.add("Australia");
        cricket.add("Viet Nam");
        cricket.add("South Africa");

        List<String> football = new ArrayList<>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> basketball = new ArrayList<>();
        basketball.add("United States");
        basketball.add("Spain");
        basketball.add("Argentina");
        basketball.add("France");
        basketball.add("Russia");

        expandableListDetail.put("CRICKET TEAMS",cricket);
        expandableListDetail.put("FOOTBALL TEAMS",football);
        expandableListDetail.put("BASKETBALL TEAMS",basketball);

        return expandableListDetail;

    }

    public void AbrirSugestaoAlimentos(List<Alimento> alimentosSugeridos){
        //System.out.println(numero);
        FragmentAlterarSugestaoAlimento fragmentAlterarSugestaoAlimento = new FragmentAlterarSugestaoAlimento(alimentosSugeridos);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentSugestao, fragmentAlterarSugestaoAlimento);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void FecharSugestaoAlimentos(View v){
        System.out.println("Fechou");
        getSupportFragmentManager().popBackStack();
    }
}