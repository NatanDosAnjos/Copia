import java.io.File

val TIME_OF_DELAY: Long = 200L

fun main(args: Array<String>?) {
    val userPath = System.getProperty("user.home")
    val source: String?
    var destination = userPath + File.separator
    var verbose: Boolean = true
    var argsSize: Int = args!!.size
    var listFilesNames = mutableListOf <String>()

    //checks that the arguments are not null
    if(argsSize != 0) {
        if(args.get(0) == "nonVerbose") {
            verbose = false
        }
        
        //Put the file names arguments in a mutableList
        for (i in 1 .. argsSize.minus(1)) {
            var dirName = args.get(i)
            listFilesNames.add(dirName)
        }
    }

    source = getPathFile()
    Thread.sleep(900)
    if(source != null) {
        if(listFilesNames.size != 0) {
            for(fileName in listFilesNames) {
                var sourceTmp = File(source, fileName)

                if(sourceTmp.exists()) { 
                    println("Start!")
                    copyArchive(sourceTmp.absolutePath, destination, true, verbose)
                }
            }
        }
        else {
            println("Start!")
            copyArchive(source, destination, true, verbose)
        } 
    }

    else {
        println("OS not suported")
    }
    println("Finish!")
}

fun createTheDirFolder(destination: String, create: Boolean): File? {
    var destin = File(destination)

    if (!(destin.exists())) {
        return null
    }

    else {
        var theDir = File(destination, "The dir")

        if(create && !(theDir.exists())) {
            theDir.mkdir()
            return File (destination, theDir.name)              /* /home/user/Desktop/dir1/"The Dir"/ */

        } else if(theDir.exists()) {
            return File (destination, theDir.name)

        } else {
            return File(destination)
        }
        
    }
}

fun getPathFile(): String? {
    var osName = System.getProperty("os.name").toLowerCase()
    var path: String? = null

    if(osName.indexOf("win") >= 0) {            
        path = getPenPathFileWindows()          //GetPath to Windows
    }

    else if (osName.indexOf("unix") >= 0 || osName.indexOf("linux") >= 0 ) {
        path = getPenPathFileLinux()            //GetPath to Linux
    }

    return path
}


/* Verifica o tempo todo se a pasta "/media/nomeDoUsuarioLogado" foi alterada
 Caso ela tenha sido alterada, adicionando uma nova midia montada ela retornara
 o caminho absoluto dessa nova midia
*/

fun getPenPathFileLinux(): String {

    var path = File("/media/" + System.getProperty("user.name"))
    var base = path.list()
    var current: Array<String>?

    do {
        current = path.list()
        Thread.sleep(TIME_OF_DELAY)
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

fun getPenPathFileWindows(): String {

    var base = File.listRoots()

    do {
        var current = File.listRoots()
        Thread.sleep(TIME_OF_DELAY)
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
    val rootPath = File(root)                                           /* /media/user/drive/ */
    var destination = dest

    var destinationString = createTheDirFolder(dest, createTheDirPath)?.absolutePath
    if (destinationString == null)  {
        return
    }
    else {
        if(createTheDirPath) {
            destination = destinationString + File.separator + rootPath.name
        }
    }
    
    
    var list: Array<File> = rootPath.listFiles()
    for(archive in list) {
        var destinationTMP = destination + File.separator + archive.name + File.separator                     /* /home/user/Desktop/dir1/"The Dir"/otherDir/  */
        var finalDestin = File(destination, archive.name)
        try {
            if(verbose == true) {
                println(finalDestin)
            }
            var pathToString = archive.copyTo(finalDestin, true)
        }
        catch(e: Exception) {
            continue
        }
        
        if(archive.isDirectory()) {copyArchive(archive.absolutePath, destinationTMP, false, verbose)}
    }
}