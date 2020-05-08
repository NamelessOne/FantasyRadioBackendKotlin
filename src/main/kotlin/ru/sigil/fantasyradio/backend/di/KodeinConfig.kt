package ru.sigil.fantasyradio.backend.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
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
    private val mapper = ObjectMapper().registerKotlinModule().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(JodaModule())

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
        bind<IDbProvider>() with singleton { DbProvider(instance()) }
        bind<IFRBackendLogger>() with multiton { name: String -> FRBackendLogger(name) }

        //Scoped
        bind<ICrashReportsRepository>() with scoped(CallScope).singleton { CrashReportRepository() }
        bind<ICurrentStreamInformationRepository>() with scoped(CallScope).singleton { CurrentStreamInformationRepository() }

        //Providers
        bind<ObjectReader>() with provider { mapper.reader() }
        bind<ObjectWriter>() with provider { mapper.writer() }
    }
}