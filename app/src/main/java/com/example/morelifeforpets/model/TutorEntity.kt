package com.example.morelifeforpets.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID



@Entity(tableName = "Tutor",
    indices = [Index(value = ["cpf"], unique = true)])
data class TutorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int =  0,
    val nomeT: String,
    val email: String,
    val cpf: String,
    val tell: String) {

}