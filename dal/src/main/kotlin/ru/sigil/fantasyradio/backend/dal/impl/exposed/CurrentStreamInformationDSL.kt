package ru.sigil.fantasyradio.backend.dal.impl.exposed

import org.jetbrains.exposed.dao.IntIdTable

object CurrentStreamInformationDSL : IntIdTable("current_stream_information") {
        val imageURL = varchar("imageURL", 2048)
        val about = text("about")
        val created = datetime("created")
}