package com.aapavani.aesmonitoring.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.Project
import com.aapavani.aesmonitoring.ui.adapters.ProjectItemClickedListener
import com.aapavani.aesmonitoring.ui.fragments.ProjectDetailsFragment
import com.aapavani.aesmonitoring.ui.fragments.ProjectListFragment


class MonitorLandingPageActivity : FragmentActivity(), ProjectItemClickedListener {
    var fragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moveToFragment(ProjectListFragment.newInstance())
    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    override fun onItemClicked(project: Project) {
        moveToFragment(ProjectDetailsFragment.newInstance(project))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}