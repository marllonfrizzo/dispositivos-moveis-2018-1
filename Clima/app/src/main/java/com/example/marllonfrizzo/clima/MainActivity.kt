package com.example.marllonfrizzo.clima

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.marllonfrizzo.clima.util.NetworkUtils
import com.example.marllonfrizzo.clima.dados.ClimaPreferencias
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carregarDadosClima()
    }

    fun carregarDadosClima() {
        val local = ClimaPreferencias.getLocalizacaoSalva(this)
        val url = NetworkUtils.construirUrl(local)
        buscarClimaTask().execute(url)
    }

    inner class buscarClimaTask() : AsyncTask<URL, Void, String>() {

        override fun doInBackground(vararg p0: URL?): String? {
            try {
                val url = p0[0]
                val result = NetworkUtils.obterRespostaDaUrlHttp(url!!)

                return result
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            if (result != null && !result.isEmpty()) {
                dados_clima.text = result
            }
        }
    }

}
