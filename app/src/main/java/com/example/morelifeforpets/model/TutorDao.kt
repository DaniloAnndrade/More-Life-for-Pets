package com.example.morelifeforpets.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TutorDao {
    @Insert
    suspend fun addTutor(tutor: TutorEntity)

    @Update
    suspend fun updateTutor(tutor: TutorEntity)

    @Delete
    suspend fun deleteTutor(tutor: TutorEntity)

    @Query("SELECT * FROM Tutor")
    fun searchTutor(): Flow<List<TutorEntity>>

}