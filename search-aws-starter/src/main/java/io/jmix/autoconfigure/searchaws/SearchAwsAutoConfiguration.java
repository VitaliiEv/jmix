/*
 * Copyright 2021 Haulmont.
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
 */

package io.jmix.autoconfigure.searchaws;

import com.amazonaws.auth.*;
import com.google.common.base.Strings;
import io.jmix.autoconfigure.search.SearchAutoConfiguration;
import io.jmix.search.SearchApplicationProperties;
import io.jmix.search.SearchConfiguration;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(SearchAutoConfiguration.class)
public class SearchAwsAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SearchConfiguration.class);

    @Autowired
    protected SearchApplicationProperties searchApplicationProperties;

    @Bean("search_RestHighLevelClient")
    @ConditionalOnProperty(name = "jmix.search.elasticsearch.aws.iamAuth", matchIfMissing = true)
    public RestHighLevelClient elasticSearchClient() {
        log.debug("Create ES Client with AWS IAM Authentication");
        String esUrl = searchApplicationProperties.getElasticsearchUrl();
        HttpHost esHttpHost = HttpHost.create(esUrl);
        RestClientBuilder restClientBuilder = RestClient.builder(esHttpHost);


        String region = searchApplicationProperties.getElasticsearchAwsRegion();
        String serviceName = searchApplicationProperties.getElasticsearchAwsServiceName();

        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName(serviceName);
        signer.setRegionName(region);

        AWSCredentialsProvider credentialsProvider;
        if (Strings.isNullOrEmpty(searchApplicationProperties.getElasticsearchAwsAccessKey())) {
            credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();
        } else {
            AWSCredentials credentials = new BasicAWSCredentials(
                    searchApplicationProperties.getElasticsearchAwsAccessKey(),
                    searchApplicationProperties.getElasticsearchAwsSecretKey()
            );

            credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        }

        HttpRequestInterceptor interceptor = new AwsRequestSigningInterceptor(serviceName, signer, credentialsProvider);
        restClientBuilder.setHttpClientConfigCallback(builder -> builder.addInterceptorLast(interceptor));

        return new RestHighLevelClient(restClientBuilder);
    }
}
