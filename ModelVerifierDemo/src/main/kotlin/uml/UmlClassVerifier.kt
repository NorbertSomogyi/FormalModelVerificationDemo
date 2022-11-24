package uml

import core.ModelVerifier
import core.model.ModelElement
import core.model.Relation

class UmlClassVerifier() {
    fun generateNuSMVScript() : String {
        val modelElements = listOf<ModelElement>(
            ModelElement("FactoryA"),
            ModelElement("FactoryB"),
            ModelElement("SpecialDevice"),
            ModelElement("Factory"),
            ModelElement("Device"),
            ModelElement("Factory.location"),
            ModelElement("Device.imei"),
            ModelElement("Device.capacity"),
            ModelElement("SpecialDevice.modifier"),
            ModelElement("Device01"),
            ModelElement("Device01.modifier"),
            ModelElement("Zero")
        )

        val relations = listOf("Inheritance", "Attributes", "Instantiation", "Value")
        val inheritanceTransitions = setOf(
            Pair(modelElements[0], modelElements[3]),
            Pair(modelElements[1], modelElements[3]),
            Pair(modelElements[0], modelElements[3])
        )
        val attributesTransitions = setOf(
            Pair(modelElements[3], modelElements[5]),
            Pair(modelElements[4], modelElements[6]),
            Pair(modelElements[4], modelElements[7]),
            Pair(modelElements[9], modelElements[10])
        )
        val instantiationTransitions = setOf(
            Pair(modelElements[9], modelElements[2]),
            Pair(modelElements[10], modelElements[8])
        )
        val valueTransitions = setOf(
            Pair(modelElements[10], modelElements[11])
        )
        val initialStateTransitions = setOf(
            modelElements[0],
            modelElements[1],
            modelElements[9]
        )
        val ctlFormulas = mutableSetOf(
            "AG (Attributes -> !EX EX Attributes)",
            "AG ((EX (Instantiation & EX SpecialDevice.modifier)) -> !EX(Value & EX Zero))"
        )
        ctlFormulas.addAll(generateAcyclicConstraints())

        val allRelations = setOf(
            Relation(relations[0], inheritanceTransitions),
            Relation(relations[1], attributesTransitions),
            Relation(relations[2], instantiationTransitions),
            Relation(relations[3], valueTransitions)
        )

        val verifier = ModelVerifier(
            modelElements.toSet(),
            allRelations,
            initialStateTransitions,
            ctlFormulas
        )

        return verifier.generateNuSMVScript()
    }

    private fun generateAcyclicConstraints(): Set<String> {
        val formulae = mutableSetOf<String>()
        val classes = setOf("FactoryA", "FactoryB", "Factory", "Device", "SpecialDevice")
        for (c in classes) {
            formulae += "AG ($c -> !EX(Inheritance & E[(!Instantiation & !Attributes & !Value) U $c]))"
        }
        return formulae
    }
}