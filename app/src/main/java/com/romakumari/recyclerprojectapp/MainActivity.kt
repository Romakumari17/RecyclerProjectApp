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
        var Noteslist= arrayListOf<NotesEntity>()
        lateinit var notesDB: NotesDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notesDB= NotesDB.getDatabase(this)
        recyclerAdapter = RecyclerAdapter(Noteslist, this)
        binding.recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recycler.adapter = recyclerAdapter
//        notesDB.notesdao().insertNotes(NotesEntity(title = "Roma", description = "one"))
//        notesDB.notesdao().insertNotes(NotesEntity(title = "shruti", description = "two" ))
//        notesDB.notesdao().insertNotes(NotesEntity(title = "ridham", description = "four"))
//        notesDB.notesdao().insertNotes(NotesEntity(title = "sandhaya", description = "five"))
        getNotes()
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
                    dialogbinding.etname.error = "enter title"
                } else if (dialogbinding.etrollno.text.toString().isNullOrEmpty()) {
                    dialogbinding.etrollno.error = "enter description"
                }
//                else if (dialogbinding.etclass.text.toString().isNullOrEmpty()) {
//                    dialogbinding.etclass.error = "enter class"
//                }
                else {
                    notesDB.notesdao()
                        .insertNotes(
                            NotesEntity(
                                title=   dialogbinding.etname.text.toString(),
                                description =  dialogbinding.etrollno.text.toString()
                            )
                        )
                    getNotes()
                    recyclerAdapter.notifyDataSetChanged()
                    dialog.dismiss()

                }
            }
            dialog.show()
        }
    }


    override fun deleteclick(position: Int) {
//         studentlist.removeAt(position)
        notesDB.notesdao().deleteNotes(Noteslist[position])
        getNotes()
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
        dialogbinding.etname.setText(Noteslist[position].title)
        dialogbinding.etrollno.setText(Noteslist[position].description.toString())
//        dialogbinding.etclass.setText(studentlist[position].myclass.toString())
        dialogbinding.btnupdate.setOnClickListener {
            if (dialogbinding.etname.text.toString().isNullOrEmpty()) {
                dialogbinding.etname.error = "enter title"
            } else if (dialogbinding.etrollno.text.toString().isNullOrEmpty()) {
                dialogbinding.etrollno.error = "enter description"
            }

            else {

                notesDB.notesdao().updateNotes(NotesEntity(id=Noteslist[position].id,title=dialogbinding.etname.text.toString(),
                    description=  dialogbinding.etrollno.text.toString()))
                getNotes()
                recyclerAdapter.notifyDataSetChanged()
                dialog.dismiss()

            }
        }
        dialog.show()
    }
    private fun getNotes(){
        Noteslist.clear()
        Noteslist.addAll(notesDB.notesdao().getNotes())
    }
    }
