package com.example.userside

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userside.databinding.DayWiseItemBinding

class DayAdapter(var dayList : ArrayList<DayModel>, var dayClickInterface: DayClickInterface): RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    class ViewHolder(var binding: DayWiseItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(dayModel: DayModel) {
            binding.tvDay.setText(dayModel.day)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view = DayWiseItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return dayList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dayList[position])
        holder.itemView.setOnClickListener {
            dayClickInterface.onDayClick(dayList[position])
        }
    }
}