package com.aapavani.aesmonitoring.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.Project
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import kotlin.coroutines.coroutineContext


class ProjectViewModel(val context: Context) : ViewModel() {
    var projectListLiveData = MutableLiveData<ArrayList<Project>>()

    fun fetchProjects() {
        val `is`: InputStream = context.getResources().openRawResource(R.raw.project_list)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            var n = 0
            while (reader.read(buffer).also({ n = it }) != -1) {
                writer.write(buffer, 0, n)
            }
        } finally {
            `is`.close()
        }

        val gson = Gson();
        val projectListType: Type = object : TypeToken<ArrayList<Project?>?>() {}.type

        val projectList = gson.fromJson<ArrayList<Project>>(writer.toString(), projectListType)
        projectListLiveData.postValue(projectList)
    }
}