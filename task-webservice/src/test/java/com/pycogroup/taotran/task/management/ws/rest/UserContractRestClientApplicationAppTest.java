package com.pycogroup.taotran.task.management.ws.rest;

import com.pycogroup.taotran.task.management.ws.BaseAppTest;
import com.pycogroup.taotran.task.management.ws.entity.User;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserContractRestClientApplicationAppTest extends BaseAppTest {

    private static final String GROUP_ID = "com.pycogroup.taotran.springmongo";
    private static final String ARTIFACT_ID = "SpringBootMongoDB";
    private static final String VERSION = "1.0-SNAPSHOT";
    private static final String CLASSIFIER = "stubs";

    @Rule
    public StubRunnerRule stubRunnerRule = new StubRunnerRule()
            .downloadStub(GROUP_ID, ARTIFACT_ID, VERSION, CLASSIFIER)
            .withPort(7777)
            .workOffline(true);

    @Test
    public void get_users_from_service_contract() {
        //given:
        final RestTemplate restTemplate = new RestTemplate();

        //when:
        final ResponseEntity<User[]> usersResponse = restTemplate.getForEntity("http://localhost:7777/v1.0/api/users", User[].class);

        BDDAssertions.then(usersResponse.getStatusCodeValue()).isEqualTo(200);

        final User[] users = usersResponse.getBody();

        BDDAssertions.then(users.length).isEqualTo(2);

        BDDAssertions.then(users[0].getId()).isEqualTo("1");
        BDDAssertions.then(users[0].getAge()).isEqualTo(8);
        BDDAssertions.then(users[0].getUsername()).isEqualTo("testUser1");
        BDDAssertions.then(users[0].getPassword()).isEqualTo("abcdef");
        BDDAssertions.then(users[0].getAuthorities()).isEmpty();

        BDDAssertions.then(users[1].getId()).isEqualTo("2");
        BDDAssertions.then(users[1].getAge()).isEqualTo(9);
        BDDAssertions.then(users[1].getUsername()).isEqualTo("testUser2");
        BDDAssertions.then(users[1].getPassword()).isEqualTo("abcdefg");
        BDDAssertions.then(users[1].getAuthorities()).isEmpty();

    }
}
