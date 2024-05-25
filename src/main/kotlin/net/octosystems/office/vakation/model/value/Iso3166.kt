package net.octosystems.office.vakation.model.value

/**
 * Iso 3166-2 Codes
 *
 * **see** [https://en.wikipedia.org/wiki/ISO_3166-2](https://en.wikipedia.org/wiki/ISO_3166-2)
 */
@JvmInline
value class Iso3166(val value: String) : CharSequence by value {
    init {
        require(value.isNotEmpty()) { "$value should not be empty!" }
        require(value.length == 2) { "$value does not comply to ISO-3166" }
    }
}

typealias CountryCode = Iso3166
typealias StateCode = Iso3166
