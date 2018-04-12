package br.com.mfrizzo.metaspessoais

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateFormat
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MetasPessoaisAdapter.ItemClickListener {

    data class meta(val titulo: String, val descricao: String, val dataLimite: Date?, var concluido: String)
    var listaMetas: ArrayList<meta> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preencher()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_adicionar) {
            if (et_data.text.toString() == "" || et_titulo.text.toString() == "" || et_descricao.text.toString() == "") {
                Toast.makeText(this, "Digite os dados!", Toast.LENGTH_SHORT).show();
            } else {
                addItem()
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun preencher() {

        if (!listaMetas.isEmpty()) {
            bt_excluir.isEnabled = true
            bt_editar.isEnabled = true
            bt_concluida.isEnabled = true

        } else {
            bt_editar.isEnabled = false
            bt_excluir.isEnabled = false
            bt_concluida.isEnabled = false
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MetasPessoaisAdapter(this, listaMetas, this)

        rv_lista_tarefas.layoutManager = layoutManager
        rv_lista_tarefas.adapter = adapter

    }

    /* Limpa os campos e coloca o cursor no primeiro campo */
    fun limpar() {
        et_titulo.text.clear()
        et_descricao.text.clear()
        et_data.text.clear()
        et_titulo.requestFocus()
    }

    /* Retorna um objeto Date com a String da data informada pelo usuário */
    private fun dataHandler(dataLimite: String): Date? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        var data: Date = Date()

        try {
            data = dateFormat.parse(dataLimite)
            return data
        } catch (e: Exception) {
            Toast.makeText(this, "Deu ruim", Toast.LENGTH_SHORT).show();
            return null
        }

    }

    /* Formata a mensagem de estado de acordo com os dias atrasados/restantes */
    fun estadoHandler(diffDays: Long, concluido: Boolean): String {
        val dataAtual = Calendar.getInstance().time
        if (diffDays > 0 && concluido == false) {
            return "Não concluída, faltam $diffDays para terminar."
        } else if (diffDays < 0 && concluido == false) {
            return "Não concluída, finalizou a ${diffDays*-1} dias."
        } else if (diffDays > 0 && concluido == true) {
            return "Concluída em ${DateFormat.format("dd/MM/yyyy", dataAtual)}, faltando $diffDays dias."
        } else {
            return "Concluída em ${DateFormat.format("dd/MM/yyyy", dataAtual)}, com um atraso de ${diffDays*-1} dias."
        }
    }

    /* Realiza o cálculo de diferenças entre a data limite e a data atual */
    fun diff(data: Date?): Long {
        val dataAtual = Calendar.getInstance().time
        val diff = data!!.time - dataAtual.time

        return diff / (24*60*60*1000)
    }

    /* Adiciona uma meta na lista */
    fun addItem() {
        val titulo = et_titulo.text
        val desc = et_descricao.text
        val dataLimite = et_data.text
        val data = dataHandler(dataLimite.toString())

        val diffDays = diff(data)
        listaMetas.add(meta(titulo.toString(), desc.toString(), data, estadoHandler(diffDays, false)))

        preencher()
        limpar()
    }

    /* Recupera as informações da Meta selecionada */
    fun recuperaDados(position: Int) {
        et_titulo.append(listaMetas.get(position).titulo)
        et_descricao.append(listaMetas.get(position).descricao)
        et_data.append(DateFormat.format("dd/MM/yyyy", listaMetas.get(position).dataLimite))
    }

    /* Edita as informações de uma determinada Meta */
    fun editar(position: Int) {
        val dataLimite = et_data.text
        val data = dataHandler(dataLimite.toString())

        val diffDays = diff(data)

        recuperaDados(position)

        listaMetas[position] = meta(et_titulo.text.toString(), et_descricao.text.toString(), data, estadoHandler(diffDays, false))
        limpar()
        preencher()
    }

    /* Exclui uma determinada Meta */
    fun excluir(position: Int) {
        limpar()

        listaMetas.removeAt(position)
        Toast.makeText(this, "Meta Apagada!", Toast.LENGTH_SHORT).show();
        preencher()
    }

    /* Define uma Meta como concluída */
    fun metaConcluida(position: Int) {
        val data = dataHandler(et_data.text.toString())
        val diffDays = diff(data)

        listaMetas.get(position).concluido = estadoHandler(diffDays, true)
        preencher()
        limpar()
    }

    override fun onItemClick(position: Int) {
        recuperaDados(position)

        bt_editar.setOnClickListener(){
            editar(position)
        }

        bt_excluir.setOnClickListener() {
            excluir(position)
        }

        bt_concluida.setOnClickListener() {
            metaConcluida(position)
        }
    }

    override fun onLongItemClick(position: Int) {

    }

}
