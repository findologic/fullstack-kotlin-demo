package org.example.jvm.api

import com.github.salomonbrys.kodein.KodeinInjector
import org.example.jvm.Application

open class BaseResource {
    protected val injector = KodeinInjector()
    protected val kodein = injector.kodein()

    init {
        injector.inject(Application.kodein)
    }
}