package net.octosystems.office.vakation.model

interface ValueSourceKey {
    val keys: List<String>

    enum class ArgumentSourceKey(val value: String) : ValueSourceKey {
        YEAR("year"),
        VACATION_DAYS("vacation"),
        ;

        override val keys: List<String>
            get() = listOf(value)
    }

    enum class OptionSourceKey(vararg val value: String) : ValueSourceKey {
        WORKDAY("--workday", "-d"),
        EXTRA_DAYS("--extra-days", "-e"),
        COUNTRY("--country", "-l"),
        FORMAT("--format", "-r"),
        ;

        override val keys: List<String>
            get() = value.toList()
    }

    companion object {
        fun lookUpSourceKey(key: String): ValueSourceKey {

            val argumentKey = ArgumentSourceKey.entries.find { it.keys.contains(key) };

            if (argumentKey != null) {
                return argumentKey

            }
            val optionKey = OptionSourceKey.entries.find { it.keys.contains(key) }
            if (optionKey != null) {
                return optionKey

            }
            throw IllegalArgumentException("Unknown option key: $key")
        }
    }
}

