package com.equipo2.fimeats.models

import java.io.Serializable


data class Faculty(
    val id: String,
    val name: String,
    val shortName: String,
    val mapsUrl: String,
    val createdAt: String,
    val updatedAt: String
) : Serializable
