package br.com.mfrizzo.buscadorgithub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {

    companion object {
        val URL_BUSCA = "URL_BUSCA"
        val BUSCA_GITHUB_LOADER_ID = 1000
    }

    var cacheResultado : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_busca.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cacheResultado = null
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nada
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nada
            }
        })

        supportLoaderManager.initLoader(BUSCA_GITHUB_LOADER_ID, null, this)

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("CONTEUDO_TEXTVIEW")) {
                val conteudoTextView = savedInstanceState.getString("CONTEUDO_TEXTVIEW")
                tv_url.text = conteudoTextView
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val conteudoTextView = tv_url.text.toString()
        outState?.putString("CONTEUDO_TEXTVIEW", conteudoTextView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_buscar) {
            buscarNoGithub()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun buscarNoGithub() {
        val url = NetworkUtils.construirUrl(et_busca.text.toString())
        tv_url.text = url.toString()
        if (url != null) {
            val parametros = Bundle()
            parametros.putString(URL_BUSCA, url.toString())
            supportLoaderManager.restartLoader(BUSCA_GITHUB_LOADER_ID, parametros, this)

        }
    }

    fun exibirResultado() {
        tv_github_resultado.visibility = View.VISIBLE;
        tv_mensagem_erro.visibility = View.INVISIBLE;
        pb_aguarde.visibility = View.INVISIBLE;
    }

    fun exibirMensagemErro() {
        tv_mensagem_erro.visibility = View.VISIBLE;
        tv_github_resultado.visibility = View.INVISIBLE;
        pb_aguarde.visibility = View.INVISIBLE;
    }

    fun exibirProgressBar() {
        pb_aguarde.visibility = View.VISIBLE;
        tv_github_resultado.visibility = View.INVISIBLE;
        tv_mensagem_erro.visibility = View.INVISIBLE;
    }

    override fun onCreateLoader(id: Int, parametros: Bundle?): Loader<String> {
        val loader = object : AsyncTaskLoader<String>(this) {

            override fun onStartLoading() {
                super.onStartLoading()
                if (parametros == null) {
                    return
                }
                exibirProgressBar()
                if (cacheResultado != null) {
                    deliverResult(cacheResultado)
                } else {
                    forceLoad()
                }
            }

            override fun loadInBackground(): String? {
                try {
                    var urlBusca = parametros!!.getString(URL_BUSCA)
                    val url = URL(urlBusca)
                    val result = NetworkUtils.obterRespostaDaUrlHttp(url)

                    return result
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                return null
            }

            override fun deliverResult(resultado: String?) {
                super.deliverResult(resultado)
                cacheResultado = resultado
            }

        }

        return loader
    }

    override fun onLoadFinished(loader: Loader<String>?, result: String?) {

        if (result != null) {
            tv_github_resultado.text = ""

            val json = JSONObject(result)
            val items = json.getJSONArray("items")

            for (i in 0 until items.length()) {
                val repository = items.getJSONObject(i)
                val name = repository.getString("name")

                tv_github_resultado.append("$name \n\n\n")
            }

            exibirResultado()
        } else {
            exibirMensagemErro()
        }
    }

    override fun onLoaderReset(loader: Loader<String>?) {
        // Nada por enquanto
    }

}
