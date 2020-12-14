package util

import java.io.File

fun readFile(fileName: String): List<String> = File(fileName).readLines()