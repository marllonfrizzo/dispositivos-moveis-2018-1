package br.com.mfrizzo.ciclovida

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("Create","Create")

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("CONTEUDO_TEXTVIEW")) {
                val conteudoTextView = savedInstanceState.getString("CONTEUDO_TEXTVIEW")
                tv_mensagem_log.text = conteudoTextView
            }
        }

        imprimir("onCreate")
    }

    fun imprimir(mensagem: String) {
        tv_mensagem_log.append("$mensagem\n")
        Log.e("Mensagem", mensagem)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        imprimir("onSaveInstanceState")
        val conteudoTextView = tv_mensagem_log.text.toString()
        outState?.putString("CONTEUDO_TEXTVIEW", conteudoTextView)
    }

    override fun onStart() {
        super.onStart()
        //Log.e("Start","Start")
        imprimir("Start")
    }

    override fun onRestart() {
        super.onRestart()
        //Log.e("Restart","Restart")
        imprimir("Restart")
    }

    override fun onResume() {
        super.onResume()
        //Log.e("Resume","Resume")
        imprimir("Resume")
    }

    override fun onStop() {
        super.onStop()
        //Log.e("Stop","Stop")
        imprimir("Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        //Log.e("Destroy","Destroy")
        imprimir("Destroy")
    }
}
