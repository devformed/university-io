import { AuthProviderProps } from 'react-oidc-context';

export const oidcConfig: AuthProviderProps = {
  authority: 'http://localhost:8080/realms/lockermat',
  client_id: 'lockermat-gui',
  redirect_uri: 'http://localhost:3000',
  scope: 'openid profile email',
  automaticSilentRenew: true,
  loadUserInfo: true,
  onSigninCallback: () => {
    window.history.replaceState({}, document.title, window.location.pathname);
  }
};
