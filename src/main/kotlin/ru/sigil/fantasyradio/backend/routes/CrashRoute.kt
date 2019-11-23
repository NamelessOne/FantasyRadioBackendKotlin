package ru.sigil.fantasyradio.backend.routes

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.request.receiveText
import io.ktor.routing.Route
import io.ktor.routing.post
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import ru.sigil.fantasyradio.backend.dal.IDataSorce
import ru.sigil.fantasyradio.backend.shared.data.CrashReport

fun Route.crash(kodeinFactory: (ApplicationCall) -> Kodein) {
    post("/Crash") {
        val contextedDi = kodeinFactory(call)
        val repo by contextedDi.instance<IDataSorce>()
        val entity = CrashReport(0, call.receiveText())
        repo.saveCrashReport(entity)
    }
}