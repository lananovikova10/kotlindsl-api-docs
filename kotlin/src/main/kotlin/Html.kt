import kotlinx.html.*
import kotlinx.html.stream.createHTML
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.writeText

val htmlOut: Path = Paths.get("./output/openapi.html")
val html = createHTML()

fun main() {
    html.head {
        title { +"OpenAPI reference example" }
        unsafe { +"""<link rel="stylesheet" type="text/css" href="./styles.css"/>""" }
    }


    htmlOut.writeText(html.finalize())

}

private fun BODY.methodDescription(method: Method) {
    h2 { +method.type }
    p { +method.summary }
    p { +method.description }

    table {
        tr {
            td { +"Name" }; td { +"Type" }; td { +"Required" }
        }
        method.parameters.forEach { parameter ->
            tr {
                td { +parameter.name }; td { +parameter.type }; td { +if (parameter.required) "Yes" else "No" }
            }
        }


    }
}