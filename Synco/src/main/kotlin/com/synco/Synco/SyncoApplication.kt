package com.synco.Synco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class SyncoApplication

fun main(args: Array<String>) {
	runApplication<SyncoApplication>(*args)
	//variabler
	val user: String = "eadmin"
	val password: String = "Holland36"
	val host:String ="ehhcampbell.no"
	val folder:String = "C:\\Users\\Edward Campbell\\Documents\\Test"

	//Oppretter object
	val synco = syncofun(folder, user, password, host)

	//Kall:
	synco.syncoInfo()

	synco.syncFilesUP("Test1.txt", "/home/myOpenFolder/")
	//synco.syncFileDelete("Test3.txt", "/myOpenFolder/")

}//main
