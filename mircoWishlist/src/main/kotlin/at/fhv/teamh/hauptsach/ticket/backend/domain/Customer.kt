package at.fhv.teamh.hauptsach.ticket.backend.domain

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.bson.codecs.pojo.annotations.BsonProperty

@MongoEntity(collection = "customer")
data class Customer(
    @BsonProperty("customerId") var customerId: Int,
    @BsonProperty("wishList") var wishList: MutableSet<String> = mutableSetOf(),
) : PanacheMongoEntity() {
    companion object : PanacheMongoCompanion<Customer>
}