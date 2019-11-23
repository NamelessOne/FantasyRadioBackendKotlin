package ru.sigil.fantasyradio.backend.dal.impl.exposed

import com.google.gson.Gson
import org.jetbrains.exposed.dao.IntIdTable
import ru.sigil.fantasyradio.backend.dal.util.jsonb

object CrashReportDSL : IntIdTable("crash_report") {
    val detail = jsonb<Any>("detail", Gson())
}