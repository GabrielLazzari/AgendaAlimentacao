package com.atividade.agendaalimentacao.Repositorio;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atividade.agendaalimentacao.model.Alimento;
import com.atividade.agendaalimentacao.model.Dia;
import com.atividade.agendaalimentacao.model.Refeicao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlimentoRepositorio extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "agendaAlimentos.db";
    private static final int DATABASE_VERSION = 1;

    public AlimentoRepositorio(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        new CriarBase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Alimento");
        onCreate(sqLiteDatabase);
    }

    public void InserirAlimento(String nome, String calorias, String tipo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Nome", nome);
        values.put("Calorias", calorias);
        values.put("Tipo", tipo);

        db.insert("Alimento", null, values);

        db.close();
    }

    public void AtualizarAlimento(Alimento alimento, Alimento alimentoAntes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Nome", alimento.Nome);
        values.put("Calorias", alimento.Calorias);
        values.put("Tipo", alimento.Tipo);

        db.update("Alimento", values, "Nome = ? AND Calorias = ? AND Tipo = ?",
                new String[]{String.valueOf(alimentoAntes.getNome()), alimentoAntes.getCalorias(), alimentoAntes.getTipo()});

        db.close();
    }

    public void DeletarAlimento(Alimento alimento){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Alimento", "Nome = ? AND Calorias = ? AND Tipo = ?", new String[]{String.valueOf(alimento.Nome), alimento.Calorias, alimento.Tipo});
        db.close();
    }

    public void RetornarAlimento(String nome){

    }

    public List<Alimento> RetornarTodosAlimentos(){
        String query = String.format("SELECT * FROM Alimento");

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Alimento> listaAlimentos = new ArrayList<Alimento>();

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
                @SuppressLint("Range") String calorias = cursor.getString(cursor.getColumnIndex("Calorias"));
                @SuppressLint("Range") String tipo = cursor.getString(cursor.getColumnIndex("Tipo"));

                listaAlimentos.add(new Alimento(nome, calorias, tipo));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaAlimentos;
    }

    public ArrayList<String> RetornarListaNomesAlimentos(){
        String query = String.format("SELECT Nome FROM Alimento");

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> listaNomeAlimentos = new ArrayList<String>();

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));

                listaNomeAlimentos.add(nome);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaNomeAlimentos;
    }

    public boolean CriarPrimeirosRegistrosAlimento(){
        String query = String.format("SELECT * FROM Alimento LIMIT 1");

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        boolean temRegistros = false;

        if (cursor.moveToFirst()){
            do {
                temRegistros = true;

            } while (cursor.moveToNext());
        }

        if (!temRegistros){
            this.InserirAlimento("Arroz", "130", "Grão");
            this.InserirAlimento("Feijão", "347", "Grão");
            this.InserirAlimento("Frango", "239", "Carne");
            this.InserirAlimento("Porco", "239", "Carne");
            this.InserirAlimento("Massa", "131", "Carboidrato");
            this.InserirAlimento("Alimento1", "261", "Proteína");
            this.InserirAlimento("Alimento2", "110", "Proteína");
            this.InserirAlimento("Alimento3", "90", "Carboidrato");
            this.InserirAlimento("Alimento4", "105", "Carboidrato");
            this.InserirAlimento("Café", "0", "Grão");
            this.InserirAlimento("Leite", "0", "");
            this.InserirAlimento("Pão", "265", "Carboidrato");
        }

        cursor.close();
        db.close();

        return temRegistros;
    }
}
