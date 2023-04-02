def call(){
    if(!env.SONAR_EXTRA_OPTS){
        env.SONAR_EXTRA_OPTS = ""
    }
    try{
        node('workstation') {
            stage('Checkout') {
                cleanWs()
                git branch: 'main', url: "https://github.com/nvrnagella/${component}"
            }
            stage('compile/built') {
                common.compile()
            }
            stage('unit tests') {
                common.unittests()
            }
            stage('quality control') {
                SONAR_USER = sh( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                SONAR_PASS = sh( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
                    //sh "sonar-scanner -Dsonar.host.url=http://172.31.6.251:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"
                }
            }
            stage('upload to centralized place') {
                echo 'upload'
            }
        }
    }catch (Exception e){
        common.email("failed")
    }
}
