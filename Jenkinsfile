pipeline {
  agent any
  tools { jdk 'Java17'; maven 'Maven3' }
  stages {
    stage('Test') {
      steps { dir('backend') { sh 'mvn -B test' } }
    }
    stage('Package') {
      steps { dir('backend') { sh 'mvn -B package -DskipTests' } }
    }
  }
}
