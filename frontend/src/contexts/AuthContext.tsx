import { createContext, useContext, useState, useEffect, ReactNode, useCallback, useRef } from 'react';

interface AuthContextType {
  isAuthenticated: boolean;
  isLoading: boolean;
  accessToken: string | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
  refreshToken: () => Promise<string | null>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const KEYCLOAK_URL = 'http://localhost:8080/realms/lockermat/protocol/openid-connect/token';
const REFRESH_THRESHOLD = 60;

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const refreshTimeoutRef = useRef<NodeJS.Timeout | null>(null);

  const scheduleTokenRefresh = useCallback((expiresIn: number) => {
    if (refreshTimeoutRef.current) {
      clearTimeout(refreshTimeoutRef.current);
    }

    // Odśwież token przed wygaśnięciem
    const refreshTime = (expiresIn - REFRESH_THRESHOLD) * 1000;
    if (refreshTime > 0) {
      refreshTimeoutRef.current = setTimeout(() => {
        refreshTokenFn();
      }, refreshTime);
    }
  }, []);

  const refreshTokenFn = useCallback(async (): Promise<string | null> => {
    const storedRefreshToken = localStorage.getItem('refresh_token');
    if (!storedRefreshToken) {
      logout();
      return null;
    }

    try {
      const formData = new URLSearchParams();
      formData.append('client_id', 'lockermat-gui');
      formData.append('grant_type', 'refresh_token');
      formData.append('refresh_token', storedRefreshToken);

      const response = await fetch(KEYCLOAK_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString(),
      });

      if (!response.ok) {
        logout();
        return null;
      }

      const data = await response.json();
      const newAccessToken = data.access_token;
      const newRefreshToken = data.refresh_token;
      const expiresIn = data.expires_in;

      localStorage.setItem('access_token', newAccessToken);
      localStorage.setItem('refresh_token', newRefreshToken);
      localStorage.setItem('token_expires_at', String(Date.now() + expiresIn * 1000));
      
      setAccessToken(newAccessToken);
      scheduleTokenRefresh(expiresIn);

      return newAccessToken;
    } catch (error) {
      console.error('Token refresh failed:', error);
      logout();
      return null;
    }
  }, [scheduleTokenRefresh]);

  useEffect(() => {
    const token = localStorage.getItem('access_token');
    const expiresAt = localStorage.getItem('token_expires_at');
    
    if (token && expiresAt) {
      const now = Date.now();
      const expiresAtMs = parseInt(expiresAt, 10);
      
      if (expiresAtMs > now) {
        setAccessToken(token);
        setIsAuthenticated(true);
        
        // Zaplanuj odświeżenie
        const expiresInSeconds = Math.floor((expiresAtMs - now) / 1000);
        scheduleTokenRefresh(expiresInSeconds);
      } else {
        // Token wygasł, spróbuj odświeżyć
        refreshTokenFn();
      }
    }
    setIsLoading(false);

    return () => {
      if (refreshTimeoutRef.current) {
        clearTimeout(refreshTimeoutRef.current);
      }
    };
  }, [scheduleTokenRefresh, refreshTokenFn]);

  const login = async (username: string, password: string) => {
    const formData = new URLSearchParams();
    formData.append('client_id', 'lockermat-gui');
    formData.append('username', username);
    formData.append('password', password);
    formData.append('grant_type', 'password');
    formData.append('scope', 'openid');

    const response = await fetch(KEYCLOAK_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: formData.toString(),
    });

    if (!response.ok) {
      throw new Error('Nieprawidłowy login lub hasło');
    }

    const data = await response.json();
    const token = data.access_token;
    const refreshToken = data.refresh_token;
    const expiresIn = data.expires_in; // w sekundach
    
    localStorage.setItem('access_token', token);
    localStorage.setItem('refresh_token', refreshToken);
    localStorage.setItem('token_expires_at', String(Date.now() + expiresIn * 1000));
    
    setAccessToken(token);
    setIsAuthenticated(true);
    scheduleTokenRefresh(expiresIn);
  };

  const logout = useCallback(() => {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('token_expires_at');
    
    if (refreshTimeoutRef.current) {
      clearTimeout(refreshTimeoutRef.current);
    }
    
    setAccessToken(null);
    setIsAuthenticated(false);
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthenticated, isLoading, accessToken, login, logout, refreshToken: refreshTokenFn }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};
