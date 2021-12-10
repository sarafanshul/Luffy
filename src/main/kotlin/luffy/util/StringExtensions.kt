package luffy.util

fun String?.isOk() : Boolean =
    ! ( isNullOrEmpty() || isNullOrBlank() )