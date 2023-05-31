data class Path(
    val url: String,
    val methods: List<Method>,
)

data class Method(
    val type: String,
    val summary: String,
    val description: String,
    val parameters: List<Parameter> = emptyList()
)

data class Parameter(
    val name: String,
    val type: String,
    val required: Boolean,
    val schema: Map<String, String>
)
