package ru.sigil.fantasyradio.backend.log

import org.slf4j.LoggerFactory
import ru.sigil.fantasyradio.backend.shared.model.IFRBackendLogger

class FRBackendLogger(name: String): IFRBackendLogger {
    private val logger = LoggerFactory.getLogger(name)

    override fun error(message: String, throwable: Throwable?) {
        if(throwable == null) logger.error(message) else logger.error(message, throwable)
    }

    override fun warn(message: String, throwable: Throwable?) {
        if(throwable == null) logger.error(message) else logger.warn(message, throwable)
    }

    override fun trace(message: String) = logger.trace(message)

    override fun debug(message: String) = logger.debug(message)

    override fun info(message: String) = logger.info(message)

}