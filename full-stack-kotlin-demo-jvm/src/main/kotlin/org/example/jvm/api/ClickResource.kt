package org.example.jvm.api

import com.github.salomonbrys.kodein.instance
import org.example.common.Person
import org.example.jvm.MemoryStorage
import javax.ws.rs.Consumes
import javax.ws.rs.NotFoundException
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType

@Path("/clicker")
class ClickResource: BaseResource() {
    private val storage: MemoryStorage by injector.instance()

    @POST
    @Path("/click")
    @Consumes(MediaType.APPLICATION_JSON)
    fun click(person: Person): Int {
        if (person.id !in storage.people) {
            throw NotFoundException("Person is unknown.")
        }

        storage.people[person.id]!!.clicks++

        return storage.people[person.id]!!.clicks
    }
}