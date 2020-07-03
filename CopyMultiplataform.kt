import java.io.File

fun main(args: Array<String>?) {
    var osName = System.getProperty("os.name").toLowerCase()
    val userPath = System.getProperty("user.home")
    val source: String?
    var destination = userPath + "/Desktop/dir1/"       //For Linux
    var verbose: Boolean = true

    //checks that the arguments are not null
    if(args?.size != 0) {
        if(args?.get(0) != null) {
            verbose = false
        }
    }

    if(osName.indexOf("win") >= 0) {
        source = getPenPathFileWindows()
        destination = userPath + File.separator + "Desktop"
    }
    else if (osName.indexOf("unix") >= 0 || osName.indexOf("linux") >= 0 ) {
        source = getPenPathFileLinux()
    } else {
        println("System not found")
        return
    }

    Thread.sleep(1000)
    copyArchive(source, destination, true, verbose)
}


/* Verifica o tempo todo se a pasta "/media/nomeDoUsuarioLogado" foi alterada
// Caso ela tenha sido alterada, adicionando uma nova midia montada ela retornara
// o caminho absoluto dessa nova midia
*/
fun getPenPathFileLinux(pathSize: Boolean = false): String {

    var path = File("/media/" + System.getProperty("user.name"))
    var base = path.list()
    var current: Array<String>?

    do {
        current = path.list()
        if (current.size != base.size) {
            if (current.size < base.size) {
                base = current
            }
            else if (current.size > base.size) {
                for (index in current.indices) {
                    if(index < base.size) {
                        if (current[index] != base[index]) {  
                            var newPath: String = (path.absolutePath + File.separator + current[index]) + File.separator.toString()

                            return newPath
                        }
                    }
                    else
                    {
                            var newPath: String = (path.absolutePath + File.separator + current[index]) + File.separator.toString()

                            return newPath
                    }
                }
            }
        }
    } while (true)
}

fun getPenPathFileWindows(pathSize: Boolean = false): String {

    var base = File.listRoots()

    do {
        var current = File.listRoots()
        if (current.size != base.size) {
            if (current.size < base.size) {
                base = current
            }
            else if (current.size > base.size) {
                for (index in current.indices) {
                    if(index < base.size) {
                        if (current[index] != base[index]) {  
                            return current[index].absolutePath
                        }
                    }
                    else
                    {
                        return current[index].absolutePath
                    }
                }
            }
        }
    } while (true)
}

fun copyArchive(root: String, dest: String, createTheDirPath: Boolean = false, verbose: Boolean = true) {
    val theDir = File(dest, "The Dir")
    val rootPath = File(root)                                           /* /media/user/drive/ */
    var destination = dest
 
    if(createTheDirPath == true && !(theDir.exists())) {
        theDir.mkdir()
        destination =  destination + File.separator + theDir.name + File.separator    /* /home/user/Desktop/dir1/"The Dir"/  */
    } else if(theDir.exists()) destination =  destination + File.separator + theDir.name + File.separator

    var list: Array<File> = rootPath.listFiles()
    for(archive in list) {
        var destinationTMP = destination + archive.name + File.separator                     /* /home/user/Desktop/dir1/"The Dir"/otherDir/  */
        var finalDestin = File(destinationTMP)
        try {
            var pathToString = archive.copyTo(finalDestin, true)

            if(verbose == true) {
                println(pathToString)
            }
        }
        catch(e: Exception) {
            continue
        }
        

        if(archive.isDirectory()) {copyArchive(archive.absolutePath, destinationTMP, false, verbose)}
    }
}
