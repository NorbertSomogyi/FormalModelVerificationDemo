# FormalModelVerificationDemo

A simple demo kotlin application created in Intellij, that generates a NuSMV script for the UML class example submitted for publication to Modelsward 2023. The application consists of two parts: ModelVerifierDemo/src/main/kotlin/core and  ModelVerifierDemo/src/main/kotlin/uml packages. The first is responsible for the general translator that takes the input of the Joint Relational Model as well as the CTL formulas, and generates a NuSMV script suitable for verification. The second package is the demo part, which creates the UML class example depicted in the paper and calls the translator with the appropriate parameters. The script is generated to the folder ModelVerifierDemo/output/, named verify.smv. This file is already included in the repository, pre-generated. To reproduce the generation, import the project to Intellij and run the file ModelVerifierDemo/src/main/kotlin/Main.kt as the main class of the project.

To execute the verification of the generated file verify.smv, download NuSMV for the appropriate platform here: https://nusmv.fbk.eu/

From the root folder of NuSMV, navigate to the folder 'bin', then run NuSMV like this:
     NuSMV <path_to_smv_file>
Or copy the generated verify.smv file into the bin folder, and run it locally:
     NuSMV verify.smv
     
Verification should finish in an instant, and log the results to the console. All specifications should be true, except for the one checking the value of SpecialDevice.modifier, since the example was constructed for this to be false. A counterexample should also be printed to the console, showing the path InitialState -> Device01 -> Attributes -> Device01.modifier -> Instantiation -> SpecialDevice.modifier as violating the formula.
