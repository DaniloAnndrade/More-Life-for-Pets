package com.example.morelifeforpets.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName= "pet")
data class PetEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val nomeP: String,
    val tipo: String,
    val idade: Int) {

}