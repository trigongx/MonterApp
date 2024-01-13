package com.example.monterapp.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.monterapp.R
import com.example.monterapp.app.App
import com.example.monterapp.core.base.BaseFragment
import com.example.monterapp.data.local.pref.Pref
import com.example.monterapp.databinding.FragmentAuthBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthFragment : BaseFragment<FragmentAuthBinding,AuthViewModel>() {

    private val pref: Pref by lazy {
        Pref(requireContext())
    }

    override fun inflateViewBinding(): FragmentAuthBinding =
        FragmentAuthBinding.inflate(layoutInflater)

    override fun setViewModel(): AuthViewModel = AuthViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            val enteredLogin = binding.etLogin.text.toString()
            val enteredPassword = binding.etPassword.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                val isCredentialValid = checkCredentials(enteredLogin,enteredPassword)

                if (isCredentialValid){
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.navigation_profile)
                } else {
                    Toast.makeText(requireContext(), "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun checkCredentials(login: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val monter = App.database.getMonterDao().getMonterByLoginAndPassword(login, password)
            val isCredentialValid = monter != null

            if(isCredentialValid){
                pref.setUserLoggedInStatus(true)
                if (monter != null) {
                    monter.fullName?.let { pref.setUserLoggedInFullName(it) }
                    monter.regionName?.let { pref.setUserLoggedInRegionName(it)}
                }

            }
            Log.d("ololo", "checkCredentials: $isCredentialValid")
            isCredentialValid
        }
    }

}