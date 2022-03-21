package com.example.idletapperversion3.savedata

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "savedata")
data class SaveData (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull @ColumnInfo(name = "taps") val taps: Int,
    @NonNull @ColumnInfo(name = "prestige") val prestige: Float,
    @NonNull @ColumnInfo(name = "tap_power") val tapPower: Int,
    @NonNull @ColumnInfo(name = "idle_power") val idlePower: Int,
    @NonNull @ColumnInfo(name = "tap_upgrade_small") val tapUpgradeSmall: Int,
    @NonNull @ColumnInfo(name = "tap_upgrade_med") val tapUpgradeMed: Int,
    @NonNull @ColumnInfo(name = "tap_upgrade_big") val tapUpgradeBig: Int,
    @NonNull @ColumnInfo(name = "idle_upgrade_small") val idleUpgradeSmall: Int,
    @NonNull @ColumnInfo(name = "idle_upgrade_med") val idleUpgradeMed: Int,
    @NonNull @ColumnInfo(name = "idle_upgrade_big") val idleUpgradeBig: Int
    )