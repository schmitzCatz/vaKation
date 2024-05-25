package net.octosystems.office.vakation.repository

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import net.octosystems.office.vakation.model.VacationPlan
import net.octosystems.office.vakation.repository.entity.CsvReport
import java.io.OutputStream

class CsvReportRepository(private val out: OutputStream = System.out) : ReportRepository {

    private val mapper = CsvMapper().apply {
        enable(CsvParser.Feature.TRIM_SPACES)
        enable(CsvParser.Feature.SKIP_EMPTY_LINES)
    }

    private val schema = CsvSchema.builder()
        .addNumberColumn(YEAR)
        .addColumn(WEEK)
        .addArrayColumn(VACATION)
        .addArrayColumn(DAYS_OFF)
        .addColumn(TOTAL)
        .setUseHeader(true)
        .setColumnSeparator(';')
        .setQuoteChar('"')
        .build()

    override fun save(vacation: VacationPlan) {
        val report = vacation.vacations.map { CsvReport(it.value) }
        mapper.writer().with(schema).writeValues(out).writeAll(report)
    }

    companion object {
        const val YEAR = "year"
        const val WEEK = "week"
        const val VACATION = "vacation"
        const val DAYS_OFF = "days"
        const val TOTAL = "total"
    }

}
