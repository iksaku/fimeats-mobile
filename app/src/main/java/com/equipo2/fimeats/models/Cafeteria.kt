package com.equipo2.fimeats.models

import java.io.Serializable

data class Cafeteria(
    val id: String,
    val faculty: Faculty,
    val name: String,
    val createdAt: String,
    val updatedAt: String
) : Serializable
