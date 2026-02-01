// package main.java.com.example;

// public class Calculator {

//     // Code Smell: Long method + high complexity
//     public int calculate(int a, int b, String op) {

//         if(op.equals("add")) {
//             return a + b;
//         }
//         if(op.equals("sub")) {
//             return a - b;
//         }
//         if(op.equals("mul")) {
//             return a * b;
//         }
//         if(op.equals("div")) {
//             if(b == 0) {
//                 return 0;
//             }
//             return a / b;
//         }
//         if(op.equals("mod")) {
//             return a % b;
//         }

//         return 0;
//     }

//     // Code Duplication (students must remove)
//     public int addNumbers(int x, int y) {
//         return x + y;
//     }

//     public int sumValues(int a, int b) {
//         return a + b;
//     }
// }


name: SonarCloud Analysis

on:
  push:
    branches:
      - main
  pull_request:
    types: [opened, synchronize, reopened]

jobs:
  build:
    name: Build and Analyze
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Cache SonarCloud packages
        uses: actions/cache@v4
        with:
          path: ~/.sonar/cache
          key: ${{ runner.os }}-sonar
          restore-keys: ${{ runner.os }}-sonar

      - name: Build with Maven
        run: mvn -B verify

      - name: SonarCloud Scan
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: |
          mvn sonar:sonar \
            -Dsonar.projectKey=YOUR_PROJECT_KEY \
            -Dsonar.organization=YOUR_ORG_KEY \
            -Dsonar.host.url=https://sonarcloud.io
