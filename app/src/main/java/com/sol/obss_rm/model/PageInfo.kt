package com.sol.obss_rm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PageInfo (
    var next : String,
    var prev : String
): Parcelable