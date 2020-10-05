package com.shayan.firebaseuploadexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shayan.firebaseuploadexample.R
import com.shayan.firebaseuploadexample.model.Upload

class ImageListAdapter(private val context: Context, private val uploads: MutableList<Upload>) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.image_item_view, parent, false), context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(uploads[position])
    }

    override fun getItemCount(): Int {
        return uploads.size
    }

    fun setImages(uploads: MutableList<Upload>) {
        this.uploads.clear()
        this.uploads.addAll(uploads)
        notifyDataSetChanged() // Invoke onBindViewHolder
    }


    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val imageViewUpload = itemView.findViewById<ImageView>(R.id.image_view_upload)
        val textViewName = itemView.findViewById<TextView>(R.id.text_view_name)
        lateinit var upload: Upload
        val context: Context = context

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        fun bind(upload: Upload) {
            this.upload = upload

            textViewName.text = upload.name
            Glide.with(context).load(upload.imageUrl).centerCrop().into(imageViewUpload)
        }
    }
}