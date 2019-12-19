package com.synco.Synco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import com.jcraft.jsch.SftpException

@SpringBootApplication
class SyncoApplication

val FOLDER = "C:\\Users\\Svein\\Documents\\Synco"

fun main(args: Array<String>) {
	runApplication<SyncoApplication>(*args)

	val localFolder = "C:\\Users\\Svein\\Documents\\Synco"
	val user = "eadmin"
	val password = "Holland36"
	val host ="ehhcampbell.no"

	val channel = createConnection(user, host, password)
	watchFolder(localFolder, channel)

	//val sync = SyncoSync(folder, user, password, host)
	//sync.syncoInfo()
	//sync.syncFilesUP("Test1.txt", "/home/myOpenFolder/")
	//synco.syncFileDelete("Test1.txt", "/myOpenFolder/")
	//var path = Paths.get("C:\\Users\\Svein\\Documents\\Synco")
	//WatchDir(path, true).processEvents(folder, user, password, host)

}
