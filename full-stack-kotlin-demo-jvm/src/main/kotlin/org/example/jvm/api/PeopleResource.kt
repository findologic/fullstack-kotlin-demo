package org.example.jvm.api

import com.github.salomonbrys.kodein.instance
import io.swagger.annotations.Api
import org.example.common.Leaderboard
import org.example.common.Person
import org.example.jvm.MemoryStorage
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Api
@Path("")
class PeopleResource: BaseResource() {
    private val storage: MemoryStorage by injector.instance()

    @POST
    @Path("/people")
    @Produces(MediaType.APPLICATION_JSON)
    fun createPerson(@QueryParam("name") name: String): Person {
        return if (name !in storage.people) {
            val newPerson = Person(storage.people.size + 1, name)
            storage.people[newPerson.name] = newPerson
            newPerson
        } else {
            storage.people[name]!!
        }
    }

    @GET
    @Path("/people")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLeaderboard(@QueryParam("top") @DefaultValue("10") countStr: String): Leaderboard {
        val count = countStr.toInt()

        return Leaderboard(storage.people.values.sortedByDescending { it.clicks }.take(count))
    }
}