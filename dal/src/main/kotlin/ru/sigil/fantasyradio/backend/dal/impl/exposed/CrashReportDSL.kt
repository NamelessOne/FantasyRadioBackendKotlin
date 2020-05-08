package ru.sigil.fantasyradio.backend.dal.impl.exposed

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.jetbrains.exposed.dao.IntIdTable
import ru.sigil.fantasyradio.backend.dal.util.jsonb

object CrashReportDSL : IntIdTable("crash_report") {
    val detail = jsonb<Any>("detail", ObjectMapper().registerKotlinModule().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS))
}