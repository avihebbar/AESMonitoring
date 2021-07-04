package com.aapavani.aesmonitoring.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.ServiceRequest
import com.aapavani.aesmonitoring.databinding.SrListItemBinding
import java.util.*

class SRListAdapter(val context: Context,val list: ArrayList<ServiceRequest>?, val listener : SRItemClickedListener) :
    RecyclerView.Adapter<ServiceRequestHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceRequestHolder {
        val binding = DataBindingUtil.inflate<SrListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.sr_list_item,
            parent,
            false
        )
        return ServiceRequestHolder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: ServiceRequestHolder, position: Int) {
        val sr = list!![position]
        holder.bind(sr)
        holder.binding.root.setOnClickListener {
            listener.onItemClicked(list[position])
        }
    }
}

interface SRItemClickedListener {
    fun onItemClicked(sr: ServiceRequest)
}

class ServiceRequestHolder(val binding : SrListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(sr: ServiceRequest) {
        binding.sr = sr;
    }
}

