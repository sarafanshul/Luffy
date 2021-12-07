package luffy.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Provides a static logger of respective class
 *
 * Usage
 * ```Kotlin
 * @JvmStatic
 * private val log : Logger = logger()
 * ```
 */
inline fun <reified R : Any> R.logger(): Logger =
    LoggerFactory.getLogger(this::class.java.name.substringBefore("\$Companion"))