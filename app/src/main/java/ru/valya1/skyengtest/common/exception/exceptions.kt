package ru.valya1.skyengtest.common.exception

import java.lang.Exception

open class UIException(val description: String) : Exception(description)

class DataLoadingException(text: String) : UIException(text)

class InternalAppException : Exception()