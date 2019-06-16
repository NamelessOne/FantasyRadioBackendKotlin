package ru.sigil.fantasyradio.backend.dal

import com.google.gson.Gson
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.ds.PGSimpleDataSource
import ru.sigil.fantasyradio.backend.dal.util.jsonb
import ru.sigil.fantasyradio.backend.dto.CrashReportDTO
import ru.sigil.fantasyradio.backend.dto.CurrentStreamInformationDTO
import ru.sigil.fantasyradio.backend.settings.IDbSettings

class DbManager(settings: IDbSettings) : IDbManager {
    init {
        val source = PGSimpleDataSource()
        source.serverName = settings.url.replaceFirst("//", "")
        source.user = settings.username
        source.password = settings.password
        Database.connect(source)
    }

    override fun getLastCurrentStreamInformation() : CurrentStreamInformationDTO? {
        var res: CurrentStreamInformationDTO? = null
        transaction {
            val info = CurrentStreamInformations.selectAll()
                .orderBy(CurrentStreamInformations.created to SortOrder.DESC).first()

            //res = CurrentStreamInformationDTO(info.id.value, info.imageURL, info.about, info.created)
            res = CurrentStreamInformationDTO(info[CurrentStreamInformations.id].value, info[CurrentStreamInformations.imageURL],
                info[CurrentStreamInformations.about], info[CurrentStreamInformations.created])
        }
        return res
    }

    override fun saveCurrentStreamInformation(entity: CurrentStreamInformationDTO) {
        transaction {
            CurrentStreamInformations.insert {
                it[imageURL] = entity.imageURL
                it[about] = entity.about
            }
        }
    }

    override fun saveCrashReport(entity: CrashReportDTO) {
            transaction {
                CrashReports.insert {
                    it[detail] = entity.detail
                }
            }
    }
}

private object CurrentStreamInformations : IntIdTable("current_stream_information") {
    val imageURL = varchar("imageURL", 2048)
    val about = text("about")
    val created = datetime("created")
}

private object CrashReports: IntIdTable("crash_report") {
    val detail = jsonb<Any>("detail", Gson())
}