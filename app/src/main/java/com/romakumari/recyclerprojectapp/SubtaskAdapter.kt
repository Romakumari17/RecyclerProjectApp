package com.romakumari.recyclerprojectapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox

class SubtaskAdapter (val list :ArrayList<SubTaskEntity>, val subinterface:subTaskInterface) : RecyclerView.Adapter<SubtaskAdapter.ViewHolder>() {
        class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
            val name = view.findViewById<TextView>(R.id.subtaskName)
            val delete = view.findViewById<ImageView>(R.id.imageDelete)
            val update = view.findViewById<ImageView>(R.id.imageUpdate)
            val checkbox = view.findViewById<MaterialCheckBox>(R.id.cbtask)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.subtask_layout, parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val subTask = list[position]

            holder.name.text = subTask.subtaskName
            holder.checkbox.isChecked = subTask.isCompleted


            if (list[position].isCompleted) {
                holder.name.setTextColor(Color.GRAY)

            } else {
                holder.name.setTextColor(Color.BLACK)

            }
            holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
                list[position].isCompleted = isChecked
                if (isChecked) {
                    holder.name.setTextColor(Color.GRAY)
                    subinterface.onTaskChecked(list[position], position)
                } else {
                    holder.name.setTextColor(Color.BLACK)
                    subinterface.onTaskChecked(list[position], position)
                }

            }

            holder.itemView.setOnClickListener {
                subinterface.onAdd(subTask, position)
            }



            holder.delete.setOnClickListener {
                subinterface.delete(subTask, position)
            }
            holder.update.setOnClickListener {
                subinterface.update(subTask, position)
            }



        }


        interface subTaskInterface{
            fun onAdd(subTaskEntity: SubTaskEntity,position: Int)
            fun delete(subTaskEntity: SubTaskEntity,position: Int)
            fun update(subTaskEntity: SubTaskEntity,position: Int)

            fun onTaskChecked(subTaskEntity: SubTaskEntity, position: Int)
        }


    }
