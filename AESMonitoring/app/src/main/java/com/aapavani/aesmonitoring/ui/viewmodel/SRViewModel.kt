package com.aapavani.aesmonitoring.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.ServiceRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type

class SRViewModel(val context: Context) : ViewModel() {
    var srListLiveData = MutableLiveData<ArrayList<ServiceRequest>>()

    fun fetchServiceRequests() {
        val `is`: InputStream = context.getResources().openRawResource(R.raw.service_request_list)
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
        val srListType: Type = object : TypeToken<ArrayList<ServiceRequest?>?>() {}.type

        val srList = gson.fromJson<ArrayList<ServiceRequest>>(writer.toString(), srListType)
        srListLiveData.postValue(srList)
    }

}