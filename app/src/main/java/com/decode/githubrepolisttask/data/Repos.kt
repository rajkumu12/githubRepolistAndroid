package com.decode.githubrepolisttask.data

import com.google.gson.annotations.SerializedName

data class Repos (
    @SerializedName("name")
    var repoName : String,
    var isSelected:Boolean,
    @SerializedName("owner")
    var owner:Owners
        ){
   fun set(value:Boolean) {         // setter
        isSelected = value
    }
}

data class Owners (
    @SerializedName("avatar_url")
    var userImage : String
        )



