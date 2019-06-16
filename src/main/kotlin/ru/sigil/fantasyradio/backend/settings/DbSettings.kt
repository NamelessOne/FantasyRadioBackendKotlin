package ru.sigil.fantasyradio.backend.settings

import com.typesafe.config.ConfigFactory

class DbSettings: IDbSettings {
    override val driver: String
        get() = ConfigFactory.load().getString("db.default.driver")
    override val url: String
        get() = ConfigFactory.load().getString("db.default.url")
    override val username: String
        get() = ConfigFactory.load().getString("db.default.username")
    override val password: String
        get() = ConfigFactory.load().getString("db.default.password")
}