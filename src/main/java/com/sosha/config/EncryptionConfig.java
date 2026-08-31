package com.sosha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sosha.encryption")
public class EncryptionConfig {
    private String sqliteCipherKey;
    private String pgCryptoPassword;
    private String encryptionAlgorithm = "AES-256-GCM";

    // Getters & Setters
    public String getSqliteCipherKey() { return sqliteCipherKey; }
    public void setSqliteCipherKey(String key) { sqliteCipherKey = key; }
    
    public String getPgCryptoPassword() { return pgCryptoPassword; }
    public void setPgCryptoPassword(String pwd) { pgCryptoPassword = pwd; }
    
    public String getEncryptionAlgorithm() { return encryptionAlgorithm; }
    public void setEncryptionAlgorithm(String algo) { encryptionAlgorithm = algo; }

    /**
     * Load encryption key dari OS Keychain (Windows Credential Manager)
     * or fallback ke ~/.sosha/secret.key file
     */
    public void loadKeysFromOS() {
        try {
            // Windows: load dari Credential Manager
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                // Placeholder: in production use DPAPI or third-party lib
                this.sqliteCipherKey = "sosha-demo-key-256bit";
            } else {
                // Linux/Mac: load dari ~/.sosha/secret.key
                this.sqliteCipherKey = "sosha-demo-key-256bit";
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load encryption keys: " + e.getMessage());
        }
    }
}
