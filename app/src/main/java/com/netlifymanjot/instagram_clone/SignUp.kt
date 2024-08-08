package com.netlifymanjot.instagram_clone

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.netlifymanjot.instagram_clone.databinding.ActivitySignUpBinding
import com.netlifymanjot.instagram_clone.utils.USER_NODE
import com.netlifymanjot.instagram_clone.utils.USER_PROFILE_FOLDER
import com.netlifymanjot.instagram_clone.utils.uploadImage
import com.squareup.picasso.Picasso


data class User(
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var image: String = ""
)

class SignUp : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private var user = User()
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(it, USER_PROFILE_FOLDER) { imageUrl ->
                if (imageUrl.isNullOrEmpty()) {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                } else {
                    user.image = imageUrl
                    binding.upload.setImageURI(uri)
                    Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val text = "<font color=#FF000000>Already have an account?</font> <font color=#1E88E5>Log In</font>"
        binding.login.setText(Html.fromHtml(text))
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        user = User()
        if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE", -1)==1){
                binding.signup.text="Update Profile"
                Firebase.firestore.collection(USER_NODE)
                    .document(Firebase.auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        val fetchedUser: User? = document.toObject(User::class.java)
                        if (fetchedUser != null) {
                            user = fetchedUser
                            binding.editName.setText(user.name)
                            binding.editEmail.setText(user.email)
                            if (user.image.isNotEmpty()) {
                                Picasso.get().load(user.image).into(binding.upload)
                            }
                            binding.name.editText?.setText(user.name)
                            binding.email.editText?.setText(user.email)
                            binding.password.editText?.setText(user.password)
            }
        }
        binding.signup.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE", -1)==1){
                    Firebase.firestore.collection(USER_NODE)
                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                        .set(user)
                        .addOnSuccessListener {
                            startActivity(Intent(this@SignUp, HomeActivity::class.java))
                            finish()
                        }

                }

            }else{



            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignUp, "Please fill all the information", Toast.LENGTH_SHORT).show()
            } else {
                user = User(name = name, email = email, password = password)

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            Firebase.firestore.collection(USER_NODE)
                                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                                .set(user)
                                .addOnSuccessListener {
                                    startActivity(Intent(this@SignUp, HomeActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this@SignUp, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this@SignUp, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }}
        }

        binding.plus.setOnClickListener {
            launcher.launch("image/*")
        }
                binding.login.setOnClickListener {
                    Toast.makeText(this, "Login TextView clicked", Toast.LENGTH_SHORT).show()
                }

            }
}}}
