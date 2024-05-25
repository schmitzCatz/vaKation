package net.octosystems.office.vakation.model

import net.octosystems.office.vakation.service.days
import org.threeten.extra.YearWeek
import java.time.LocalDate

data class Vacation(
    val week: YearWeek,
    val workdays: Set<LocalDate>,
    val daysOff: Set<LocalDate>,
) {
    val numberOfWeekDays: Int
        get() = week.days.size
    val numberOfWorkdays: Int
        get() = workdays.size
    val vacationDates:Set<LocalDate>
        get() = workdays
    val numberOfVacationDays: Int
        get() = vacationDates.size
    val numberOfDaysOff: Int
        get() = daysOff.size
}
