package ru.sigil.fantasyradio.backend.settings

import com.typesafe.config.ConfigFactory

class DbConfig: IDbConfig {
    override val driver: String
        get() = ConfigFactory.load().getString("db.default.driver")
    override val url: String
        get() = ConfigFactory.load().getString("db.default.url")
    override val username: String
        get() = ConfigFactory.load().getString("db.default.username")
    override val password: String
        get() = ConfigFactory.load().getString("db.default.password")
}