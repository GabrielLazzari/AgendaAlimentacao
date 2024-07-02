package com.atividade.agendaalimentacao.Repositorio;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;

import com.atividade.agendaalimentacao.model.Alimento;
import com.atividade.agendaalimentacao.model.Dia;
import com.atividade.agendaalimentacao.model.Refeicao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class AgendaRepositorio extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agendaAlimentos.db";
    private static final int DATABASE_VERSION = 1;

    AlimentoRepositorio alimentoRepositorio = null;

    public AgendaRepositorio(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        new CriarBase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Refeicao");
        onCreate(sqLiteDatabase);
    }

    public Dia RetornarDiaSemana(int dia){
        String query = String.format("SELECT * FROM RefeicaoDia WHERE DiaSemana = %d", dia);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Dia diaModel = new Dia();
        diaModel.DiaSemana = dia;
        diaModel.ListaRefeicoes = new ArrayList<Refeicao>();

        String nomeRefeicao = "";

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nomeRefeicaoBanco = cursor.getString(cursor.getColumnIndex("Refeicao"));
                @SuppressLint("Range") String descricaoRefeicaoBanco = cursor.getString(cursor.getColumnIndex("Descricao"));
                if (nomeRefeicao != nomeRefeicaoBanco){
                    diaModel.ListaRefeicoes.add(new Refeicao());
                    nomeRefeicao = nomeRefeicaoBanco;
                }

                diaModel.ListaRefeicoes.get(diaModel.ListaRefeicoes.size()-1).DiaSemana = dia;
                diaModel.ListaRefeicoes.get(diaModel.ListaRefeicoes.size()-1).Refeicao = nomeRefeicaoBanco;
                diaModel.ListaRefeicoes.get(diaModel.ListaRefeicoes.size()-1).Descricao = descricaoRefeicaoBanco;
                diaModel.ListaRefeicoes.get(diaModel.ListaRefeicoes.size()-1).listaAlimentoModels = this.RetornarAlimentoRefeicao(dia, nomeRefeicaoBanco, db);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return diaModel;
    }

    public void AtualizarDia(Dia dia){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("AlimentoRefeicao", "RefeicaoDia = ?", new String[]{String.valueOf(dia.DiaSemana)});
        db.delete("RefeicaoDia", "DiaSemana = ?", new String[]{String.valueOf(dia.DiaSemana)});

        for (int d=0; d<dia.ListaRefeicoes.size(); d++){
            Refeicao refeicao = dia.ListaRefeicoes.get(d);

            //for (int a=0; a<refeicao.listaAlimentoModels.size(); a++){
            //    Alimento alimento = refeicao.listaAlimentoModels.get(a);
            //    db.delete("AlimentoRefeicao", "RefeicaoDia = ? AND Refeicao = ? AND NomeAlimento = ?", new String[]{String.valueOf(refeicao.DiaSemana), refeicao.Refeicao, String.valueOf(alimento.Nome)});
            //}

            //db.delete("RefeicaoDia", "DiaSemana = ? AND Refeicao = ?", new String[]{String.valueOf(refeicao.DiaSemana), refeicao.Refeicao});

            this.InserirDia(refeicao, db);
        }

        db.close();
    }

    private void InserirDia(Refeicao refeicao, SQLiteDatabase db){
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("DiaSemana", refeicao.DiaSemana);
        values.put("Refeicao", refeicao.Refeicao);
        values.put("Descricao", refeicao.Descricao);

        db.insert("RefeicaoDia", null, values);

        for (int a=0; a<refeicao.listaAlimentoModels.size(); a++){
            Alimento alimento = refeicao.listaAlimentoModels.get(a);
            this.InserirAlimentoRefeicao(refeicao.DiaSemana, refeicao.Refeicao, alimento.Nome, db);
        }

        //db.close();
    }

    private void InserirAlimentoRefeicao(int dia, String refeicao, String nomeAlimento, SQLiteDatabase db){
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("RefeicaoDia", dia);
        values.put("Refeicao", refeicao);
        values.put("NomeAlimento", nomeAlimento);

        db.insert("AlimentoRefeicao", null, values);

        //db.close();
    }

    public ArrayList<Alimento> RetornarAlimentoRefeicao(int dia, String refeicao, SQLiteDatabase db){
        ArrayList<Alimento> alimentos = new ArrayList<Alimento>();

        String query = String.format("SELECT * FROM AlimentoRefeicao WHERE RefeicaoDia = ? AND Refeicao = ?;");

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(dia), refeicao});

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("NomeAlimento"));

                Alimento alimento = new Alimento(nome, "", "");
                alimento.ListaSugestoes = RetornarSugestoes(alimento, db);
                alimentos.add(alimento);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return alimentos;
    }

    public Alimento RetornarAlimentoCompleto(Alimento alimento, SQLiteDatabase db){

        String query = String.format("SELECT * FROM Alimento WHERE Nome = ?;");
        Cursor cursor = db.rawQuery(query, new String[]{alimento.Nome});

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String calorias = cursor.getString(cursor.getColumnIndex("Calorias"));
                @SuppressLint("Range") String tipo = cursor.getString(cursor.getColumnIndex("Tipo"));

                alimento.Calorias = calorias;
                alimento.Tipo = tipo;
            } while (cursor.moveToNext());
        }

        return alimento;
    }

    public ArrayList<Alimento> RetornarSugestoes(Alimento alimento, SQLiteDatabase db){
        ArrayList<Alimento> alimentosSugeridos = new ArrayList<Alimento>();

        alimento = RetornarAlimentoCompleto(alimento, db);

        String query = String.format("SELECT * FROM Alimento WHERE Nome <> ? AND (Tipo = ? OR Calorias = ?);");
        Cursor cursor = db.rawQuery(query, new String[]{alimento.Nome, alimento.Tipo, alimento.Calorias});

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
                @SuppressLint("Range") String calorias = cursor.getString(cursor.getColumnIndex("Calorias"));
                @SuppressLint("Range") String tipo = cursor.getString(cursor.getColumnIndex("Tipo"));

                Alimento alimentoSugerido = new Alimento(nome, calorias, tipo);
                alimentosSugeridos.add(alimentoSugerido);

            } while (cursor.moveToNext());
        }

        return alimentosSugeridos;
    }

    public void InserirAlimentoSugerido(int dia, String refeicao, String nomeAlimento){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("RefeicaoDia", dia);
        values.put("Refeicao", refeicao);
        values.put("NomeAlimento", nomeAlimento);

        db.insert("AlimentoRefeicao", null, values);

        db.close();
    }

    public void DesvincularAlimentoRemovidoSugestao(int dia, String refeicao, String nomeAlimento){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("AlimentoRefeicao", "RefeicaoDia = ? AND Refeicao = ? AND NomeAlimento = ?", new String[]{String.valueOf(dia), String.valueOf(refeicao), nomeAlimento});
        db.close();
    }

    public void CriarPrimeirosRegistros(){
        Calendar cal = Calendar.getInstance();
        int diaSelecionado = cal.get(Calendar.DAY_OF_WEEK);

        Alimento alimentoModel1 = new Alimento("Café", "", "");
        Alimento alimentoModel2 = new Alimento("Pão", "", "");
        Alimento alimentoModel3 = new Alimento("Feijão", "", "");
        Alimento alimentoModel4 = new Alimento("Frango", "", "");
        Alimento alimentoModel5 = new Alimento("Massa", "", "");

        Refeicao refeicao1 = new Refeicao(diaSelecionado, "Café", new ArrayList<Alimento>(Arrays.asList(
                alimentoModel1, alimentoModel2
        )));

        Refeicao refeicao2 = new Refeicao(diaSelecionado, "Almoço", new ArrayList<Alimento>(Arrays.asList(
                alimentoModel3
        )));

        List<Alimento> listaSugestoes = new ArrayList<Alimento>(Arrays.asList(
                alimentoModel1, alimentoModel3
        ));

        alimentoModel4.setListaSugestoes(listaSugestoes);

        Refeicao refeicao3 = new Refeicao(diaSelecionado, "Jantar", new ArrayList<Alimento>(Arrays.asList(
                alimentoModel4, alimentoModel5
        )));

        Dia dia = new Dia(diaSelecionado, new ArrayList<Refeicao>(Arrays.asList(
                refeicao1, refeicao2, refeicao3
        )));

        this.AtualizarDia(dia);
    }


    public void LimparRefeicaoBuffer(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("RefeicaoBuffer", "", new String[]{});
        db.close();
    }

    public Dia RetornarRefeicaoBuffer(int dia){
        String query = String.format("SELECT * FROM RefeicaoBuffer");

        Dia diaModel = new Dia();
        diaModel.DiaSemana = dia;
        diaModel.ListaRefeicoes = new ArrayList<Refeicao>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String nomeRefeicao = "";

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nomeRefeicaoBanco = cursor.getString(cursor.getColumnIndex("Refeicao"));
                @SuppressLint("Range") String nomeAlimentoBanco = cursor.getString(cursor.getColumnIndex("NomeAlimento"));
                if (!nomeRefeicao.equals(nomeRefeicaoBanco)){
                    diaModel.ListaRefeicoes.add(new Refeicao());
                    nomeRefeicao = nomeRefeicaoBanco;
                    diaModel.ListaRefeicoes.get(diaModel.ListaRefeicoes.size()-1).listaAlimentoModels = new ArrayList<Alimento>();
                    diaModel.ListaRefeicoes.get(diaModel.ListaRefeicoes.size()-1).DiaSemana = dia;
                    diaModel.ListaRefeicoes.get(diaModel.ListaRefeicoes.size()-1).Refeicao = nomeRefeicaoBanco;
                }
                diaModel.ListaRefeicoes.get(diaModel.ListaRefeicoes.size()-1).listaAlimentoModels.add(new Alimento(nomeAlimentoBanco, "", ""));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return diaModel;
    }

    public void InserirEditarPrimeiraVezRefeicaoBuffer(Dia diaModel){
        this.LimparRefeicaoBuffer();
        SQLiteDatabase db = this.getWritableDatabase();

        for (int r=0; r<diaModel.ListaRefeicoes.size(); r++){
            Refeicao refeicao = diaModel.ListaRefeicoes.get(r);
            for (int a=0; a<refeicao.listaAlimentoModels.size(); a++){
                Alimento alimento = refeicao.listaAlimentoModels.get(a);
                ContentValues values = new ContentValues();
                values.put("Refeicao", refeicao.Refeicao);
                values.put("NomeAlimento", alimento.Nome);

                db.insert("RefeicaoBuffer", null, values);
            }
        }

        db.close();
    }

    public void InserirAlimentoRefeicaoBuffer(String refeicao, String nomeAlimento){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Refeicao", refeicao);
        values.put("NomeAlimento", nomeAlimento);

        db.insert("RefeicaoBuffer", null, values);

        db.close();
    }

    public void RemoverAlimentoRefeicaoBuffer(String refeicao, String nomeAlimento){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("RefeicaoBuffer", "Refeicao = ? AND NomeAlimento = ?", new String[]{String.valueOf(refeicao), nomeAlimento});
        db.close();
    }
}
