package cz.borsma.zpravodajstvi.model

import cz.borsma.zpravodajstvi.R

data class NewsData(val id:Int, val image:Int = R.drawable.breaking_news, val author:String, val title:String, val description:String, val publishedAt:String)