package ru.sigil.fantasyradio.backend.routes

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import ru.sigil.fantasyradio.backend.shared.model.IDbProvider
import ru.sigil.fantasyradio.backend.shared.data.CrashReport
import ru.sigil.fantasyradio.backend.shared.model.ICrashReportsRepository

fun Route.crash(kodeinFactory: (ApplicationCall) -> Kodein) {
    post("/Crash") {
        val contextedDi = kodeinFactory(call)
        val dbProvider by contextedDi.instance<IDbProvider>()
        val repo by contextedDi.instance<ICrashReportsRepository>()
        val entity = CrashReport(0, call.receiveText())
        dbProvider.suspendedTransaction {
            repo.saveCrashReport(entity)
        }
        call.respond(HttpStatusCode.OK)
    }
}