package com.example.monterapp.presentation.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.monterapp.R
import com.example.monterapp.app.App
import com.example.monterapp.core.base.BaseFragment
import com.example.monterapp.data.local.pref.Pref
import com.example.monterapp.data.models.Application
import com.example.monterapp.databinding.FragmentApplicationBinding
import com.example.monterapp.presentation.dashboard.adapter.ApplicationAdapter
import com.example.monterapp.utils.KEYS

class ApplicationFragment : BaseFragment<FragmentApplicationBinding,ApplicationViewModel>() {

    private val applicationDao = App.database.getApplicationDao()
    private val adapter = ApplicationAdapter(this::onClickItem)
    private val pref: Pref by lazy { Pref(requireContext()) }


    override fun inflateViewBinding(): FragmentApplicationBinding =
        FragmentApplicationBinding.inflate(layoutInflater)

    override fun setViewModel(): ApplicationViewModel =
        ApplicationViewModel(ApplicationRepository(applicationDao))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvApplications.adapter = adapter
        viewModel.loadApplicationsForMonter(pref.getUserLoggedInRegionName().toString())
        viewModel.applications.observe(viewLifecycleOwner){ applications ->
            Log.d("ololo", "onViewCreated: $applications")
            adapter.addApplication(applications)
        }
    }

    private fun onClickItem(application: Application){
        findNavController().navigate(R.id.detailsApplicationFragment,
            bundleOf(KEYS.KEY_BUNDLE_MAIN_TO_DETAIL to application))
    }


}