node {
 
       stage('Checkout') {
            git url: 'https://github.com/piomin/sample-spring-microservices.git'
        }
 
 
        stage('Build') {
            sh 'mvn clean install'
         }
 
        stage('Image') {
            dir ('discovery-service') {
                def app = docker.build "localhost:5000/discovery-service:${env.version}"
                app.push()
            }
        }
 
        stage ('Run') {
            docker.image("localhost:5000/discovery-service:${env.version}").run('-p 8761:8761 -h discovery --name discovery')
        }
 
        stage ('Final') {
            build job: 'account-service-pipeline', wait: false
        }      
 
    
 
}