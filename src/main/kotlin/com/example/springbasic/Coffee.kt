package com.example.springbasic

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Coffee(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var name: String = "Cup O' Joe"
)