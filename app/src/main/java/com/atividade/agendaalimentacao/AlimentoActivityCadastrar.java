package com.atividade.agendaalimentacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AlimentoActivityCadastrar extends AppCompatActivity {

    Calendar calendario = Calendar.getInstance();
    int DiaSelecionado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alimento_cadastrar);
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

        Intent intent = getIntent();
        String receivedValueNome = intent.getStringExtra("nome");
        String receivedValueCalorias = intent.getStringExtra("calorias");

        EditText cadastrar_alimento_nome = findViewById(R.id.cadastrar_alimento_nome);
        cadastrar_alimento_nome.setText(receivedValueNome);

        EditText cadastrar_alimento_calorias = findViewById(R.id.cadastrar_alimento_calorias);
        cadastrar_alimento_calorias.setText(receivedValueCalorias);


        ImageButton imagebutton_excluir = findViewById(R.id.imagebutton_excluir);

        imagebutton_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlimentoActivityCadastrar.this, AlimentoActivityEditar.class);
                intent.putExtra("DiaSelecionado", DiaSelecionado);

                startActivity(intent);
            }
        });


        ImageButton imageButton_cancelar = findViewById(R.id.imageButton_cancelar);

        imageButton_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityAlimentoEditar = new Intent(AlimentoActivityCadastrar.this, AlimentoActivityEditar.class);

                activityAlimentoEditar.putExtra("DiaSelecionado", DiaSelecionado);
                startActivity(activityAlimentoEditar);
            }
        });


        ImageButton imageButton_salvar = findViewById(R.id.imageButton_salvar);

        imageButton_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityAlimentoEditar = new Intent(AlimentoActivityCadastrar.this, AlimentoActivityEditar.class);

                activityAlimentoEditar.putExtra("DiaSelecionado", DiaSelecionado);
                startActivity(activityAlimentoEditar);
            }
        });
    }
}
