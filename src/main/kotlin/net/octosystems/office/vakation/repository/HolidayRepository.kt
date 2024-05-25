package net.octosystems.office.vakation.repository

import net.octosystems.office.vakation.model.Country
import java.time.LocalDate
import java.time.Year

interface HolidayRepository {
    fun findHolidaysByYear(
        year: Year,
        country: Country,
    ): Set<LocalDate>
}
