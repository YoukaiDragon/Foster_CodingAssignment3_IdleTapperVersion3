package com.example.idletapperversion3.storeData

import com.example.idletapperversion3.R
import com.example.idletapperversion3.model.StoreItem

class Datasource {
    fun loadData(): List<StoreItem> {
        return listOf<StoreItem>(
            StoreItem(R.string.small_tapper, 10, 1.15f, false, 1, 0),
            StoreItem(R.string.med_tapper, 100, 1.15f, false, 5, 1),
            StoreItem(R.string.big_tapper, 1000, 1.35f, false, 20, 2),
            StoreItem(R.string.small_idler, 20, 1.25f, true, 1, 3),
            StoreItem(R.string.med_idler, 200, 1.25f, true, 5, 4),
            StoreItem(R.string.big_idler, 2000, 1.5f, true, 20, 5)
        )
    }
}