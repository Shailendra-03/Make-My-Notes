package com.codingsp.makemynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.codingsp.makemynotes.databinding.ItemColorsBinding
import com.codingsp.makemynotes.model.Color

class ColorsSpinnerAdapter(
    context: Context,colorList:ArrayList<Color>
) :ArrayAdapter<Color>(context,0,colorList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup):View{
        val color = getItem(position)
        val binding=ItemColorsBinding.inflate(LayoutInflater.from(context),parent,false)
        binding.ivColor.setImageResource(color!!.color)
        return binding.root
    }
}