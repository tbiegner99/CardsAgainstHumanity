pipeline {
    agent any

    stages {

        stage('Build') {
            parallel {
                stage("Build UI") {
                    agent {
                       docker "node:16"
                    }
                    steps {
                        git(
                            url: "https://github.com/tbiegner99/CardsAgainstHumanity.git",
                            branch: "master",
                            changelog: true
                        )
                        dir("ui") {
                            sh 'pwd'
                            sh 'ls'
                            sh 'npm i'
                            sh 'npm run build'
                            echo "UI BUILD Complete..."
                            sh 'ls build'
                           stash includes: 'build/**', name: 'ui_build'
                        }


                    }
                }

                 stage("Build Backend") {
                    steps {
                        git(
                            url: "https://github.com/tbiegner99/CardsAgainstHumanity.git",
                            branch: "master",
                            changelog: true
                        )
                        dir("server") {
                            sh 'docker build .'

                        }
                        echo "BACKEND BUILD Complete..."
                    }
                }
            }

        }
         stage('Migrate') {

            steps {
                echo "Migration Complete..."
            }

        }
         stage('Deploy') {

            failFast true
            parallel {
                stage("Deploy UI") {
                    steps {
                        unstash 'ui_build'
                        sh 'ls build'
                        sshPublisher(publishers: [sshPublisherDesc(configName: 'cards-against-humanity', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: './CardsAgainstHumanity/ui/build', remoteDirectorySDF: false, removePrefix: 'build', sourceFiles: 'build/**/*')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
                        echo "UI DEPLOY Complete..."
                    }
                }

                 stage("Deploy Baackend") {
                    steps {
                        echo "BACKEND DEPLOY Complete..."
                    }
                }
            }

        }

    }
}
