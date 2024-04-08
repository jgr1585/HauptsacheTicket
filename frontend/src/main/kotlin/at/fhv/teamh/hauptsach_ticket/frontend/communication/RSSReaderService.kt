package at.fhv.teamh.hauptsach_ticket.frontend.communication

import at.fhv.teamh.hauptsach_ticket.library.dto.RSSObjectDTO
import java.net.HttpURLConnection
import java.net.URL

class RSSReaderService {
    private val rssUrl = URL("https://www.festivalticker.de/rss-festivalfeed/festivalkalender.xml")

    fun readRSS(): List<RSSObjectDTO> {
        val urlConnection = rssUrl.openConnection() as HttpURLConnection
        val rssObjects = mutableListOf<RSSObjectDTO>()
        val titles = mutableListOf<String>()
        val descriptions = mutableListOf<String>()

        try {
            val text = urlConnection.inputStream.bufferedReader(charset = Charsets.ISO_8859_1).readLines()

            for (line in text) {

                if (line.contains("<title>")) {
                    titles.add(line.substringAfter("<title>").substringBefore("</title>"))
                } else if (line.contains("<description>")) {
                    descriptions.add(line.substringAfter("<![CDATA[").substringBefore("]]></description>"))
                }
            }
        } finally {
            urlConnection.disconnect()
        }


        titles.forEachIndexed { i, title ->
            if (i == 0) return@forEachIndexed

            rssObjects.add(RSSObjectDTO(
                title,
                descriptions[i]
                    .split("<br>")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .joinToString("\n")
            ))
        }

        return rssObjects
    }
}