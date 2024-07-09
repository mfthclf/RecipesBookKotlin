package com.mfthc.yemekkitabikotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mfthc.yemekkitabikotlin.databinding.RecyclerRowBinding
import com.mfthc.yemekkitabikotlin.model.Tarif
import com.mfthc.yemekkitabikotlin.view.ListeFragmentDirections

class TarifAdapter(val tarifListesi: List<Tarif>) :
    RecyclerView.Adapter<TarifAdapter.TarifHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifHolder {
        val binding : RecyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TarifHolder(binding)
    }

    override fun getItemCount(): Int {
        return tarifListesi.size
    }

    override fun onBindViewHolder(holder: TarifHolder, position: Int) {
        holder.binding.recyclerViewTextView.text = tarifListesi[position].isim
        holder.itemView.setOnClickListener {
            val action = ListeFragmentDirections.actionListeFragment2ToTarifFragment(
                bilgi = "eski",
                id = tarifListesi[position].id
            )
            Navigation.findNavController(it).navigate(action)
        }

    }

    class TarifHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}