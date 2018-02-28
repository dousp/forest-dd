package org.forest.ssl;

import org.forest.exceptions.ForestRuntimeException;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * SSL keyStore
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2018-01-30 16:04
 */
public class SSLKeyStore {

    public final static String DEFAULT_KEYSTORE_TYPE = "jks";

    protected final String id;

    protected final String keystoreType;

    protected String filePath;

    protected InputStream inputStream;

    protected String keystorePass;


    protected KeyStore trustStore;

    protected String[] protocols;

    protected String[] cipherSuites;

    public SSLKeyStore(String id, String keystoreType, String filePath, String keystorePass) {
        this.id = id;
        this.keystoreType = keystoreType;
        this.filePath = filePath;
        this.keystorePass = keystorePass;
        init();
        loadTrustStore();
    }

    public String getId() {
        return id;
    }

    public String getKeystoreType() {
        return keystoreType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String[] getProtocols() {
        return protocols;
    }

    public void setProtocols(String[] protocols) {
        this.protocols = protocols;
    }

    public String[] getCipherSuites() {
        return cipherSuites;
    }

    public void setCipherSuites(String[] cipherSuites) {
        this.cipherSuites = cipherSuites;
    }

    public void init() {
        File file = new File(filePath.trim());
        if (!file.exists()) {
            throw new ForestRuntimeException(
                    "The file of SSL KeyStore \"" + id + "\" " + filePath + " cannot be found!");
        }
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ForestRuntimeException(
                    "An error occurred while reading he file of SSL KeyStore \"\" + id + \"\"", e);
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getKeystorePass() {
        return keystorePass;
    }

    public void setKeystorePass(String keystorePass) {
        this.keystorePass = keystorePass;
    }

    public void loadTrustStore() {
        if (inputStream != null) {
            try {
                trustStore = KeyStore.getInstance(keystoreType);
                String pass = this.keystorePass;
                if (pass == null) {
                    trustStore.load(inputStream, null);
                }
                else {
                    trustStore.load(inputStream, pass.trim().toCharArray());
                }
            } catch (KeyStoreException e) {
                throw new ForestRuntimeException(e);
            } catch (CertificateException e) {
                throw new ForestRuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new ForestRuntimeException(e);
            } catch (IOException e) {
                throw new ForestRuntimeException(e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public KeyStore getTrustStore() {
        return trustStore;
    }


}