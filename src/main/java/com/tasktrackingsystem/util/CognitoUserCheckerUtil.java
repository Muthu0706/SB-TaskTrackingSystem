package com.tasktrackingsystem.util;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;

public class CognitoUserCheckerUtil {
    
    private final String userPoolId = "us-east-1_AbCdEfGhi"; 
    private final String region = "us-east-1"; 

    private AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
            .withRegion(region)
            .build();

    public boolean isUsernameExists(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        AdminGetUserRequest adminGetUserRequest = new AdminGetUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username);

        try {
            AdminGetUserResult result = cognitoClient.adminGetUser(adminGetUserRequest);
            return result != null; 
            } catch (UserNotFoundException e) {
            return false; 
            } catch (InvalidParameterException | NotAuthorizedException e) {
            System.err.println("Error occurred: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        CognitoUserCheckerUtil checker = new CognitoUserCheckerUtil();
        String usernameToCheck = "muthuraj"; 

        if (checker.isUsernameExists(usernameToCheck)) {
            System.out.println("Username " + usernameToCheck + " exists in Cognito User Pool.");
        } else {
            System.out.println("Username " + usernameToCheck + " does not exist.");
        }
    }
    
}