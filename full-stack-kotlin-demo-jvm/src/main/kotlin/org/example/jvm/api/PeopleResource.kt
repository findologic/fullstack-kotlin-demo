package org.example.jvm.api

import com.github.salomonbrys.kodein.instance
import org.example.common.Leaderboard
import org.example.common.Person
import org.example.jvm.MemoryStorage
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("")
class PeopleResource: BaseResource() {
    private val storage: MemoryStorage by injector.instance()

    @POST
    @Path("/people")
    fun createPerson(@QueryParam("name") name: String): Person {
        val newPerson = Person(storage.people.size + 1, name)
        storage.people[newPerson.id] = newPerson

        return newPerson
    }

    @GET
    @Path("/people")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLeaderboard(@QueryParam("top") @DefaultValue("10") countStr: String): Leaderboard {
        val count = countStr.toInt()

        return Leaderboard(storage.people.values.sortedByDescending { it.clicks }.take(count))
    }
}