pipeline {
	agent any
    tools {
        maven 'maven-local'
    }
    stages {
        stage('Checkout') {
			steps {
				git url: 'https://github.com/djvasquezn/rest-devops.git' 
				}
			}
 
        stage('Build') {
			steps {
				sh 'mvn clean install'
	 
				def pom = readMavenPom file:'pom.xml'
				print pom.version
				env.version = pom.version
			}
        }
 
        stage('Image') {
			steps {
				dir ('account-service') {
					def app = docker.build "localhost:5000/account-service:${env.version}"
					app.push()
				}
			}
        }
 
        stage ('Run') {
			steps {
				docker.image("localhost:5000/account-service:${env.version}").run('-p 2222:2222 -h account --name account --link discovery')}
			}
 
    }
}