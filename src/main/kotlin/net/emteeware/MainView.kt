package net.emteeware

import javafx.collections.FXCollections
import javafx.scene.Node
import tornadofx.*
import java.net.URL
import java.util.*

class MainView : View("My View") {
    override val root = borderpane {
        center = centerVBox()
    }

    private fun centerVBox(): Node {
        return vbox {
            val media = FXCollections.observableArrayList(
                    ImportedMedia(Position=2542, Const="kk4934924", Created=Date(118,3,18), Modified=Date(118,3,18), Description="", Title="Ydakr Eadsjo", URL= URL("https://www.example.com/title/kk4934924/"), TitleType= TraktMediaType.EPISODE, IMDbRating="8,2", Runtime=45, Year=2017, Genres="Drama", NumVotes=1738, ReleaseDate=Date(116,5,30), Directors="Lago Majore", YourRating=7, DateRated=Date(118,3,19)),
                    ImportedMedia(Position=2542, Const="kk4934923", Created=Date(118,7,24), Modified=Date(118,7,24), Description="", Title="OIHlkasd Aasec", URL= URL("https://www.example.com/title/kk4934923/"), TitleType= TraktMediaType.EPISODE, IMDbRating="8,2", Runtime=45, Year=2017, Genres="Drama", NumVotes=1738, ReleaseDate=Date(114,5,30), Directors="Baik O'Nur", YourRating=7, DateRated=Date(118,3,19))
            )
            tableview(media) {
                readonlyColumn("Date", ImportedMedia::Created)
                readonlyColumn("Title", ImportedMedia::Title)
                readonlyColumn("Type", ImportedMedia::TitleType)
                columnResizePolicy = SmartResize.POLICY
            }
        }
    }
}
