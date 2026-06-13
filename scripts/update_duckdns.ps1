$domain = "digitalpharma"
$token = "914b8e8a-ed7b-471a-aebe-1ebc4f796daf"
$url = "https://www.duckdns.org/update?domains=$domain&token=$token"

try {
    $response = Invoke-RestMethod -Uri $url -Method Get
    if ($response -eq "OK") {
        Write-Output "$(Get-Date): DuckDNS update successful."
    } else {
        Write-Warning "$(Get-Date): DuckDNS update failed. Response: $response"
    }
} catch {
    Write-Error "$(Get-Date): Error updating DuckDNS: $_"
}
