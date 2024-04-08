package at.fhv.teamh.hauptsach.ticket.backend.domain

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.bson.codecs.pojo.annotations.BsonProperty

@MongoEntity(collection = "Trailers")
data class Trailer(
    @BsonProperty("eventId") var eventId: String,
    @BsonProperty("file") var file: String,
) : PanacheMongoEntity() {
    companion object : PanacheMongoCompanion<Trailer>
}