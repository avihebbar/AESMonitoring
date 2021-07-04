package com.aapavani.aesmonitoring.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.File

class CameraUtils {
    companion object {
        var CAMERA_PERMISSION_REQUEST = 200;
        var CAMERA_CAPTURE_REQUEST = 300;
        var fieldName: String? = null;
        var TAG = CameraUtils.javaClass.simpleName
        fun openCameraWithPermissions(fragment: Fragment) {
            Log.d(TAG, "openCameraWithPermissions: ")
            if (ContextCompat.checkSelfPermission(
                    fragment.requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(
                    fragment.requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestCameraPermission(fragment)
            } else {
                openCamera(fragment!!)
            }
        }

        private fun requestCameraPermission(fragment: Fragment) {
            Log.d(TAG, "requestCameraPermission: ")
            fragment.requestPermissions(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                CAMERA_PERMISSION_REQUEST
            );
        }

        fun openCamera(fragment: Fragment) {
            Log.d(TAG, "openCamera: ")
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photo = File(fragment.activity?.getExternalFilesDir(null), "$fieldName.jpg")
            val uriForFile = FileProvider.getUriForFile(
                fragment.requireContext(),
                fragment.activity?.application?.packageName!!,
                photo
            )
            takePictureIntent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                uriForFile
            )
            fragment.startActivityForResult(takePictureIntent, CAMERA_CAPTURE_REQUEST)

        }
    }
}