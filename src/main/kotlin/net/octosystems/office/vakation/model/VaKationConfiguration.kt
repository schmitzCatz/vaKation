package net.octosystems.office.vakation.model

import net.octosystems.office.vakation.model.value.CountryCode
import net.octosystems.office.vakation.model.value.VacationDays
import net.octosystems.office.vakation.model.ValueSourceKey.ArgumentSourceKey
import net.octosystems.office.vakation.model.ValueSourceKey.OptionSourceKey
import net.octosystems.office.vakation.model.value.ReportFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.util.Locale

data class VaKationConfiguration(
    val year: Year = Year.now(),
    val workdays: Set<DayOfWeek> = DEFAULT_WORK_DAYS,
    val extraDays: Set<LocalDate> = DEFAULT_EXTRA_DAYS_OFF,
    val vacationDays: VacationDays = VacationDays(DEFAULT_VACATION_DAYS),
    val country: Country = Country(CountryCode(DEFAULT_COUNTRY)),
    val format: ReportFormat = ReportFormat.CSV
) {
    companion object {
        const val DEFAULT_VACATION_DAYS = 24
        val DEFAULT_WORK_DAYS = setOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        )
        val DEFAULT_EXTRA_DAYS_OFF = emptySet<LocalDate>()
        val DEFAULT_COUNTRY: String = Locale.getDefault().language
    }

    fun getValueFromSourceKey(key: ValueSourceKey): List<String> = when (key) {
        ArgumentSourceKey.VACATION_DAYS -> listOf(vacationDays.value.toString())
        ArgumentSourceKey.YEAR -> listOf(year.value.toString())
        OptionSourceKey.FORMAT -> listOf(format.name)
        OptionSourceKey.COUNTRY -> listOf(country.toString())
        OptionSourceKey.WORKDAY -> workdays.map { it.name }
        OptionSourceKey.EXTRA_DAYS -> extraDays.map { it.toString() }
        else -> throw IllegalArgumentException("Unknown key $key")
    }
}

