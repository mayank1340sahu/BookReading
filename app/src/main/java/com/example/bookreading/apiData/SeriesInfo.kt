package com.example.bookreading.apiData

data class SeriesInfo(
    val bookDisplayNumber: String,
    val kind: String,
    val shortSeriesBookTitle: String,
    val volumeSeries: List<VolumeSery>
)