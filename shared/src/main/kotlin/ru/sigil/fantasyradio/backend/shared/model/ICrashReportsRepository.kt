package ru.sigil.fantasyradio.backend.shared.model

import ru.sigil.fantasyradio.backend.shared.data.CrashReport

interface ICrashReportsRepository {
    fun saveCrashReport(entity: CrashReport): Int
}