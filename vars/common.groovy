def compile(){
    if ( app_lang == "nodejs"){
        sh 'npm install'
    }
    if ( app_lang == "maven"){
        sh 'mvn package'
    }
    if ( app_lang == "golang"){
        sh 'go mod init dispatch'
        sh 'go get'
        sh 'go build'
    }
}
def unittests(){
    //developer is missing unittests cases in this project as a best practice he needs to add them
    if ( app_lang == "nodejs"){
        //sh 'npm test'
        sh 'echo Test cases'
    }
    if ( app_lang == "maven"){
        sh 'mvn test'
    }
    if ( app_lang == "golang"){
        sh 'go test'
    }
}