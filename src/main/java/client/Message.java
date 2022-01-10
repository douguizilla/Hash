package client;

import java.io.Serializable;

public class Message implements Serializable {
    private int content;
    private int keySize;
    private String key;
    private int valueSize;
    private String value;
    private int error;

    public Message(int content, int keySize, String key, int valueSize, String value, int error) {
        this.content = content;
        this.keySize = keySize;
        this.key = key;
        this.valueSize = valueSize;
        this.value = value;
        this.error = error;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValueSize() {
        return valueSize;
    }

    public void setValueSize(int valueSize) {
        this.valueSize = valueSize;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
