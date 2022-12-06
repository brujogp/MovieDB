package com.soyjoctan.moviedb.presentation.models


data class MovieCreditsModel(
    val cast: ArrayList<Cast> = arrayListOf(),
    val crew: ArrayList<Crew> = arrayListOf(),
    val actors: ArrayList<Cast> = arrayListOf(),
    val directors: ArrayList<Crew> = arrayListOf(),

)

data class Cast(
    val adult: Boolean? = null,
    val gender: String? = null,
    val character: String? = null,
    val name: String? = null,
    override var itemName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var itemId: Long?,
    override var backdropPath: String?,
) : PresentationModelParent()

data class Crew(
    val department: String? = null,
    val job: String? = null,
    val name: String? = null,
    val originalName: String? = null
)

enum class CrewTypes(val type: String) {
    ACTORS("Acting"),
    DIRECTOR("Directing"),
    CREW("Crew"),
    PRODUCTION("Production"),
    ART("Art")
}