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

    //Laster opp valgt fil til desPath på serveren.
    fun syncFilesUP(filename: String, desPath: String) {
        val srcFilePath = loci +"\\"+filename
        val srcFile = File(srcFilePath)
        val stream = FileInputStream(srcFile)
        val despath = desPath

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
        println("File uploading.....")

        sftpChannel.put(stream, "$despath$filename")
        print("File Uploaded")
    }//syncFilesUp


    //Sletter angitt fil fra serveren.
    fun syncFileDelete (filename: String, desPath: String){
        val despath = desPath

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
    }//syncFileDelete
}//syncoFun