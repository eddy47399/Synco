package com.synco.Synco

import org.apache.commons.io.monitor.FileEntry
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor
import org.apache.commons.io.monitor.FileAlterationListener
import org.apache.commons.io.monitor.FileAlterationObserver
import java.io.File

fun watchFolder(){
    val pathString = "C:\\Users\\Svein\\Documents\\Synco"

    val observer = FileAlterationObserver(pathString)
    val monitor = FileAlterationMonitor(5000)
    val fal = object : FileAlterationListenerAdaptor() {

        override fun onFileCreate(file: File) {
            println("Created: ${file.absolutePath}")
        }
        override fun onFileChange(file: File) {
            println("Changed: ${file.absolutePath}")
        }

        override fun onFileDelete(file: File) {
            println("Deleted: ${file.absolutePath}")
        }



        override fun onDirectoryCreate(dir: File) {
            println("Created: ${dir.absolutePath}")
        }

        override fun onDirectoryChange(dir: File) {
            println("Changed: ${dir.absolutePath}")
        }

        override fun onDirectoryDelete(dir: File) {
            println("Deleted: ${dir.absolutePath}")
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