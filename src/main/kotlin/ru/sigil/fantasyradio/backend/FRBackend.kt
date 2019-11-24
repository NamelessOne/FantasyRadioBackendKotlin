package ru.sigil.fantasyradio.backend

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import ru.sigil.fantasyradio.backend.di.KodeinConfig
import ru.sigil.fantasyradio.backend.routes.crash
import ru.sigil.fantasyradio.backend.routes.currentStreamInformation
import ru.sigil.fantasyradio.backend.shared.model.IFRBackendLogger

fun Application.main() {
    val kodein = KodeinConfig.configure()
    install(DefaultHeaders)
    install(CallLogging)
    install(StatusPages) {
        exception<Throwable> {
            val logger by kodein.instance<IFRBackendLogger>()
            logger.error("Unhandled exception", it)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
    install(Routing) {
        currentStreamInformation { call -> kodein.on(call) }
        crash { call -> kodein.on(call) }
    }
}