package contracts.user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return 2 users"

    request {
        url "/v1.0/api/users"
        method GET()
    }

    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body(file("response.json"))
    }

}