package com.example.monterapp.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.monterapp.R
import com.example.monterapp.core.base.BaseFragment
import com.example.monterapp.data.local.pref.Pref
import com.example.monterapp.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding,ProfileViewModel>() {

    private val pref:Pref by lazy {
        Pref(requireContext())
    }

    override fun inflateViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override fun setViewModel(): ProfileViewModel = ProfileViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        loadFullName()
    }

    private fun loadFullName() {
        val fullName = pref.getUserLoggedInFullName()
        binding.tvFullName.text = fullName
    }

    private fun initListener() {
        binding.btnExit.setOnClickListener {
            pref.setUserLoggedInStatus(isLoggedIn = false)
            findNavController().popBackStack()
            findNavController().navigate(R.id.authFragment)
        }
    }

}