package org.example.js

import org.example.common.ClickFeedback
import org.example.common.Leaderboard
import org.example.common.Person
import org.w3c.xhr.XMLHttpRequest

class ClickerClient(private val baseUrl: String) {
    private fun request(method: String, url: String, body: dynamic, callback: (response: String) -> Unit) {
        val request = XMLHttpRequest()

        request.open(method, url)

        if (body != null) {
            request.setRequestHeader("Content-Type", "application/json")
        }

        request.onreadystatechange = fun (_) {
            if (request.readyState.toInt() == 4) {
                callback(request.responseText)
            }
        }

        request.onerror = fun (event) {
            console.log(event)
        }

        request.send(body)
    }

    fun createPerson(name: String, callback: (person: Person) -> Unit) {
        request("POST", "$baseUrl/people?name=$name", null) {
            val person: Person = JSON.parse(it)

            callback(person)
        }
    }

    fun getLeaderboard(top: Int? = null, callback: (leaderboard: Leaderboard) -> Unit) {
        val url = "$baseUrl/people" + if (top != null) "?top=$top" else ""

        request("GET", url, null) {
            val leaderboard: Leaderboard = JSON.parse(it)

            callback(leaderboard)
        }
    }

    fun click(person: Person, callback: (clickFeedback: ClickFeedback) -> Unit) {
        request("POST", "$baseUrl/clicker/click", JSON.stringify(person)) {
            val clickFeedback: ClickFeedback = JSON.parse(it)

            callback(clickFeedback)
        }
    }
}