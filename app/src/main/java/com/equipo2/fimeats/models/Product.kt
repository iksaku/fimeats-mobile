package com.equipo2.fimeats.models

import java.io.Serializable

data class Product(
    val id: String,
    val cafeteria: Cafeteria,
    val name: String,
    val quantity: String,
    val price: Double,
    val image: String,
    val createdAt: String,
    val updatedAt: String
) : Serializable
