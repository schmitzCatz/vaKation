package net.octosystems.office.vakation.model.value

@JvmInline
value class VacationDays(val value: Int) {
    init {
        require(value > 0) { "$value should be greater than 0" }
    }
}
