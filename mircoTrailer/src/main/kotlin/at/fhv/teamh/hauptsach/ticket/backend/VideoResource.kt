package at.fhv.teamh.hauptsach.ticket.backend

import at.fhv.teamh.hauptsach.ticket.backend.domain.Trailer
import at.fhv.teamh.hauptsach.ticket.backend.exception.NoVideoException
import jakarta.ws.rs.*
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.util.*

@Path("/trailers")
class VideoResource {

    @GET
    @Produces("application/mp4")
    fun streamTrailer(@QueryParam("eventId") eventId: String): ByteArray {
        val videoName = Trailer.find("eventId", eventId).firstResult()?.file
            ?: throw NoVideoException()

        val videoStream = FileInputStream("videos/$videoName.mp4")
            .use { ByteArrayOutputStream().apply { it.copyTo(this) } }

        return videoStream.toByteArray()
    }
}