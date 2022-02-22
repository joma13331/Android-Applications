package com.example.pennydrop.data

import android.text.TextUtils
import androidx.room.TypeConverter
import com.example.pennydrop.game.AI
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class Converters {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toOffsetDateTime(value: String?) = value?.let { formatter.parse(it, OffsetDateTime::from) }

    @TypeConverter
    fun fromOffsetDateTime(dateTime: OffsetDateTime?) = dateTime?.format((formatter))

    @TypeConverter
    fun fromGameStateToInt(gameState: GameState?) = (gameState?:GameState.Unknown).ordinal

    fun fromIntToGameState(gameStateInt: Int?) = GameState.values().let { gameStateValues->
        if(gameStateInt != null && gameStateValues.any { it.ordinal==gameStateInt }){
            GameState.values()[gameStateInt]
        }else GameState.Unknown
    }

    @TypeConverter
    fun toIntList(value: String?) = value?.split(",")?.let{
        it.filter { numberString-> !TextUtils.isEmpty(numberString) }
            .map{ numberString -> numberString.toInt()}
    }?: emptyList()

    @TypeConverter
    fun fromListOfIntToString(numbers:List<Int>?) = numbers?.joinToString(",")?:""

    @TypeConverter
    fun toAI(aiId:Long?) = AI.basicAI.firstOrNull(){it.aiId== aiId}

    @TypeConverter
    fun fromAItoId(ai:AI?) = ai?.aiId
}