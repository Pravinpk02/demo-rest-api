const localtunnel = require('localtunnel');
const fs = require('fs');

(async () => {
  try {
    const tunnel = await localtunnel({ port: 80, subdomain: 'digitalpharma' });
    console.log('Tunnel started at:', tunnel.url);
    fs.writeFileSync('c:/Users/Pravinkumar K/tunnel_url.txt', tunnel.url);
  } catch (err) {
    console.error('Error:', err);
    fs.writeFileSync('c:/Users/Pravinkumar K/tunnel_url.txt', 'Error: ' + err.message);
  }
})();
