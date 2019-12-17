package com.synco.Synco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import java.nio.file.Paths

@SpringBootApplication
class SyncoApplication

fun main(args: Array<String>) {
	runApplication<SyncoApplication>(*args)


	//variabler
	val user: String = "eadmin"
	val password: String = "Holland36"
	val host:String ="ehhcampbell.no"
	val folder:String = "C:\\Users\\Svein\\Documents\\Synco\\"

	//Oppretter object
	val synco = syncofun(folder, user, password, host)

	synco.syncoInfo()

	//synco.syncFilesUP("Test1.txt", "/home/myOpenFolder/")
	//synco.syncFileDelete("Test1.txt", "/myOpenFolder/")



	var path = Paths.get("C:\\Users\\Svein\\Documents\\Synco")

	WatchDir(path, true).processEvents()


}//main
