package com.soyjoctan.moviedb.data.model.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemsLiked(
    @SerialName("item_id")
    val itemId: Long,
    @SerialName("item_name")
    val itemName: String,
    @SerialName("when_liked")
    val whenLiked: String
)
