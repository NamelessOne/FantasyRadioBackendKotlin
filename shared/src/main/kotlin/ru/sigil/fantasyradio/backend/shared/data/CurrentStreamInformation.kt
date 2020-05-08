package ru.sigil.fantasyradio.backend.shared.data

import org.joda.time.DateTime

data class CurrentStreamInformation(var id: Int?, var imageURL: String, var about: String, var created: DateTime?)