package com.atividade.agendaalimentacao;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.atividade.agendaalimentacao.Repositorio.AgendaRepositorio;
import com.atividade.agendaalimentacao.model.Alimento;

import java.util.ArrayList;

public class AdicionarAlimentoDialogFragment extends DialogFragment {

    int DiaSelecionado;
    String refeicao;
    ArrayList<String> alimentos;


    public AdicionarAlimentoDialogFragment(int dia, String refeicao, ArrayList<String> alimentos){
        this.DiaSelecionado = dia;
        this.refeicao = refeicao;
        this.alimentos = alimentos;
    }

    public String[] ConverterArray(ArrayList<String> alimentos){
        String[] stringArray = new String[alimentos.size()];
        stringArray = alimentos.toArray(stringArray);

        return stringArray;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adicionaralimento, container, false);
        // Add your fragment content setup code here
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = view.findViewById(R.id.spinner);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, alimentos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });


        Button fragmentAdicinarAlimento_button_salvar = view.findViewById(R.id.fragmentAdicinarAlimento_button_salvar);
        ImageButton btnFechar = view.findViewById(R.id.btnFecharModal);

        Spinner spinnerAlimento = view.findViewById(R.id.spinner);
        EditText editTextQtdAlimento = view.findViewById(R.id.fragmentAdicinarAlimento_EditText_Alimento);

        fragmentAdicinarAlimento_button_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityEditarRefeicaoDia.class);
                intent.putExtra("DiaSelecionado", DiaSelecionado);
                intent.putExtra("Refeicao", refeicao);
                intent.putExtra("AlimentoSelecionado", spinnerAlimento.getSelectedItem().toString());
                intent.putExtra("QtdAlimentoSelecionado", editTextQtdAlimento.getText().toString());

                startActivity(intent);
            }
        });

        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

