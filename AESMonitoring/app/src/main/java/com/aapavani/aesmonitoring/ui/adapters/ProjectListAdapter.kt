package com.aapavani.aesmonitoring.ui.adapters

import android.view.LayoutInflater.*
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.*
import androidx.recyclerview.widget.RecyclerView
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.Project
import com.aapavani.aesmonitoring.databinding.ProjectListItemBinding

class ProjectListAdapter(val projectList: ArrayList<Project>?, val listener : ProjectItemClickedListener) : RecyclerView.Adapter<ProjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = inflate<ProjectListItemBinding>(
            from(parent.context),
            R.layout.project_list_item,
            parent,
            false
        )
        return ProjectViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return projectList?.size!!
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projectList!![position]
        holder.bind(project)
        holder.binding.root.setOnClickListener({
            listener.onItemClicked(projectList[position])
        })
    }
}

interface ProjectItemClickedListener {
    fun onItemClicked(project: Project)
}

class ProjectViewHolder(val binding : ProjectListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(project: Project) {
        binding.project = project;
    }

}
