package br.com.mfrizzo.buscadorgithub

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import br.grupointegrado.tads.buscadorgithub.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_buscar) {
            //Log.d("MainActivity", "Clicou!")
            //Toast.makeText(this, "Clicou", Toast.LENGTH_LONG).show()
            buscarNoGithub()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun buscarNoGithub() {
        var url = NetworkUtils.construirUrl(et_busca.text.toString())
        tv_url.text = url.toString()
        if (url != null) {
            //tv_github_resultado.text = NetworkUtils.obterRespostaDaUrlHttp(url)
            val task = GithubBuscaTask()
            task.execute(url)
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

    inner class GithubBuscaTask : AsyncTask<URL, Void, String>() {

        override fun onPreExecute() {
            exibirProgressBar();
        }

        override fun doInBackground(vararg p0: URL): String? {
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
                tv_github_resultado.text = result
                exibirResultado()
            } else {
                exibirMensagemErro()
            }

        }
    }
}
