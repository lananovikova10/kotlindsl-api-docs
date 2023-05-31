import dsl.*
import dsl.List

val paths = parse("/Users/svetlana.novikova_1/IdeaProjects/Kotlin-DSL-API/kotlin/space-open-api.json")

val content = StardustDslContentProvider {
    paths.forEach { path ->
        chapter(title = path.url) {
            path.methods.forEach { method ->
                val font = when(method.type){
                    "get" -> "Blue"
                    "post" -> "Green"
                    "delete" -> "Red"
                    "put" -> "Yellow"
                    "patch" -> "Orange"
                    else -> throw RuntimeException("Invalid method type: ${method.type}")
                }
                xml ("""<p>Method type: <b><font color="$font">${method.type.uppercase()}</font></b></p><deflist><def><title>${method.summary}</title><p>${method.description}</p></def></deflist>""")
//                p {+ method.summary}
//                p {+ method.description}
                links[path.url]?.let { xml("""<p>For more information refer to: <a href="$it"/></p>""") }
            }
            table {
                tr {
                    td {
                        + "Parameter name"
                    }
                    td {
                        + "Parameter type"
                    }
                }
                path.methods.forEach { method ->
                    method.parameters.forEach { parameter ->
                        tr {
                            td {
                                + parameter.name
                            }
                            td {
                                + parameter.type
                            }
                        }
                    }
                }
            }
        }
    }
    this
}

private fun List.listItemN(n: Int) {
    li { +"List item $n" }
}

fun main() {
    generateChunk(
        "API.xml",
        "Generated_content",
        content = content
    )
}

val links = mapOf(
    "/absences" to "https://www.jetbrains.com/help/space/configure-absences.html",

    )
