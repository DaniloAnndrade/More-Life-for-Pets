package com.example.morelifeforpets.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities= [TutorEntity::class,PetEntity::class],version = 2)
abstract class Database : RoomDatabase() {

    abstract fun TutorDao(): TutorDao

    abstract fun PetDao(): PetDao

}