package br.com.mfrizzo.metaspessoais

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MetasPessoaisAdapter : RecyclerView.Adapter<MetasPessoaisAdapter.ViewHolder> {

    val lista: List<MainActivity.meta>
    val contexto: Context
    val itemClickListener: ItemClickListener

    constructor(contexto: Context, lista: List<MainActivity.meta>, itemClickListener: ItemClickListener) {
        this.contexto = contexto
        this.lista = lista
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItem = LayoutInflater.from(contexto)
                .inflate(R.layout.list_items, parent, false)
        val viewHolder = ViewHolder(listItem)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista.get(position)

        holder.textViewHead?.text = item.titulo
        holder.textViewDesc?.text = item.descricao
        holder.textViewDate?.text = DateFormat.format("dd/MM/yyyy", item.dataLimite)
        holder.textViewConcluida?.text = item.concluido
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
        fun onLongItemClick(position: Int)
    }

    inner class ViewHolder : RecyclerView.ViewHolder, View.OnClickListener, View.OnLongClickListener {
        val textViewHead: TextView?
        val textViewDesc: TextView?
        val textViewDate: TextView?
        val textViewConcluida: TextView?

        constructor(itemView: View?) : super(itemView) {
            textViewHead = itemView?.findViewById(R.id.textViewHead)
            textViewDesc = itemView?.findViewById(R.id.textViewDesc)
            textViewDate = itemView?.findViewById(R.id.textViewDate)
            textViewConcluida = itemView?.findViewById(R.id.tv_estado)

            itemView?.setOnClickListener(this)
            itemView?.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val posicaoClicada = adapterPosition
            itemClickListener.onItemClick(posicaoClicada)
        }

        override fun onLongClick(v: View?): Boolean {
            val posicaoClicada = adapterPosition
            itemClickListener.onLongItemClick(posicaoClicada)
            return true
        }

    }

}