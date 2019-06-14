node {
 
       stage('Checkout') {
            git url: 'https://github.com/djvasquezn/rest-devops.git'
        }
 
        stage('Build') {
            sh 'mvn clean install'
         }
 
        stage('Image') {
            dir ('devops') {
                def app = docker.build "localhost:5000/devops:${env.version}"
                app.push()
            }
        }
 
        stage ('Run') {
            docker.image("localhost:5000/devops:${env.version}").run('-p 8081:8081 -h devops --name devops')
        }
 
}