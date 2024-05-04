package com.atividade.agendaalimentacao;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RelatorioActivity extends AppCompatActivity {

    private EditText relatorio_dataInicial;
    private EditText relatorio_dataFinal;
    private Calendar calendar;

    int DiaSelecionado = -1;

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

        calendar = Calendar.getInstance();

        try{
            DiaSelecionado = getIntent().getIntExtra("DiaSelecionado", -1);
        }catch (Exception e){
            DiaSelecionado = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        }


        relatorio_dataInicial = findViewById(R.id.relatorio_datainicial);
        relatorio_dataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(RelatorioActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Update the EditText with the selected date
                                relatorio_dataInicial.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        relatorio_dataFinal = findViewById(R.id.relatorio_datafinal);
        relatorio_dataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(RelatorioActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Update the EditText with the selected date
                                relatorio_dataFinal.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
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

        relatorio_buttonExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityMain = new Intent(RelatorioActivity.this, MainActivity.class);

                activityMain.putExtra("DiaSelecionado", DiaSelecionado);
                startActivity(activityMain);
            }
        });
    }
}