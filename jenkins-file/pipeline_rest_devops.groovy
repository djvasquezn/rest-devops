node {
 
       stage('Checkout') {
            git url: 'https://github.com/djvasquezn/rest-devops.git'
        }
 
        stage('Build') {
			sh "kill 8081"
            sh 'mvn clean install'
         }
 
        stage('Image') {
			def app = docker.build("rest-devops-docker")
        }
 
        stage ('Run') {
            docker.image("rest-devops-docker").run('-p 8081:8081 -h devops --name devops')
        }
 
}