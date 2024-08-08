package com.netlifymanjot.instagram_clone.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName: String, callback: (String?) -> Unit) {
    val storageReference = FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
    storageReference.putFile(uri)
        .addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                callback(uri.toString())
            }.addOnFailureListener {
                callback(null)
            }
        }
        .addOnFailureListener {
            callback(null)
        }
}
