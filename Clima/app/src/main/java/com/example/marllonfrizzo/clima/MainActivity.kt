package com.example.marllonfrizzo.clima

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.marllonfrizzo.clima.dados.ClimaPreferencias
import com.example.marllonfrizzo.clima.util.JsonUtils
import com.example.marllonfrizzo.clima.util.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PrevisaoAdapter.PrevisaoItemClickListener, LoaderManager.LoaderCallbacks<Array<String?>?> {

    var previsaoAdapter: PrevisaoAdapter? = null

    companion object {
        val DADOS_PREVISAO_LOADER = 1000
        val LOCALIZACAO_EXTRA = "LOCALIZACAO_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previsaoAdapter = PrevisaoAdapter(null, this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_clima.layoutManager = layoutManager
        rv_clima.adapter = previsaoAdapter

        supportLoaderManager.initLoader(DADOS_PREVISAO_LOADER, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Array<String?>?> {
        val loader = object : AsyncTaskLoader<Array<String?>?>(this) {

            var dadosPrevisao: Array<String?>? = null

            override fun onStartLoading() {
                if (dadosPrevisao != null) {
                    deliverResult(dadosPrevisao);
                } else {
                    exibirProgressBar()
                    forceLoad()
                }
            }

            override fun loadInBackground(): Array<String?>? {
                try {
                    val localizacao = ClimaPreferencias
                            .getLocalizacaoSalva(this@MainActivity)
                    val url = NetworkUtils.construirUrl(localizacao)

                    if (url != null) {
                        val resultado = NetworkUtils.obterRespostaDaUrlHttp(url)
                        val dadosClima = JsonUtils
                                .getSimplesStringsDeClimaDoJson(this@MainActivity,
                                        resultado!!)
                        return dadosClima
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                return null
            }

            override fun deliverResult(data: Array<String?>?) {
                super.deliverResult(data)
                dadosPrevisao = data
            }
        }
        return loader
    }

    override fun onLoadFinished(loader: Loader<Array<String?>?>?, dadosClima: Array<String?>?) {
        previsaoAdapter?.setDadosClima(dadosClima)
        if (dadosClima != null) {
            exibirResultado()
        } else {
            exibirMensagemErro()
        }
    }

    override fun onLoaderReset(loader: Loader<Array<String?>?>?) {
    }

    override fun onItemClick(index: Int) {
        val previsao = previsaoAdapter!!.getDadosClima()!!.get(index)

        val intentDetalhes = Intent(this, DetalhesActivity::class.java)
        intentDetalhes.putExtra(DetalhesActivity.DADOS_PREVISAO, previsao)

        startActivity(intentDetalhes)
    }

    fun abrirMapa() {
        val addressString = "Campo Mourão, Paraná, Brasil"
        val uriGeo = Uri.parse("geo:0,0?q=$addressString")

        val intentMapa = Intent(Intent.ACTION_VIEW)
        intentMapa.data = uriGeo

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intentMapa)
        }
    }

    fun exibirResultado() {
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
        if (item?.itemId === R.id.acao_atualizar) {
            supportLoaderManager.restartLoader(DADOS_PREVISAO_LOADER, null, this)
            return true
        }
        if (item?.itemId === R.id.acao_mapa) {
            abrirMapa()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
