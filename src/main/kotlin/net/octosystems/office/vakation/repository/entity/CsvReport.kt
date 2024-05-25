package net.octosystems.office.vakation.repository.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import net.octosystems.office.vakation.model.Vacation
import net.octosystems.office.vakation.repository.CsvReportRepository
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

@JsonPropertyOrder(
    CsvReportRepository.YEAR,
    CsvReportRepository.WEEK,
    CsvReportRepository.VACATION,
    CsvReportRepository.DAYS_OFF,
    CsvReportRepository.TOTAL
)
data class CsvReport(
    @field:JsonProperty(CsvReportRepository.YEAR)
    val year: Int,
    @field:JsonProperty(CsvReportRepository.WEEK)
    val week: String,
    @field:JsonProperty(CsvReportRepository.VACATION)
    val vacation: Set<String>,
    @field:JsonProperty(CsvReportRepository.DAYS_OFF)
    val daysOff: Set<String>,
    @field:JsonProperty(CsvReportRepository.TOTAL)
    val total: Int
) {
    @Suppress("Unused")
    constructor() : this(0, "", emptySet<String>(), emptySet<String>(), 0)

    constructor(vacation: Vacation) : this(
        vacation.week.year,
        vacation.week.toString(),
        vacation.vacationDates.map { formatter.format(it) }.toSet(),
        vacation.daysOff.map { formatter.format(it) }.toSet(),
        vacation.numberOfVacationDays
    )
}
