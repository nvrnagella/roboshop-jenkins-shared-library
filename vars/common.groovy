def compile(){
    if ( app_lang == "nodejs"){
        sh 'npm install'
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
        //sh 'npm test || true'
        sh 'npm test'
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
def email(email_note){
    mail bcc: '', body: 'Hi i am from jenkins test failed', cc: '', from: 'nvrnagella90@gmail.com', replyTo: 'nvrnagella@gmail.com', subject: 'test failure email from jenkins', to: ''
}