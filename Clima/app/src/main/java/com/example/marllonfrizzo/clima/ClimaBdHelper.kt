package com.example.marllonfrizzo.clima

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class ClimaBdHelper : SQLiteOpenHelper {

    companion object {
        val BD_NOME = "clima.db"
        val BD_VERSAO = 1
    }

    constructor(context: Context) : super(context, BD_NOME, null, BD_VERSAO)

    override fun onCreate(bd: SQLiteDatabase) {
        val CREATE_TABLE_CLIMA = """
            CREATE TABLE ${ClimaContrato.Clima.TABELA} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ClimaContrato.Clima.COLUNA_DATA_HORA} INTEGER NOT NULL,
                ${ClimaContrato.Clima.COLUNA_CLIMA_ID} INTEGER NOT NULL,
                ${ClimaContrato.Clima.COLUNA_MIN_TEMP} REAL NOT NULL,
                ${ClimaContrato.Clima.COLUNA_MAX_TEMP} REAL NOT NULL,
                ${ClimaContrato.Clima.COLUNA_UMIDADE} REAL NOT NULL,
                ${ClimaContrato.Clima.COLUNA_PRESSAO} REAL NOT NULL,
                ${ClimaContrato.Clima.COLUNA_VEL_VENTO} REAL NOT NULL,
                ${ClimaContrato.Clima.COLUNA_GRAUS} REAL NOT NULL,
                UNIQUE ${ClimaContrato.Clima.COLUNA_DATA_HORA} ON CONFLICT REPLACE
            );
        """
        bd.execSQL(CREATE_TABLE_CLIMA)
    }

    override fun onUpgrade(bd: SQLiteDatabase, versaoAnterior: Int, novaVersao: Int) {
        bd.execSQL("DROP TABLE IF EXISTS ${ClimaContrato.Clima.TABELA};")
        onCreate(bd)
    }

}