package br.grupointegrado.tads.listadeespera

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var clientesAdapter: ClientesAdapter? = null
    private var database: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_clientes.layoutManager = LinearLayoutManager(this)

        val bdHelper = ListaEsperaBdHelper(this)
        database = bdHelper.writableDatabase

        // Inserir alguns clientes de exemplo
        BdUtil.inserirDadosFicticios(database)

        val cursor = getTodosClientes()

        clientesAdapter = ClientesAdapter(cursor)
        rv_clientes.adapter = clientesAdapter

        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = viewHolder.itemView.tag as Long
                removerCliente(id)
                clientesAdapter!!.atualizarCursor(getTodosClientes())
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rv_clientes)
    }

    private fun getTodosClientes() : Cursor {
        return database!!.query(
                ListaEsperaContrato.Clientes.TABELA,
                null,
                null,
                null,
                null,
                null,
                ListaEsperaContrato.Clientes.COLUNA_DATA_HORA
        )
    }

    fun adicionar(view: View) {
        if (ed_nome_cliente.text.isEmpty()) {
            return
        }

        val nome = ed_nome_cliente.text.toString()
        var tamanhoGrupo = 1

        try {
            tamanhoGrupo = ed_tamanho_grupo.text.toString().toInt()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        adicionarNovoCliente(nome, tamanhoGrupo)
        clientesAdapter!!.atualizarCursor(getTodosClientes())

        ed_nome_cliente.text.clear()
        ed_tamanho_grupo.text.clear()
        ed_tamanho_grupo.clearFocus()
    }

    private fun adicionarNovoCliente(nome: String, tamanhoGrupo: Int): Long {
        val cliente = ContentValues()
        cliente.put(ListaEsperaContrato.Clientes.COLUNA_NOME, nome)
        cliente.put(ListaEsperaContrato.Clientes.COLUNA_TAMANHO_GRUPO, tamanhoGrupo)

        return database!!.insert(ListaEsperaContrato.Clientes.TABELA, null, cliente)
    }

    private fun removerCliente(id : Long) : Boolean {
        val removidos = database!!.delete(ListaEsperaContrato.Clientes.TABELA,
                "${BaseColumns._ID} = ?",
                arrayOf(id.toString()))

        return removidos > 0
    }

}
