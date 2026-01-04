// Import refresh token function at the top (note: this creates circular dependency, better approach below)
let refreshTokenFunction: (() => Promise<string | null>) | null = null;

export const setRefreshTokenFunction = (fn: () => Promise<string | null>) => {
  refreshTokenFunction = fn;
};

export const apiFetch = async (
  endpoint: string,
  method: string,
  requestData: any,
  isRetry: boolean = false
): Promise<any> => {
  const token = localStorage.getItem('access_token');

  const options: RequestInit = {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` })
    }
  };

  // Add body for all methods except GET
  if (requestData !== null && requestData !== undefined && method !== 'GET') {
    options.body = JSON.stringify(requestData);
  }

  const response = await fetch(`http://localhost:8071/${endpoint}`, options);

  // Handle 401 - token expired
  if (response.status === 401 && !isRetry && refreshTokenFunction) {
    console.log('[apiFetch] 401 Unauthorized - attempting token refresh');
    const newToken = await refreshTokenFunction();
    
    if (newToken) {
      // Retry request with new token
      return apiFetch(endpoint, method, requestData, true);
    } else {
      // Refresh failed, redirect to login
      window.location.href = '/';
      throw new Error('Session expired, please login again');
    }
  }

  if (!response.ok) {
    const errorText = await response.text();
    console.error('API Error:', response.status, errorText);
    throw new Error(`API fetch failed: ${response.status} - ${errorText}`);
  }

  const contentLength = response.headers.get('content-length');
  if (response.status === 204 || contentLength === '0') {
    return null;
  }

  return response.json();
};