import com.reneodlt.edj.EDJ
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import org.apache.commons.configuration2.builder.fluent.Configurations
import java.io.File
import java.io.FileInputStream
import java.nio.file.Path
import java.util.*


fun main(args: Array<String>) {
    println("Starting...")

    val parser = ArgParser("edj-cli")

    val update by parser.option(ArgType.Boolean, shortName = "u", description = "Perform updates on database")

    parser.parse(args)

    val edj = EDJ()
    val configs = Configurations()

    val config = configs.properties(File("config.properties"))

    config.keys.forEach {
        println("File exists flag: ${it} -> ${config.getString(it)}")
    }

    edj.connectToDbs(listOf(Path.of(config.getString("MAINDB"))))
    edj.readMainData { _: Int, _: Int, _: Int, _: Int -> }

    println("${edj.trackCount()} tracks in primary database")

    println("- MISSING FILES --")
    edj.getMissingFiles().forEach {
        println("-- $it")
    }

    edj.scanOtherDirectories(config.getStringArray("SEARCH").map{
        Path.of(it)
    })

    var matchedFiles = edj.matchMissingFilesWithFoundFiles()

    println("Found ${matchedFiles.size} matching files in new directories.")

    matchedFiles.forEach {
        if (update == true) {
            print("Updating for ${it.key.path}")
            it.key.executePathUpdateSQL(edj.connections.first().conn)
        }
        else {
            print("Would update ${it.key.path}")
            println("${it.key.path} --> ${it.key.newPath}")
        }
    }

    if (update != true) {
        println("Rerun with --update flag to perform updates")
    }


    println("Closing.")

}