package com.example.idletapperversion3.storeData

import com.example.idletapperversion3.model.StoreItem

class Datasource {
    fun loadData(): List<StoreItem> {
        return listOf(
            StoreItem(10, 1.15f, 1),
            StoreItem(100, 1.15f, 5),
            StoreItem(1000, 1.35f, 20),
            StoreItem(20, 1.25f, 1),
            StoreItem(200, 1.25f, 5),
            StoreItem(2000, 1.5f, 20)
        )
    }
}