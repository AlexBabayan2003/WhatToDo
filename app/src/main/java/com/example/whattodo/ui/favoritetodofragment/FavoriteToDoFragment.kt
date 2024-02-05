package com.example.whattodo.ui.favoritetodofragment

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.R
import com.example.whattodo.data.entity.ToDo
import com.example.whattodo.databinding.FavoriteToDoFragmentBinding
import com.example.whattodo.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class FavoriteToDoFragment : Fragment() {

    private var binding: FavoriteToDoFragmentBinding? = null
    private val toDoAdapter = ToDoAdapter()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return FavoriteToDoFragmentBinding.inflate(layoutInflater, container, false)
            .also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            favoriteTodoRv.adapter = toDoAdapter
            updateRv()

            val backgroundAnim: AnimationDrawable = parentLayout.background as AnimationDrawable
            backgroundAnim.setEnterFadeDuration(1500)
            backgroundAnim.setExitFadeDuration(4000)
            backgroundAnim.start()

            btnBack.setOnClickListener {
                val action =
                    FavoriteToDoFragmentDirections.actionFavoriteToDoFragmentToToDoFragment()
                findNavController().navigate(action)
            }


        }
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false


            @SuppressLint("ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val swipedToDo = toDoAdapter.currentList[position]

                lifecycleScope.launch(Dispatchers.IO) {
                    swipedToDo.favorite = false
                    viewModel.changeActivity(swipedToDo)
                }

                binding?.root?.let {
                    Snackbar.make(it, "activity deleted", Snackbar.LENGTH_SHORT)
                        .setAction("Undo",
                            View.OnClickListener {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    swipedToDo.favorite = true
                                    viewModel.changeActivity(swipedToDo)
                                }
                            }).show()
                }
            }
        })
        updateRv()
        itemTouchHelper.attachToRecyclerView(binding?.favoriteTodoRv)

    }

    fun updateRv() {
        lifecycleScope.launch {
            viewModel.getFavoriteToDo.collect { updatedToDo ->
                toDoAdapter.submitList(updatedToDo)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
