pipeline {
    agent any

    stages {
        stage ('Compile') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                echo './gradlew compileJava compileTestJava --no-daemon'
            }
        }
        stage('Verify') {
            parallel {
                stage ('Checkstyle') {
                    steps {
                        echo './gradlew checkstyleMain checkstyleTest --no-daemon'
                    }
                }
                stage ('PMD') {
                    steps {
                        echo './gradlew pmdMain pmdTest --no-daemon'
                    }
                }
                stage ('Spotbugs') {
                    steps {
                        echo './gradlew spotbugsMain spotbugsTest --no-daemon'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                echo './gradlew test --no-daemon'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}