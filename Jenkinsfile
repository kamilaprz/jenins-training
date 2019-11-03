pipeline {
    agent any

    stages {
        stage ('Compile') {
            steps {
                sh './gradlew compileJava compileTestJava --no-daemon'
            }
        }
        stage('Verify') {
            parallel {
                stage ('Checkstyle') {
                    steps {
                        sh './gradlew checkstyleMain checkstyleTest --no-daemon'
                    }
                }
                stage ('PMD') {
                    steps {
                        sh './gradlew pmdMain pmdTest --no-daemon'
                    }
                }
                stage ('Spotbugs') {
                    steps {
                        sh './gradlew spotbugsMain spotbugsTest --no-daemon'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test --no-daemon'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}