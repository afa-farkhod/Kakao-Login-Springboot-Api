# Kakao-Login-Springboot-Api
Kakao Login Springboot Application

### [Kakao Login Rest API](https://developers.kakao.com/)

- REST API is a suitable architectural style to implement Kakao Login both in PC and on the mobile web. To implement Kakao Login using a REST API, you need to get an authorization code, and then get tokens with the obtained authorization code as follows.
  
![image](https://github.com/af4092/Kakao-Login-Springboot-Api/assets/24220136/e38289cb-8f84-4f5f-9f30-dd8f842a250e)

1. If a user clicks [Login with Kakao], the user is asked to log in on the Kakao login page provided by Kakao.

2. When the user logs in with Kakao Account information on the page, the Getting authorization code API is invoked. This API prompts the Consent screen to a user. If a web browser retains a session cookie that includes the Kakao Account information, the Consent screen is presented to the user without asking a login with Kakao Account.

3. Once the user approves permissions by clicking [Accept and Continue], the Kakao authorization server validates the user’s credentials and issues an authorization code. The user is redirected back to your app with the authorization code via redirect_uri.

4. Your app requests an access token and a refresh token with the issued authorization code by calling the Getting tokens.

5. The Kakao authorization server validates the request, issues an access token and a refresh token based on the authorization code, and provides authorization. See Token information to learn more about the access token and refresh token.
