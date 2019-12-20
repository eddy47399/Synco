package com.synco.Synco

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import com.jcraft.jsch.SftpException
import java.io.File
import java.io.FileInputStream

fun upload(localFolder: String, localFilePath: String, desPath: String, channel: ChannelSftp) {

    try{
        if(File(localFilePath).isDirectory){
            channel.mkdir(convertFilePathToUnix(localFilePath, localFolder))
        }
        else{
            var file = File(localFilePath)
            var fileInputStream = FileInputStream(file)
            channel.put(FileInputStream(File(localFilePath)), desPath)
            fileInputStream.close()
        }
        println("$localFilePath uploaded")
    }
    catch (e: JSchException){
        println("Could not connect to host, try again")
    }

}

fun delete (localFolder: String, localFilePath: String, channel: ChannelSftp){
    try{
        try {
            channel.rmdir(convertFilePathToUnix(localFilePath, localFolder))
            println("Folder ${convertFilePathToUnix(localFilePath, localFolder)} deleted")
        }
        catch(e: SftpException) {
            try {
                channel.rm(convertFilePathToUnix(localFilePath, localFolder))
                println("${convertFilePathToUnix(localFilePath, localFolder)} deleted")
            }
            catch(e: SftpException){
                println("${convertFilePathToUnix(localFilePath, localFolder)}: File or folder does not exist")
            }
        }
    }
    catch (e: JSchException){
        println("Could not connect to host, try again")
    }

}

fun convertFilePathToUnix(localFilePath: String, localFolder: String): String{
    var x ="/home/myOpenFolder/${localFilePath.replace(localFolder, "")}"
    x = x.replace("\\", "/" )
    return x
}