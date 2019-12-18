package com.synco.Synco

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import com.jcraft.jsch.SftpException
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.lang.Exception
import java.nio.file.Paths


class SyncoSync constructor(val localFolder: String, val user: String, val password: String, val host: String){

    //printer ut info om pålogging til angitt server.
    fun syncoInfo(){
        println("Server connection information:")
        println("Username: $user")
        println("Password: *******")
        println("Host: $host")
        println("Synced folder: $localFolder")
        println("")
    }//syncoInfo

    fun convertFilePathToUnix(localFilePath: String): String{
        var x ="/home/myOpenFolder/${localFilePath.replace(localFolder, "")}"
        x = x.replace("\\", "/" )
        return x
    }

    //Laster opp valgt fil til desPath på serveren.
    fun syncFilesUP(localFilePath: String, desPath: String) {

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
            if(File(localFilePath).isDirectory){
                sftpChannel.mkdir(convertFilePathToUnix(localFilePath))
            }
            else{
                sftpChannel.put(FileInputStream(File(localFilePath)), desPath)
            }
            println("File Uploaded")

            session.disconnect()
            println("Session disconnected")
        }
        catch (e: JSchException){
            println("Could not connect to host, try again")
        }

    }//syncFilesUp


    //Sletter angitt fil fra serveren.
    fun syncFileDelete (localFilePath: String){
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

            try {
                    sftpChannel.rmdir(convertFilePathToUnix(localFilePath))
                    println("Folder deleted")
            }
            catch(e: SftpException) {
                try {
                    sftpChannel.rm(convertFilePathToUnix(localFilePath))
                    println("File deleted")
                }
                catch(e: SftpException){
                    println("File or folder does not exist")
                }
            }

            session.disconnect()
        }
        catch (e: JSchException){
            println("Could not connect to host, try again")
        }

    }//syncFileDelete
}//syncoFun