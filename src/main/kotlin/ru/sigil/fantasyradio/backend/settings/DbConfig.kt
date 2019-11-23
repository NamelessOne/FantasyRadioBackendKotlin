package ru.sigil.fantasyradio.backend.settings

import com.typesafe.config.ConfigFactory
import ru.sigil.fantasyradio.backend.dal.IDbConfig

class DbConfig: IDbConfig {
    private val config by lazy { ConfigFactory.load() }
    override val driver: String by lazy { config.getString("db.default.driver") }
    override val url: String by lazy { config.getString("db.default.url") }
    override val username: String by lazy { config.getString("db.default.username") }
    override val password: String by lazy { config.getString("db.default.password") }
    override val poolSize: Int by lazy { config.getInt("db.default.poolSize") }
    override val minIdle: Int? by lazy {
        if (config.hasPath("db.default.minIdle")) config.getInt("db.default.minIdle") else null
    }
}