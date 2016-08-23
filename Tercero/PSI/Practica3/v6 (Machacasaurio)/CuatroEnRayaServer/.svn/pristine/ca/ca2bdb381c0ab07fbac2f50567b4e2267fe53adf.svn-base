/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author roberto
 */
public class ReturnError {

    int errorCode;
    int value;
    String errorMessage;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ReturnError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ReturnError() {
        this.errorCode = 0;
        this.errorMessage = "OK";
        this.value=0;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorDode) {
        this.errorCode = errorDode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}