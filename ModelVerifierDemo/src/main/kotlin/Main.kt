import uml.UmlClassVerifier
import java.io.File

fun main(args: Array<String>) {
    File("output/verify.smv").writeText(UmlClassVerifier().generateNuSMVScript())
}