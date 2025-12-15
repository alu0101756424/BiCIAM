# BiCIAM

[![CI](https://github.com/alu0101756424/BiCIAM/actions/workflows/testing.yaml/badge.svg)](https://github.com/alu0101756424/BiCIAM/actions/workflows/testing.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=alu0101756424_BiCIAM&metric=alert_status&token=946fae3d6dae767ce223b83f0af827438af65a34)](https://sonarcloud.io/summary/new_code?id=alu0101756424_BiCIAM)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=alu0101756424_BiCIAM&metric=bugs&token=946fae3d6dae767ce223b83f0af827438af65a34)](https://sonarcloud.io/summary/new_code?id=alu0101756424_BiCIAM)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=alu0101756424_BiCIAM&metric=code_smells&token=946fae3d6dae767ce223b83f0af827438af65a34)](https://sonarcloud.io/summary/new_code?id=alu0101756424_BiCIAM)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=alu0101756424_BiCIAM&metric=coverage&token=946fae3d6dae767ce223b83f0af827438af65a34)](https://sonarcloud.io/summary/new_code?id=alu0101756424_BiCIAM)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=alu0101756424_BiCIAM&metric=duplicated_lines_density&token=946fae3d6dae767ce223b83f0af827438af65a34)](https://sonarcloud.io/summary/new_code?id=alu0101756424_BiCIAM)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=alu0101756424_BiCIAM&metric=sqale_rating&token=946fae3d6dae767ce223b83f0af827438af65a34)](https://sonarcloud.io/summary/new_code?id=alu0101756424_BiCIAM)

## Overview

BiCIAM is a Java-based metaheuristics framework designed to solve optimization problems using various algorithms such as Genetic Algorithms, Hill Climbing, Particle Swarm Optimization (PSO), and Evolution Strategies. It includes strategies for both single-objective and multi-objective optimization.

## Prerequisites

- **Java Development Kit (JDK)**: version 17 or higher.
- **Maven**: version 3.8.x or higher.

## Getting Started

### Installation

Clone the repository and build the project using Maven:

```bash
git clone https://github.com/alu0101756424/BiCIAM.git
cd BiCIAM
mvn clean install
```

### Running Tests

To run the unit tests:

```bash
mvn test
```

## Project Structure

- `src/main/java/metaheuristics`: Core metaheuristic implementations (Generators, Strategies).
- `src/main/java/problem`: Problem definitions and interfaces.
- `src/main/java/local_search`: Local search implementations and candidate management.
- `src/test/java`: Unit tests using JUnit 5 and Mockito.

## Quality Assurance

This project integrates several quality assurance tools:

- **SonarCloud**: For continuous code quality and security analysis.
- **JaCoCo**: For code coverage reporting.
- **SpotBugs & FindSecBugs**: For static analysis and security auditing.
- **PMD & Checkstyle**: For code style and common error patterns.

To generate quality reports locally:

```bash
mvn verify site
```
