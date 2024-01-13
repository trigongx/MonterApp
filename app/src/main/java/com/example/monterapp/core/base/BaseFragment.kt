package com.example.monterapp.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB:ViewBinding,VM:ViewModel>:Fragment() {
    private var _binding:VB? = null
    protected val binding get() = _binding!!
    private var _viewModel:VM? = null
    protected val viewModel get() = _viewModel!!
    protected abstract fun inflateViewBinding():VB
    protected abstract fun setViewModel():VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateViewBinding()
        _viewModel = setViewModel()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}