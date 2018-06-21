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
                Message("Click?", 20),
                Message("We're hiring!", 15),
                Message("The burgers are coming.", 10),
                Message("I hope your drink is still cold.", 15),
                Message("I'm trapped in a click message factory - send help!", 5),
                Message("What's the favorite type of shoe of a Java developer? A spring boot.", 3),
                Message("If you're happy and you know it syntax error", 3),
                Message("Knock knock. - Race condition. - Who's there?", 3),
                Message("I've got a really good UDP joke to tell you, but I don't know if you'll get it.", 3),
                Message("Give me a <br>.", 3),
                Message("A SQL statement walks into a bar and sees two tables. It approaches, and asks “may I join you?”", 3),
                Message("An SEO expert walks into a bar, pub, liquor store, brewery, alcohol, beer, whiskey, vodka", 3),
                Message("OLO demands it!", 3),
                Message("What do Antarctica and HTTP have in common? - They're both stateless", 3),
                Message("She was sending me mixed signals, so I did a fourier analysis.", 3),
                Message("Do you even shift, bro?", 3),
                Message("Web Developers use caniuse.com , lawyers use canisue.com ", 3),
                Message("If someone's camel is stolen, does the police consider it a camelCase?", 3),
                Message("Are your fingers bleeding yet?", 2),
                Message("Secret message - don't tell anyone.", 1)
        )
        private val messageWeightSum = messages.sumBy { it.weight }

        private fun getRandomMessage(): String {
            val absoluteTargetWeight = Math.random() * messageWeightSum

            return messages.first {
                val relativeTargetWeight = absoluteTargetWeight - it.weight
                relativeTargetWeight > 0
            }.text
        }
    }

    private val storage: MemoryStorage by injector.instance()

    @POST
    @Path("/click")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun click(person: Person): ClickFeedback {
        if (person.name !in storage.people) {
            throw NotFoundException("Person is unknown.")
        }

        storage.people[person.name]!!.clicks++

        return ClickFeedback(storage.people[person.name]!!.clicks, getRandomMessage())
    }
}