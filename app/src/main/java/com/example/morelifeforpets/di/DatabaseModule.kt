package com.example.morelifeforpets.di

import android.content.Context
import androidx.room.Room
import com.example.morelifeforpets.model.Database
import com.example.morelifeforpets.model.PetDao
import com.example.morelifeforpets.model.TutorDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "clinicaMore_db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideTutorDao(database: Database) : TutorDao {
        return database.TutorDao()

    }

    @Provides
    @Singleton
    fun privedePetDao( database:Database): PetDao {
        return database.PetDao()
    }

}