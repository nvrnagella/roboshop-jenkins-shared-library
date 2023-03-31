
def call(){
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
                    echo 'quality control'
                }
            }
            stage('upload to centralized place'){
                steps{
                    echo 'upload'
                }
            }
        }
    }
}
