package com.aapavani.aesmonitoring.data.models

data class ServiceRequest(
    val id : String,
    val projectId : String,
    val projectName : String,
    val date : String,
    val status : String,
    val description : String,
    val rootCause : String?,
    val rectification : String?
)