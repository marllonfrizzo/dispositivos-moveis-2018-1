package br.com.mfrizzo.metaspessoais

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*
import java.util.*


class MainActivity : AppCompatActivity(), MetasPessoaisAdapter.ItemClickListener {

    data class meta(val titulo: String, val descricao: String, val dataLimite: String)
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
        } else {
            bt_editar.isEnabled = false
            bt_excluir.isEnabled = false
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MetasPessoaisAdapter(this, listaMetas, this)

        rv_lista_tarefas.layoutManager = layoutManager
        rv_lista_tarefas.adapter = adapter

    }

    fun limpar() {
        et_titulo.text.clear()
        et_descricao.text.clear()
        et_data.text.clear()
        et_titulo.requestFocus()
    }

    fun addItem() {
        val titulo = et_titulo.text
        val desc = et_descricao.text
        val dataLimite = et_data.text

        listaMetas.add(meta(titulo.toString(), desc.toString(), dataLimite.toString()))
        preencher()
        limpar()

    }

    fun recuperaDados(position: Int) {
        et_titulo.append(listaMetas.get(position).titulo)
        et_descricao.append(listaMetas.get(position).descricao)
        et_data.append(listaMetas.get(position).dataLimite)
    }

    fun editar(position: Int) {
        limpar()

        listaMetas[position] = meta(et_titulo.text.toString(), et_descricao.text.toString(), et_data.text.toString())
        preencher()
    }

    fun excluir(position: Int) {
        limpar()

        listaMetas.removeAt(position)
        Toast.makeText(this, "Meta Apagada!", Toast.LENGTH_SHORT).show();
        preencher()
    }

    override fun onItemClick(position: Int) {
        val metaClicada = listaMetas.get(position)

        recuperaDados(position)

        bt_editar.setOnClickListener(){
            editar(position)
        }

        bt_excluir.setOnClickListener() {
            excluir(position)
        }
    }


    override fun onLongItemClick(position: Int) {
        val numeroClicado = listaMetas.get(position)
        //exibirMensagem("Clique longo em $numeroClicado")
    }

}
