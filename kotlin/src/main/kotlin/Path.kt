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
    val schema: ParameterSchema
)

data class ParameterSchema(
    val nullable: Boolean? = null,
    val default: Any? = null,
    val type: String? = null,
    val format: String? = null
)