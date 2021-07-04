package com.aapavani.aesmonitoring.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.data.models.FormItem
import com.aapavani.aesmonitoring.utils.CameraUtils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.File

private const val ARG_FORM_ITEMS = "FORM_ITEMS"
private const val ARG_EDITABLE = "EDITABLE"

class FormFragment : Fragment() {
    private var formItems: ArrayList<FormItem>? = null
    private var editable: Boolean = false
    lateinit var parentView: View;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            formItems = it.getParcelableArrayList(ARG_FORM_ITEMS)
            editable = it.getBoolean(ARG_EDITABLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentView = inflater.inflate(R.layout.fragment_form, container, false)
        val container: LinearLayout = parentView.findViewById(R.id.formContainer)
        populateForm(container, formItems)
        return parentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraUtils.CAMERA_CAPTURE_REQUEST && resultCode == Activity.RESULT_OK) {
            updateImageData()
        }
    }

    private fun updateImageData() {
        val container: LinearLayout = parentView.findViewById(R.id.formContainer)
        val filePath = CameraUtils.fieldName + ".jpg"
        container.findViewWithTag<TextInputEditText>(CameraUtils.fieldName)?.setText(filePath)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CameraUtils.CAMERA_PERMISSION_REQUEST) {
            if (grantResults?.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CameraUtils.openCameraWithPermissions(this)
            } else {
                Snackbar.make(
                    parentView,
                    "Please grant camera permissions to capture image",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun getValues(): HashMap<String, String> {
        val map: HashMap<String, String> = HashMap();
        if (formItems == null)
            return map
        val container: LinearLayout = parentView.findViewById(R.id.formContainer)
        for (formItem in formItems!!) {
            val viewWithTag = container.findViewWithTag<TextInputEditText>(formItem.name)
            if (null != viewWithTag && !viewWithTag.text.isNullOrBlank()) {
                map[formItem.name!!] = viewWithTag.text.toString()
            }
        }
        return map
    }

    fun validate(): Boolean {
        if (formItems == null)
            return true
        var valid = true
        val container: LinearLayout = parentView.findViewById(R.id.formContainer)
        for (formItem in formItems!!) {
//            val viewWithTag = container.findViewWithTag<TextInputEditText>(formItem.name)
//            if (formItem.required && null != viewWithTag && viewWithTag.text.isNullOrBlank()) {
//                viewWithTag.error = "Required field"
//                valid = false
//            }
        }
        return valid
    }

    private fun populateForm(container: LinearLayout, formItems: java.util.ArrayList<FormItem>?) {
        if (null == formItems)
            return
        for (formItem in formItems) {
            if (null == formItem.type)
                continue
            when (formItem.type) {
                FormItem.TEXT, FormItem.INT -> {
                    val textView = LayoutInflater.from(requireContext())
                        .inflate(R.layout.form_text_item, container, false)
                    if (null != formItem.display_text) {
                        textView.findViewById<TextInputLayout>(R.id.textFieldContainer)
                            .setHint(formItem.display_text)
                    }
                    if (formItem.type.equals(FormItem.INT)) {
                        textView.findViewById<TextInputEditText>(R.id.textField).apply {
                            this.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED)
                        }
                    }
                    textView.findViewById<TextInputEditText>(R.id.textField).tag = formItem.name
                    container.addView(textView)
                }
                FormItem.LARGE_TEXT -> {
                    val textView = LayoutInflater.from(requireContext())
                        .inflate(R.layout.form_text_item, container, false)
                    if (null != formItem.display_text) {
                        textView.findViewById<TextInputLayout>(R.id.textFieldContainer)
                            .setHint(formItem.display_text)
                    }
                    textView.findViewById<TextInputEditText>(R.id.textField).tag = formItem.name
                    container.addView(textView)
                }
                FormItem.SELECT -> {
                    val textView = LayoutInflater.from(requireContext())
                        .inflate(R.layout.form_select, container, false)
                    if (null != formItem.display_text) {
                        textView.findViewById<TextInputLayout>(R.id.textFieldContainer)
                            .setHint(formItem.display_text)
                    }
                    val textInput = textView.findViewById<TextInputEditText>(R.id.textField)
                    textInput.inputType = InputType.TYPE_NULL
                    if (formItem.values?.isNotEmpty() ?: false) {
                        textInput.setOnClickListener {
                            val popup = PopupMenu(requireContext(), it)
                            for (value in formItem.values!!) {
                                popup.menu.add(value)
                            }
                            popup.setOnMenuItemClickListener { menuItem ->
                                textInput.setText(menuItem.title!!)
                                true
                            }
                            popup.show()
                        }
                    }
                    textView.findViewById<TextInputEditText>(R.id.textField).tag = formItem.name
                    container.addView(textView)
                }

                FormItem.IMAGE -> {
                    val imageContainer = LayoutInflater.from(requireContext())
                        .inflate(R.layout.form_image, container, false)
                    val imageInput =
                        imageContainer.findViewById<TextInputLayout>(R.id.textFieldContainer)
                    if (null != formItem.display_text) {
                        imageInput.hint = formItem.display_text
                    }

                    imageContainer.findViewById<TextInputEditText>(R.id.textField).apply {
                        tag = formItem.name
                        setOnClickListener{
                            CameraUtils.fieldName = formItem.name
                            CameraUtils.openCameraWithPermissions(this@FormFragment)
                        }
                    }
                    container.addView(imageContainer)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(formItems: ArrayList<FormItem>, editable: Boolean) =
            FormFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FORM_ITEMS, formItems)
                    putBoolean(ARG_EDITABLE, editable)
                }
            }
    }
}