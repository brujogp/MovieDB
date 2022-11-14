// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json    = Json(JsonConfiguration.Stable)
// val welcome = json.parse(Welcome.serializer(), jsonString)


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class SearchItemDTO(
    val page: Long? = null,
    val results: List<Result>? = null,

    @SerialName("total_pages")
    val totalPages: Long? = null,

    @SerialName("total_results")
    val totalResults: Long? = null
)

@Serializable
data class Result (
    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,

    val id: Long? = null,

    @SerialName("media_type")
    val mediaType: String? = null,

    val name: String? = null,

    @SerialName("origin_country")
    val originCountry: List<String>? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null,

    val adult: Boolean? = null,
    val gender: Long? = null,

    @SerialName("known_for")
    val knownFor: List<Result>? = null,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    val title: String? = null,
    val video: Boolean? = null
)
