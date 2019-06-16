package ru.sigil.fantasyradio.backend.dto

import org.joda.time.DateTime

data class CurrentStreamInformationDTO(var id: Int, var imageURL: String, var about: String, var created: DateTime)