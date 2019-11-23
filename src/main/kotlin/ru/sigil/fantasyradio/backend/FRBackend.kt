package ru.sigil.fantasyradio.backend

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.routing.Routing
import org.kodein.di.generic.on
import ru.sigil.fantasyradio.backend.di.KodeinConfig
import ru.sigil.fantasyradio.backend.routes.crash
import ru.sigil.fantasyradio.backend.routes.currentStreamInformation

fun Application.main() {
    val kodein = KodeinConfig.configure()
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        currentStreamInformation { call -> kodein.on(call) }
        crash { call -> kodein.on(call) }
    }
}