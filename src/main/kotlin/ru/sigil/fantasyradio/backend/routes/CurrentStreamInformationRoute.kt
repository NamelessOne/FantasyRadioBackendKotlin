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
import ru.sigil.fantasyradio.backend.dal.IDataSorce
import ru.sigil.fantasyradio.backend.shared.data.CurrentStreamInformation

fun Route.currentStreamInformation(kodeinFactory: (ApplicationCall) -> Kodein) {
    route("/CurrentStreamInformation") {
        post {
            val contextedDi = kodeinFactory(call)
            val repo by contextedDi.instance<IDataSorce>()
            val gson by contextedDi.instance<Gson>()
            val entity = gson.fromJson(call.receiveText(), CurrentStreamInformation::class.java)
            repo.saveCurrentStreamInformation(entity)
        }
        get("/Last") {
            val contextedDi = kodeinFactory(call)
            val repo by contextedDi.instance<IDataSorce>()
            val gson by contextedDi.instance<Gson>()
            val last = repo.getLastCurrentStreamInformation() ?: call.respond(HttpStatusCode.NotFound)
            call.respondText(gson.toJson(last), contentType = ContentType.parse("application/json"))
        }
    }
}