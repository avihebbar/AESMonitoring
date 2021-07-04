package com.aapavani.aesmonitoring.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.ServiceRequest
import com.aapavani.aesmonitoring.databinding.ActivityServicerLandingPageBinding
import com.aapavani.aesmonitoring.ui.adapters.SRItemClickedListener
import com.aapavani.aesmonitoring.ui.adapters.SRListAdapter
import com.aapavani.aesmonitoring.ui.viewmodel.ProjectViewModel
import com.aapavani.aesmonitoring.ui.viewmodel.SRViewModel
import com.aapavani.aesmonitoring.ui.viewmodel.SRViewModelFactory

class ServicerLandingPageActivity : AppCompatActivity(), SRItemClickedListener {
    lateinit var binding : ActivityServicerLandingPageBinding
    lateinit var viewModelFactory : SRViewModelFactory
    lateinit var viewModel : SRViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_servicer_landing_page)
        viewModelFactory = SRViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SRViewModel::class.java);
        viewModel.fetchServiceRequests()
        viewModel.srListLiveData.observe(this, Observer {
            binding.srRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.srRecyclerView.adapter = SRListAdapter(this, it, this)
        } )
    }

    override fun onItemClicked(sr: ServiceRequest) {
        print("test")
    }


}