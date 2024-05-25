package net.octosystems.office.vakation

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.MordantHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.varargValues
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import net.octosystems.office.vakation.model.Country
import net.octosystems.office.vakation.model.VaKationConfiguration
import net.octosystems.office.vakation.model.ValueSourceKey.ArgumentSourceKey
import net.octosystems.office.vakation.model.ValueSourceKey.OptionSourceKey
import net.octosystems.office.vakation.model.value.ReportFormat
import net.octosystems.office.vakation.model.value.VacationDays
import net.octosystems.office.vakation.repository.JollydayRepository
import net.octosystems.office.vakation.service.DefaultReportService
import net.octosystems.office.vakation.service.DefaultVacationService
import net.octosystems.office.vakation.service.YamlValueSource
import net.octosystems.office.vakation.support.LocalizationSupport
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.ResourceBundle


fun main(args: Array<String>) = CommandLineInterface().context {
    terminal = Terminal(ansiLevel = AnsiLevel.TRUECOLOR, interactive = true)
    valueSource = YamlValueSource()
    helpFormatter = { MordantHelpFormatter(it, showDefaultValues = true) }
}.main(args)

internal enum class Messages(
    private val key: String,
    private val bundle: ResourceBundle = ResourceBundle.getBundle("messages")
) : LocalizationSupport {
    YEAR("cli.argument.year"),
    VACATION("cli.argument.vacation"),
    WORKDAYS("cli.option.workdays"),
    EXTRA_DAYS("cli.option.extra.dates"),
    COUNTRY("cli.option.country"),
    FORMAT("cli.option.format"),
    FORMAT_CSV("cli.option.format.csv")
    ;

    override val text
        get(): String = bundle.getString(key)
}

internal class CommandLineInterface : CliktCommand(name = "vaKation") {

    private val year by argument(
        name = ArgumentSourceKey.YEAR.value, help = Messages.YEAR.text
    )
        .int()
        .default(Year.now().value)
    private val vacation by argument(
        name = ArgumentSourceKey.VACATION_DAYS.value,
        help = Messages.VACATION.text
    )
        .int()
        .default(VaKationConfiguration.DEFAULT_VACATION_DAYS)

    private val workdays by option(
        names = OptionSourceKey.WORKDAY.value,
        help = Messages.WORKDAYS.text
    )
        .varargValues()
        .default(VaKationConfiguration.DEFAULT_WORK_DAYS.map { it.name })
    private val extraDates by option(
        names = OptionSourceKey.EXTRA_DAYS.value,
        help = Messages.EXTRA_DAYS.text
    )
        .varargValues()
        .default(emptyList())
    private val country by option(
        names = OptionSourceKey.COUNTRY.value,
        help = Messages.COUNTRY.text
    )
        .default(VaKationConfiguration.DEFAULT_COUNTRY)

    private val format by option(
        names = OptionSourceKey.FORMAT.value,
        help = Messages.FORMAT.text
    )
        .choice(Messages.FORMAT_CSV.text to ReportFormat.CSV)
        .default(ReportFormat.CSV)

    override fun run() {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val config = VaKationConfiguration(
            year = Year.of(year),
            workdays = workdays.map { DayOfWeek.valueOf(it) }.toSet(),
            extraDays = extraDates.map { LocalDate.parse(it, dateFormatter) }.toSet(),
            vacationDays = VacationDays(vacation),
            country = Country(country),
            format = format
        )

        val service = DefaultVacationService(JollydayRepository())
        val plan = service.createVacationPlan(config)
        val report = DefaultReportService()

        report.report(ReportFormat.CSV, plan)
    }
}
