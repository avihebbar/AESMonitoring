package com.aapavani.aesmonitoring.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.Project
import com.aapavani.aesmonitoring.databinding.FragmentProjectListBinding
import com.aapavani.aesmonitoring.ui.adapters.ProjectItemClickedListener
import com.aapavani.aesmonitoring.ui.adapters.ProjectListAdapter
import com.aapavani.aesmonitoring.ui.viewmodel.ProjectViewModel
import com.aapavani.aesmonitoring.ui.viewmodel.ProjectViewModelFactory

class ProjectListFragment : Fragment(), ProjectItemClickedListener {
    lateinit var viewModelFactory : ProjectViewModelFactory;
    lateinit var viewModel: ProjectViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = ProjectViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectViewModel::class.java);
        viewModel.fetchProjects()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProjectListBinding>(inflater, R.layout.fragment_project_list, container, false)
        viewModel.projectListLiveData.observe(viewLifecycleOwner, Observer {
            binding.rvProjectList.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvProjectList?.adapter = ProjectListAdapter(it, this)
        });
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProjectListFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onItemClicked(project : Project) {
        (activity as ProjectItemClickedListener).onItemClicked(project)
    }
}