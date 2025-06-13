export const apiFetch = async (
  endpoint: string,
  method: string,
  requestData: any
): Promise<any> => {
  const options: RequestInit = {
    method,
    headers: {
      'Content-Type': 'application/json'
    }
  };

  // GET method does not have a body
  if (method !== 'GET' && requestData) {
    options.body = JSON.stringify(requestData);
  }

  const response = await fetch(`http://localhost:8080/${endpoint}`, options);

  if (!response.ok) {
    throw new Error(`API fetch failed: ${response.status}`);
  }

  // Handle special cases for empty responses
  const contentLength = response.headers.get('content-length');
  if (response.status === 204 || contentLength === '0') {
    return null;
  }

  return response.json();
};