package net.octosystems.office.vakation.service

import net.octosystems.office.vakation.model.VacationPlan
import net.octosystems.office.vakation.model.value.ReportFormat
import net.octosystems.office.vakation.repository.CsvReportRepository

class DefaultReportService : ReportService {

    private val repositories = mapOf(ReportFormat.CSV to CsvReportRepository())

    override fun report(format: ReportFormat, vacation: VacationPlan) {
        repositories.getOrDefault(format, CsvReportRepository()).save(vacation)
    }

}
