def call() {
    if (!env.SONAR_EXTRA_OPTS) {
        env.SONAR_EXTRA_OPTS = ""
    }

    if (!env.TAG_NAME) {
        env.PUSH_CODE = "false"
    } else {
        env.PUSH_CODE = "true"
    }

    try {
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
                sh 'echo running sonar scan'
                //SONAR_USER = sh( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                //SONAR_PASS = sh( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                //wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
                //sh "sonar-scanner -Dsonar.host.url=http://172.31.6.251:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"
            }
            if (env.PUSH_CODE == "true") {
                stage('upload to centralized place') {
                    common.artifactPush()
                }
            }
        }
    }
    catch (Exception e) {
        common.email("failed")
    }
}