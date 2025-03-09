package com.romakumari.recyclerprojectapp


import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.romakumari.recyclerprojectapp.databinding.FragmentSubTaskBinding
import com.romakumari.recyclerprojectapp.databinding.MainTaskItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubTaskFragment : Fragment(),SubtaskAdapter.subTaskInterface {
    lateinit var binding: FragmentSubTaskBinding
    lateinit var mainActivity: MainActivity
    lateinit var adapter: SubtaskAdapter
    var subtasklist= ArrayList<SubTaskEntity>()
    lateinit var notesDB: NotesDB
    var taskId = ""
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity=activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            taskId = it.getString("todoId", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSubTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesDB= NotesDB.getDatabase(mainActivity)
        adapter = SubtaskAdapter(subtasklist, this)
        binding.recyaclersubtask.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
         getsubtask()
        binding.recyaclersubtask.adapter = adapter
        binding.fab.setOnClickListener {
            val dialog = Dialog(requireContext())
            val dialogBinding = MainTaskItemBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.taskName.text.isNullOrEmpty()) {
                    dialogBinding.taskName.error = "Enter Todo name"
                } else {
                    notesDB.notesdao().insertSub(SubTaskEntity(subtaskName = dialogBinding.taskName.text.toString(), taskOwnerId = taskId.toInt()))
                    getsubtask()
                    dialog.dismiss()
                }
            }
            adapter.notifyDataSetChanged()
            dialog.show()
        }


    }

    override fun onAdd(subTaskEntity: SubTaskEntity, position: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = MainTaskItemBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialogBinding.taskName.setText(subtasklist[position].subtaskName)

        dialogBinding.btnAdd.setOnClickListener {
            if (dialogBinding.taskName.text.isNullOrEmpty()) {
                dialogBinding.taskName.error = "Enter Todo name"
            } else {
                notesDB.notesdao().updateSub(SubTaskEntity(subtaskId = subtasklist[position].subtaskId, subtaskName = dialogBinding.taskName.text.toString(), taskOwnerId = taskId.toInt()))
                getsubtask()
                dialog.dismiss()
            }
        }
        adapter.notifyDataSetChanged()
        dialog.show()
    }

    override fun delete(subTaskEntity: SubTaskEntity, position: Int) {
        notesDB.notesdao().deleteSub(subtasklist[position])
        getsubtask()
        adapter.notifyDataSetChanged()
    }

    override fun update(subTaskEntity: SubTaskEntity, position: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = MainTaskItemBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialogBinding.taskName.setText(subtasklist[position].subtaskName)

        dialogBinding.btnAdd.setText("Update")

        dialogBinding.btnAdd.setOnClickListener {
            if (dialogBinding.taskName.text.isNullOrEmpty()) {
                dialogBinding.taskName.error = "Enter Todo name"
            } else {
                notesDB.notesdao().updateSub(SubTaskEntity(subtaskId = subtasklist[position].subtaskId, subtaskName = dialogBinding.taskName.text.toString(), taskOwnerId = taskId.toInt()))
                getsubtask()
                dialog.dismiss()
            }
        }
        adapter.notifyDataSetChanged()
        dialog.show()
    }


    override fun onTaskChecked(subTaskEntity: SubTaskEntity, position: Int) {

        notesDB.notesdao().updateSub(SubTaskEntity(subtaskId = subtasklist[position].subtaskId, taskOwnerId = taskId.toInt(), subtaskName = subtasklist[position].subtaskName, isCompleted = subtasklist[position].isCompleted))
        getsubtask()
        adapter.notifyDataSetChanged()
    }
    fun getsubtask(){
        subtasklist.clear()
        subtasklist.addAll(notesDB.notesdao().getSub(taskId.toInt()))
        adapter.notifyDataSetChanged()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubTaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}