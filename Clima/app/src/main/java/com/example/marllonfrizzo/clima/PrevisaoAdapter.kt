package com.example.marllonfrizzo.clima

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PrevisaoAdapter : RecyclerView.Adapter<PrevisaoAdapter.PrevisaoViewHolder> {

    private var dadosClima: Array<String?>?
    private var itemClickListener: PrevisaoItemClickListener

    constructor(dadosClima: Array<String?>?, itemClickListener: PrevisaoItemClickListener) {
        this.dadosClima = dadosClima
        this.itemClickListener = itemClickListener;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PrevisaoViewHolder {
        val climaListItem = LayoutInflater.from(parent?.context)
                .inflate(R.layout.previsao_lista_item, parent, false)
        val previsaoViewHolder = PrevisaoViewHolder(climaListItem)

        return previsaoViewHolder
    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder?, position: Int) {
        val posicao = dadosClima?.get(position)

        holder?.tvDadosPrevisao?.text = posicao.toString()
    }

    override fun getItemCount(): Int {
        if (dadosClima == null) {
            return  0
        } else {
            return  dadosClima!!.size
        }
    }

    fun setDadosClima(dados: Array<String?>?) {
        dadosClima = dados
        notifyDataSetChanged()
    }

    fun getDadosClima() : Array<String?>? {
        return dadosClima
    }

    interface PrevisaoItemClickListener {
        fun onItemClick(index: Int)
    }

    inner class PrevisaoViewHolder : RecyclerView.ViewHolder {
        val tvDadosPrevisao: TextView

        constructor(itemView: View) : super(itemView) {
            tvDadosPrevisao = itemView.findViewById(R.id.tv_dados_previsao)

            itemView.setOnClickListener({
                itemClickListener.onItemClick(adapterPosition)
            })
        }
    }

}