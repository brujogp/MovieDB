package com.soyjoctan.moviedb.presentation.models


data class ClassBaseItemModel(
    override var itemName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var itemId: Long?,
    override var backdropPath: String?
) : PresentationModelParent()

inline fun convertItems(itemsToConvert: ArrayList<out PresentationModelParent>): ArrayList<ClassBaseItemModel> {
    val items: ArrayList<ClassBaseItemModel> = arrayListOf()

    itemsToConvert.forEach {
        items.add(
            ClassBaseItemModel(
                itemName = it.itemName,
                posterPathImage = it.posterPathImage,
                popularity = it.popularity?.toDouble(),
                itemId = it.itemId,
                backdropPath = it.backdropPath
            )
        )
    }

    return items
}