package com.shayan.firebaseuploadexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.shayan.firebaseuploadexample.adapter.ImageListAdapter
import com.shayan.firebaseuploadexample.model.Upload
import com.shayan.firebaseuploadexample.presenter.IImageListPresenter
import com.shayan.firebaseuploadexample.presenter.ImageListPresenter
import kotlinx.android.synthetic.main.activity_image_list.*

class ImageListActivity : AppCompatActivity(), IImageListPresenter.View {

    private lateinit var presenter: IImageListPresenter
    private lateinit var adapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)

        initRecyclerView()
        initPresenter()
    }

    private fun initRecyclerView() {
        recycler_view_image_list.layoutManager = GridLayoutManager(applicationContext, 2)
        this.adapter = ImageListAdapter(applicationContext, mutableListOf())
        recycler_view_image_list.adapter = this.adapter
    }

    private fun initPresenter() {
        this.presenter = ImageListPresenter()
    }

    override fun showData(uploads: MutableList<Upload>) {
        this.adapter.setImages(uploads)
    }

    override fun showError() {
        TODO("Not yet implemented")
    }
}