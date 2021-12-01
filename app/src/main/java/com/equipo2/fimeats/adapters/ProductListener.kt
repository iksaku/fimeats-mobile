package com.equipo2.fimeats.adapters

import com.equipo2.fimeats.models.Product

interface ProductListener {
    fun onClick(product: Product, position: Int)
}