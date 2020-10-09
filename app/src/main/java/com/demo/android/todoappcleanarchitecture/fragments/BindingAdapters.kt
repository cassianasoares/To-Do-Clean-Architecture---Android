package com.demo.android.todoappcleanarchitecture.fragments

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.demo.android.todoappcleanarchitecture.R
import com.demo.android.todoappcleanarchitecture.data.models.Priority
import com.demo.android.todoappcleanarchitecture.data.models.ToDoData
import com.demo.android.todoappcleanarchitecture.fragments.list.ListFragmentDirections
import com.demo.android.todoappcleanarchitecture.fragments.settings.Mode
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_settings.*

class BindingAdapters {

    companion object{

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean){
            view.setOnClickListener {
                if (navigate){
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }
//
//        @BindingAdapter("android:parseModeToInt")
//        @JvmStatic
//        fun parseModeToInt(view: RadioGroup, mode: Mode){
//            return when(mode){
//                Mode.LIGHT -> view.check(R.id.light)
//                Mode.DARK -> view.check(R.id.dark)
//                Mode.SYSTEM -> view.check(R.id.system)
//                else -> view.check(R.id.system)
//            }
//        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority){
            return when(priority){
                Priority.HIGH -> {view.setSelection(0)}
                Priority.MEDIUM -> {view.setSelection(1)}
                Priority.LOW -> {view.setSelection(2)}
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                    Priority.HIGH -> {cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.red))}
                    Priority.MEDIUM -> {cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.yellow))}
                    Priority.LOW -> {cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.green))}
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData){
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }

}