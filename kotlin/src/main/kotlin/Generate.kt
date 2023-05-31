import dsl.*
import dsl.List

val paths = parse("/Users/svetlana.novikova_1/IdeaProjects/Kotlin-DSL-API/kotlin/space-open-api.json")

val content = StardustDslContentProvider {

    paths.forEach { path ->
        chapter(title = path.url) {
            path.methods.forEach { method ->
                val font = when (method.type) {
                    "get" -> "Blue"
                    "post" -> "Green"
                    "delete" -> "Red"
                    "put" -> "Yellow"
                    "patch" -> "Orange"
                    else -> throw RuntimeException("Invalid method type: ${method.type}")
                }
                xml("""
                     <p>Method type: <b><font color="$font">${method.type.uppercase()}</font></b></p>
                     <deflist>
                         <def title="${method.summary}">
                             <p>${method.description}</p>
                         </def>
                     </deflist>""".trimIndent())

                links[path.url]?.let { xml("""<p>For more information refer to: <a href="$it"/></p>""") }
            }
            table {
                tr {
                    td { +"Parameter name" }; td { +"Parameter type" }; td { +"Required" }
                }
                path.methods.forEach { method ->
                    method.parameters.forEach { parameter ->
                        tr {
                            td { +parameter.name }; td { +parameter.type }; td { + if (parameter.required) "Yes" else "No" }
                        }
                    }
                }
            }
        }
    }
    this
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
    "/projects" to "https://www.jetbrains.com/help/space/projects.html",

    )
