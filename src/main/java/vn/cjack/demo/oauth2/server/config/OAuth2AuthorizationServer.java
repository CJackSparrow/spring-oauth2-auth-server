package vn.cjack.demo.oauth2.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
@Slf4j
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Value("${app.security.client-id}")
	private String clientId;

	@Value("${app.security.client-secret}")
	private String clientSecret;

	@Value("${app.security.scopes}")
	private String[] scopes;

	@Value("${app.security.grant-type}")
	private String[] grantTypes;

	@Value("${app.security.access-token-expire-time}")
	private Integer accessTokenExpireTime;

	@Value("${app.security.refresh-token-expire-time}")
	private Integer refreshTokenExpireTime;

	private static final String KEY_STORE_PATH = "emrtest.jks";
	private static final String KEY_STORE_PASS = "emrpass";
	private static final String KEY_PAIR = "emrtest";

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	UserDetailsService userDetailsService;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
				.inMemory()
				.withClient(clientId)
				.secret(passwordEncoder.encode(clientSecret))
				.authorizedGrantTypes(grantTypes)
				.resourceIds(clientId)
				.scopes(scopes)
				.accessTokenValiditySeconds(accessTokenExpireTime)
				.refreshTokenValiditySeconds(refreshTokenExpireTime);
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

		endpoints.tokenStore(tokenStore())
				.tokenEnhancer(tokenEnhancerChain)
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService) // add for refresh_token enpoint
				.pathMapping("/oauth/token", "/public/oauth/token"); // override the default endpoints mappings
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory =
				new KeyStoreKeyFactory(new ClassPathResource(KEY_STORE_PATH), KEY_STORE_PASS.toCharArray());
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(KEY_PAIR));

		return converter;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

}
