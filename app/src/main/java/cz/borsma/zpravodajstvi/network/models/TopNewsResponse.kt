package cz.borsma.zpravodajstvi.network.models

data class TopNewsResponse(val status : String? = null,
                           val totalResults : Int? = null,
                           val articles : List<TopNewsArticle>? = null)
