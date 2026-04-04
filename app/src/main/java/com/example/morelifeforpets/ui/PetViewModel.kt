package com.example.morelifeforpets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morelifeforpets.model.PetDao
import com.example.morelifeforpets.model.PetEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(private val petDao: PetDao): ViewModel() {
    val todasOsPet: Flow<List<PetEntity>> = petDao.searchPet()
    fun salvarPet(pet: PetEntity){
        viewModelScope.launch{
            petDao.addPet(pet)
        }
    }
}