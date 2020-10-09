package com.demo.android.todoappcleanarchitecture.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.demo.android.todoappcleanarchitecture.R
import com.demo.android.todoappcleanarchitecture.data.models.ToDoData
import com.demo.android.todoappcleanarchitecture.data.viewmodel.ToDoViewModel
import com.demo.android.todoappcleanarchitecture.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private val mTodoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewmodel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //Set Menu
        setHasOptionsMenu(true)

        view.priorities_spinner.onItemSelectedListener = mSharedViewmodel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_menu){
            inserDataToDb()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun inserDataToDb() {
        val mTitle = title_et.text.toString()
        val mPriority = priorities_spinner.selectedItem.toString()
        val mDescription = description_et.text.toString()

        val validation = mSharedViewmodel.verifyDataFromUser(mTitle, mDescription)
        if (validation){
            //Insert Data to Database
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewmodel.parsePriority(mPriority),
                mDescription
            )
            mTodoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            //Navigation Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }

    }

}