package com.example.marllonfrizzo.clima.dados

import android.net.Uri
import android.provider.BaseColumns

object ClimaContrato {

    val AUTORIDADE = "com.example.marllonfrizzo.clima.dados.ClimaContentProvider"
    val URI_BASE = Uri.parse("content://${AUTORIDADE}")
    val URI_CLIMA = "clima"

    internal object Clima : BaseColumns {
        val CONTENT_URI = URI_BASE.buildUpon()
                .appendPath(URI_CLIMA)
                .build()
        const val TABELA = "clima"
        const val COLUNA_DATA_HORA = "data_hora"
        const val COLUNA_CLIMA_ID = "clima_id"
        const val COLUNA_MIN_TEMP = "min_temp"
        const val COLUNA_MAX_TEMP = "max_temp"
        const val COLUNA_UMIDADE = "umidade"
        const val COLUNA_PRESSAO = "pressao"
        const val COLUNA_VEL_VENTO = "vel_vento"
        const val COLUNA_GRAUS = "graus"

        fun getSqlSelectHojeEmDiante(): String {
            val utcNow = System.currentTimeMillis()
            return "${COLUNA_DATA_HORA} >= $utcNow"
        }

        fun construirPrevisaoUri(dataHora: Long): Uri {
            return CONTENT_URI.buildUpon()
                    .appendPath(dataHora.toString())
                    .build()
        }
    }
}