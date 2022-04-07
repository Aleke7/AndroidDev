package com.project.mvvmtodo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "category_table")
data class Category(
    val title: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
}