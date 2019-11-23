package ru.sigil.fantasyradio.backend.dal.impl

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ru.sigil.fantasyradio.backend.shared.model.IDbProvider
import javax.sql.DataSource

class DbProvider(private val dataSource: DataSource) : IDbProvider {
    init {
        Database.connect(dataSource)
    }

    override fun check() = dataSource.connection.use { it.isValid(it.networkTimeout) }

    override suspend fun <T> suspendedTransaction(statement: suspend () -> T) =
        newSuspendedTransaction { statement() }
}