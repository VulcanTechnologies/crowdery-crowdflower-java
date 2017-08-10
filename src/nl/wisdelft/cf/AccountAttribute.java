package nl.wisdelft.cf;

public enum AccountAttribute {

    COMPANY_TYPE("company_type"),
    COMPANY("company"),
    LICENSE("license"),
    CRYPTED_PASSWORD("crypted_password"),
    CF_SUPPORT_EMAIL("cf_support_email"),
    AFFILIATED_ID("affiliate_id"),
    FIRST_NAME("first_name"),
    CREDITS("credits"),
    ORDER_APPROVED("order_approved"),
    AFFILIATED_CHANNEL("affiliate_channel"),
    PHONE_NUMBER("phone_number"),
    AUTH_KEY("auth_key"),
    LAST_NAME("last_name"),
    EMAIL("email"),
    EXTERNAL_TYPE("external_type"),
    LAST_LOGGED_IN_AT("last_logged_in_at"),
    ACTIVE("active"),
    ADMIN("admin"),
    PROJECT_NUMBER("project_number")
,   ROLE("role"),
    CREATED_AT("created_at"),
    CONTACT_STATUS("contact_status"),
    DIY("diy"),
    SALT("salt"),
    PROCESSOR_ROLE("processor_role"),
    AUTH_CODE("auth_code"),
    AKON_ID("akon_id"),
    JOB_TYPE("job_type"),
    AUTH_EXPIRATION("auth_expiration"),
    ID("id"),
    OPTIONS("options");
    private String theOptions;

    private AccountAttribute(final String aOptions)
    {
        theOptions = aOptions;
    }

    public String getValue()
    {
        return theOptions;
    }
}
