export const apiFetch = async (
  endpoint: string,
  method: string,
  requestData: any
): Promise<any> => {
  const response = await fetch(`http://localhost:8080/${endpoint}`, {
    method,
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestData)
  });

  if (!response.ok) {
    throw new Error(`API fetch failed: ${response.status}`);
  }

  return response.json();
};