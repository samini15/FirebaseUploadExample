package com.shayan.firebaseuploadexample.presenter

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.webkit.MimeTypeMap
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.shayan.firebaseuploadexample.MainActivity
import com.shayan.firebaseuploadexample.model.Upload

class MainActivityPresenter(var view: IMainActivityPresenter.View, var context: Context) : IMainActivityPresenter {

    // TODO -> Replace this static field by Dependancy Injection mecanisme
    companion object {
        private val storageRef = FirebaseStorage.getInstance().getReference(MainActivity.STORAGE_FOLDER)
        private val databaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.STORAGE_FOLDER)
    }

    private lateinit var imageUri: Uri

    private var uploadTask: StorageTask<*>? = null

    override fun updateView() {
        view.showData(0)
    }

    override fun setImageUri(uri: Uri) {
        this.imageUri = uri
    }

    /*override fun initFirebase() {
        this.storageRef = FirebaseStorage.getInstance().getReference(MainActivity.STORAGE_FOLDER)
        this.databaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.STORAGE_FOLDER)
    }*/

    override fun uploadFile(fileName: String) {
        if (uploadTask != null && uploadTask!!.isInProgress) {
            view.showToast("In progress")
        } else {
            upload(fileName)
        }
    }
    
    private fun upload(fileName: String) {
        val storageReference = storageRef.child(
            "${System.currentTimeMillis()} . ${
                getFileExtension(
                    imageUri
                )
            }"
        )
        uploadTask = storageReference.putFile(imageUri).addOnSuccessListener {
            val handler = Handler()
            handler.postDelayed({
                view.showData(0) // Update View
            }, 5000)

            view.showToast("Upload successful")
            val upload = Upload(
                fileName,
                it.uploadSessionUri.toString()
            )

            // Create a database entry
            val uploadId = databaseRef.push().key
            databaseRef.child(uploadId!!).setValue(upload)
        }.addOnFailureListener {
            it.message?.let { it1 -> view.showToast(it1) }
        }.addOnProgressListener {
            val progress = (100.0 * it.bytesTransferred / it.totalByteCount)
            view.showData(progress.toInt()) // Update View
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }
}
