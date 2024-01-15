package com.example.monterapp.presentation.details

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
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
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DetailsApplicationFragment :
    BaseFragment<FragmentDetailsApplicationBinding,DetailsApplicationViewModel>() {

    override fun inflateViewBinding(): FragmentDetailsApplicationBinding =
        FragmentDetailsApplicationBinding.inflate(layoutInflater)

    override fun setViewModel(): DetailsApplicationViewModel =
        DetailsApplicationViewModel()

    private var imageUrl: String? = null
    private var formattedDate:String? = null
    private var calendar: Calendar = Calendar.getInstance()

    private val pref: Pref by lazy { Pref(requireContext()) }
    private var application: Application? = null
    private val completedApplicationDao = App.database.getCompletedApplicationDao()
    private val applicationDao = App.database.getApplicationDao()
    private var launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                binding.imgMonterPhoto.load(uri)
                imageUrl = uri.toString()
                Log.d("ololo", "$imageUrl")
            } else if (result.resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(
                    requireContext(),
                    "Не удалось сохранить изображение",
                    Toast.LENGTH_SHORT
                ).show()
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
            ImagePicker.Companion.with(requireActivity())
                .crop()
                .maxResultSize(1080, 1080)
                .provider(ImageProvider.BOTH)
                .createIntentFromDialog { launcher.launch(it) }
        }
        binding.completedDateContainer.setEndIconOnClickListener {
            showDatePickerDialog()
        }

        binding.btnSaveApplication.setOnClickListener {
            val commentText = binding.etMontersComment.text.toString().trim()
            val dateText = binding.etCompletedDate.text.toString().trim()
            val applicationStatusText = binding.spinnerApplicationStatus.selectedItem.toString()
            if (commentText.isEmpty()) {
                binding.montersCommentContainer.error =
                    getString(R.string.this_field_must_be_filled)
                return@setOnClickListener
            } else binding.montersCommentContainer.error = null
            if (dateText.isEmpty()) {
                binding.completedDateContainer.error = getString(R.string.this_field_must_be_filled)
                return@setOnClickListener
            } else binding.completedDateContainer.error = null
            if (applicationStatusText == getString(R.string.done)) {
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
                Toast.makeText(
                    requireContext(),
                    "Невыполненная заявка не может быть сохранена",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                // Обработка выбранной даты
                calendar.set(year, month, dayOfMonth)

                // Форматирование даты
                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                formattedDate = dateFormat.format(calendar.time)

                // Установка отформатированной даты в EditText
                binding.etCompletedDate.setText(formattedDate)

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Опционально: установка ограничений на дату (например, минимальная и максимальная дата)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        datePickerDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val resultUri = data?.data
            imageUrl = resultUri.toString()
            binding.imgMonterPhoto.load(resultUri)
        } else {
            Toast.makeText(requireContext(), "Не удалось сохранить изображение", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkApplicationStatus(statusText: String): Boolean {
        return statusText == getString(R.string.done)
    }

    private fun getDataFromApplicationFragment() {
        if (application != null) {
            binding.tvReason.text = application!!.reason
            binding.tvAddress.text = application!!.address
            binding.tvRegionName.text = application!!.regionName
            binding.tvPersonalAccount.text = application!!.personalAccount.toString()
            binding.tvPhoneNumber.text = application!!.phoneNumber.toString()
            binding.tvDate.text = application!!.date
        }
    }
}