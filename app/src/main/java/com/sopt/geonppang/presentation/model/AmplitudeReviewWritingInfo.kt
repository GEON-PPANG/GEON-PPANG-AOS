package com.sopt.geonppang.presentation.model

import com.sopt.geonppang.presentation.type.KeyWordType
import com.sopt.geonppang.presentation.type.LikeType

data class AmplitudeReviewWritingInfo(
    val likeType: LikeType?,
    val userKeyWordType: Map<KeyWordType, Boolean>,
    val reviewText: String
)