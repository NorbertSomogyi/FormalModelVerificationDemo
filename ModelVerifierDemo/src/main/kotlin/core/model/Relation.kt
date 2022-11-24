package core.model

data class Relation(val id: String, val transitions: Set<Pair<ModelElement, ModelElement>>)
