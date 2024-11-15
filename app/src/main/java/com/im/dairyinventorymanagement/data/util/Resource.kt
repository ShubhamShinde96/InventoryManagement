package com.shubham.newsapiclientproject.data.util


// In kotlin "SealedClasses" allow us to represent hierarchies in the same file or as nested classes
// Therefore we have selected the "SealedClass" type while creating this class

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T): Resource<T>(data)
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)

    // We have copied this boilerplate class code from google jetpack documentation

//     https://developer.android.com/jetpack/guide

}
