
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.swing.JFrame

/**
 * Created by Michael on 22.02.2018.
 */
class SocketHandler(val socket: Socket): Runnable {

    var exit = false
    private val TAG = "SocketHandler"
    private val mainWindow = MainWindow()
            
    override fun run() {
        val jFrame = JFrame("Server Output")
        jFrame.contentPane = mainWindow.jp_main
        jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        jFrame.pack()
        jFrame.isVisible = true
        log("Ready to read client messages")
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        var line = input.readLine()
        var msg = ""
        while((line) != null && !exit){
            msg += line
            line = input.readLine()
        }
        log("Received Message from Client: $msg")
        val response = messageHandler.handleMessage(msg)
        log("Sending Response: $response")
        val output = PrintWriter(socket.getOutputStream(), true)
        try {
            output.println(response)
            output.close()
        }
        catch (e: IOException){
            e.printStackTrace()
        }
        finally {
            try{
                output.close()
            }
            catch (e: IOException){}
        }
    }

    fun shutdown(){
        log("Shutdown SocketHandler")
        exit = true
        Thread.currentThread().interrupt()
    }
    
    fun log(msg: String){
        mainWindow.appendTextToMainView("$msg\n")
    }
}