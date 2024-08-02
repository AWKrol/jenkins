import ./myUtils.groovy

timeout(60){
    node("maven"){
        prepareConfig()
        def jobDescription = """
            api tests
         """
        try {
            currentBuild.description=jobDescription;
            def testContainerName = "api_tests_$BUILD_NUMBER"
            stage ("Run API Tests ${jobDescription}"){

                sh "docker run --rm --network=host --name $testContainerName -t localhost:5005/apitests"
            }
            stage("Publish allure report"){
                allure([
                        disabled:true,
                        results:["$pwd/allure-results"]
                ])
            }
            stage("Telegram notification"){

            }
        }
        finally {
            sh "docker stop $jobDescription"
        }
    }
}