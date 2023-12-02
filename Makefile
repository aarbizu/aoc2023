build:
	./gradlew build spotlessCheck installDist idea

run:
	./build/install/aoc2023/bin/aoc2023

tasks:
	./gradlew tasks

.PHONY: build
