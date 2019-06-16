package ru.sigil.fantasyradio.backend.controllers

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.GsonBuilder
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import ru.sigil.fantasyradio.backend.dal.DbManager
import ru.sigil.fantasyradio.backend.dto.CrashReportDTO
import ru.sigil.fantasyradio.backend.dto.CurrentStreamInformationDTO
import ru.sigil.fantasyradio.backend.settings.DbSettings

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {

        }
    }
    install(Routing) {
        route("/CurrentStreamInformation") {
            post("/") {
                val entity = call.receive<CurrentStreamInformationDTO>()
                DbManager(DbSettings()).saveCurrentStreamInformation(entity)
            }
            get("/Last") {
                val last = DbManager(DbSettings()).getLastCurrentStreamInformation() ?: call.respond(HttpStatusCode.NotFound)
                call.respond(Converters.registerDateTime(GsonBuilder()).create().toJson(last))
            }
        }
        post("/Crash") {
            val entity = call.receive<CrashReportDTO>()
            DbManager(DbSettings()).saveCrashReport(entity)
        }
    }
}