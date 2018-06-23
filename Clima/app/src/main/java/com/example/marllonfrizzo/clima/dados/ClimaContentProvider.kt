package com.example.marllonfrizzo.clima.dados

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import java.lang.UnsupportedOperationException

class ClimaContentProvider : ContentProvider() {

    companion object {
        var CODE_CLIMA = 100
        var CODE_CLIMA_POR_DATA = 101
        val uriMatcher: UriMatcher

        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(ClimaContrato.AUTORIDADE, ClimaContrato.URI_CLIMA, CODE_CLIMA)
            uriMatcher.addURI(ClimaContrato.AUTORIDADE, "${ClimaContrato.URI_CLIMA}/#", CODE_CLIMA_POR_DATA)
        }
    }

    var bdHelper : ClimaBdHelper? = null

    override fun onCreate(): Boolean {
        bdHelper = ClimaBdHelper(this.context)
        return true
    }

    override fun shutdown() {
        bdHelper?.close()
    }

    override fun insert(p0: Uri?, p1: ContentValues?): Uri {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor {

        val bd = bdHelper!!.readableDatabase
        val cursor: Cursor

        when (uriMatcher.match(uri)) {
            CODE_CLIMA -> {
                cursor = bd.query(
                        ClimaContrato.Clima.TABELA,
                        null,
                        null,
                        null,
                        null,
                        null,
                        sortOrder
                )
            }

            CODE_CLIMA_POR_DATA -> {
                val dataClima = uri.lastPathSegment
                val where = "${ClimaContrato.Clima.COLUNA_DATA_HORA} = ?"
                val whereArgs = arrayOf(dataClima.toString())
                cursor = bd.query(
                        ClimaContrato.Clima.TABELA,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        sortOrder
                )
            }

            else -> throw UnsupportedOperationException("URI não localizada!")
        }
        cursor.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun bulkInsert(uri: Uri?, values: Array<out ContentValues>): Int {
        val bd = bdHelper!!.writableDatabase

        when (uriMatcher.match(uri)) {
            CODE_CLIMA -> {
                var registrosInseridos = 0
                bd.beginTransaction()
                try {
                    for (value in values) {
                        val _id = bd.insert(ClimaContrato.Clima.TABELA, null, value)
                        if (_id != -1L) {
                            registrosInseridos++
                        }
                    }
                    bd.setTransactionSuccessful()
                } finally {
                    bd.endTransaction()
                }
                if (registrosInseridos > 0) {
                    context.contentResolver.notifyChange(uri, null)
                }
                return registrosInseridos
            }
        }




        return super.bulkInsert(uri, values)
    }

    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(p0: Uri?, p1: String?, p2: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(p0: Uri?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}