package org.example.js

import org.example.common.Leaderboard
import org.example.common.Person
import org.w3c.xhr.XMLHttpRequest

class ClickerClient(val baseUrl: String) {
    private fun request(method: String, url: String, body: dynamic, function: (response: String) -> Unit) {
        val request = XMLHttpRequest()
        request.open(method, url)

        request.onreadystatechange = fun (_) {
            if (request.readyState.toInt() == 4) {
                function(request.responseText)
            }
        }

        request.onerror = fun (event) {
            console.log(event)
        }

        request.send(body)
    }

    fun createPerson(name: String, function: (person: Person) -> Unit) {
        request("POST", "$baseUrl/people?name=$name", null) {
            val person: Person = JSON.parse(it)

            function(person)
        }
    }

    fun getLeaderboard(top: Int? = null, function: (leaderboard: Leaderboard) -> Unit) {
        val url = "$baseUrl/people" + if (top != null) "?top=$top" else ""

        request("GET", url, null) {
            val leaderboard: Leaderboard = JSON.parse(it)

            function(leaderboard)
        }
    }

    fun click(person: Person, function: (updatedClicks: Int) -> Unit) {
        request("POST", "$baseUrl/clicker/click", JSON.stringify(person)) {
            val updatedClicks: Int = it.toInt()

            function(updatedClicks)
        }
    }
}