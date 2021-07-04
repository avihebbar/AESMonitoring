package com.aapavani.aesmonitoring.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.Project
import com.aapavani.aesmonitoring.databinding.FragmentProjectDetailsBinding
import com.aapavani.aesmonitoring.ui.activities.CreateLogsActivity
import com.aapavani.aesmonitoring.utils.Constants
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


private const val ARG_PROJECT = "project"

class ProjectDetailsFragment : Fragment(), OnMapReadyCallback {
    lateinit var project: Project;
    lateinit var mMap : GoogleMap;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            project = it.getParcelable(ARG_PROJECT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProjectDetailsBinding>(
            inflater,
            R.layout.fragment_project_details,
            container,
            false
        );
        val mapFragment = SupportMapFragment.newInstance()
        val fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragMap, mapFragment)
        fragmentTransaction.commit()
        mapFragment.getMapAsync(this)

        binding.project = project
        binding.fragment = this
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(project: Project) =
            ProjectDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PROJECT, project)
                }
            }
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map!!;
        val plantLocation = LatLng(project.location.lat, project.location.lon)
        mMap.setMinZoomPreference(12.0F)
        mMap?.addMarker(
            MarkerOptions()
                .zIndex(2.0F)
                .position(plantLocation)
                .title(project.name)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(plantLocation))

    }

    fun onCreateLogsClick(){
        var intent = Intent(requireActivity(), CreateLogsActivity::class.java)
        intent.putExtra(Constants.PROJECT, project)
        requireActivity().startActivity(intent)
    }
}