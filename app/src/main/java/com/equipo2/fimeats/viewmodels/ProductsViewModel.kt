package com.equipo2.fimeats.viewmodels

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.equipo2.fimeats.database.providers.Products
import com.equipo2.fimeats.models.Cafeteria
import com.equipo2.fimeats.models.Product

class ProductsViewModel(): ViewModel() {
    var db: SQLiteDatabase? = null
    val listProducts: MutableLiveData<List<Product>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()
    public lateinit var cafeteria: Cafeteria

    fun refresh() {
        getProducts()
    }

    private fun getProducts() {
        listProducts.postValue(db?.let { Products.get(it, this.cafeteria) })
        processFinished()
    }

    fun processFinished() {
        isLoading.value = false
    }
}