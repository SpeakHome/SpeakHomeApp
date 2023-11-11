package com.example.speakhomeapp

import Models.ApiResponse
import Models.Contact.ContactResource
import Models.ContactMessageResource.ContactMessageResource
import Models.ContactMessageResource.CreateContactMessageResource
import Models.Device.CreateDeviceResource
import Models.Device.DeviceResource
import Models.Message.CreateMessageResource
import Models.Message.MessageResource
import Models.PofileDevice.CreateProfileDeviceResource
import Models.PofileDevice.ProfileDeviceResource
import Services.ContactMessageService
import Services.ContactService
import Services.MessageService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.RecyclerViews.MessageAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : AppCompatActivity() {
    lateinit var textViewContactUserName: TextView
    lateinit var recyclerViewMessages: RecyclerView
    lateinit var editTextInputMessage: EditText
    lateinit var buttonSendMessage: Button
    lateinit var contactMessageService: ContactMessageService
    lateinit var contactService: ContactService
    lateinit var messageService: MessageService
    private var profileId:Long = 0
    private var isAuthenticated:Boolean = false
    private var contactProfileId:Long = 0
    lateinit var contactResource: ContactResource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_view)

        textViewContactUserName = findViewById<TextView>(R.id.textViewContactUserName)
        recyclerViewMessages = findViewById<RecyclerView>(R.id.recyclerViewMessages)
        recyclerViewMessages.layoutManager = LinearLayoutManager(applicationContext)
        editTextInputMessage = findViewById<EditText>(R.id.editTextInputMessage)
        buttonSendMessage = findViewById<Button>(R.id.buttonSendMessage)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.47:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        contactMessageService = retrofit.create<ContactMessageService>(ContactMessageService::class.java)
        messageService = retrofit.create<MessageService>(MessageService::class.java)
        contactService = retrofit.create<ContactService>(ContactService::class.java)

        profileId = (application as MyApplication).profile?.id ?:0
        isAuthenticated = (application as MyApplication).isAuthenticated
        contactProfileId = intent.getLongExtra("CONTACT_ID", 0)

        getContact()
        loadMessages()
        //Toast.makeText(this@ChatActivity, "se encontro: ${contactResource.id}", Toast.LENGTH_SHORT).show()
        buttonSendMessage.setOnClickListener{
            createMessage()
        }
    }
    private fun getContact(){
        if (isAuthenticated) {
            contactService.getByProfileIdAndContactProfileId(profileId.toInt(), contactProfileId.toInt()).enqueue(object : Callback<ContactResource>{
                override fun onResponse(call: Call<ContactResource>, response: Response<ContactResource>) {
                    if (response.isSuccessful) {
                        contactResource= response.body()!!
                        textViewContactUserName.text=contactResource.contactProfile.userName
                        Toast.makeText(this@ChatActivity, "se encontro: ${contactResource.id}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ChatActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ContactResource>, t: Throwable) {
                    Toast.makeText(this@ChatActivity, "Fallo: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else {
            Toast.makeText(this@ChatActivity, "No se encontró el ID del contacto", Toast.LENGTH_SHORT).show()
        }
    }
    private fun loadMessages() {
        contactMessageService.getMessagesByProfileId(profileId).enqueue(object : Callback<ApiResponse<List<ContactMessageResource>>> {
            override fun onResponse(call: Call<ApiResponse<List<ContactMessageResource>>>, response: Response<ApiResponse<List<ContactMessageResource>>>) {
                if (response.isSuccessful) {
                    val contactMessages = response.body()?.content ?: emptyList()
                    recyclerViewMessages.adapter=MessageAdapter(profileId,contactMessages)
                    //Toast.makeText(this@ChatActivity, "se cargo los mensajes correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle the error
                    Toast.makeText(this@ChatActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<ContactMessageResource>>>, t: Throwable) {
                // Handle failure, such as no internet connection
                Toast.makeText(this@ChatActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createMessage() {
        //createMessageResource.content=editTextInputMessage.text.toString()
        val newMessage=CreateMessageResource(
            content = editTextInputMessage.text.toString()
        )
        // Primero, crear el dispositivo
        messageService.create(newMessage).enqueue(object : Callback<MessageResource> {
            override fun onResponse(call: Call<MessageResource>, response: Response<MessageResource>) {
                if (response.isSuccessful) {
                    // Si se creó el dispositivo, proceder a crear el ProfileDevice
                    val message = response.body()!!
                    createContactMessage(message.id)
                    Toast.makeText(this@ChatActivity, "se posteo con exito XD", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ChatActivity, "Error al crear el dispositivo: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MessageResource>, t: Throwable) {
                Toast.makeText(this@ChatActivity, "Fallo al crear el dispositivo: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createContactMessage(messageId: Long) {

        val createContactMessageResource = CreateContactMessageResource(
            contactId = contactResource.id,
            messageId = messageId
        )

        contactMessageService.create(createContactMessageResource).enqueue(object : Callback<ContactMessageResource> {
            override fun onResponse(call: Call<ContactMessageResource>, response: Response<ContactMessageResource>) {
                if (response.isSuccessful) {
                    loadMessages()
                    Toast.makeText(this@ChatActivity, "Dispositivo asociado al perfil correctamente.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ChatActivity, "Error al asociar el dispositivo: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ContactMessageResource>, t: Throwable) {
                Toast.makeText(this@ChatActivity, "Fallo al asociar el dispositivo: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}