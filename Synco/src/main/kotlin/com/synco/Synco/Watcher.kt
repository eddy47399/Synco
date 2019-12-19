package com.synco.Synco

import com.jcraft.jsch.ChannelSftp
import org.apache.commons.io.monitor.FileEntry
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor
import org.apache.commons.io.monitor.FileAlterationListener
import org.apache.commons.io.monitor.FileAlterationObserver
import java.io.File

fun watchFolder(localFolder: String, channel: ChannelSftp){

    val observer = FileAlterationObserver(localFolder)
    val monitor = FileAlterationMonitor(5000)
    val fal = object : FileAlterationListenerAdaptor() {

        override fun onFileCreate(file: File) {
            println("Created: ${file.absolutePath}")
            upload(localFolder, file.absolutePath, convertFilePathToUnix(file.absolutePath, localFolder), channel)
        }
        override fun onFileChange(file: File) {
            println("Changed: ${file.absolutePath}")
        }

        override fun onFileDelete(file: File) {
            println("Deleted: ${file.absolutePath}")
            delete(localFolder, file.absolutePath, channel)
        }


        override fun onDirectoryCreate(dir: File) {
            println("Created: ${dir.absolutePath}")
            upload(localFolder, dir.absolutePath, convertFilePathToUnix(dir.absolutePath, localFolder), channel)
        }

        override fun onDirectoryChange(dir: File) {
            println("Changed: ${dir.absolutePath}")
        }

        override fun onDirectoryDelete(dir: File) {
            println("Deleted: ${dir.absolutePath}")
            delete(localFolder, dir.absolutePath, channel)
        }

       /* override fun onStart(observer: FileAlterationObserver) {
            println("Observer active...")
        }

        override fun onStop(observer: FileAlterationObserver) {
            println("Observer stopped")
        }*/
    }

    observer.addListener(fal)
    monitor.addObserver(observer)
    monitor.start()
    println("Monitor active...")


}