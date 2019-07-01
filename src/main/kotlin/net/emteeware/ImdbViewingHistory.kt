package net.emteeware

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import java.io.File
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.*
import java.util.function.Consumer

class ImdbViewingHistory {
    private var viewingHistory = mutableListOf<ImportedMedia>()

    fun importFromCsv(filename: String, separator: Char, recognizedCharset: Charset, contentVersion: ContentVersion) {
        // This method is adopted from a solution of user Xaxxus ( https://stackoverflow.com/users/9768031/xaxxus )
        // on Stack Overflow: https://stackoverflow.com/a/50278646
        // used under cc-by-sa license https://creativecommons.org/licenses/by-sa/3.0/

        val mapper = CsvMapper().apply { registerModule(KotlinModule()) }
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
        val bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(separator)
        val objectReader = if (contentVersion == ContentVersion.V1) {
            mapper.readerFor(ImportedMediaV1::class.java).with(bootstrapSchema)
        } else {
            mapper.readerFor(ImportedMediaV2::class.java).with(bootstrapSchema)
        }

        // bufferedReader returns wrong column headers with UTF-8 for some reason
        // e.g. ?Position instead of Position
        if (recognizedCharset == StandardCharsets.ISO_8859_1) {
            File(filename).inputStream().use { fileInputStream ->
                val mappingIterator: MappingIterator<ImportedMedia> = objectReader.readValues(fileInputStream.bufferedReader(recognizedCharset))
                mappingIterator.forEach { viewingHistory.add(it) }
            }
        } else {
            File(filename).inputStream().use { fileInputStream ->
                val mappingIterator: MappingIterator<ImportedMedia> = objectReader.readValues(fileInputStream)
                mappingIterator.forEach { viewingHistory.add(it) }
            }
        }
    }

    fun getMinimumWatchDate(): LocalDate {
        return viewingHistory.stream().min{ o1, o2 -> o1.Created.compareTo(o2.Created) }.get().Created.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
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
        viewingHistory.removeAll { it -> it.TitleType == type }
    }

    fun removeUndefined() {
        removeByType(TraktMediaType.UNDEFINED)
    }

    fun removeDuplicateCheckInsWithin(rewatchThreshold: Period) {
        viewingHistory.sortedWith(compareBy({ it.Const }, { it.Created }))
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

    fun getTraktMediaList(): ObservableList<Media> {
        val returnList = FXCollections.observableArrayList<Media>()
        viewingHistory.forEach(Consumer { m -> returnList += convertedMedia(m) })
        return returnList
    }

    private fun convertedMedia(importedMedia: ImportedMedia): Media {
        return Media(
                position = importedMedia.Position,
                imdbId = importedMedia.Const,
                watchDate = importedMedia.Created.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                title = importedMedia.Title,
                imdbUrl = importedMedia.URL,
                mediaType = importedMedia.TitleType,
                yourRating = importedMedia.YourRating)
    }

    fun getMaximumWatchDate(): LocalDate {
        return viewingHistory.stream().max{ o1, o2 -> o1.Created.compareTo(o2.Created) }.get().Created.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
}