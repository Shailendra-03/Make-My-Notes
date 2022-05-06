package com.codingsp.makemynotes.model

data class Color(val color:Int)
object Colors{

    fun getColors():ArrayList<Color> {
        val list=Note.noteColors
        val colorList = ArrayList<Color>()
        for(i in list){
            colorList.add(Color(i))
        }
        return colorList
    }
}
