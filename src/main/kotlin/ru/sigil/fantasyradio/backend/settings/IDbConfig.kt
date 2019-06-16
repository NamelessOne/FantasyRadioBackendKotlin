package ru.sigil.fantasyradio.backend.settings

interface IDbConfig {
    val driver: String
    val url: String
    val username: String
    val password: String
}