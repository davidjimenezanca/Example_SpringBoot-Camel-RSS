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
package camel.route;

import camel.processor.CNNXmlProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rss.RssEndpoint;
import org.springframework.stereotype.Component;

/**
 * A simple example router demonstrating the camel-rss component.
 */
@Component
public class CNNRoutesBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Latest news RSS feed
        RssEndpoint endpointLatest =
                endpoint("rss:http://rss.cnn.com/rss/cnn_latest.rss", RssEndpoint.class);
        // Sport news RSS feed
        RssEndpoint endpointSport =
                endpoint("rss:http://rss.cnn.com/rss/edition_sport.rss", RssEndpoint.class);

        // START SNIPPET: e1
        from(endpointLatest).routeId("CNN:latest")
                            .streamCaching().threads(10)
                            .marshal().rss()
                            .startupOrder(1)
                            .process(new CNNXmlProcessor())
                            .to("log:cnn_latest");

        from(endpointSport).routeId("CNN:sport")
                           .streamCaching().threads(5)
                           .marshal().rss()
                           .startupOrder(2)
                           .to("log:cnn_sport");
        // END SNIPPET: e1
    }

}