package com.example.idletapperversion3.model

class StoreItem(val stringResourceID: Int, val baseCost: Int,
                val costIncreaseFactor: Float, val idle: Boolean,
                val upgradePowerBoost: Int, val upgradeIndex: Int) {
}