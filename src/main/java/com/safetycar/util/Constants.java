package com.safetycar.util;

public final class Constants {

    private Constants() {
    }

    public static final class ConfigConstants {
        public static final String MESSAGE_SOURCE = "messageSource";
        public static final String APPLICATION_PROPERTIES = "classpath:application.properties";
        public static final String COM_MYSQL_CJ_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        public static final String DATABASE_PASSWORD = "database.password";
        public static final String DATABASE_USERNAME = "database.username";
        public static final String DATABASE_URL = "database.url";
        public static final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
        public static final String USER_DETAILS_SERVICE = "userDetailsService";
        public static final String ORG_HIBERNATE_DIALECT_MY_SQL_DIALECT = "org.hibernate.dialect.MySQLDialect";
        public static final String SAFETYCAR_MODELS = "com.safetycar.models";
        public static final String HIBERNATE_DIALECT = "hibernate.dialect";
        public static final String HIBERNATE_CONNECTION_AUTOCOMMIT = "hibernate.connection.autocommit";
        public static final String HIBERNATE_CONNECTION_RELEASE_MODE = "hibernate.connection.release_mode";
        public static final String HIBERNATE_USE_SQL_COMMENTS = "hibernate.use_sql_comments";
        public static final String CONTROLLERS_REST = "com.safetycar.web.controllers.rest";
        public static final String CONTROLLERS_MVC = "com.safetycar.web.controllers.mvc";
        public static final String COM_SAFETYCAR_WEB = "com.safetycar.web";
        public static final String COM_SAFETYCAR_SECURITY = "com.safetycar.security";
    }

    public static final class ErrorsConstants {
        public static final String UNEXPECTED_MESSAGE = "We are sorry for the inconvenience." +
                " Please bear with us while we fix the problem.";
        public static final String EMAIL_ALREADY_EXISTS = "User with this email already exists!";
        public static final String INVALID_TOKEN = "Invalid token!";
        public static final String DUPLICATE_DETAILS_ERROR = "Possible duplicate details!";
        public static final String NO_USER_LIKE_THIS_WAS_FOUND = "No user like this was found!";
    }

    public static final class BaseAmountConstants {
        public static final String NO_CALCULATOR_FOUND_FOR = "No calculator found for capacity %s";
        public static final String CC_MIN = "ccMin";
        public static final String CC_MAX = "ccMax";
        public static final String CAR_AGE_MIN = "carAgeMin";
        public static final String CAR_AGE_MAX = "carAgeMax";
        public static final String CAPACITY = "capacity";
        public static final String CAR_AGE = "age";
    }

    public static final class OfferConstants {
        public static final String TRANSFER_OFFER = "transferOffer";
        public static final String OFFER_DTO = "offerDto";
        public static final String MODELS = "models";
        public static final String BRANDS = "brands";
        public static final double MILLI_SECONDS_IN_YEAR = 3.15576e+10; //3.154e+10;//3.15576e+10;
        public static final String RESULT = "result";
        public static final String MESSAGE = "message";
        public static final String MY_OFFERS = "myOffers";
        public static final String OFFER_RESULTS_VIEW = "offer-results";
    }

    public static final class UserConstants {
        public static final String CREATE_USER_DTO = "createUserDto";
        public static final String FILL_DETAILS = "Please fill your details " +
                "before considering the offer.";
        public static final String DETAILS = "details";
    }

    public static final class ValidationConstants {
        public static final String PASSWORD_MUST_BE_AT_LEAST_SIX_CHARACTERS_LONG =
                "Password must be at least six characters long!";
        public static final String EMAIL_REGEX = "^(.+)@(.+)$";
        public static final String PLEASE_PROVIDE_MATCHING_PASSWORDS = "Please provide matching passwords!";
        public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";
        public static final String THIS_FIELD_CANNOT_BE_BLANK = "This field cannot be blank!";
        public static final String USER_YOUNGER_THAN_LEGAL_YEARS_ERROR = "User younger than legal [18] years!";
        public static final String TELEPHONE_REGEX = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
        public static final String NAME_REGEX = "^(?<firstchar>[A-Z])((?<alphachars>[A-Za-z])|(?<specialchars>[A-Za-z]['-][A-Za-z])|(?<spaces> [A-Za-z]))*$";
        public static final String PLEASE_PROVIDE_A_VALID_PHONE = "Please provide a valid phone";
        public static final String ERROR_ = "error.";
        public static final String BINDING_RESULT_ERRORS = "errors";
        public static final String ERROR = "error";
    }

    public static final class Views {
        public static final String CALCULATE_FIRST = "Calculate your offer before accepting!";
        public static final String SEARCH_OFFERS_DTO = "searchOffersDto";
        public static final String REGISTER_VIEW = "register";
        public static final String EMAIL_CONFIRMATION_VIEW = "email-confirmation";
        public static final String REGISTER_CONFIRMATION_VIEW = "register-confirmation";
        public static final String BAD_USER_VIEW = "bad-user";
        public static final String INDEX_VIEW = "index";
        public static final String OFFER_VIEW = "offer";
        public static final String LOGIN_VIEW = "login";

        public static final class QueryConstants {

            public static final String TELEPHONE = "telephone";
            public static final String FIRST_NAME = "firstName";
            public static final String LAST_NAME = "lastName";
            public static final String ADDRESS = "address";
            public static final String EMAIL = "email";
            public static final String DESC_SORT = "desc";
            public static final String ASC_SORT = "asc";
            public static final String SORT_PARAMETER = "sortParameter";
            public static final String PREMIUM = "premium";
            public static final String SUBMISSION_DATE = "submissionDate";
            public static final String EXPIRATION_DATE = "expirationDate";
            public static final String BRAND = "brand";
            public static final String NAME = "name";
            public static final String FIRST_REGISTRATION = "firstRegistration";
            public static final String CAR = "car";
            public static final String ID = "id";
            public static final String CAR_ = "car.";
            public static final String NET_PREMIUM_APPROVED =
                    "(SELECT SUM(o.premium)\n" +
                            "FROM offers AS o\n" +
                            "INNER JOIN policies AS p on o.offer_id = p.offer_id\n" +
                            "RIGHT JOIN policy_statuses AS ps on ps.status_id = p.status_id\n" +
                            "INNER JOIN user_details_policies AS udp on p.policy_id = udp.policy_id\n" +
                            "WHERE ps.status = 'approved' AND udp.username = email)";
            public static final String NET_PREMIUM_PENDING =
                    "(SELECT SUM(o.premium)\n" +
                            "FROM offers AS o\n" +
                            "INNER JOIN policies AS p on o.offer_id = p.offer_id\n" +
                            "RIGHT JOIN policy_statuses AS ps on ps.status_id = p.status_id\n" +
                            "INNER JOIN user_details_policies AS udp on p.policy_id = udp.policy_id\n" +
                            "WHERE ps.status = 'pending' AND udp.username = email)";
            public static final String COUNT_PREMIUM_APPROVED =
                    "(SELECT COUNT(o.premium)\n" +
                            "FROM offers AS o\n" +
                            "INNER JOIN policies AS p on o.offer_id = p.offer_id\n" +
                            "RIGHT JOIN policy_statuses AS ps on ps.status_id = p.status_id\n" +
                            "INNER JOIN user_details_policies AS udp on p.policy_id = udp.policy_id\n" +
                            "WHERE ps.status = 'approved' AND udp.username = email)";
            public static final String COUNT_PREMIUM_PENDING =
                    "(SELECT COUNT(o.premium)\n" +
                            "FROM offers AS o\n" +
                            "INNER JOIN policies AS p on o.offer_id = p.offer_id\n" +
                            "RIGHT JOIN policy_statuses AS ps on ps.status_id = p.status_id\n" +
                            "INNER JOIN user_details_policies AS udp on p.policy_id = udp.policy_id\n" +
                            "WHERE ps.status = 'pending' AND udp.username = email)";
            public static final String COUNT_OFFERS =
                    "(SELECT COUNT(o.premium)\n" +
                            "FROM offers AS o\n" +
                            "LEFT JOIN user_details_offers AS udo on o.offer_id = udo.offer_id\n" +
                            "WHERE udo.username = email)";

        }
    }
}
