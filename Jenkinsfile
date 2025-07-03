pipeline {
    agent any

    environment {
        AWS_USER = 'Serge'                         // ec2-user для Amazon Linux
        AWS_HOST = 'eu-north-1.console.aws.amazon.com'
        KEY_PATH = '/var/lib/jenkins/.ssh/your-key.pem'
        JAR_NAME = 'parser-service.jar'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                sh """
                echo 'Copying JAR to AWS EC2...'
                scp -i $KEY_PATH -o StrictHostKeyChecking=no target/$JAR_NAME $AWS_USER@$AWS_HOST:/home/$AWS_USER/

                echo 'Restarting application remotely...'
                ssh -i $KEY_PATH -o StrictHostKeyChecking=no $AWS_USER@$AWS_HOST '
                    pkill -f $JAR_NAME || true
                    nohup java -jar /home/$AWS_USER/$JAR_NAME > app.log 2>&1 &
                '
                """
            }
        }
    }
}
