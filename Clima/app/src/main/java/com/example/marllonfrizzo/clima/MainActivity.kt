package com.example.marllonfrizzo.clima

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.marllonfrizzo.clima.dados.ClimaPreferencias
import com.example.marllonfrizzo.clima.util.JsonUtils
import com.example.marllonfrizzo.clima.util.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    var previsaoAdapter: PrevisaoAdapter? = null
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        previsaoAdapter = PrevisaoAdapter(null)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_clima.layoutManager = layoutManager
        rv_clima.adapter = previsaoAdapter

        /* val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ListaVerdeAdapter(this, numeros, this)

        rv_lista_numerica.layoutManager = layoutManager
        rv_lista_numerica.adapter = adapter */
        carregarDadosClima()
    }

    fun exibirResultados() {
        rv_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro() {
        tv_mensagem_erro.visibility = View.VISIBLE
        rv_clima.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar() {
        pb_aguarde.visibility = View.VISIBLE
        rv_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.acao_atualizar) {
            //dados_clima.text = ""
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
                val retorno = JsonUtils.getSimplesStringsDeClimaDoJson(context, result)
                previsaoAdapter?.setDadosDoClima(retorno)

                exibirResultados()
            } else {
                exibirMensagemErro()
            }
        }
    }

}
