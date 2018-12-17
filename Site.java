package application;

import javax.crypto.SecretKey;

public class Site {
    private String domainName;
    private String domain;
    private String username;
    private SecretKey secretKey;
    private byte[] cypherText;

    Site(String domainName, String domain, String username, SecretKey secretKey, byte[] cypherText){
        this.domainName = domainName;
        this.domain = domain;
        this.username = username;
        this.secretKey = secretKey;
        this.cypherText = cypherText;
    }
    public String getUsername() {
        return username;
    }
    public String getDomainName() {
        return domainName;
    }
    public String getDomain() {
        return domain;
    }
    public SecretKey getSecretKey() {
        return secretKey;
    }
    public byte[] getCypherText() {
        return cypherText;
    }


}