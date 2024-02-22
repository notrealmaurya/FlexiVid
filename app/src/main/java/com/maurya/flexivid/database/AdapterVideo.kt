package com.maurya.flexivid.database

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maurya.flexivid.MainActivity
import com.maurya.flexivid.R
import com.maurya.flexivid.activity.PlayerActivity
import com.maurya.flexivid.dataEntities.VideoDataClass
import com.maurya.flexivid.databinding.ItemVideoBinding
import com.maurya.flexivid.util.OnItemClickListener
import com.maurya.flexivid.util.sendIntent
import java.io.File

class AdapterVideo(
    private val context: Context,
    private var listener: OnItemClickListener,
    private var itemList: ArrayList<VideoDataClass> = arrayListOf(),
    private val isFolder: Boolean = false,
    private var isLongClickListenerInitialized: Boolean = false
) : RecyclerView.Adapter<AdapterVideo.DayHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(context), parent, false)

        return DayHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        val currentItem = itemList[position]

        with(holder) {
            videoTitle.isSelected = true

            videoTitle.text = currentItem.videoName
            folderName.text = currentItem.folderName
            durationText.text = DateUtils.formatElapsedTime(currentItem.durationText / 1000)

            Glide.with(context)
                .asBitmap()
                .load(currentItem.image)
                .centerCrop()
                .error(R.drawable.mp4)
                .into(image)

            root.setOnClickListener {

                if (isLongClickListenerInitialized) {
                    itemList[position].isChecked = !itemList[position].isChecked

                    isLongClickListenerInitialized = false
                } else {
                    when {
                        itemList[position].id == PlayerActivity.nowPlayingId -> {
                            sendIntent(context, position, "nowPlaying")
                        }

                        isFolder -> {
                            PlayerActivity.pipStatus = 1
                            sendIntent(context, position, "folderActivity")
                        }

                        MainActivity.search -> {
                            PlayerActivity.pipStatus = 2
                            sendIntent(context, position, "searchView")
                        }

                        else -> {
                            PlayerActivity.pipStatus = 3
                            sendIntent(context, position, "allVideos")
                        }
                    }
                }
            }
//
//            root.setOnLongClickListener {
//                isLongClickListenerInitialized = true
//
//                true
//            }

            if (currentItem.isChecked) {
                checkBox.isChecked = true
            }


        }


    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateVideoList(videoList: ArrayList<VideoDataClass>) {
        itemList = ArrayList()
        itemList.addAll(videoList)
        notifyDataSetChanged()
    }

    fun updateSearchList(searchList: ArrayList<VideoDataClass>) {
        itemList = ArrayList()
        itemList.addAll(searchList)
        notifyDataSetChanged()
    }

    fun getFile(position: Int): VideoDataClass {
        return itemList[position]
    }

    inner class DayHolder(binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener, View.OnLongClickListener {
        val videoTitle = binding.videoNameVideoItem
        val folderName = binding.pathNameVideoItem
        val durationText = binding.videoDurationVideoItem
        val image = binding.videoImageVideoItem
        val root = binding.root
        val checkBox = binding.checkBoxVideoItem

        init {
            root.setOnClickListener(this)
            root.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClickListener(position)
            }
        }

        override fun onLongClick(p0: View?): Boolean {
            val position = adapterPosition
            val currentFile = File(itemList[position].path)
            itemList[position].isChecked = true

            if (position != RecyclerView.NO_POSITION) {
                for (item in itemList) {
                    checkBox.visibility = View.VISIBLE
                }
                notifyDataSetChanged()
                listener.onItemLongClickListener(currentFile, position)
            }
            return true
        }

    }

}