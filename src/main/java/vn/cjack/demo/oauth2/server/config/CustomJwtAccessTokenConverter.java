package vn.cjack.demo.oauth2.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import vn.cjack.demo.oauth2.server.repository.UserRepository;
import vn.cjack.demo.oauth2.server.repository.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Autowired
    UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if(authentication.getOAuth2Request().getGrantType() != null && authentication.getOAuth2Request().getGrantType().equalsIgnoreCase("password")) {
            final Map<String, Object> additionalInfo = new HashMap<String, Object>();
            Optional<User> userOpt = userRepository.findByUsername(authentication.getName());
            if(userOpt.isPresent()){
                User user = userOpt.get();
                additionalInfo.put("address", user.getAddress());
                additionalInfo.put("organization", authentication.getName());
                ((DefaultOAuth2AccessToken) accessToken)
                        .setAdditionalInformation(additionalInfo);
            }
            log.info("(accessTokenConverter) {}", authentication.getPrincipal());

        }
        accessToken = super.enhance(accessToken, authentication);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());
        return accessToken;
    }
}
