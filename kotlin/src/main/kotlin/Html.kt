import kotlinx.html.*
import kotlinx.html.stream.createHTML
import java.nio.file.Paths
import kotlin.io.path.writeText

val htmlOut = Paths.get("./output/openapi.html")
val html = createHTML()

fun main() {
    html.head {
        title { +"OpenAPI reference example" }
        unsafe { +"""<link rel="stylesheet" type="text/css" href="./styles.css"/>""" }
    }
    html.body {
        header { +"Excellent API" }

        paths.forEach { path ->
            h1 { +path.url }

            path.methods.forEach { method ->
                methodDescription(method)
            }
        }
    }

    htmlOut.writeText(html.finalize())

}

private fun BODY.methodDescription(m: Method) {
    h2 { +m.type }
    p { +m.summary }
    p { +m.description }

    table {
        tr {
            td { +"Name" }; td { +"Type" }; td { +"Required" }
        }
        m.parameters.forEach { parameter ->
            tr {
                td { +parameter.name }; td { +parameter.type }; td { +if (parameter.required) "Yes" else "No" }
            }
        }


    }
}