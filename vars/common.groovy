def compile(){
    if ( app_lang == "nodejs"){
        sh 'npm install'
        //sh 'env'
    }
    if ( app_lang == "maven"){
        sh 'mvn package'
    }
    if ( app_lang == "golang"){
        sh 'go mod init dispatch || true'
        sh 'go get'
        sh 'go build'
    }
}
def unittests(){
    //developer is missing unittests cases in this project as a best practice he needs to add them
    if ( app_lang == "nodejs"){
        sh 'npm test || true'
        //sh 'npm test'
    }
    if ( app_lang == "maven"){
        sh 'mvn test'
    }
    if ( app_lang == "golang"){
        sh 'go test'
    }
    if ( app_lang == "python" ){
        sh 'python3 -m unittest'
    }
}

def artifactPush(){
    sh "echo ${TAG_NAME} > VERSION"
    if (app_lang == "nodejs"){
        sh "zip -r cart-${TAG_NAME}.zip node_modules server.js VERSION"
    }
    sh 'ls -l'
}

def email(email_note){
    echo "sending email on job failure"
   // mail bcc: '', body: "job failure - ${JOB_BASE_NAME} \n ${JOB_NAME} \n jenkins URL ${JOB_URL}", cc: '', from: 'nvrnagella90@gmail.com', replyTo: '', subject: 'test from jenkins', to: 'nvrnagella@gmail.com'
}

