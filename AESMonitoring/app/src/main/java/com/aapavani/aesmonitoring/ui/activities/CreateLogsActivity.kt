package com.aapavani.aesmonitoring.ui.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.FormItem
import com.aapavani.aesmonitoring.data.models.NetworkCallStatus
import com.aapavani.aesmonitoring.data.models.Project
import com.aapavani.aesmonitoring.databinding.ActivityCreateLogsBinding
import com.aapavani.aesmonitoring.ui.fragments.FormFragment
import com.aapavani.aesmonitoring.ui.viewmodel.LogsViewModel
import com.aapavani.aesmonitoring.ui.viewmodel.LogsViewModelFactory
import com.aapavani.aesmonitoring.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.*
import java.lang.reflect.Type

class CreateLogsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateLogsBinding
    var project : Project? = null
    lateinit var viewModelFactory : LogsViewModelFactory;
    lateinit var viewModel: LogsViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityCreateLogsBinding>(
            this,
            R.layout.activity_create_logs
        )
        viewModelFactory = LogsViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LogsViewModel::class.java);
        project = intent.getParcelableExtra(Constants.PROJECT)
        binding.projectTitle.text = project?.name
        val progressBar = binding.progressBar
        viewModel.status.observe(this,
            Observer<NetworkCallStatus> {
                when (it) {
                    NetworkCallStatus.SUCCESS, NetworkCallStatus.FAILURE -> {
                        progressBar.visibility = View.INVISIBLE
                        val dialog = AlertDialog.Builder(this)
                            .setTitle("Log Created successfully")
                            .setPositiveButton(
                                "Ok"
                            ) { dialog, which -> this@CreateLogsActivity.finish() }
                            .create()
                        dialog.show()
                    }
                    NetworkCallStatus.IN_PROGRESS -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            })

        populateForms();
    }

    private fun populateForms() {
        val formPages = fetchFormPages()
        val forms : MutableList<FormFragment> = mutableListOf()
        for (form in formPages) {
            forms.add(FormFragment.newInstance(form, false))
        }
        var currPos = 0;
        moveToFragment(forms[currPos])
        val resMap :MutableMap<String, String> = mutableMapOf()
        binding.nextBtn.setOnClickListener{
            if( !forms[currPos].validate() ){
                return@setOnClickListener
            }
            if( currPos == forms.size - 1 ){
                createLogObject(resMap)
                return@setOnClickListener
            }

            resMap.putAll(forms[currPos].getValues())
            currPos++;
            handleTextVisibility(currPos, forms.size)
            moveToFragment(forms[currPos])

        }

        binding.prevBtn.setOnClickListener{
            if( !forms[currPos].validate() ){
                return@setOnClickListener
            }
            resMap.putAll(forms[currPos].getValues())
            currPos--;
            handleTextVisibility(currPos, forms.size)
            moveToFragment(forms[currPos])
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLogObject(resMap: MutableMap<String, String>) {
        val obj : JSONObject = JSONObject(resMap as Map<*, *>)
//        var log = MonitoringLogs(
//            null,
//            projectId = project?.id!!,
//            date = LocalDateTime.now().toString(),
//            status = obj.optString("status"),
//            shutDownReason = obj.optString("shutdown_reason"),
//            pump1Status = obj.optString("pump_1"),
//            pump2Status = obj.optString("pump_2"),
//            controlPanelErrorMessage = obj.optString("control_panel_error_message"),
//            inletElectricitySupply = obj.optInt("inlet_electricity_supply", Constants.MINUS_ONE),
//            leakages = obj.optString("leakages"),
//            flowMeterReading = obj.optInt("flow_meter_reading", Constants.MINUS_ONE),
//            sludgeVolume = obj.optInt("sludge_volume", Constants.MINUS_ONE),
//            vaccumPressure = obj.optInt("vacuum_pressure", Constants.MINUS_ONE),
//            barScreenStatus = obj.optString("bar_screen_status"),
//            sludgeDryingBedStatus = obj.optString("sludge_drying_bed_status"),
//            areaCleanliness = obj.optString("area_cleanliness"),
//            otherIssues = obj.optString("other_issues"),
//            siteInchargeId = "incharge person",
//            monitoringPersonnelId = "monitoring person",
//            areaPhotoId = null,
//            sludgePhotoId = null,
//            treatedWaterPhotoId = null
//        );
        viewModel.createLog(null)
    }

    private fun handleTextVisibility(currPos : Int, totalSize : Int) {
        val prev = binding.prevBtn
        val next = binding.nextBtn
        prev.visibility = View.VISIBLE
        if( currPos == 0 ){
            prev.visibility = View.INVISIBLE
        }
        if( currPos == totalSize - 1 ){
            next.setText("Finish")
        }
    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    fun fetchFormPages() : ArrayList<ArrayList<FormItem>> {
        val `is`: InputStream = getResources().openRawResource(R.raw.log_form_items)
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
        val formItemsListType: Type = object : TypeToken<ArrayList<ArrayList<FormItem>>>() {}.type
        return gson.fromJson<ArrayList<ArrayList<FormItem>>>(writer.toString(), formItemsListType)
    }

}
