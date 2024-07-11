package com.example.android.instagramclone.Post

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android.instagramclone.HomeActivity
import com.example.android.instagramclone.Models.Post
import com.example.android.instagramclone.Models.User
import com.example.android.instagramclone.R
import com.example.android.instagramclone.Utils.POST
import com.example.android.instagramclone.Utils.POST_FOLDER
import com.example.android.instagramclone.Utils.USER_NODE
import com.example.android.instagramclone.Utils.USER_PROFILE_FOLDER
import com.example.android.instagramclone.Utils.uploadImage
import com.example.android.instagramclone.databinding.ActivityPostBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl:String?=null

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let{
            uploadImage(uri, POST_FOLDER){
                url->
                if(url!==null){
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url

                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar()!!?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }
        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document().get().addOnSuccessListener {

                var user = it.toObject<User>()
                val post:Post=Post(imageUrl!!,binding.caption.editText?.text.toString(),Firebase.auth.currentUser!!.uid,System.currentTimeMillis().toString())
                Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                        startActivity(Intent(this@PostActivity,HomeActivity::class.java))
                        finish()
                    }
            }


            }
        }
    }
}