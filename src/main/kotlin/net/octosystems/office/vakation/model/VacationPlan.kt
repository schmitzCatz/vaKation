package net.octosystems.office.vakation.model

import org.threeten.extra.YearWeek
import java.time.Year

data class VacationPlan(
    val year: Year,
    val vacations: Map<YearWeek, Vacation>
)
