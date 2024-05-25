package net.octosystems.office.vakation.service

import net.octosystems.office.vakation.model.VaKationConfiguration
import net.octosystems.office.vakation.model.Vacation
import net.octosystems.office.vakation.model.VacationPlan
import net.octosystems.office.vakation.repository.HolidayRepository
import org.threeten.extra.YearWeek
import java.time.LocalDate

class DefaultVacationService(
    private val holidayRepository: HolidayRepository,
) : VacationService {

    override fun getWorkdaysPerWeek(config: VaKationConfiguration): Map<YearWeek, Int> {
        val workdays = config.workdays
        val holidays = holidayRepository.findHolidaysByYear(config.year, config.country)
        val extras = config.extraDays
        val result = mutableMapOf<YearWeek, Int>()

        config.year.weeks.forEachRemaining {
            it.days.forEach { date ->
                if (workdays.contains(date.dayOfWeek)) {
                    if (workdays.contains(date.dayOfWeek)) {
                        if (holidays.contains(date) || extras.contains(date)) {
                            result.compute(it) { _, v -> if (v == null) 1 else v + 1 }
                        }
                    }
                }
            }
        }

        return result.toMap()
    }

    override fun createVacationPlan(config: VaKationConfiguration): VacationPlan {
        val holidays = holidayRepository.findHolidaysByYear(config.year, config.country)
        val extras = config.extraDays

        val vacations = mutableMapOf<YearWeek, Vacation>()

        config.year.weeks.forEachRemaining {
            val workdays = mutableSetOf<LocalDate>()
            val daysOff = mutableSetOf<LocalDate>()
            it.days.forEach { date ->
                if (holidays.contains(date) || extras.contains(date)) {
                    daysOff.add(date)
                } else {
                    if (config.workdays.contains(date.dayOfWeek)) {
                        workdays.add(date)
                    }
                }
            }
            if (daysOff.isNotEmpty()) {
                vacations[it] = Vacation(it, workdays, daysOff)
            }
        }

        return VacationPlan(config.year, vacations)
    }
}
