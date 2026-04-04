package com.example.morelifeforpets.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TutorDao {
    @Insert
    fun addTutor(tutor: TutorEntity)

    @Update
    fun updateTutor(tutor: TutorEntity)

    @Delete
    fun deleteTutor(tutor: TutorEntity)

    @Query("SELECT * FROM Tutor")
    fun searchTutor(): List<TutorEntity>

}