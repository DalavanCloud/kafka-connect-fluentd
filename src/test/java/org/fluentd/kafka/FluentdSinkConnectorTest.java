/**
 * Copyright 2017 ClearCode Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package org.fluentd.kafka;

import org.apache.kafka.connect.connector.ConnectorContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluentdSinkConnectorTest {
    private FluentdSinkConnector connector;
    private ConnectorContext context;

    @Before
    public void setup() {
        connector = new FluentdSinkConnector();
        context = PowerMock.createMock(ConnectorContext.class);
        connector.initialize(context);
    }
    @Test
    public void testTaskConfig() {
        PowerMock.replayAll();
        connector.start(buildSinkProperties());
        List<Map<String, String>> taskConfigs = connector.taskConfigs(1);
        Assert.assertEquals("localhost:24225", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CONNECT));
        Assert.assertEquals("100", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_MAX_BUFFER_BYTES));
        Assert.assertEquals("10", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_BUFFER_CHUNK_INITIAL_BYTES));
        Assert.assertEquals("10", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_BUFFER_CHUNK_RETENTION_BYTES));
        Assert.assertEquals("500", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_FLUSH_INTERVAL));
        Assert.assertEquals("true", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_ACK_RESPONSE_MODE));
        Assert.assertEquals("/tmp/fluency", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_FILE_BACKUP_DIR));
        Assert.assertEquals("120", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_WAIT_UNTIL_BUFFER_FLUSHED));
        Assert.assertEquals("120", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_WAIT_UNTIL_FLUSHER_TERMINATED));
        Assert.assertEquals("true", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_JVM_HEAP_BUFFER_MODE));
        Assert.assertEquals("true", taskConfigs.get(0).get(FluentdSinkConnectorConfig.FLUENTD_CLIENT_TIMESTAMP_INTEGER));
        PowerMock.verifyAll();
    }

    private Map<String, String> buildSinkProperties() {
        Map<String, String> sinkProperties = new HashMap<>();
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CONNECT, "localhost:24225");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_MAX_BUFFER_BYTES, "100");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_BUFFER_CHUNK_INITIAL_BYTES, "10");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_BUFFER_CHUNK_RETENTION_BYTES, "10");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_FLUSH_INTERVAL, "500");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_ACK_RESPONSE_MODE, "true");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_FILE_BACKUP_DIR, "/tmp/fluency");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_WAIT_UNTIL_BUFFER_FLUSHED, "120");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_WAIT_UNTIL_FLUSHER_TERMINATED, "120");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_JVM_HEAP_BUFFER_MODE, "true");
        sinkProperties.put(FluentdSinkConnectorConfig.FLUENTD_CLIENT_TIMESTAMP_INTEGER, "true");
        return sinkProperties;
    }
}