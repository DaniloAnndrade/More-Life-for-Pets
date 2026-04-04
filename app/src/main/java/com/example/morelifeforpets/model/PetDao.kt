package com.example.morelifeforpets.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PetDao {
    @Insert
    fun addPet(pet: PetEntity)

    @Update
    fun updatePet(pet: PetEntity)

    @Delete
    fun delPet(pet: PetEntity)

    @Query("SELECT * FROM pet")
    fun searchPet(): List<PetEntity>
}