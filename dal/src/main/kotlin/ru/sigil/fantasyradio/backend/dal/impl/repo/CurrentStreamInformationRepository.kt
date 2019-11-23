package ru.sigil.fantasyradio.backend.dal.impl.repo

import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import ru.sigil.fantasyradio.backend.dal.impl.exposed.CurrentStreamInformationDSL
import ru.sigil.fantasyradio.backend.shared.data.CurrentStreamInformation
import ru.sigil.fantasyradio.backend.shared.model.ICurrentStreamInformationRepository

class CurrentStreamInformationRepository : ICurrentStreamInformationRepository {
    override fun getLastCurrentStreamInformation(): CurrentStreamInformation? {
        val info = CurrentStreamInformationDSL.selectAll()
            .orderBy(CurrentStreamInformationDSL.created to SortOrder.DESC).first()
        return CurrentStreamInformation(
            info[CurrentStreamInformationDSL.id].value, info[CurrentStreamInformationDSL.imageURL],
            info[CurrentStreamInformationDSL.about], info[CurrentStreamInformationDSL.created]
        )
    }

    override fun saveCurrentStreamInformation(entity: CurrentStreamInformation) =
        CurrentStreamInformationDSL.insertAndGetId {
            it[imageURL] = entity.imageURL
            it[about] = entity.about
        }.value
}