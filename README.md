# Truth Table Generator by Felipe Yanaga

[LINK TO PROJECT](https://rocky-savannah-36851.herokuapp.com)

## Why did I create this project? 

I created this project after taking COMP 283 - Discrete Structures. We had one assignment where we had to create a complex truth table with up to 5 statements in Excel. To be completely honest, I am not the best Excel user, so I struggled a lot with that particular assignment. For most propositional logic statements, [Stanford's truth table generator](https://web.stanford.edu/class/cs103/tools/truth-table-tool/) suffices, but it does not take in multiple statements. Therefore, I obviously had to do something about that. 

I hope this little project helps future students taking COMP 283!

## How does the code work? 

The main structure used in this project was a *Push Down Automata (PDA)*. Propositional Logic turns out to be a Context Free Grammar (CFG), and so, I used a PDA to evaluate whether the UserInput was valid or not. 

In addition to that, I used a *Parse Tree* to evalute the statements. The parse tree was similar to this one: 

![Parse Tree](https://user-images.githubusercontent.com/69719875/115172024-5b28c500-a092-11eb-8b3f-67ce7235ae23.jpeg =250x250)

*More details to be added later*

## Where to go from here? 

There is a lot to be added to this project. One of the features that need to be added is error mesasge handling. When users write something invalid, a "Internal Server Error," and so I need to start displaying proper error messages.

I also need to add features to tell users how to correct their statements - This is something that Stanford's website does, but mine doesn't *YET*.

## Problems I ran into

*TO BE ADDED*

Spoiler: it's a lot.

## Message to More Experienced Programmers:

There's still a lot to be done with respect to readibility! This is just the first *working* version of the code. Please, feel free to message me with any suggestions on how to improve anything!

## Message to Other Novice Programmers:

If you would like to learn more about the technologies I used, feel free to message me! 


# Quarkus Instructions

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Related guides


## Provided examples

### RESTEasy JAX-RS example

REST is easy peasy with this Hello World RESTEasy resource.

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
