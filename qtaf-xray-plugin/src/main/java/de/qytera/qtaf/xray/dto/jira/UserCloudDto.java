package de.qytera.qtaf.xray.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * The DTO for Jira Cloud when working with users.
 *
 * @see <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-users/#api-rest-api-3-user-get">Get user (Jira Cloud)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCloudDto extends UserDto<ApplicationRoleCloudDto, GroupCloudDto> {
    /**
     * The account ID of the user, which uniquely identifies the user across all Atlassian products.
     * For example, 5b10ac8d82e05b22cc7d4ef5. Required in requests.
     */
    private String accountId;
    /**
     * The user account type. Can take the following values:
     * <dl>
     *     <dt>{@code atlassian}</dt>
     *     <dd>regular Atlassian user account</dd>
     *     <dt>{@code app}</dt>
     *     <dd>system account used for Connect applications and OAuth to represent external systems</dd>
     *     <dt>{@code customer}</dt>
     *     <dd>Jira Service Desk account representing an external service desk</dd>
     *     <dt>{@code unknown}</dt>
     *     <dd>everything else</dd>
     * </dl>
     */
    private String accountType;

    /**
     * The user account type.
     */
    @RequiredArgsConstructor
    public enum AccountType {
        /**
         * A regular Atlassian user account.
         */
        ATLASSIAN("atlassian"),
        /**
         * A system account used for Connect applications and OAuth to represent external systems.
         */
        APP("app"),
        /**
         * A Jira Service Desk account representing an external service desk.
         */
        CUSTOMER("customer"),
        /**
         * An unknown account.
         */
        UNKNOWN("unknown");
        /**
         * The account type.
         */
        public final String text;
    }

    /**
     * Set the request's account type.
     *
     * @param accountType the account type
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType.text;
    }

    /**
     * Get the account type.
     *
     * @return the account type or null if not set
     */
    public AccountType getAccountType() {
        if (this.accountType == null) {
            return null;
        }
        return AccountType.valueOf(this.accountType.toUpperCase());
    }

}
