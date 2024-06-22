package com.atividade.agendaalimentacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.atividade.agendaalimentacao.Repositorio.AlimentoRepositorio;
import com.atividade.agendaalimentacao.model.Alimento;

import java.util.Calendar;

public class AlimentoActivityCadastrar extends AppCompatActivity {

    Calendar calendario = Calendar.getInstance();
    int DiaSelecionado = 0;

    int idAlimento;
    EditText cadastrar_alimento_nome;
    EditText cadastrar_alimento_calorias;
    EditText cadastrar_alimento_tipo;

    String nomeAlimentoAntes;
    String caloriasAlimentoAntes;
    String tipoAlimentoAntes;

    private AlimentoRepositorio bancoAlimento;

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
        String receivedValueTipo = intent.getStringExtra("tipo");
        idAlimento = intent.getIntExtra("idAlimento", -1);

        nomeAlimentoAntes = receivedValueNome;
        caloriasAlimentoAntes = receivedValueCalorias;
        tipoAlimentoAntes = receivedValueTipo;

        cadastrar_alimento_nome = findViewById(R.id.cadastrar_alimento_nome);
        cadastrar_alimento_nome.setText(receivedValueNome);

        cadastrar_alimento_calorias = findViewById(R.id.cadastrar_alimento_calorias);
        cadastrar_alimento_calorias.setText(receivedValueCalorias);

        cadastrar_alimento_tipo = findViewById(R.id.cadastrar_alimento_tipo);
        cadastrar_alimento_tipo.setText(receivedValueTipo);

        ImageButton imagebutton_excluir = findViewById(R.id.imagebutton_excluir);

        bancoAlimento = new AlimentoRepositorio(this);

        imagebutton_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alimento alimentoAntes = new Alimento(nomeAlimentoAntes, caloriasAlimentoAntes, tipoAlimentoAntes);
                bancoAlimento.DeletarAlimento(alimentoAntes);

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

                Alimento alimentoAntes = new Alimento(nomeAlimentoAntes, caloriasAlimentoAntes, tipoAlimentoAntes);
                Alimento alimento = new Alimento(idAlimento, cadastrar_alimento_nome.getText().toString(),
                        cadastrar_alimento_calorias.getText().toString(), cadastrar_alimento_tipo.getText().toString());

                if (nomeAlimentoAntes == null){
                    bancoAlimento.InserirAlimento(alimento.Nome, alimento.Calorias, alimento.Tipo);
                }else{
                    bancoAlimento.AtualizarAlimento(alimento, alimentoAntes);
                }

                Intent activityAlimentoEditar = new Intent(AlimentoActivityCadastrar.this, AlimentoActivityEditar.class);

                activityAlimentoEditar.putExtra("DiaSelecionado", DiaSelecionado);
                startActivity(activityAlimentoEditar);
            }
        });
    }
}
