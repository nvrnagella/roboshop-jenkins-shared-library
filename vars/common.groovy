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
    if ( app_lang == "nodejs"){
        sh 'npm test'
    }
    if ( app_lang == "maven"){
        sh 'mvn package'
    }
    if ( app_lang == "golang"){
        sh 'go test'
    }
}