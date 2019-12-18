package com.synco.Synco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import java.io.IOException
import java.nio.file.Paths


@SpringBootApplication
class SyncoApplication

val FOLDER = "C:\\Users\\Svein\\Documents\\Synco"

fun main(args: Array<String>) {
	runApplication<SyncoApplication>(*args)



    //val folder:String = "C:\\Users\\Svein\\Documents\\Synco\\"
	//val user: String = "eadmin"
	//val password: String = "Holland36"
	//val host:String ="ehhcampbell.no"
	//val sync = SyncoSync(folder, user, password, host)
	//sync.syncoInfo()
	//sync.syncFilesUP("Test1.txt", "/home/myOpenFolder/")
	//synco.syncFileDelete("Test1.txt", "/myOpenFolder/")
	//var path = Paths.get("C:\\Users\\Svein\\Documents\\Synco")
	//WatchDir(path, true).processEvents(folder, user, password, host)

	watchFolder()


}
