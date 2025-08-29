package com.app.senderappcontroller

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ipc_library.ApiController
import com.app.ipc_library.IpcApiController
import com.app.senderappcontroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var apiController: ApiController

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiController = IpcApiController(
            this,
            "com.app.ipcsender",
            "com.app.ipcsender.services.IControlService"
        )

        binding.senderEditTextBtn.setOnClickListener {
            Toast.makeText(this,"Called",Toast.LENGTH_SHORT).show()
            apiController.enableEditText(
                shouldSendEditText = false
            )
        }

        binding.senderJsonFileBtn.setOnClickListener {
            Toast.makeText(this,"Called",Toast.LENGTH_SHORT).show()
            apiController.enableJsonFile(
                shouldSendJsonFile = false
            )
        }

        binding.senderJsonAPi.setOnClickListener {
            Toast.makeText(this,"Called",Toast.LENGTH_SHORT).show()
            apiController.enableJsonApi(
                shouldSendJsonApi = false
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //apiController.unbind(this)
    }
}