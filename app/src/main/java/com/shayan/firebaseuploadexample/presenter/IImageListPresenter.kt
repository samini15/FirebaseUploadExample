package com.shayan.firebaseuploadexample.presenter

import com.shayan.firebaseuploadexample.model.Upload

interface IImageListPresenter {

    interface View {
        fun showData(uploads: MutableList<Upload>)

        fun showError()
    }

    fun updateView()
}