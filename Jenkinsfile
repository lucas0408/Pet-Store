pipeline {
    agent any
    
    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("petshop-prod:latest", ".")
                }
            }
        }
        
        stage('Run Docker Container') {
            steps {
                script {
                    // Para evitar conflito de containers antigos, você pode parar e remover antes
                    sh "docker stop petshop-prod || true"
                    sh "docker rm petshop-prod || true"
                    sh "docker run -d -p 8090:8080 --name petshop-prod petshop-prod:latest"
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline finalizado.'
        }
    }
}