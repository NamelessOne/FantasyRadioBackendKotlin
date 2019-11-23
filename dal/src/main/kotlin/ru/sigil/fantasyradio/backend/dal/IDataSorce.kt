package ru.sigil.fantasyradio.backend.dal

import ru.sigil.fantasyradio.backend.shared.data.CrashReport
import ru.sigil.fantasyradio.backend.shared.data.CurrentStreamInformation

interface IDataSorce {
    fun getLastCurrentStreamInformation() : CurrentStreamInformation?
    fun saveCurrentStreamInformation(entity: CurrentStreamInformation)
    fun saveCrashReport(entity: CrashReport)
}