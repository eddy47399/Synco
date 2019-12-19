package com.synco.Synco

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch

fun createConnection(user: String, host: String, password: String) : ChannelSftp{
    val session = JSch().getSession(user, host)
    session.setPassword(password)
    session.setTimeout(2000)

    println("Connecting to server.....")
    session.setConfig("StrictHostKeyChecking", "no")
    session.connect()
    println("Connected \n")

    val sftpChannel = session.openChannel("sftp") as ChannelSftp
    println("Opening SFTP Channel.....")
    sftpChannel.connect()
    println("SFTP Channel open \n")



    return sftpChannel
}
