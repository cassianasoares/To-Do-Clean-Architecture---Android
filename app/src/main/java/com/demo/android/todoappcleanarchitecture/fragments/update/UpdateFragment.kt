package com.demo.android.todoappcleanarchitecture.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.demo.android.todoappcleanarchitecture.R
import com.demo.android.todoappcleanarchitecture.data.models.Priority
import com.demo.android.todoappcleanarchitecture.data.models.ToDoData
import com.demo.android.todoappcleanarchitecture.data.viewmodel.ToDoViewModel
import com.demo.android.todoappcleanarchitecture.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        //Set New Menu
        setHasOptionsMenu(true)

        view.current_title_et.setText(args.currentItem.title)
        view.current_description_et.setText(args.currentItem.description)
        view.current_priorities_spinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.current_priorities_spinner.onItemSelectedListener = mSharedViewModel.listener

        // Inflate the layout for this fragment
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = current_title_et.text.toString()
        val description = current_description_et.text.toString()
        val getPriority = current_priorities_spinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if(validation){
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            //Navigation Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    //Show AlertDialog to Confirm item Removal
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(), "Successfully Removed: ${args.currentItem.title}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'?")
        builder.create().show()
    }

}