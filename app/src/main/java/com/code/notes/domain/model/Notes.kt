package com.code.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.code.notes.ui.theme.*

@Entity
data class Notes(val title:String, val description:String, val timeStamp:Long, val color:Int, @PrimaryKey val id:Int?=null){
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNotesException(message:String): Exception(message)
