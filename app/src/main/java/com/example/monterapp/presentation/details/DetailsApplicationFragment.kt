package com.example.monterapp.presentation.details

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.monterapp.R
import com.example.monterapp.app.App
import com.example.monterapp.core.base.BaseFragment
import com.example.monterapp.data.local.pref.Pref
import com.example.monterapp.data.models.Application
import com.example.monterapp.data.models.CompletedApplication
import com.example.monterapp.databinding.FragmentDetailsApplicationBinding
import com.example.monterapp.utils.KEYS
import kotlinx.coroutines.launch

class DetailsApplicationFragment :
    BaseFragment<FragmentDetailsApplicationBinding,DetailsApplicationViewModel>() {

    override fun inflateViewBinding(): FragmentDetailsApplicationBinding =
        FragmentDetailsApplicationBinding.inflate(layoutInflater)

    override fun setViewModel(): DetailsApplicationViewModel =
        DetailsApplicationViewModel()

    private var imageUrl:String? = null
    private val pref: Pref by lazy { Pref(requireContext()) }
    private var application: Application? = null
    private val completedApplicationDao = App.database.getCompletedApplicationDao()
    private val applicationDao = App.database.getApplicationDao()
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK && it.data != null){
                val uri: Uri? = it.data?.data
                imageUrl = uri.toString()
                binding.imgMonterPhoto.load(uri.toString())
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        application = arguments?.getSerializable(KEYS.KEY_BUNDLE_MAIN_TO_DETAIL) as Application
        getDataFromApplicationFragment()
        initListener(application!!)

    }

    private fun initListener(application: Application) {
        binding.imgMonterPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcher.launch(intent)
        }
        binding.btnSaveApplication.setOnClickListener {
            val commentText = binding.etMontersComment.text.toString().trim()
            val dateText = binding.etCompletedDate.text.toString().trim()
            val applicationStatusText = binding.spinnerApplicationStatus.selectedItem.toString()
            if (commentText.isEmpty()){
                binding.montersCommentContainer.error = getString(R.string.this_field_must_be_filled)
                return@setOnClickListener
            } else binding.montersCommentContainer.error = null
            if (dateText.isEmpty()){
                binding.completedDateContainer.error = getString(R.string.this_field_must_be_filled)
                return@setOnClickListener
            } else binding.completedDateContainer.error = null
            if (applicationStatusText == getString(R.string.done)){
                val comletedApplication = CompletedApplication(
                    address = binding.tvAddress.text.toString(),
                    regionName = binding.tvRegionName.text.toString(),
                    phoneNumber = (binding.tvPhoneNumber.text.toString()).toInt(),
                    personalAccount = (binding.tvPhoneNumber.text.toString()).toInt(),
                    date = binding.tvDate.text.toString(),
                    reason = binding.tvReason.text.toString(),
                    status = checkApplicationStatus(binding.spinnerApplicationStatus.selectedItem.toString()),
                    monterFullName = pref.getUserLoggedInFullName(),
                    monterComment = binding.etMontersComment.text.toString(),
                    completedDate = binding.etCompletedDate.text.toString(),
                    monterPhoto = imageUrl
                )
                viewModel.viewModelScope.launch {
                    completedApplicationDao.insertCompletedApplication(comletedApplication)
                    application.id?.let { it1 -> applicationDao.deleteApplicationById(it1) }
                    findNavController().navigateUp()
                    Toast.makeText(requireContext(), "Заявка сохранена", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Невыполненная заявка не может быть сохранена", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkApplicationStatus(statusText:String):Boolean{
        return statusText == getString(R.string.done)
    }

    private fun getDataFromApplicationFragment() {
        if (application != null){
            binding.tvReason.text = application!!.reason
            binding.tvAddress.text = application!!.address
            binding.tvRegionName.text = application!!.regionName
            binding.tvPersonalAccount.text = application!!.personalAccount.toString()
            binding.tvPhoneNumber.text = application!!.phoneNumber.toString()
            binding.tvDate.text = application!!.date
        }
    }

}