package com.synco.Synco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class SyncoApplication

fun main(args: Array<String>) {
	runApplication<SyncoApplication>(*args)
	var synco = syncofun("C:\\Users\\eddy3\\Documents\\Github")


	//synco.syncFilesUP("Test3.txt", "/home/myOpenFolder/")
	//synco.syncFileDelete("Test3.txt", "/myOpenFolder/")


}
