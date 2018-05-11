/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = camel.CamelRSSApplication.class)
@MockEndpoints("log:*")
@EnableRouteCoverage
public class CamelRSSApplicationCNNTest {

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "mock:log:cnn_latest")
    private MockEndpoint mockLatestNews;

    @EndpointInject(uri = "mock:log:cnn_sport")
    private MockEndpoint mockSportNews;

    @EndpointInject(uri = "mock:log:cnn_technology")
    private MockEndpoint mockTechnologyNews;

    @Test
    public void latestNewsFails() throws Exception {

        NotifyBuilder notify = new NotifyBuilder(camelContext)
                                    .from("CNN:latest")
                                    .whenDone(2)
                                    .create();

        // Introduce a timeout as a delay in case net too low
        notify.matches(5, TimeUnit.SECONDS);

        assertNotNull("Should be done", notify);
        mockLatestNews.expectedMessagesMatches(ex -> !ex.getIn()
                                                        .getBody(String.class)
                                                        .contains("djsfhsdjfh"));
        mockLatestNews.assertIsSatisfied();
    }

    @Test
    public void latestNews() throws Exception {

        NotifyBuilder notify = new NotifyBuilder(camelContext)
                .from("CNN:latest")
                .whenDone(2)
                .create();

        // Introduce a timeout as a delay in case net too low
        notify.matches(5, TimeUnit.SECONDS);

        assertNotNull("Should be done", notify);
        mockLatestNews.expectedMessagesMatches(ex -> ex.getIn()
                                                       .getBody(String.class)
                                                       .contains("<item>"));
        mockLatestNews.assertIsSatisfied();
    }

    @Test
    public void sportNews() throws Exception {

        NotifyBuilder notify = new NotifyBuilder(camelContext)
                .from("CNN:sport")
                .whenDone(2)
                .create();

        // Introduce a timeout as a delay in case net too low
        notify.matches(5, TimeUnit.SECONDS);

        assertNotNull("Should be done", notify);
        mockSportNews.expectedMessagesMatches(ex -> ex.getIn()
                                                       .getBody(String.class)
                                                       .contains("<description>"));
        mockSportNews.assertIsSatisfied();
    }

    @Test
    public void technologyNews() throws Exception {

        NotifyBuilder notify = new NotifyBuilder(camelContext)
                                    .from("CNN:technology")
                                    .whenDone(2)
                                    .create();

        // Introduce a timeout as a delay in case net too low
        notify.matches(5, TimeUnit.SECONDS);

        assertNotNull("Should be done", notify);
        mockTechnologyNews.expectedMessagesMatches(ex -> ex.getIn()
                                                           .getBody(String.class)
                                                           .contains("<description>"));
        mockTechnologyNews.assertIsSatisfied();
    }

}