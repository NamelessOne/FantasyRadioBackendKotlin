package ru.sigil.fantasyradio.backend.routes

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import ru.sigil.fantasyradio.backend.shared.model.IDbProvider
import ru.sigil.fantasyradio.backend.shared.data.CurrentStreamInformation
import ru.sigil.fantasyradio.backend.shared.model.ICurrentStreamInformationRepository

fun Route.currentStreamInformation(kodeinFactory: (ApplicationCall) -> Kodein) {
    route("/CurrentStreamInformation") {
        post {
            val contextedDi = kodeinFactory(call)
            val dbProvider by contextedDi.instance<IDbProvider>()
            val repo by contextedDi.instance<ICurrentStreamInformationRepository>()
            val entity = call.receive<CurrentStreamInformation>()
            dbProvider.suspendedTransaction {
                repo.saveCurrentStreamInformation(entity)
            }
            call.respond(HttpStatusCode.OK)
        }
        get("/Last") {
            val contextedDi = kodeinFactory(call)
            val dbProvider by contextedDi.instance<IDbProvider>()
            val repo by contextedDi.instance<ICurrentStreamInformationRepository>()
            dbProvider.suspendedTransaction {
                val last = repo.getLastCurrentStreamInformation() ?: call.respond(HttpStatusCode.NotFound)
                call.respond(last)
            }
        }
    }
}