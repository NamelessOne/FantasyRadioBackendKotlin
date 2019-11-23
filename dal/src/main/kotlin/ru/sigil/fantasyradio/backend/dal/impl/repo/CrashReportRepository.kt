package ru.sigil.fantasyradio.backend.dal.impl.repo

import org.jetbrains.exposed.sql.insertAndGetId
import ru.sigil.fantasyradio.backend.dal.impl.exposed.CrashReportDSL
import ru.sigil.fantasyradio.backend.shared.data.CrashReport
import ru.sigil.fantasyradio.backend.shared.model.ICrashReportsRepository

class CrashReportRepository: ICrashReportsRepository {
    override fun saveCrashReport(entity: CrashReport) =
        CrashReportDSL.insertAndGetId { it[detail] = entity.detail }.value
}