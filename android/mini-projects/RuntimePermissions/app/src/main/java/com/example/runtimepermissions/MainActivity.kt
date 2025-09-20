package com.example.runtimepermissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.runtimepermissions.databinding.ActivityMainBinding
import com.example.runtimepermissions.databinding.CustomPermissionExplanationDialogBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.graphics.drawable.toDrawable
import com.example.runtimepermissions.databinding.CustomPermissionSettingsDialogBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var imageTempUri: Uri? = null

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
        if(isGranted) {
            openCamera()
        }
        else {
            if(!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                showSettingsDialog()
            else
                Toast.makeText(this, "Camera access permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        isSuccess: Boolean ->
        if(isSuccess) {
            imageTempUri?.let {
                uri ->
                Glide.with(this).load(uri).into(binding.imageContainer)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.takePictureButton.setOnClickListener { requestCameraPermission() }
    }

    fun openCamera() {
        val tempUri = getTempFileUri()
        imageTempUri = tempUri
        takePictureLauncher.launch(tempUri)
        binding.imageContainerMessage.visibility = View.GONE
    }

    fun showSettingsDialog() {
        val settingsDialogBinding = CustomPermissionSettingsDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(this)
            .setView(settingsDialogBinding.root)

        val dialog = builder.create()

        dialog.window?.setBackgroundDrawable(0x00FFFFFF.toDrawable())

        settingsDialogBinding.settingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
            dialog.dismiss()
        }

        settingsDialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)

        val density = resources.displayMetrics.density
        val widthInPixels = (300 * density).toInt()

        layoutParams.width = widthInPixels
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.attributes = layoutParams
    }

    fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showExplanationDialog()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    fun showExplanationDialog() {
        val explanationDialogBinding = CustomPermissionExplanationDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(this)
            .setView(explanationDialogBinding.root)

        val dialog = builder.create()

        dialog.window?.setBackgroundDrawable(0x00FFFFFF.toDrawable())

        explanationDialogBinding.okayButton.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            dialog.dismiss()
        }

        explanationDialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)

        val density = resources.displayMetrics.density
        val widthInPixels = (300 * density).toInt()

        layoutParams.width = widthInPixels
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window?.attributes = layoutParams
    }

    fun getTempFileUri(): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val tempFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(
            applicationContext,
            "${applicationContext.packageName}.provider",
            tempFile
        )
    }
}