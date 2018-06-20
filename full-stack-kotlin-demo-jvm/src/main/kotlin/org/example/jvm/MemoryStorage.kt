package org.example.jvm

import org.example.common.Person

data class MemoryStorage(val people: MutableMap<Int, Person> = HashMap())