package com.romakumari.recyclerprojectapp

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.romakumari.recyclerprojectapp.databinding.FragmentSubTaskBinding
import com.romakumari.recyclerprojectapp.databinding.FragmentTaskBinding
import com.romakumari.recyclerprojectapp.databinding.MainTaskItemBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() ,RecyclerAdapter.buttonclick{
    // TODO: Rename and change types of parameters
    lateinit var binding : FragmentTaskBinding
    lateinit var mainActivity: MainActivity
    lateinit var notesDB: NotesDB
    var list = ArrayList<NotesEntity>()
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter : RecyclerAdapter
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerAdapter(list, this)
        binding.taskrecycler.adapter = adapter
        linearLayoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        binding.taskrecycler.layoutManager = linearLayoutManager
        notesDB = NotesDB.getDatabase(mainActivity)
        getTodo()
        binding.fab.setOnClickListener {
            val dialog = Dialog(mainActivity)
            val dialogBinding = MainTaskItemBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )

            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.taskName.text.isNullOrEmpty()) {
                    dialogBinding.taskName.error = "Enter Todo name"
                } else {
                    notesDB.notesdao()
                        .insertNotes(NotesEntity(taskdescription = dialogBinding.taskName.text.toString(),))
                    getTodo()
                    dialog.dismiss()
                }
            }
            adapter.notifyDataSetChanged()
            dialog.show()
        }
    }

    fun getTodo(){
        list.clear()
        list.addAll(notesDB.notesdao().getAlltask())
        adapter.notifyDataSetChanged()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onShowSubtask(notesEntity: NotesEntity, position: Int) {
        var bundle = Bundle()
        bundle.putString("todoId" , notesEntity.TaskOwnerId.toString())
        findNavController().navigate(R.id.subTaskFragment,bundle )
    }

    override fun deleteclick(notesEntity: NotesEntity, position: Int) {
        notesDB.notesdao().deleteNotes(list[position])
        getTodo()
        adapter.notifyDataSetChanged()
    }

    override fun updateclick(notesEntity: NotesEntity, position: Int) {
        val dialog = Dialog(mainActivity)
        val dialogBinding = MainTaskItemBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.taskName.setText(list[position].taskdescription)
        dialogBinding.btnAdd.setText("Update")
        dialog.window?.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        dialogBinding.btnAdd.setOnClickListener {
            if (dialogBinding.taskName.text.isNullOrEmpty()) {
                dialogBinding.taskName.error = "Enter Todo name"
            } else {
                notesDB.notesdao().updateNotes(
                   (NotesEntity(TaskOwnerId = list[position].TaskOwnerId, taskdescription = dialogBinding.taskName.text.toString())))
                getTodo()
                dialog.dismiss()
            }
        }
        adapter.notifyDataSetChanged()
        dialog.show()
    }
    }
