#!groovy​

JAVA_VERSION = "8.202"
MAVEN_VERSION = "3.6.0"


properties([[$class  : 'BuildDiscarderProperty',
             strategy: [$class      : 'LogRotator',
                        numToKeepStr: '10']]])

def pom
def currentVersion
def branchType
def branchName = "${env.BRANCH_NAME}"

node('jnode') {
    stage('Checkout') {
        cleanWs()
        branchType = get_branch_type(branchName)
        checkout scm
    }

    stage('Environment set') {
        pom = readMavenPom()
        currentVersion = pom.version
        currentBuild.displayName = "${env.BRANCH_NAME}-${currentVersion} #${env.BUILD_NUMBER}"
    }

    stage('Clean') {
        mvn "clean"
    }

    if (branchType == "pull" || branchType == "dev" || branchType == "master") {
        stage('Compile & CheckStile') {
            mvn "-Dcheckstyle.excludes=\"**/mappers/*Impl.java\" compile"
        }

        stage('Publish') {
            publish()
        }
    }
}

def get_branch_type(String branch_name) {
    print branch_name
    def dev_pattern = ".*develop"
    def release_pattern = ".*release/.*"
    def feature_pattern = ".*feature/.*"
    def hotfix_pattern = ".*hotfix/.*"
    def master_pattern = ".*master"
    def pull_pattern = ".*PR-.*"
    if (branch_name =~ dev_pattern) {
        return "dev"
    } else if (branch_name =~ release_pattern) {
        return "release"
    } else if (branch_name =~ master_pattern) {
        return "master"
    } else if (branch_name =~ feature_pattern) {
        return "feature"
    } else if (branch_name =~ hotfix_pattern) {
        return "hotfix"
    } else if (branch_name =~ pull_pattern) {
        return "pull"
    } else {
        return null;
    }
}

def mvn(String goals) {
    withMaven(
            jdk: JAVA_VERSION,
            maven: MAVEN_VERSION) {
        sh "mvn -B ${goals}"
    }
}

def publish() {
    checkstyle canComputeNew: false, canRunOnFailed: true, defaultEncoding: '', failedTotalAll: '0', healthy: '', pattern: '**/checkstyle-result.xml', unHealthy: '', unstableTotalAll: '0'
    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
}
