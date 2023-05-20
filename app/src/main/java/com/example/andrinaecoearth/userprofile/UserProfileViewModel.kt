package com.example.andrinaecoearth.userprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserProfileViewModel : ViewModel() {


    val profileUsernameInput: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val profileAgeInput: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val profileBirthday: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val profileBioText : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}