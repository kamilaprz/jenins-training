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
                stage ('Spotbugs main') {
                    steps {
                        sh './gradlew spotbugsMain'
                    }
                }
                stage ('Spotbugs test') {
                    steps {
                        sh './gradlew spotbugsTest'
                    }
                }
                stage ('Checkstyle main') {
                    steps {
                        sh './gradlew checkstyleMain'
                    }
                }
                stage ('Checkstyle test') {
                    steps {
                        sh './gradlew checkstyleTest'
                    }
                }
                stage ('PMD main') {
                    steps {
                        sh './gradlew pmdMain'
                    }
                }
                stage ('PMD test') {
                    steps {
                        sh './gradlew pmdTest'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}