package ru.sigil.fantasyradio.backend.settings

interface IDbSettings {
    val driver: String
    val url: String
    val username: String
    val password: String
}