pipeline {
    agent any
    
    stages {
        stage('Cleanup') {
            steps {
                script {
                    sh "docker image prune -f"
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("petshop-prod:latest", ".")
                }
            }
        }
        
        stage('Deploy Container') {
            steps {
                script {
                    sh "docker stop petshop-prod || true"
                    sh "docker rm petshop-prod || true"
                    
                    sh """
                        docker run -d \
                            --name petshop-prod \
                            --restart unless-stopped \
                            -p 8090:8080 \
                            --log-opt max-size=10m \
                            --log-opt max-file=2 \
                            petshop-prod:latest
                    """
                }
            }
        }
        
        stage('Verify') {
            steps {
                script {
                    sleep(5)
                    def status = sh(
                        script: "docker ps --filter name=petshop-prod --format '{{.Status}}'",
                        returnStdout: true
                    ).trim()
                    
                    if (!status.contains("Up")) {
                        error("Container não está rodando!")
                    }
                    
                    echo "✅ Container rodando: ${status}"
                }
            }
        }
    }
    
    post {
        success {
            echo '✅ Deploy concluído com sucesso!'
        }
        failure {
            echo '❌ Pipeline falhou. Verificar logs:'
            sh "docker logs petshop-prod || true"
        }
        always {
            echo 'Pipeline finalizado.'
        }
    }
}