pipeline {
    agent any

    stages {
        stage ('Compile') {
            steps {
                sh './gradlew compileJava'
                sh './gradlew compileTestJava'
            }
        }
        stage('Verify') {
            parallel {
                stage ('Spotbugs') {
                    steps {
                        sh './gradlew spotbugsMain'
                        sh './gradlew spotbugsTest'
                    }
                }
                stage ('Checkstyle') {
                    steps {
                        sh './gradlew checkstyleMain'
                        sh './gradlew checkstyleTest'
                    }
                }
                stage ('PMD') {
                    steps {
                        sh './gradlew pmdMain'
                        sh './gradlew pmdTest'
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