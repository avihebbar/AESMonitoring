package com.aapavani.aesmonitoring.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.aapavani.aesmonitoring.R

class MonitoringLandingFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_monitoring_landing, container, false)
        return inflate
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MonitoringLandingFragment().apply {

            }
    }
}