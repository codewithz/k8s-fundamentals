pipeline {
    agent any
    tools{
        jdk 'OpenJDK8'
        maven 'Maven3'
    }

    stages {
        stage('SCM') {
            steps {
               git 'https://github.com/codewithz/docker-spring-demo-project.git'
            }
        }
        stage('Maven Build') {
            steps {
                sh "mvn clean install"
            }
        }
        stage('Docker Build and Push') {
            steps {
                script{
                   
                    withDockerRegistry(credentialsId: '8b4d4b83-ef8b-423c-a30c-79df266757c7', toolName: 'Docker') {
                        sh "docker build -t  nzartab/jenkins-docker-demo:${BUILD_NUMBER} ."
                        sh "docker tag nzartab/jenkins-docker-demo:${BUILD_NUMBER} nzartab/jenkins-docker-demo:${BUILD_NUMBER}"
                        sh "docker push nzartab/jenkins-docker-demo:${BUILD_NUMBER}"
                        sh "docker tag nzartab/jenkins-docker-demo:${BUILD_NUMBER} nzartab/jenkins-docker-demo:latest"
                        sh "docker push nzartab/jenkins-docker-demo:latest"
                    }
                    

                }
            }
        }
    }
   }
