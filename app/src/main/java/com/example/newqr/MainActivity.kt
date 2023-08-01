package com.example.newqr

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.WriteAbortedException


class MainActivity : AppCompatActivity() {
    lateinit var qrImage: ImageView
    lateinit var btnGenerate: Button
    lateinit var btnScan: Button
    lateinit var etGenerate: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        qrImage = findViewById(R.id.imageViewQR)
        etGenerate = findViewById(R.id.etForGenerate)
        btnScan = findViewById(R.id.buttonScanQR)
        btnScan.setOnClickListener {
//            startActivity(Intent(this, ScannerActivity::class.java))
            checkCameraPermissions()
        }
        btnGenerate = findViewById(R.id.buttonQR)
        btnGenerate.setOnClickListener {
            val textGenerate = etGenerate.text.toString()
            generateQR(textGenerate)
        }
    }

    private fun generateQR(text: String) {
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        val qrgEncoder = QRGEncoder(text, QRGContents.Type.TEXT, 500)
        qrgEncoder.colorBlack = Color.WHITE
        qrgEncoder.colorWhite = Color.BLACK
        try {
            // Getting QR-Code as Bitmap
            val bitmap = qrgEncoder.bitmap
            // Setting Bitmap to ImageView
            qrImage.setImageBitmap(bitmap)
        } catch (e: WriteAbortedException) {
            Log.v("TAG", e.toString())
        }
    }

    private fun checkCameraPermissions() {

        if (ContextCompat.checkSelfPermission(this, permission.CAMERA) ==
            PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission.CAMERA), CAMERA_REQUEST_CODE)
        } else {
            startActivity(Intent(this, ScannerActivity::class.java))
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            CAMERA_REQUEST_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(this, ScannerActivity::class.java))
            }
        }
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 12
    }

}