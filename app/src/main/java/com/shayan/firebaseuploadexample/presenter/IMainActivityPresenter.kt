package com.shayan.firebaseuploadexample.presenter

import android.net.Uri

interface IMainActivityPresenter {

    interface View {
        fun showData(progress: Int)

        fun showToast(message: String)

        fun showError()
    }

    fun updateView()

    fun setImageUri(uri: Uri)

    fun uploadFile(fileName: String)
}