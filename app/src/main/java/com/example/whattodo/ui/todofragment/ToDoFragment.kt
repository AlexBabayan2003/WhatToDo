package com.example.whattodo.ui.todofragment

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.whattodo.R
import com.example.whattodo.data.entity.ToDo
import com.example.whattodo.databinding.ToDoFragmentBinding
import com.example.whattodo.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ToDoFragment : Fragment() {
    private var binding: ToDoFragmentBinding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ToDoFragmentBinding.inflate(layoutInflater, container, false)
            .also { binding = it }.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFromDao()

        val fadeInOutLightIcon = AnimatorInflater.loadAnimator(context, R.anim.fade_in_out)

        val fadeout = AnimatorInflater.loadAnimator(context, R.anim.fade_out)

        binding?.apply {

            val backgroundAnim: AnimationDrawable = parentLayout.background as AnimationDrawable
            backgroundAnim.setEnterFadeDuration(1500)
            backgroundAnim.setExitFadeDuration(4000)
            backgroundAnim.start()

            fadeout.setTarget(binding?.clickAnim)
            fadeout.start()
            clickAnim.repeatCount = 1

            btnAdd.setOnClickListener {
                fadeInOutLightIcon.setTarget(lightIcon)
                fadeInOutLightIcon.start()
                getToDo()
            }

            activityListBtn.setOnClickListener {
                val action = ToDoFragmentDirections.actionToDoFragmentToFavoriteToDoFragment()
                findNavController().navigate(action)
            }

            favoriteEmpty.setOnClickListener {
                lifecycleScope.launch { onFavoriteClick() }
            }
        }
    }

    @SuppressLint("ResourceType")
    private suspend fun onFavoriteClick() {
        val fadeInOutNotificationAdded = AnimatorInflater.loadAnimator(context, R.anim.fade_in_out)
        val fadeInOutNotificationRemoved =
            AnimatorInflater.loadAnimator(context, R.anim.fade_in_out)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getToDo.collect { updateToDo ->
                if (updateToDo?.favorite == false) {
                    updateToDo.favorite = true
                    viewModel.changeActivity(updateToDo)
                    withContext(Dispatchers.Main) {
                        binding?.run {
                            favorite.visibility = View.VISIBLE
                            fadeInOutNotificationAdded.setTarget(notificationAdded)
                            fadeInOutNotificationAdded.start()
                        }
                    }
                    cancel()
                } else {
                    if (updateToDo != null) {
                        updateToDo.favorite = false
                        viewModel.changeActivity(updateToDo)
                        withContext(Dispatchers.Main) {
                            binding?.run {
                                favorite.visibility = View.INVISIBLE
                                fadeInOutNotificationRemoved.setTarget(notificationRemoved)
                                fadeInOutNotificationRemoved.start()
                            }
                        }
                        cancel()
                    }

                }
            }
        }
    }

    private fun getFromDao() = lifecycleScope.launch {
        viewModel.getToDo.collect { toDo ->
            withContext(Dispatchers.Main) {
                if (toDo != null) {
                    if (toDo.favorite) {
                        binding?.favorite?.visibility = View.VISIBLE
                    } else binding?.favorite?.visibility = View.INVISIBLE
                    setText(toDo)
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun getToDo() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel.getActivity.collect { toDo ->
            withContext(Dispatchers.Main) {
                setText(toDo)
            }
        }
    }

    private fun setText(toDo: ToDo?) = binding?.run {
        val activity = "activity: "
        val type = "type: "
        val participants = "participants: "
        val price = "price: "

        activityTv.text = activity.append(toDo?.activity) ?: ""

        typeTv.text = if (toDo?.type.isNullOrEmpty()) {
            ""
        } else type.append(toDo?.type)

        participantsTv.text = if (toDo?.participants?.toString().isNullOrEmpty()) {
            ""
        } else participants.append(toDo?.participants.toString())

        binding?.priceTv?.text = when {
            toDo?.price?.toString().isNullOrEmpty() -> ""
            toDo!!.price <= 0.3 -> price.append("Low")
            toDo.price in 0.3..0.6 -> price.append("Medium")
            else -> price.append("High")
        }

        val linkText = if (toDo?.link.isNullOrEmpty()) {
            binding?.linkIcon?.visibility = View.INVISIBLE
            ""
        } else {
            binding?.linkIcon?.visibility = View.VISIBLE
            "here"
        }

        linkTv.text = linkText

        linkTv.setOnClickListener {
            if (!toDo?.link.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(toDo?.link))
                startActivity(intent)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}

private fun String.append(str: String?): String? {
    return this + str
}
