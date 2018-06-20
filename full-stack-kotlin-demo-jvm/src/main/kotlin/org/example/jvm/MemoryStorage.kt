package org.example.jvm

import org.example.common.Person

data class MemoryStorage(val people: MutableMap<String, Person> = HashMap())