import ./utils.groovy

timeout(60){
    node("maven"){
        prepareConfig()
        wrap([$class: 'BuildUser']) {
            currentBuild.description = "User: $BUILD_USER"
        }
        stage("Checkout") {
            checkout git-api-tests
            git branch: $REFSPEC, url: "https://github.com/AWKrol/rest_assured_tests"
        }

        def jobs = [:]
        env.TESTS_TYPE.each(v -> {
            jobs["$v"] = node("maven") {
                stage("Running test $v") {
                    triggerJob($v)
                }
            }
        })

        parallel jobs

        stage("Publish allure report") {
            sh "mkdir -p ./allure-results"
            dir("allure-results") {
                jobs.each(k, v -> {
                    copyArtifacts projectName: $v, selector: specific(v.getBuildNumber())
                })
            }

            allure([
                    disabled:true,
                    results:["$pwd/allure-results"]
            ])
        }

    }
}