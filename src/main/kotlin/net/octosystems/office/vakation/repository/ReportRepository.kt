package net.octosystems.office.vakation.repository

import net.octosystems.office.vakation.model.VacationPlan

interface ReportRepository {

    fun save(vacation: VacationPlan)
}
