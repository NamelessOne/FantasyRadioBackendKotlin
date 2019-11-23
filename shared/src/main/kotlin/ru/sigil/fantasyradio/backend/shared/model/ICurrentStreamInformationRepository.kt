package ru.sigil.fantasyradio.backend.shared.model

import ru.sigil.fantasyradio.backend.shared.data.CurrentStreamInformation

interface ICurrentStreamInformationRepository {
    fun getLastCurrentStreamInformation() : CurrentStreamInformation?
    fun saveCurrentStreamInformation(entity: CurrentStreamInformation): Int
}