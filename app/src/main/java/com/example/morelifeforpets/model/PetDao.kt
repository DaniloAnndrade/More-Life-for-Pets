package com.example.morelifeforpets.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Insert
    suspend fun addPet(pet: PetEntity)

    @Update
    suspend fun updatePet(pet: PetEntity)

    @Delete
    suspend fun delPet(pet: PetEntity)

    @Query("SELECT * FROM pet")
    fun searchPet(): Flow<List<PetEntity>>
}