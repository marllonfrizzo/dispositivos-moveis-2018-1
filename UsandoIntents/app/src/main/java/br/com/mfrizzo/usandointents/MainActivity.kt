package br.com.mfrizzo.usandointents

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_enviar.setOnClickListener {
            val activityDestino = SegundaActivity::class.java
            val context = this

            val intentSegundaActivity = Intent(context, activityDestino)
            val mensagem = et_mensagem.text.toString()
            intentSegundaActivity.putExtra(Intent.EXTRA_TEXT, mensagem)

            startActivity(intentSegundaActivity)
        }

        btn_abrir_site.setOnClickListener {
            val acao = Intent.ACTION_VIEW
            val dado = Uri.parse("http://www.mfrizzo.com.br")
            val intentImplicito = Intent(acao, dado)

            if (intentImplicito.resolveActivity(packageManager) != null) {
                startActivity(intentImplicito)
            }

        }

        btn_abrir_mapa.setOnClickListener {
            val endereco = "Av. Irmãos Pereira, 670 - Centro, Campo Mourão – PR"
            val builder = Uri.Builder()
                    .scheme("geo")
                    .path("0,0")
                    .appendQueryParameter("q", endereco)

            val uriEndereco = builder.build()
            val intent = Intent(Intent.ACTION_VIEW, uriEndereco)

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

}