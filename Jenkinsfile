pipeline {

    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {

            steps {

                checkout scm

            }

        }

        stage('Build & Test') {

            steps {

                bat 'mvn clean test'

            }

        }

        stage('Rerun Failed Tests') {

            steps {

                script {

                    if (fileExists('test-output/testng-failed.xml')) {

                        echo 'Failed tests found. Re-running failed tests.'

                        bat 'mvn test -Dsurefire.suiteXmlFiles=test-output/testng-failed.xml'

                    } else {

                        echo 'No failed tests found.'

                    }

                }

            }

        }

        stage('Archive Reports') {

            steps {

                archiveArtifacts artifacts: 'Reports/**', allowEmptyArchive: true

                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true

                archiveArtifacts artifacts: 'logs/**', allowEmptyArchive: true

            }

        }

    }

    post {

        always {

            echo 'Pipeline execution completed.'

        }

        success {

            echo 'Build Successful.'

        }

        failure {

            echo 'Build Failed.'

        }

    }

}