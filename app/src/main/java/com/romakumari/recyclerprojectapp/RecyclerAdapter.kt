package com.romakumari.recyclerprojectapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class RecyclerAdapter(var list: ArrayList<NotesEntity>, var ButtonInterface:buttonclick) :RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(var view:View) :RecyclerView.ViewHolder(view){
        var Name=view.findViewById<TextView>(R.id.tvname)
        var rollno=view.findViewById<TextView>(R.id.tvrollno)
//        var myclass=view.findViewById<TextView>(R.id.tvclass)
        var delete=view.findViewById<Button>(R.id.btndelete)
        var update=view.findViewById<Button>(R.id.btnupdate)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        var view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layoutitem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
       holder.apply {
           Name.setText(list[position].title)
           rollno.setText(list[position].description.toString())
//           myclass.setText(list[position].myclass.toString())
          delete.setOnClickListener {
             ButtonInterface.deleteclick(position)
         }
           update.setOnClickListener {
               ButtonInterface.updateclick(position)
           }


    }
    }

    override fun getItemCount(): Int {
       return list.size
    }

    interface buttonclick{
        fun deleteclick(position: Int)
        fun updateclick(position: Int)
    }
}

data class student(var name:String = " " ,var rollno:String="" )