package ru.sigil.fantasyradio.backend.dal.util

import com.google.gson.Gson
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import java.sql.PreparedStatement

/**
 * Created by quangio.
 */

fun <T : Any> Table.jsonb(name: String, jsonMapper: Gson): Column<T>
        = registerColumn(name, ru.sigil.fantasyradio.backend.dal.util.Json(jsonMapper))


private class Json(private val jsonMapper: Gson) : ColumnType() {
    override fun sqlType() = "jsonb"

    override fun setParameter(stmt: PreparedStatement, index: Int, value: Any?) {
        val obj = PGobject()
        obj.type = "jsonb"
        obj.value = value as String
        stmt.setObject(index, obj)
    }

    override fun valueFromDB(value: Any): String {
        return (value as PGobject).value
    }

    override fun notNullValueToDB(value: Any): Any = jsonMapper.toJson(value)
    override fun nonNullValueToString(value: Any): String = "'${jsonMapper.toJson(value)}'"
}