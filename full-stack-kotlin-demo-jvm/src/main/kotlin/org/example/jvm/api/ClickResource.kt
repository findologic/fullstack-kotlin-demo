package org.example.jvm.api

import com.github.salomonbrys.kodein.instance
import io.swagger.annotations.Api
import org.example.common.ClickFeedback
import org.example.common.Person
import org.example.jvm.MemoryStorage
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Api
@Path("/clicker")
class ClickResource: BaseResource() {
    companion object {
        data class Message(val text: String, val weight: Int)

        private val messages: List<Message> = listOf(
                Message("Click!", 90),
                Message("Click.", 70),
                Message("The burgers are coming.", 40),
                Message("Click?", 20),
                Message("I hope your drink is still cold.", 15),
                Message("I'm trapped in a click message factory - send help!", 5),
                Message("Secret message - don't tell anyone.", 1)
        )
        private val messageWeightSum = messages.sumBy { it.weight }

        private fun getRandomMessage(): String {
            val absoluteTargetWeight = Math.random() * messageWeightSum

            return messages.filter {
                val relativeTargetWeight = absoluteTargetWeight - it.weight
            relativeTargetWeight > 0
            }.first().text
        }
    }

    private val storage: MemoryStorage by injector.instance()

    @POST
    @Path("/click")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun click(person: Person): ClickFeedback {
        if (person.id !in storage.people) {
            throw NotFoundException("Person is unknown.")
        }

        storage.people[person.id]!!.clicks++

        return ClickFeedback(storage.people[person.id]!!.clicks, getRandomMessage())
    }
}