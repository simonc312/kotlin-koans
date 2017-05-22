package iii_conventions

import iii_conventions.TimeInterval.DAY

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        val compYear = year.compareTo(other.year)
        if (compYear != 0) {
            return compYear
        }
        val compMonth = month.compareTo(other.month)
        if (compMonth != 0) {
            return compMonth
        }
        return dayOfMonth.compareTo(other.dayOfMonth)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval): MyDate = addTimeIntervals(interval, 1)

operator fun MyDate.plus(pair: Pair<TimeInterval,Int>): MyDate = addTimeIntervals(pair.first, pair.second)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;
}

// will fail to handle case ex DAY * 5 * 5 because DAY * 5 is converted to Pair<TimeInterval,Int>
// flaw of operator overloading that isn't flexible for most use cases
operator fun TimeInterval.times(count: Int): Pair<TimeInterval,Int> {
    return Pair(this,count)
}

class DateRange(val start: MyDate, val endInclusive: MyDate)

operator fun DateRange.contains(date: MyDate): Boolean {
    //intellij wants to convert to rangeCheck date in start..endInclusive but that will trigger stackoverflow exception
    return start <= date && endInclusive >= date
}

operator fun DateRange.iterator(): Iterator<MyDate> {
    var curDate = start
    return object : Iterator<MyDate> {
        override fun hasNext(): Boolean = curDate <= endInclusive

        override fun next(): MyDate {
            val cur = curDate
            curDate = curDate.nextDay()
            return cur
        }
    }
}



