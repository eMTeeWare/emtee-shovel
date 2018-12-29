package net.emteeware

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.*

class ImdbViewingHistory {
    private var viewingHistory  = mutableListOf<ImportedMedia>()

    fun importFromCsv(filename: String) {
        // This method is adopted from a solution of user Xaxxus ( https://stackoverflow.com/users/9768031/xaxxus )
        // on Stack Overflow: https://stackoverflow.com/a/50278646
        // used under cc-by-sa license https://creativecommons.org/licenses/by-sa/3.0/

        val mapper = CsvMapper().apply { registerModule(KotlinModule()) }
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
        val bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';')
        val objectReader = mapper.readerFor(ImportedMedia::class.java).with(bootstrapSchema)


        File(filename).inputStream().use { fileInputStream ->
            val mappingIterator: MappingIterator<ImportedMedia> = objectReader.readValues(fileInputStream)
            mappingIterator.forEach { viewingHistory.add(it) }
        }
    }

    fun print() {
        viewingHistory.forEach(System.out::println)
    }

    fun removeUnrated() {
        viewingHistory.removeAll { it -> it.YourRating == 0 }
    }

    fun removeSeenAfter(deadline: LocalDate) {
        val deadlineDate = Date.from(deadline.atStartOfDay(ZoneId.systemDefault()).toInstant())
        viewingHistory.removeAll { it -> it.Created.after(deadlineDate) }
    }

    private fun removeByType(type: TraktMediaType) {
        viewingHistory.removeAll { it -> it.TitleType == type}
    }

    fun removeUndefined() {
        removeByType(TraktMediaType.UNDEFINED)
    }

    fun removeDuplicateCheckInsWithin(rewatchThreshold: Period) {
        viewingHistory.sortedWith(compareBy({it.Const}, {it.Created}))
        val mediaToBeRemoved = mutableListOf<ImportedMedia>()
        val upperBound = viewingHistory.size - 2
        for (i in 0..upperBound) {
            if (viewingHistory[i].Const == viewingHistory[i + 1].Const) {
                val daysBetweenViewing = Period.between((viewingHistory[i + 1].Created).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        , (viewingHistory[i].Created).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                if (daysBetweenViewing.days < rewatchThreshold.days) {
                    mediaToBeRemoved += viewingHistory[i]
                }
            }
        }
        viewingHistory.removeAll(mediaToBeRemoved)
        viewingHistory.sortBy { it.Position }
    }
}