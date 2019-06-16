package ru.sigil.fantasyradio.backend.controllers

import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import ru.sigil.fantasyradio.backend.dal.IDbManager
import ru.sigil.fantasyradio.backend.di.KodeinConfig
import ru.sigil.fantasyradio.backend.di.RequestContext
import ru.sigil.fantasyradio.backend.dto.CrashReportDTO
import ru.sigil.fantasyradio.backend.dto.CurrentStreamInformationDTO

fun Application.main() {
    val kodein = KodeinConfig().configure()
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        route("/CurrentStreamInformation") {
            post("/") {
                val contextedDi = kodein.on(RequestContext())
                val repo by contextedDi.instance<IDbManager>()
                val entity = call.receive<CurrentStreamInformationDTO>()
                repo.saveCurrentStreamInformation(entity)
            }
            get("/Last") {
                val contextedDi = kodein.on(RequestContext())
                val repo by contextedDi.instance<IDbManager>()
                val gson by contextedDi.instance<Gson>()
                val last = repo.getLastCurrentStreamInformation() ?: call.respond(HttpStatusCode.NotFound)
                call.respond(gson.toJson(last))
            }
        }
        post("/Crash") {
            val contextedDi = kodein.on(RequestContext())
            val repo by contextedDi.instance<IDbManager>()
            val entity = call.receive<CrashReportDTO>()
            repo.saveCrashReport(entity)
        }
    }
}