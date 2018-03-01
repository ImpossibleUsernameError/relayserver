
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors
import javax.swing.JFrame

/**
 * Created by Michael on 24.02.2018.
 */
class Server: Runnable, ServerMessageHandler {

    private val PORT = 8888
    var exit = false
    var running = false
    private var handlers = mutableListOf<SocketHandler>()
    private val threadPool = Executors.newCachedThreadPool()
    private val mainWindow = MainWindow()
    private val serverSocket = ServerSocket(PORT)
    private var clientCount = 0
    private val MAX_CLIENTS = 2

    override fun run() {
        val jFrame = JFrame("Server Output")
        jFrame.contentPane = mainWindow.jp_main
        jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        jFrame.
        jFrame.pack()
        jFrame.isVisible = true
        mainWindow.appendTextToMainView("Server is running on IP: ${getIpAddress()} and Port: $PORT\nWaiting for Connections...")
        running = true
        var socket: Socket? = null
        while(!exit && clientCount < MAX_CLIENTS){
            log("Waiting for Client Connects...")
            try {
                socket = serverSocket.accept()
                clientCount++
                log("Connected Client: IP: ${socket.inetAddress}, Port: ${socket.port}")
                val socketHandler = SocketHandler(socket)
                handlers.add(socketHandler)
                log("Starting Client Handling")
                threadPool.execute(socketHandler)
            }
            catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    fun shutdown(){
        log("Shutdown ListenerThread")
        exit = true

        handlers.forEach{
            it.shutdown()
        }
        threadPool.shutdown()
        serverSocket.close()
        running = false
        Thread.currentThread().interrupt()
    }

    private fun getIpAddress(){

    }

    fun log(msg: String){
        mainWindow.appendTextToMainView("$msg\n")
    }

    override fun handleMessage(msg: String): String {
        return "Received Message"
    }


}