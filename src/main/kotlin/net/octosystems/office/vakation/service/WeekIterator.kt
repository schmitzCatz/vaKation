package net.octosystems.office.vakation.service

import org.threeten.extra.YearWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.temporal.TemporalAdjusters

/**
 * Iterator implementation to iterate over all [YearWeek] of a given [Year]
 * @constructor Create an iterator of a given year
 */
internal class WeekIterator(private val year: Year) : Iterator<YearWeek> {
    /** First week of the year */
    private val start: YearWeek = YearWeek.of(year, 1)

    /** Current week of the iterator */
    private var currentWeek: YearWeek = start

    override fun hasNext(): Boolean = currentWeek.week <= start.numberOfWeeks && currentWeek.year == year.value

    override fun next(): YearWeek = if (hasNext()) currentWeek++ else throw NoSuchElementException()
}

/** Operator function to increment a [YearWeek] */
internal operator fun YearWeek.inc(): YearWeek = this.plusWeeks(1)

/**
 * Simple extension to [YearWeek] to calculate the start of a calendar week
 * @return [LocalDate] if the start of the week
 */
internal val YearWeek.start: LocalDate
    get() =
        when (week) {
            1 -> LocalDate.of(year, Month.JANUARY, 1)
            else ->
                LocalDate.of(year, Month.JANUARY, 1)
                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .plusWeeks((week - 2).toLong())
        }

/**
 * Simple extension to [YearWeek] to calculate the end of a calendar week
 * @return [LocalDate] of the end of the week
 */
@Suppress("MagicNumber")
internal val YearWeek.end: LocalDate
    get() =
        when (week) {
            numberOfWeeks -> LocalDate.of(year, Month.DECEMBER, 31)
            else -> start.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
        }

/**
 * Extension to get all [LocalDate] of a [YearWeek]
 * @return a set of [LocalDate] between [YearWeek.start] and [YearWeek.end] inclusive
 */
internal val YearWeek.days: Set<LocalDate>
    get() = start.datesUntil(end.plusDays(1)).toList().toSet()

/**
 * Extension to get the number of weeks of a [Year].
 * Either 52 or 53 weeks.
 */
@Suppress("MagicNumber")
internal val YearWeek.numberOfWeeks: Int
    get() = if (is53WeekYear) 52 else 53

/**
 * Iterator extension to get a [WeekIterator] of a [Year]
 */
internal val Year.weeks: WeekIterator
    get() = WeekIterator(this)

/**
 * Extension to get number of weeks of a [Year]
 * @return 52 or 53 depending on the year
 */
internal val Year.numberOfWeeks: Int
    get() = YearWeek.of(this, 1).numberOfWeeks
