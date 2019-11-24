package ru.sigil.fantasyradio.backend.di

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zaxxer.hikari.HikariDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.multiton
import org.kodein.di.generic.provider
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.CallScope
import ru.sigil.fantasyradio.backend.shared.model.IDbProvider
import ru.sigil.fantasyradio.backend.settings.DbConfig
import ru.sigil.fantasyradio.backend.dal.IDbConfig
import ru.sigil.fantasyradio.backend.dal.impl.DbProvider
import ru.sigil.fantasyradio.backend.dal.impl.repo.CrashReportRepository
import ru.sigil.fantasyradio.backend.dal.impl.repo.CurrentStreamInformationRepository
import ru.sigil.fantasyradio.backend.log.FRBackendLogger
import ru.sigil.fantasyradio.backend.shared.model.ICrashReportsRepository
import ru.sigil.fantasyradio.backend.shared.model.ICurrentStreamInformationRepository
import ru.sigil.fantasyradio.backend.shared.model.IFRBackendLogger
import javax.sql.DataSource

object KodeinConfig {
    fun configure() = Kodein {
        //Singletons
        bind<IDbConfig>() with singleton { DbConfig() }
        bind<DataSource>() with singleton {
            val settings = instance<IDbConfig>()
            HikariDataSource().apply {
                jdbcUrl = settings.driver + settings.url
                username = settings.username
                password = settings.password
                maximumPoolSize = settings.poolSize
                settings.minIdle?.let { minimumIdle = it }
            }
        }
        bind<IFRBackendLogger>() with multiton { name: String -> FRBackendLogger(name) }

        //Scoped
        bind<IDbProvider>() with scoped(CallScope).singleton { DbProvider(instance()) }
        bind<ICrashReportsRepository>() with scoped(CallScope).singleton { CrashReportRepository() }
        bind<ICurrentStreamInformationRepository>() with scoped(CallScope).singleton { CurrentStreamInformationRepository() }

        //Providers
        bind<Gson>() with provider { Converters.registerDateTime(GsonBuilder()).create() }
    }
}