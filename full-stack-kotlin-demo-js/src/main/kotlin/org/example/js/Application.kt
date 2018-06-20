package org.example.js

fun main(args: Array<String>) {
    console.log("Started.")

    val client = ClickerClient("http://localhost:8888")
    client.getLeaderboard {
        console.log(it.people)
    }
}