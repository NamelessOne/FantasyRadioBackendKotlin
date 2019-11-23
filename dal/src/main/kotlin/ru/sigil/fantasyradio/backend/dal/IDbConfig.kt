package ru.sigil.fantasyradio.backend.dal

interface IDbConfig {
    val driver: String
    val url: String
    val username: String
    val password: String
    val poolSize: Int
    val minIdle: Int?
}