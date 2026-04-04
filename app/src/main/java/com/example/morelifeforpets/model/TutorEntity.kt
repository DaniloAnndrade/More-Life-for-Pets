package com.example.morelifeforpets.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "Tutor")
data class TutorEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val nomeT: String,
    val email: String,
    val cpf: String,
    val tell: String) {

}