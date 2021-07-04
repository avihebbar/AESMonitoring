package com.aapavani.aesmonitoring.data.models

data class MonitoringLogs(
    val id : String?,
    val projectId : String,
    val date : String,
    val status : String,
    val shutDownReason : String?,
    val pump1Status : String,
    val pump2Status : String,
    val controlPanelErrorMessage : String,
    val inletElectricitySupply : Int,
    val leakages : String,
    val flowMeterReading : Int,
    val sludgeVolume : Int,
    val sludgePhotoId : String?,
    val treatedWaterPhotoId : String?,
    val vaccumPressure : Int,
    val barScreenStatus : String,
    val sludgeDryingBedStatus : String,
    val areaCleanliness : String,
    val areaPhotoId : String?,
    val otherIssues : String?,
    val monitoringPersonnelId : String,
    val siteInchargeId : String
)