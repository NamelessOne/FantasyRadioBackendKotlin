package ru.sigil.fantasyradio.backend.di

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.CallScope
import ru.sigil.fantasyradio.backend.dal.DbManager
import ru.sigil.fantasyradio.backend.dal.IDbManager
import ru.sigil.fantasyradio.backend.settings.DbConfig
import ru.sigil.fantasyradio.backend.settings.IDbConfig

class KodeinConfig {
    fun configure() = Kodein {
        bind<IDbConfig>() with singleton { DbConfig() }
        bind<IDbManager>() with scoped(CallScope).singleton { DbManager(instance()) }
        bind<Gson>() with provider { Converters.registerDateTime(GsonBuilder()).create() }
    }
}