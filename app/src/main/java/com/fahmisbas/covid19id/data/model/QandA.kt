package com.fahmisbas.covid19id.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class QandA(val title: String, val subtitle: String) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

@Entity
data class MythBuster(val url: String?) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
