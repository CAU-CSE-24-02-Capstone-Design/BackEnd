package Team02.BackEnd.oauth.config;

import Team02.BackEnd.oauth.client.GoogleApiClient;
import Team02.BackEnd.oauth.client.KakaoApiClient;
import Team02.BackEnd.oauth.client.NaverApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Http Interface Client 구현체를 빈으로 등록해주는 과정
 */

@Configuration
public class HttpInterfaceConfig {

    @Bean
    public KakaoApiClient kakaoApiClient() {
        return createHttpInterface(KakaoApiClient.class);
    }

    @Bean
    public NaverApiClient naverApiClient() {
        return createHttpInterface(NaverApiClient.class);
    }

    @Bean
    public GoogleApiClient googleApiClient() {
        return createHttpInterface(GoogleApiClient.class);
    }

    private <T> T createHttpInterface(Class<T> clazz) {
        WebClient webClient = WebClient.create();
        HttpServiceProxyFactory build = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();
        return build.createClient(clazz);
    }
}