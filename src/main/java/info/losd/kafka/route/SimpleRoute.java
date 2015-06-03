package info.losd.kafka.route;

import info.losd.kafka.config.KafkaServerConfig;
import info.losd.kafka.config.ZookeeperConfig;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Andrew Braithwaite
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
@Component
public class SimpleRoute extends RouteBuilder{
    @Autowired
    ZookeeperConfig zookeeperConfig;

    @Autowired
    KafkaServerConfig kafkaServerConfig;

    @Override
    public void configure() throws Exception {
        String kafka = String.format("kafka:%s:%d?zookeeperHost=%s&zookeeperPort=%d&topic=test&groupId=group1&serializerClass=kafka.serializer.StringEncoder",
                kafkaServerConfig.getHost(),
                kafkaServerConfig.getPort(),
                zookeeperConfig.getHost(),
                zookeeperConfig.getPort());

        from("netty-http:http://localhost:18080?disableStreamCache=true").noStreamCaching().log(LoggingLevel.INFO, "in").to(kafka);
        from(kafka).log(LoggingLevel.INFO, "out");
    }
}
