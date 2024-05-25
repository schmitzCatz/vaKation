package net.octosystems.office.vakation.repository

import de.focus_shift.jollyday.core.Holiday
import de.focus_shift.jollyday.core.HolidayCalendar
import de.focus_shift.jollyday.core.HolidayManager
import de.focus_shift.jollyday.core.ManagerParameters
import net.octosystems.office.vakation.model.Country
import java.time.LocalDate
import java.time.Year

class JollydayRepository : HolidayRepository {
    override fun findHolidaysByYear(
        year: Year,
        country: Country,
    ): Set<LocalDate> {
        val calendar = HolidayCalendar.entries.first { it.id.equals(country.code.value.uppercase()) }
        val parameter = ManagerParameters.create(calendar)
        val manager = HolidayManager.getInstance(parameter)
        return manager.getHolidays(year.value, country.state?.value?.uppercase() ?: "").map(Holiday::getDate).toSet()
    }
}
