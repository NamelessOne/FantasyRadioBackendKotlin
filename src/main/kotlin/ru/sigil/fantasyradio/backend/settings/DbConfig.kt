package ru.sigil.fantasyradio.backend.settings

import com.typesafe.config.ConfigFactory
import ru.sigil.fantasyradio.backend.dal.IDbConfig

class DbConfig: IDbConfig {
    override val driver: String by lazy { ConfigFactory.load().getString("db.default.driver") }
    override val url: String by lazy { ConfigFactory.load().getString("db.default.url") }
    override val username: String by lazy { ConfigFactory.load().getString("db.default.username") }
    override val password: String by lazy { ConfigFactory.load().getString("db.default.password") }
}