package com.aapavani.aesmonitoring.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aapavani.aesmonitoring.data.models.MonitoringLogs
import com.aapavani.aesmonitoring.data.models.NetworkCallStatus
import java.util.logging.Handler

class LogsViewModel(val context: Context) : ViewModel(){
    var status : MutableLiveData<NetworkCallStatus> = MutableLiveData(NetworkCallStatus.IDLE)

    fun createLog(log : MonitoringLogs?){
        status.postValue(NetworkCallStatus.IN_PROGRESS)
        android.os.Handler().postDelayed({
            status.postValue(NetworkCallStatus.SUCCESS)
        }, 3000)

    }
}