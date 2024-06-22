package com.atividade.agendaalimentacao.Repositorio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CriarBase {

    public CriarBase(SQLiteDatabase sqLiteDatabase){

        final String SQL_CREATE_METADADOS_TABLE = "CREATE TABLE Metadados (" +
                "TemRegistros INTEGER" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_METADADOS_TABLE);

        final String SQL_CREATE_REFEICAODIA_TABLE = "CREATE TABLE RefeicaoDia (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DiaSemana INTEGER," +
                "Refeicao TEXT, " +
                "Descricao TEXT " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_REFEICAODIA_TABLE);

        final String SQL_CREATE_ALIMENTO_TABLE = "CREATE TABLE Alimento (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT," +
                "Calorias TEXT, " +
                "Tipo TEXT " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_ALIMENTO_TABLE);

        final String SQL_CREATE_ALIMENTOREFEICAO_TABLE = "CREATE TABLE AlimentoRefeicao (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "RefeicaoDia INTEGER," +
                "Refeicao TEXT," +
                "NomeAlimento TEXT " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_ALIMENTOREFEICAO_TABLE);
    }
}
