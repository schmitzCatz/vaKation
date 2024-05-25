package net.octosystems.office.vakation.repository.entity

import java.util.Locale

internal data class Country(val country: String = Locale.getDefault().country, val state: String? = Locale.getDefault().variant)
