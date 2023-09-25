package com.ssafy.jutopia.android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ssafy.jutopia.android.databinding.GridviewItemBinding

class GridviewAdapter(val context: Context, val img_list : Array<Int>, val text_list : Array<String>) : BaseAdapter(){
    override fun getCount(): Int {
        return img_list.size
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding : GridviewItemBinding = if (convertView == null) {
            GridviewItemBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            GridviewItemBinding.bind(convertView)
        }

        binding.mainGridviewText.text = text_list[position]
        binding.mainGridviewImg.setImageResource(img_list[position])

        return binding.root
    }
}