package com.example.morelifeforpets.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName= "pet",
    foreignKeys = [ForeignKey(entity = TutorEntity::class,
        parentColumns = ["cpf"],//Pega a variavel do arquivo Pai no Tutor
        childColumns = [ "tutorCpf"], //Pega a Variavel do aquivo filho no Pet
        onDelete = ForeignKey.CASCADE //Deleta o Tutor junto com o Pet associado
                 )])

data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 ,
    val nomeP: String,
    val tipo: String,
    val idade: Int,
    val tutorCpf :String){

}