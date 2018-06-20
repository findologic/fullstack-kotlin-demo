package org.example.js

import org.example.common.Person
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass
import kotlin.dom.removeClass

@Suppress("unused") // Called by runtime on page load.
fun main(args: Array<String>) {
    console.log("Started.")

    val client = ClickerClient("https://converschig24.com")

    toggleViewVisibility(false)
    bindEvents(client)
    pollLeaderboard(client)
}

fun toggleViewVisibility(personExists: Boolean) {
    val setupView = document.querySelector(".setup")!!
    val clickerView = document.querySelector(".clicker")!!

    if (personExists) {
        setupView.addClass("hidden")
        setupView.removeClass("visible")
        clickerView.addClass("visible")
        clickerView.removeClass("hidden")
    } else {
        setupView.addClass("visible")
        setupView.removeClass("hidden")
        clickerView.addClass("hidden")
        clickerView.removeClass("visible")
    }
}

fun bindEvents(client: ClickerClient) {
    var person: Person? = null

    document.querySelector(".do-click")!!.addEventListener("click", fun (e: Event) {
        client.click(person!!) {
            document.querySelector(".clicks")!!.textContent = it.clicks.toString()
            document.querySelector(".click-message")!!.textContent = it.message
        }

        e.preventDefault()
    })

    document.querySelector(".register")!!.addEventListener("click", fun (e: Event) {
        val name = (document.querySelector(".person-name")!! as HTMLInputElement).value

        client.createPerson(name) {
            person = it
            toggleViewVisibility(true)
        }

        e.preventDefault()
    })
}

fun pollLeaderboard(client: ClickerClient) {
    val leaderboardContainer = document.querySelector(".leaderboard")!!

    window.setInterval(fun () {
        client.getLeaderboard {
            // Interestingly, when parsing an object containing an array as a field, the array looks like one, but
            // doesn't have an iterator. Stringifying and parsing the array itself is a hacky but functional
            // workaround.
            val people = JSON.parse(JSON.stringify(it.people)) as Array<Person>

            leaderboardContainer.innerHTML = people.joinToString("\n") {
                "<li>${it.name} (${it.clicks})</li>"
            }
        }
    }, 1000)
}