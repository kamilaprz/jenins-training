pipeline {
    agent any

    stages {
        stage ('Compile') {
            steps {
                sh './gradlew compileJava compileTestJava'
            }
        }
        stage('Verify') {
            parallel {
                stage ('Spotbugs') {
                    steps {
                        sh './gradlew spotbugsMain spotbugsTest'
                    }
                }
                stage ('Checkstyle') {
                    steps {
                        sh './gradlew checkstyleMain checkstyleTest'
                    }
                }
                stage ('PMD') {
                    steps {
                        sh './gradlew pmdMain pmdTest'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}