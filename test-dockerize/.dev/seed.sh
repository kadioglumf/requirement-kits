
set -x
#sleep 1

getJWT() {
  local response=$(curl -s --location --request POST 'http://websocket-service-svc:8080/websocket/auth/login' \
    --header 'Content-Type: application/json' \
    --data "{
      \"email\":\"$1\",
      \"password\": \"$2\"
    }")

  local token=$(echo "$response" | grep -o '"token":"[^"]*' | sed 's/"token":"//')
  echo "$token"
}

JWT=$(getJWT "admin@mail.com" "admin")
echo "JWT Token: $JWT"