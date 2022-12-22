package com.soyjoctan.moviedb.data.model.entities

import com.soyjoctan.moviedb.presentation.models.PresentationModelParent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemLiked(
    @SerialName("item_id")
    override var itemId: Long?,
    @SerialName("item_name")
    override var itemName: String?,
    @SerialName("poster_path_image")
    override var posterPathImage: String?,
    @SerialName("popularity")
    override var popularity: Double?,
    @SerialName("backdrop_path")
    override var backdropPath: String?,
    val genres: String,
    val dateAdded: String,
    val fromListToWatch: Boolean
) : PresentationModelParent()
