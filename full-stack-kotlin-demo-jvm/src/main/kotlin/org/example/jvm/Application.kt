package org.example.jvm

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletContainer
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.ExceptionMapper

class Application : ResourceConfig() {
    init {
        register(ContextResolver<ObjectMapper> {
            val mapper = ObjectMapper()

            mapper.registerModule(KotlinModule())

            mapper
        })

        register(ExceptionMapper<Exception> { exception -> Response.serverError().entity(exception).build() })

        packages("org.example.jvm.api")
    }

    companion object {
        val kodein = Kodein {
            bind<MemoryStorage>() with singleton { MemoryStorage() }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val config = Application()
            val servlet = ServletHolder(ServletContainer(config))

            val server = Server(8888)
            val context = ServletContextHandler(server, "/")

            context.addServlet(servlet, "/*")

            server.start()
            server.join()
        }
    }
}