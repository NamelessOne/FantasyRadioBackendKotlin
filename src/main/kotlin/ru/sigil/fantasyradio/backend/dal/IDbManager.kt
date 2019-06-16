package ru.sigil.fantasyradio.backend.dal

import ru.sigil.fantasyradio.backend.dto.CrashReportDTO
import ru.sigil.fantasyradio.backend.dto.CurrentStreamInformationDTO

interface IDbManager {
    fun getLastCurrentStreamInformation() : CurrentStreamInformationDTO?
    fun saveCurrentStreamInformation(entity: CurrentStreamInformationDTO)
    fun saveCrashReport(entity: CrashReportDTO)
}