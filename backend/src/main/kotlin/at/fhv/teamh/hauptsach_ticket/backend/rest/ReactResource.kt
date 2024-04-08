package at.fhv.teamh.hauptsach_ticket.backend.rest

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.*
import java.net.URL


@WebServlet("/")
class ReactResource : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        println(req?.requestURL.toString())
        val urlPath = URL(req?.requestURL.toString()).path
        println(urlPath)

        var file = File(servletContext.getRealPath(urlPath))

        if (urlPath == "/" || !file.exists()) {
            file = File(servletContext.getRealPath("/index.html"))
        }

        resp?.status = 200
        resp?.contentType = with(urlPath) {
            when {
                endsWith(".js") -> "text/javascript"
                endsWith(".css") -> "text/css"
                endsWith(".png") -> "image/png"
                endsWith(".svg") -> "image/svg+xml"
                endsWith(".json") -> "application/json"
                endsWith(".ico") -> "image/x-icon"
                endsWith(".map") -> "application/json"
                endsWith(".txt") -> "text/plain"
                else -> "text/html"
            }
        }

        val content = StringBuilder()

        BufferedReader(FileReader(file)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                content.append(line)
            }
        }

        // Write the file content to the response

        resp!!.outputStream.writer().use {
            it.write(content.toString())
        }
    }
}