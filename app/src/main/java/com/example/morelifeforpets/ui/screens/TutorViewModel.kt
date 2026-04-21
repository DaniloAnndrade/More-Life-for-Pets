package com.example.morelifeforpets.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morelifeforpets.model.TutorDao
import com.example.morelifeforpets.model.TutorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TutorViewModel @Inject constructor(private val tutorDao: TutorDao): ViewModel(){

    val todosOsTutores: Flow<List<TutorEntity>> = tutorDao.searchTutor()
    fun salvar(tutor: TutorEntity){
        viewModelScope.launch(Dispatchers.IO){
            tutorDao.addTutor(tutor)
        }

        }

    fun Deletar(tutor: TutorEntity){
        viewModelScope.launch{
            tutorDao.deleteTutor(tutor)
        }}

}