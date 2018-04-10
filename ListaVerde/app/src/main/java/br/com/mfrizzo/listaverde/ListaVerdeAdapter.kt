package br.com.mfrizzo.listaverde

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListaVerdeAdapter : RecyclerView.Adapter<ListaVerdeAdapter.NumeroViewHolder> {

    val lista: List<Int>
    val context: Context
    var viewHolderCount = 0
    val itemClickListener: ItemClickListener

    constructor(context: Context, list: List<Int>, itemClickListener: ItemClickListener) {
        this.lista = list
        this.context = context
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumeroViewHolder {
        val numeroListItem = LayoutInflater.from(context)
                .inflate(R.layout.numero_list_item, parent, false)
        val numeroViewHolder = NumeroViewHolder(numeroListItem)
        val tvViewHolderIndex = numeroListItem.findViewById<TextView>(R.id.tv_viewholder_index)
        tvViewHolderIndex.text = "ViewHolder $viewHolderCount"
        val cor = ColorUtils.getViewHolderBackgroundColor(context, viewHolderCount)
        numeroListItem.setBackgroundColor(cor)
        viewHolderCount++
        return numeroViewHolder
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: NumeroViewHolder, position: Int) {
        val numero = lista.get(position)

        holder.tvItemNumero?.text = numero.toString()
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
        fun onLongItemClick(position: Int)
    }

    inner class NumeroViewHolder : RecyclerView.ViewHolder, View.OnClickListener, View.OnLongClickListener {
        val tvItemNumero: TextView?

        constructor(itemView: View?) : super(itemView) {
            tvItemNumero = itemView?.findViewById<TextView>(R.id.tv_item_numero)
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