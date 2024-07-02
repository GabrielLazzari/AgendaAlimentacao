package com.atividade.agendaalimentacao;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.atividade.agendaalimentacao.Repositorio.AgendaRepositorio;
import com.atividade.agendaalimentacao.model.Alimento;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.PdfDocumentLoader;
import com.pspdfkit.document.html.HtmlToPdfConverter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RelatorioActivity extends AppCompatActivity {

    private Calendar calendar;
    int DiaSelecionado = -1;
    private String diaInicialSelecionado;
    private String diaFinalSelecionado;
    private AgendaRepositorio bancoAgenda;
    private Context contexto = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_relatorio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        try{
            DiaSelecionado = getIntent().getIntExtra("DiaSelecionado", -1);
        }catch (Exception e){
            DiaSelecionado = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        }

        String[] diaSemana = {"1", "2", "3", "4", "5", "6", "7"};


        Spinner spinerDataInicial = findViewById(R.id.relatorio_datainicial);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, diaSemana);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerDataInicial.setAdapter(adapter1);

        spinerDataInicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diaInicialSelecionado = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diaInicialSelecionado = Integer.toString(DiaSelecionado);
            }
        });


        Spinner spinerDataFinal = findViewById(R.id.relatorio_datafinal);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, diaSemana);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerDataFinal.setAdapter(adapter2);

        spinerDataFinal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diaFinalSelecionado = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diaFinalSelecionado = Integer.toString(DiaSelecionado);
            }
        });


        Button relatorio_buttonCancelar = findViewById(R.id.relatorio_buttonCancelar);

        relatorio_buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityMain = new Intent(RelatorioActivity.this, MainActivity.class);

                activityMain.putExtra("DiaSelecionado", DiaSelecionado);
                startActivity(activityMain);
            }
        });

        Button relatorio_buttonExportar = findViewById(R.id.relatorio_buttonExportar);

        bancoAgenda = new AgendaRepositorio(this);

        relatorio_buttonExportar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {

                if (diaInicialSelecionado != null && diaFinalSelecionado != null && Integer.parseInt(diaInicialSelecionado) <= Integer.parseInt(diaFinalSelecionado)) {

                    String htmlFinal = "<html> <head> <style> table { width: 100%; border-collapse: collapse; margin-bottom: 10px; } th, td { border: 1px solid black; padding: 8px; text-align: left; } .meal-header { background-color: #f2f2f2; font-weight: bold; } .food-table { margin-left: 20px; } </style> </head> <body>";

                    for (int diaInicial = Integer.parseInt(diaInicialSelecionado); diaInicial <= Integer.parseInt(diaFinalSelecionado); diaInicial++){

                        String diaSemana = "Domingo";
                        switch (diaInicial){
                            case 2:
                                diaSemana = "Segunda-feira";
                            case 3:
                                diaSemana = "Terça-feira";
                            case 4:
                                diaSemana = "Quarta-feira";
                            case 5:
                                diaSemana = "Quinta-feira";
                            case 6:
                                diaSemana = "Sexta-feira";
                            case 7:
                                diaSemana = "Sábado";
                        }

                        htmlFinal += "<h1>" + diaSemana + "</h1> <table> <tr class=\"meal-header\"> <th>Refeições</th> <th>Alimentos</th> </tr>";

                        for (int iRef = 1; iRef <= 6; iRef++){

                            String refeicao = "Café";
                            switch (iRef){
                                case 2:
                                    refeicao = "Lanche da Manhã";
                                case 3:
                                    refeicao = "Almoço";
                                case 4:
                                    refeicao = "Lanche da Tarde";
                                case 5:
                                    refeicao = "Jantar";
                                case 6:
                                    refeicao = "Lanche da Noite";
                            }

                            htmlFinal += "<tr> <td>" + refeicao + "</td> <td> <table class=\"food-table\">";

                            String query = String.format("SELECT NomeAlimento FROM AlimentoRefeicao WHERE RefeicaoDia = ? AND Refeicao = ?;");

                            SQLiteDatabase db = bancoAgenda.getReadableDatabase();
                            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(diaInicial), refeicao});

                            if (cursor.moveToFirst()){
                                do {
                                    @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("NomeAlimento"));

                                    htmlFinal += "<tr> <td>" + nome + "</td> </tr>";

                                } while (cursor.moveToNext());
                            }
                            htmlFinal += "</table> </td> </tr>";
                        }
                        htmlFinal += "</table>";
                    }

                    htmlFinal += "</body></html>";


                    // Criação do PDF a partir do HTML na pasta de downloads
                    final File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RelatorioAgendaAlimentacao.pdf");

                    // Perform the conversion.
                    HtmlToPdfConverter.fromHTMLString(contexto, htmlFinal)
                            // Configure title for the created document.
                            .title("Converted document")
                            // Perform the conversion.
                            .convertToPdfAsync(outputFile)
                            // Subscribe to the conversion result.
                            .subscribe(() -> {
                                // Open and process the converted document.
                                PdfDocument document = PdfDocumentLoader.openDocument(contexto, Uri.fromFile(outputFile));
                            }, throwable -> {
                                // Handle the error.
                            });


                    Intent activityMain = new Intent(RelatorioActivity.this, MainActivity.class);
                    activityMain.putExtra("DiaSelecionado", DiaSelecionado);
                    startActivity(activityMain);
                } else {
                    Toast.makeText(RelatorioActivity.this, "A Data Inicial precisa ser menor ou igual à Data Final", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}