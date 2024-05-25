package net.octosystems.office.vakation.model

import net.octosystems.office.vakation.model.value.CountryCode
import net.octosystems.office.vakation.model.value.StateCode
import java.util.Locale

data class Country(
    val code: CountryCode = CountryCode(Locale.getDefault().country),
    val state: StateCode? = null
) {
    constructor(code: String) : this(Locale.forLanguageTag(code))

    constructor(locale: Locale) : this(
        CountryCode(locale.language),
        if (locale.variant.isNotEmpty()) StateCode(locale.variant) else null
    )
}
