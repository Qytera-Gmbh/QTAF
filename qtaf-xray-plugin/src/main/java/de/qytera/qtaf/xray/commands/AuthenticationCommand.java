package de.qytera.qtaf.xray.commands;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.patterns.Command;
import de.qytera.qtaf.xray.repository.xray.XrayTestRepository;

/**
 * Command for Xray authentication
 */
public class AuthenticationCommand implements Command {

    /**
     * Configuration
     */
    ConfigMap config;

    /**
     * Logger
     */
    final Logger logger = QtafFactory.getLogger();

    /**
     * JWT Token
     */
    String jwtToken = null;

    /**
     * Constructor
     */
    public AuthenticationCommand() {
        try {
            config = QtafFactory.getConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        try {
            jwtToken = XrayTestRepository.getInstance().getXrayAuthorizationHeaderValue();
        } catch (Exception e) {
            logger.error("Could not authenticate");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get jwtToken
     *
     * @return jwtToken
     */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Set jwtToken
     *
     * @param jwtToken JwtToken
     * @return this
     */
    public AuthenticationCommand setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }
}
