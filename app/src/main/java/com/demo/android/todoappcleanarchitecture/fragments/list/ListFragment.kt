package com.demo.android.todoappcleanarchitecture.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.android.todoappcleanarchitecture.R
import com.demo.android.todoappcleanarchitecture.data.models.ToDoData
import com.demo.android.todoappcleanarchitecture.data.viewmodel.ToDoViewModel
import com.demo.android.todoappcleanarchitecture.databinding.FragmentListBinding
import com.demo.android.todoappcleanarchitecture.fragments.SharedViewModel
import com.demo.android.todoappcleanarchitecture.fragments.list.adapter.ListAdapter
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // IData binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        //Setup RecyclerView
        setupRecyclerview()

        //Observe LiveData
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

//        view.listLayout.setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
//        }

        //Set Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //Swipe to delete
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                //Delete Item
                mToDoViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                //Restore Deleted Item
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun restoreDeletedData(view: View, deletedItem: ToDoData, position: Int){
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo"){
            mToDoViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete_all -> confirmRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    //Show AlertDialog to Confirm Removal of All Items from Database Table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Everything!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove Everything?")
        builder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}