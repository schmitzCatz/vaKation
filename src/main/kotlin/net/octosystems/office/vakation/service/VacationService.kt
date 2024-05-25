package net.octosystems.office.vakation.service

import net.octosystems.office.vakation.model.VaKationConfiguration
import net.octosystems.office.vakation.model.VacationPlan
import org.threeten.extra.YearWeek

interface VacationService {

    fun getWorkdaysPerWeek(config: VaKationConfiguration): Map<YearWeek, Int>

    fun createVacationPlan(config: VaKationConfiguration): VacationPlan
}
