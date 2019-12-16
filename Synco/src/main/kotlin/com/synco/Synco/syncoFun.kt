package com.synco.Synco

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths


class syncofun constructor(location: String){

    var user = "eadmin"
    var password = "Holland36"
    var host = "ehhcampbell.no"

    var loci = location


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
        println("OK!")

        println(srcFile.canonicalPath)
        println("Does file exist?: ${srcFile.exists()}")
        println(sftpChannel.lpwd())

        println(sftpChannel.home)

        println("File uploading.....")

        sftpChannel.put(stream, "$despath$filename")
        print("File Uploaded")

    }//syncFilesUp



    fun syncFilesDown() {
        val jsch = JSch()
        val session = jsch.getSession(user, host)
        session.setPassword(password)
        session.connect()

        val sftpChannel = session.openChannel("sftp") as ChannelSftp
        sftpChannel.connect()

        sftpChannel.put("File_from_here", "File_to_here")
    }//syncFilesDown

    fun syncFilesRemoveFile() {
        try{
            val jsch = JSch()
            val session = jsch.getSession(user, host)
            session.setPassword(password)
            session.connect()

            val sftpChannel = session.openChannel("sftp") as ChannelSftp
            sftpChannel.connect()

            sftpChannel.rm("PathString")
        }
        catch(e: JSchException){
            println("There was an error pleace try again")
        }


    }//syncFilesRemoveFile

    fun syncFilesRemoveDir() {
        val jsch = JSch()
        val session = jsch.getSession(user, host)
        session.setPassword(password)
        session.connect()

        val sftpChannel = session.openChannel("sftp") as ChannelSftp
        sftpChannel.connect()

        sftpChannel.rmdir("PathString")
    }//syncFilesRemoveDir

}//syncoFun