package com.shayan.firebaseuploadexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shayan.firebaseuploadexample.presenter.IMainActivityPresenter
import com.shayan.firebaseuploadexample.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainActivityPresenter.View {

    companion object {
        const val PICK_IMAGE_REQUEST = 1
        const val IMAGE_URI_STATE = "image_uploaded_state"
        const val STORAGE_FOLDER = "uploads"  // name of the folder that file will be oploaded into firebase
    }

    private lateinit var presenter: IMainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPresenter()
        initListeners()
        presenter
        this.presenter.updateView()
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(IMAGE_URI_STATE, imageUri.toString())
    }*/

    /*override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imageUri = Uri.parse(savedInstanceState.getString(IMAGE_URI_STATE))

    }*/

    fun initPresenter() {
        this.presenter = MainActivityPresenter(this, this)
    }

    fun initListeners() {
        button_choose_image.setOnClickListener {
            openFileChooser()
        }

        button_upload.setOnClickListener {
            presenter.uploadFile(edit_text_file_name.text.toString().trim())
        }

        text_view_show_uploads.setOnClickListener {
            goToImagesActivity()
        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            val imageUri = data.data!!
            presenter.setImageUri(imageUri) // Send uri to Presenter
            // Picture loading => Glide
            Glide.with(this).load(imageUri).into(image_view_upload_file)
        }
    }

    override fun showData(progress: Int) {
        progress_bar.progress = progress
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        TODO("Not yet implemented")
    }

    private fun goToImagesActivity() {
        val intent = Intent(this, ImageListActivity::class.java)
        startActivity(intent)
    }
}