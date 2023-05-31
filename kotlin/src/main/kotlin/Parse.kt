import com.google.gson.*
import java.io.FileReader

fun parse(jsonPath: String): List<Path> = mutableListOf<Path>().apply {
    val parsedFile = Gson().fromJson(FileReader(jsonPath), JsonElement::class.java)
    val pathsJsonElement = parsedFile.asJsonObject["paths"].asJsonObject
    pathsJsonElement.keySet().forEach { urlKey ->
        val urlElement = pathsJsonElement[urlKey].asJsonObject
        val methodsList = mutableListOf<Method>()

        urlElement.keySet().forEach { methodKey ->
            val methodElement = urlElement[methodKey].asJsonObject
            val summary = methodElement["summary"].asString
            val description = methodElement["description"]?.asJsonObject?.get("text")?.asString ?: "Not provided"
            val parametersJsonArray = methodElement["parameters"]?.asJsonArray

            val parametersList = parametersJsonArray?.map { parameterElement ->
                val parameterObject = parameterElement.asJsonObject
                val parameterName = parameterObject["name"].asString
                val parameterType = parameterObject["schema"]?.asJsonObject?.get("type")?.asString ?: "Unknown"
                val parameterRequired = parameterObject["required"].asBoolean
                val parameterSchema = parameterObject["schema"]?.asJsonObject?.let { schemaElement ->
                    ParameterSchema(
                        nullable = schemaElement["nullable"]?.asBoolean,
                        type = schemaElement["type"]?.asString,
                        format = schemaElement["format"]?.asString
                    )
                } ?: ParameterSchema()

                Parameter(parameterName, parameterType, parameterRequired, parameterSchema)
            } ?: emptyList()

            methodsList += Method(methodKey, summary, description, parametersList)
        }

        add(Path(urlKey, methodsList))
    }
}