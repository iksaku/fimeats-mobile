package com.equipo2.fimeats.adapters

import com.equipo2.fimeats.models.Faculty

interface FacultyListener {
    fun onClick(faculty: Faculty, position: Int)
}