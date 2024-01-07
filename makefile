make:
	javac -d dist src/*.java src/modèle/*.java src/controller/*.java src/controller/StrategyIA/*.java src/modèle/ChainOfResponsibility/*.java src/modèle/Factory/*.java src/modèle/Strategy/*.java src/test/*.java src/vue/*.java  -cp ":./lib/junit.jar"
	java -cp dist main
	
test: 
	javac -d dist src/*.java src/modèle/*.java src/controller/*.java src/controller/StrategyIA/*.java src/modèle/ChainOfResponsibility/*.java src/modèle/Factory/*.java src/modèle/Strategy/*.java src/test/*.java src/vue/*.java  -cp ":./lib/junit.jar"
	java -cp "./lib/junit.jar:dist" MainTest

docs:
	rm -rf doc
	javadoc -d doc src/*.java src/modèle/*.java src/controller/*.java src/controller/StrategyIA/*.java src/modèle/ChainOfResponsibility/*.java src/modèle/Factory/*.java src/modèle/Strategy/*.java src/test/*.java src/vue/*.java -cp ":./lib/junit.jar"
	firefox doc/index.html	
