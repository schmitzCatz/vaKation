package net.octosystems.office.vakation.repository.entity

import java.time.DayOfWeek
import java.time.LocalDate

internal data class Configuration(
    val workdays: Set<DayOfWeek> =
        setOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
        ),
    val extraDays: Set<LocalDate> = emptySet(),
    val vacationDays: Int,
    val country: Country = Country(),
)
