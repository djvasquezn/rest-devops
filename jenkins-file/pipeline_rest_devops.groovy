node {
 
       stage('Checkout') {
            git url: 'https://github.com/djvasquezn/rest-devops.git'
        }
 
        stage('Build') {
			sh 'docker ps -f name=rest-devops-docker -q | xargs --no-run-if-empty docker container stop'
			sh 'docker container ls -a -fname=rest-devops-docker -q | xargs -r docker container rm'
            sh 'mvn clean install'
         }
 
        stage('Image') {
			def app = docker.build("rest-devops-docker")
        }
 
        stage ('Run') {
            docker.image("rest-devops-docker").run('-p 8081:8081 -h devops --name devops')
        }
 
}