package com.example.morelifeforpets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morelifeforpets.model.TutorDao
import com.example.morelifeforpets.model.TutorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TutorViewModel @Inject constructor(private val tutorDao: TutorDao): ViewModel(){
    fun salvar(tutor: TutorEntity){
        viewModelScope.launch{
            tutorDao.addTutor(tutor)
        }
    }
}