package com.example.marllonfrizzo.clima

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val dadosClimaFicticios = listOf("Hoje - Céu limpo - 17°C / 15°C",
            "Amanhã - Nublado - 19°C / 15°C",
            "Quinta - Chuvoso - 30°C / 11°C",
            "Sexta - Chuva com raios - 21°C / 9°C",
            "Sábado - Chuva com raios - 16°C / 7°C",
            "Domingo - Chuvoso - 16°C / 8°C",
            "Segunda - Parcialmente nublado - 15°C / 10°C",
            "Ter, Mai 24 - Vai curintia - 16°C / 18°C",
            "Qua, Mai 25 - Nublado - 19°C / 15°C",
            "Qui, Mai 26 - Tempestade - 30°C / 11°C",
            "Sex, Mai 27 - Furacão - 21°C / 9°C",
            "Sáb, Mai 28 - Meteóro - 16°C / 7°C",
            "Dom, Mai 29 - Apocalipse - 16°C / 8°C",
            "Seg, Mai 30 - Pós-apocalipse - 15°C / 10°C")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exJson()

        /* for (clima in dadosClimaFicticios) {
            dados_clima.append("$clima \n\n\n")
        } */
    }

    fun exJson() {
        var dadosJson =
                """
            {
              "temperatura": {
                "minima": 11.34,
                "maxima": 19.01
              },
              "clima": {
                "id": 801,
                "condicao": "Nuvens",
                "descricao": "poucas nuvens"
              },
              "pressao": 1023.51,
              "umidade": 87
            }
                """


        val objJson = JSONObject(dadosJson)
        val clima = objJson.getJSONObject("clima")
        val condicao = clima.getString("condicao")
        Log.d("exJson", "A condicao e $condicao")
    }
}
