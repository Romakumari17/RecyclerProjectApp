package com.romakumari.recyclerprojectapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class RecyclerAdapter(var list: ArrayList<NotesEntity>, var ButtonInterface:buttonclick) :RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(var view:View) :RecyclerView.ViewHolder(view){
        var Name=view.findViewById<TextView>(R.id.taskName)
        var update=view.findViewById<Button>(R.id.btnUpdate)
        var delete=view.findViewById<Button>(R.id.btnDelete)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        var view= LayoutInflater.from(parent.context)
            .inflate(R.layout.main_task_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
       holder.apply {
           Name.setText(list[position].taskdescription)
           itemView.setOnClickListener {
               ButtonInterface.onShowSubtask(list[position],position)
           }

           update.setOnClickListener {
               ButtonInterface.updateclick(list[position],position)
           }
           delete.setOnClickListener {
               ButtonInterface.deleteclick(list[position],position)
           }


    }
    }

    override fun getItemCount(): Int {
       return list.size
    }

    interface buttonclick{
        fun onShowSubtask(notesEntity: NotesEntity,position: Int)
        fun deleteclick(notesEntity: NotesEntity,position: Int)
        fun updateclick(notesEntity: NotesEntity,position: Int)
    }
}

data class student(var name:String = " " ,var rollno:String="" )