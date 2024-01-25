package com.example.bookreading.apiData

data class PanelizationSummary(
    val containsEpubBubbles: Boolean,
    val containsImageBubbles: Boolean,
    val epubBubbleVersion: String,
    val imageBubbleVersion: String
)