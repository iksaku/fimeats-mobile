package com.equipo2.fimeats.viewmodels

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.equipo2.fimeats.database.providers.Faculties
import com.equipo2.fimeats.models.Faculty

class FacultiesViewModel(): ViewModel() {
    var db: SQLiteDatabase? = null
    val listFaculties: MutableLiveData<List<Faculty>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()
    var getAll: Boolean? = null

    fun refresh() {
        getFaculties()
    }

    private fun getFaculties() {
        listFaculties.postValue(db?.let { Faculties.get(it) })
        processFinished()
    }

    fun processFinished() {
        isLoading.value = false
    }

}