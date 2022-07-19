package com.ruiaaryan.memeshare

data class MemeResponse(
    val author: String = "",
    val nsfw: Boolean = false,
    val postLink: String = "",
    val preview: List<String> = listOf(),
    val spoiler: Boolean = false,
    val subreddit: String = "",
    val title: String = "",
    val ups: Int = 0,
    val url: String = ""
)