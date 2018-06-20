package org.example.common

data class Person(val id: Int, val name: String, var clicks: Int = 0)

data class Leaderboard(val people: List<Person>)

data class ClickFeedback(val clicks: Int, val message: String = "Click!")