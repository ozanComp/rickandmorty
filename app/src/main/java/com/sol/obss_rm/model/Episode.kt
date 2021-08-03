package com.sol.obss_rm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode (
    var id : Int,
    var name: String,
    var air_date : String
): Parcelable