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

    constructor(context: Context, list: List<Int>) {
        this.lista = list
        this.context = context
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

    inner class NumeroViewHolder : RecyclerView.ViewHolder {
        val tvItemNumero: TextView?

        constructor(itemView: View?) : super(itemView) {
            tvItemNumero = itemView?.findViewById<TextView>(R.id.tv_item_numero)
        }

    }
}