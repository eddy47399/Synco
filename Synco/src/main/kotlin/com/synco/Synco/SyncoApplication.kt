package com.synco.Synco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class SyncoApplication

fun main(args: Array<String>) {
	runApplication<SyncoApplication>(*args)

	var location: String = "C:\\Users\\Edward Campbell\\Desktop"
	syncofun("C:\\Users\\Edward Campbell\\Documents\\GitHub\\Synco\\Synco").syncFilesUP("Test1.txt", "/myOpenFolder/")

}
