package com.synco.Synco

import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.LinkOption.*
import java.io.IOException
import java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.util.HashMap
import java.nio.file.FileSystems
import java.nio.file.FileVisitResult
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
import java.nio.file.StandardWatchEventKinds.ENTRY_DELETE
import java.nio.file.WatchService


class WatchDir
/**
 * Creates a WatchService and registers the given directory
 */
@Throws(IOException::class)
internal constructor(dir: Path, private val recursive: Boolean) {

    private val watcher: WatchService
    private val keys: MutableMap<WatchKey, Path>
    private var trace = false

    /**
     * Register the given directory with the WatchService
     */
    @Throws(IOException::class)
    private fun register(dir: Path) {
        val key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
        if (trace) {
            val prev = keys[key]
            if (prev == null) {
                System.out.format("register: %s\n", dir)
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir)
                }
            }
        }
        keys[key] = dir
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    @Throws(IOException::class)
    private fun registerAll(start: Path) {
        // register directory and sub-directories
        Files.walkFileTree(start, object : SimpleFileVisitor<Path>() {
            @Throws(IOException::class)
            override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                register(dir)
                return FileVisitResult.CONTINUE
            }
        })
    }

    init {
        this.watcher = FileSystems.getDefault().newWatchService()
        this.keys = HashMap()

        if (recursive) {
            System.out.format("Scanning %s ...\n", dir)
            registerAll(dir)
            println("Done.")
        } else {
            register(dir)
        }

        // enable trace after initial registration
        this.trace = true
    }

    /**
     * Process all events for keys queued to the watcher
     */
    internal fun processEvents() {
        while (true) {

            // wait for key to be signalled
            val key: WatchKey
            try {
                key = watcher.take()
            } catch (x: InterruptedException) {
                return
            }

            val dir = keys[key]
            if (dir == null) {
                System.err.println("WatchKey not recognized!!")
                continue
            }

            for (event in key.pollEvents()) {
                val kind = event.kind()

                // TBD - provide example of how OVERFLOW event is handled
                if (kind === OVERFLOW) {
                    continue
                }

                // Context for directory entry event is the file name of entry
                val ev = cast<Path>(event)
                val name = ev.context()
                val child = dir.resolve(name)

                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child)

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && kind === ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child)
                        }
                    } catch (x: IOException) {
                        // ignore to keep sample readable
                    }

                }
            }

            // reset key and remove from set if directory no longer accessible
            val valid = key.reset()
            if (!valid) {
                keys.remove(key)

                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break
                }
            }
        }
    }

    companion object {
        internal fun <T> cast(event: WatchEvent<*>): WatchEvent<T> {
            @Suppress("UNCHECKED_CAST")
            return event as WatchEvent<T>
        }

        internal fun usage() {
            System.err.println("usage: java WatchDir [-r] dir")
            System.exit(-1)
        }
    }
}