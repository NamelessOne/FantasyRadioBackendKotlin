package ru.sigil.fantasyradio.backend.routes

import com.google.gson.Gson
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
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
            val gson by contextedDi.instance<Gson>()
            val entity = gson.fromJson(call.receiveText(), CurrentStreamInformation::class.java)
            dbProvider.suspendedTransaction {
                repo.saveCurrentStreamInformation(entity)
            }
        }
        get("/Last") {
            val contextedDi = kodeinFactory(call)
            val dbProvider by contextedDi.instance<IDbProvider>()
            val repo by contextedDi.instance<ICurrentStreamInformationRepository>()
            val gson by contextedDi.instance<Gson>()
            dbProvider.suspendedTransaction {
                val last = repo.getLastCurrentStreamInformation() ?: call.respond(HttpStatusCode.NotFound)
                call.respondText(gson.toJson(last), contentType = ContentType.parse("application/json"))
            }
        }
    }
}