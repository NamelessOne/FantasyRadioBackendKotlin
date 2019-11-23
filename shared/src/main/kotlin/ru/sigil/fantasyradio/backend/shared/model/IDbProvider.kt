package ru.sigil.fantasyradio.backend.shared.model

interface IDbProvider {
    fun check(): Boolean
    suspend fun <T> suspendedTransaction(statement: suspend () -> T): T
}