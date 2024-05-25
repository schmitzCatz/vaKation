package net.octosystems.office.vakation.service

import net.octosystems.office.vakation.model.VacationPlan
import net.octosystems.office.vakation.model.value.ReportFormat

interface ReportService {

    fun report(format: ReportFormat, vacation: VacationPlan)
}
