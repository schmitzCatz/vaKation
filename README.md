# VaKation

Foobar is a Python library for dealing with word pluralization.

## Installation

Download and unzip the archive. Make sure java runtime is installed.

## Usage

```bash
Usage: vaKation [<options>] [<year>] [<vacation>]

Options:
  -d, --workday=<text>...     Day to work during a week (default: [MONDAY,
                              TUESDAY, WEDNESDAY, THURSDAY, FRIDAY])
  -e, --extra-days=<text>...  Extra days of through the year in yyyy-mm-dd
                              format (default: [])
  -l, --country=<text>        Country to generate schedule (default: GB)
  -r, --format=<(csv)>        Report Format (default: CSV)
  -h, --help                  Show this message and exit

Arguments:
  <year>      Year to generate vacation schedule (default: 2024)
  <vacation>  Total amount of vacation days (default: 24)

```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[GPL-3.0](https://choosealicense.com/licenses/gpl-3.0/)
