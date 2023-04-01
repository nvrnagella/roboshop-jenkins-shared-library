
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
                steps{
                    withAWSParameterStore(credentialsId: '', naming: 'relative', path: '/service', recursive: true, regionName: 'us-east-1'){
                        echo "Hello"
                    }
                    //sh 'sonar-scanner -Dsonar.host.url=http://172.31.6.251:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=cart'
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
