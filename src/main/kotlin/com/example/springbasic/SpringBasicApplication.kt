package com.example.springbasic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.repository.CrudRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@SpringBootApplication
class SpringBasicApplication

fun main(args: Array<String>) {
    runApplication<SpringBasicApplication>(*args)
}


@Component
class DataLoader(private val coffeeRepository: CoffeeRepository) {
    @PostConstruct
    private fun loadData() = coffeeRepository.saveAll(
        listOf(
            Coffee("Café Cereza"),
            Coffee("Café Ganador"),
            Coffee("Café Lareño"),
            Coffee("Café Três Pontas")
        )
    )
}


@RestController
@RequestMapping("/coffees")
class RestApiDemoController(
    val coffeeRepository: CoffeeRepository
) {


//    @RequestMapping(value = ["/coffees"], method = [RequestMethod.GET]) // RequestMapping it means to return some json format, RequestMethod.GET restrict only use HTTP GET request
//    fun getCoffees(): Iterable<Coffee> = coffees

    // Alternative above code
    @GetMapping
    fun getCoffees() = coffeeRepository.findAll()

    @GetMapping("/{id}")
    fun getCoffeesById(@PathVariable id: String) = coffeeRepository.findById(id)

    @PostMapping
    fun postCoffee(@RequestBody coffee: Coffee) = coffeeRepository.save(coffee)

    @PutMapping("/{id}")
    fun putCoffee(@PathVariable id: String, @RequestBody coffee: Coffee): ResponseEntity<Coffee> {

        return if (!coffeeRepository.existsById(id)) ResponseEntity(coffeeRepository.save(coffee), HttpStatus.CREATED) else ResponseEntity(
            coffeeRepository.save(coffee),
            HttpStatus.OK
        )
    }

    @DeleteMapping("/{id}")
    fun deleteCoffee(@PathVariable id: String) {
        coffeeRepository.deleteById(id)
    }

}

interface CoffeeRepository : CrudRepository<Coffee, String> {

}
