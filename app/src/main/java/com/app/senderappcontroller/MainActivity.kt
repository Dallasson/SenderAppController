package com.app.senderappcontroller

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ipc_library.ApiController
import com.app.ipc_library.IpcApiController
import com.app.ipc_library.MessageStore
import com.app.senderappcontroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var apiController: ApiController
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    private val pickFileLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let { apiController.sendJsonFile(it) }
        }

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
            context = this,
            targetPackage = "com.app.receiverappcontroller",
            fileProviderPackage = "com.app.senderappcontroller",
            serviceClassName = "com.app.ipc_library.IpcService" // matches Receiver’s <action>
        )


        binding.sendJsonBtn.setOnClickListener {
            val messageInput = binding.messageInput.text.toString()
            if (messageInput.isEmpty()) {
                Toast.makeText(this, "Please type something", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            apiController.sendEditText(messageInput)
        }

        binding.pickFileBtn.setOnClickListener {
            pickFileLauncher.launch(arrayOf("application/json"))
        }

        binding.apiCallBtn.setOnClickListener {
            apiController.sendJsonApi()
        }

        MessageStore.messageLiveData.observe(this) { message ->
            binding.receivedResponseTxt.text = message
        }
    }
}
