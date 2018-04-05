package com.example.marllonfrizzo.clima

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.marllonfrizzo.clima.util.NetworkUtils
import com.example.marllonfrizzo.clima.dados.ClimaPreferencias
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carregarDadosClima()
    }

    fun exibirResultados() {
        dados_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro() {
        tv_mensagem_erro.visibility = View.VISIBLE
        dados_clima.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar() {
        pb_aguarde.visibility = View.VISIBLE
        dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.acao_atualizar) {
            dados_clima.text = ""
            carregarDadosClima()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun carregarDadosClima() {
        val local = ClimaPreferencias.getLocalizacaoSalva(this)
        val url = NetworkUtils.construirUrl(local)
        buscarClimaTask().execute(url)
    }

    inner class buscarClimaTask() : AsyncTask<URL, Void, String>() {

        override fun onPreExecute() {
            exibirProgressBar()
        }

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
            if (result != null) {
                dados_clima.text = ""

                val json = JSONObject(result)
                val list = json.getJSONArray("list")

                for (i in 0 until list.length()) {
                    var weatherDescription = ""
                    val result = list.getJSONObject(i)
                    val dt = result.getString("dt")
                    val dataHoraMilissegundos: Long = dt.toLong()
                    val dataHora = Date(dataHoraMilissegundos)
                    //val dataHoraFormatada = DateFormat.format("dd/MM/yyyy HH:mm", dataHora)
                    dados_clima.append("Data: $dataHora\n")

                    val main = result.getJSONObject("main")
                    val temp = main.getString("temp")
                    val umidade = main.getString("humidity")
                    dados_clima.append("Temperatura: $temp°C\n")
                    dados_clima.append("Umidade: $umidade\n")

                    dados_clima.append("Descrição:")
                    val weather = result.getJSONArray("weather")
                    for (j in 0 until weather.length()) {
                        val x = weather.getJSONObject(j)
                        weatherDescription = x.getString("description")
                        dados_clima.append(" $weatherDescription")
                    }
                    dados_clima.append("\n\n\n")
                }

                exibirResultados()
            } else {
                exibirMensagemErro()
            }
        }
    }

}
