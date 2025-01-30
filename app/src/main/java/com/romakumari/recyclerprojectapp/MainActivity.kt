package com.romakumari.recyclerprojectapp

import android.app.Dialog
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toolbar.LayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.romakumari.recyclerprojectapp.databinding.ActivityMainBinding
import com.romakumari.recyclerprojectapp.databinding.CustomlayoutBinding
import java.util.ArrayList
import java.util.jar.Attributes.Name

class MainActivity : AppCompatActivity(),RecyclerAdapter.buttonclick {
        lateinit var binding: ActivityMainBinding
        var studentlist= arrayListOf<student>()
        lateinit var recyclerAdapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerAdapter = RecyclerAdapter(studentlist, this)
        binding.recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = recyclerAdapter
        studentlist.add(student("roma", 4,6))
        studentlist.add(student("shruti", 8,9))
        studentlist.add(student("ridham", 4,9))
        studentlist.add(student("sandhaya", 4,4))
        binding.fab.setOnClickListener {
            var dialog = Dialog(this)
            var dialogbinding = CustomlayoutBinding.inflate(layoutInflater)
            dialog.setContentView(dialogbinding.root)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialogbinding.btnupdate.setOnClickListener {
                if (dialogbinding.etname.text.toString().isNullOrEmpty()) {
                    dialogbinding.etname.error = "enter name"
                } else if (dialogbinding.etrollno.text.toString().isNullOrEmpty()) {
                    dialogbinding.etrollno.error = "enter rollno"
                }
                else if (dialogbinding.etclass.text.toString().isNullOrEmpty()) {
                    dialogbinding.etclass.error = "enter class"
                }
                else {
                    studentlist.add(student(name = dialogbinding.etname.text.toString(),
                        rollno = dialogbinding.etrollno.text.toString().toInt(),
                        myclass = dialogbinding.etclass.text.toString().toInt()
                        ))
                    recyclerAdapter.notifyDataSetChanged()
                    dialog.dismiss()

                }
            }
            dialog.show()
        }
    }


    override fun deleteclick(position: Int) {
         studentlist.removeAt(position)
         recyclerAdapter.notifyDataSetChanged()
    }

    override fun updateclick(position: Int) {
        var dialog = Dialog(this)
        var dialogbinding = CustomlayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogbinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogbinding.etname.setText(studentlist[position].name)
        dialogbinding.etrollno.setText(studentlist[position].rollno.toString())
        dialogbinding.etclass.setText(studentlist[position].myclass.toString())
        dialogbinding.btnupdate.setOnClickListener {
            if (dialogbinding.etname.text.toString().isNullOrEmpty()) {
                dialogbinding.etname.error = "enter name"
            } else if (dialogbinding.etrollno.text.toString().isNullOrEmpty()) {
                dialogbinding.etrollno.error = "enter rollno"
            }
            else if (dialogbinding.etclass.text.toString().isNullOrEmpty()) {
                dialogbinding.etclass.error = "enter class"
            }
            else {
                studentlist.set(position,student(name = dialogbinding.etname.text.toString(),
                    rollno = dialogbinding.etrollno.text.toString().toInt(),
                    myclass = dialogbinding.etclass.text.toString().toInt()
                ))
                recyclerAdapter.notifyDataSetChanged()
                dialog.dismiss()

            }
        }
        dialog.show()
    }
    }
