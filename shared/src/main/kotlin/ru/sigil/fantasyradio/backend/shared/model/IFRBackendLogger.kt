package ru.sigil.fantasyradio.backend.shared.model

interface IFRBackendLogger {
    fun error(message: String, throwable: Throwable? = null)
    fun warn(message: String, throwable: Throwable? = null)
    fun trace(message: String)
    fun debug(message: String)
    fun info(message: String)
}