package com.synco.Synco

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths


class syncofun constructor(folderPathToSync: String, username: String, password: String, host: String){

    var user = username
    var password = password
    var host = host
    var loci = folderPathToSync

    //printer ut info om pålogging til angitt server.
    fun syncoInfo(){
        println("Server connection information:")
        println("Username: $user")
        println("Password: *******")
        println("Host: $host")
        println("Synced folder: $loci")
        println("")
    }//syncoInfo

    //Laster opp valgt fil til desPath på serveren.
    fun syncFilesUP(filename: String, desPath: String) {
        val srcFilePath = loci +"\\"+filename
        val srcFile = File(srcFilePath)
        val stream = FileInputStream(srcFile)
        val despath = desPath

        try{
            val jsch = JSch()
            val session = jsch.getSession(user, host)
            session.setPassword(password)
            println("Connecting to server.....")
            session.setConfig("StrictHostKeyChecking", "no")
            session.connect()
            println("Connected \n")

            val sftpChannel = session.openChannel("sftp") as ChannelSftp
            println("Opening SFTP Channel.....")
            sftpChannel.connect()
            println("SFTP Channel open \n")

            println("File uploading.....")
            sftpChannel.put(stream, "$despath$filename")
            println("File Uploaded")

            session.disconnect()
        }
        catch (e: JSchException){
            println("Could not connect to host, try again")
        }

    }//syncFilesUp


    //Sletter angitt fil fra serveren.
    fun syncFileDelete (filename: String, desPath: String){
        val despath = desPath
        try{
            val jsch = JSch()
            val session = jsch.getSession(user, host)
            session.setPassword(password)
            println("Connecting.....")
            session.setConfig("StrictHostKeyChecking", "no")
            session.connect()
            println("Connected")

            val sftpChannel = session.openChannel("sftp") as ChannelSftp
            println("Opening SFTP Channel......")
            sftpChannel.connect()
            println("SFTP Channel open")
            println("Deleting file.....")

            sftpChannel.rm("/home/$despath$filename")
            println("File Deleted")

            session.disconnect()
        }
        catch (e: JSchException){
            println("Could not connect to host, try again")
        }

    }//syncFileDelete
}//syncoFun