def call(){
    try{
        pipeline{
            agent {
                label 'workstation'
            }
            stages{
                stage('compile/built'){
                    steps{
                        script{
                            common.compile()
                        }
                    }
                }
                stage('unit tests'){
                    steps{
                        script{
                            common.unittests()
                        }
                    }
                }
                stage('quality control'){
                    environment{
                        SONAR_USER= '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                        //SONAR_PASS= '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                    }
                    steps{
                        script{
                            SONAR_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                            wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]){
                                println "Password = ${SONAR_PASS}"
                                sh "echo sh password = ${SONAR_PASS}"
                                sh "sonar-scanner -Dsonar.host.url=http://172.31.6.251:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=cart"
                            }
                        }
                    }
                }
                stage('upload to centralized place'){
                    steps{
                        echo 'upload'
                    }
                }
            }
        }
    }catch (Exception e){
        common.email("failed")
    }
}