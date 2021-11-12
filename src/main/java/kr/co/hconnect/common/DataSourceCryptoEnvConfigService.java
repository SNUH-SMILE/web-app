package kr.co.hconnect.common;

/**
 * DataSource 암호화 환경설정 서비스
 */
public class DataSourceCryptoEnvConfigService {

    /**
     * 암호화된 비밀번호
     */
    private String encryptedPassword;

    /**
     * 암호화된 비밀번호 Setter
     * @param encryptedPassword 암호화된 비밀번호
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * 복호화된 비밀번호 Getter
     * @return 복호화된 비밀번호
     */
    public String getPassword() {
        return CryptoUtils.decrypt(encryptedPassword);
    }

}
