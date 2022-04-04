package com.example.idletapperversion3.model

// Why not a data class?
class StoreItem(
    val baseCost: Int, val costIncreaseFactor: Float,
    val upgradePowerBoost: Int
) // Can remove braces if unused