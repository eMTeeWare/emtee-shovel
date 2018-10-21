package net.emteeware

import java.time.LocalDate
import java.time.Period


fun main(args: Array<String>) {
    if(args.isEmpty() || args.size > 1){
        println("Please give the csv file exported from IMDb as the only parameter.")
        System.exit(1)
    }
    val imdbViewingHistory = ImdbViewingHistory()
    imdbViewingHistory.importFromCsv(args[0])
    imdbViewingHistory.removeUnrated()
    imdbViewingHistory.removeSeenAfter(LocalDate.of(2018, 4, 21))
    imdbViewingHistory.removeUndefined() // Some of these should be handled as episodes or movies, there should be a manual process for that
    imdbViewingHistory.removeDuplicateCheckInsWithin(Period.ofDays(14))
    imdbViewingHistory.print()
}

