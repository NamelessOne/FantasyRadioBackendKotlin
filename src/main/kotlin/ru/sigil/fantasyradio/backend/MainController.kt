package ru.sigil.fantasyradio.backend

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        route("/CurrentStreamInformation") {
            post("/") {
                //TODO
            }
            get("/Last") {
                //TODO
            }
        }
        post("/Crash") {

        }
    }
}